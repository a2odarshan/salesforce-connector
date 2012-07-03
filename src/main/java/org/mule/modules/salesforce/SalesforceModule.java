/**
 * Mule Salesforce Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.modules.salesforce;

import com.sforce.async.AsyncApiException;
import com.sforce.async.AsyncExceptionCode;
import com.sforce.async.BatchInfo;
import com.sforce.async.BatchRequest;
import com.sforce.async.BatchResult;
import com.sforce.async.JobInfo;
import com.sforce.async.OperationEnum;
import com.sforce.async.RestConnection;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.EmptyRecycleBinResult;
import com.sforce.soap.partner.GetDeletedResult;
import com.sforce.soap.partner.GetUpdatedResult;
import com.sforce.soap.partner.GetUserInfoResult;
import com.sforce.soap.partner.LeadConvert;
import com.sforce.soap.partner.LeadConvertResult;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.UpsertResult;
import com.sforce.soap.partner.fault.ApiFault;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.MessageHandler;
import com.sforce.ws.transport.SoapConnection;
import org.apache.log4j.Logger;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.InvalidateConnectionOn;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.Source;
import org.mule.api.annotations.SourceThreadingModel;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.api.callback.SourceCallback;
import org.mule.api.callback.StopSourceCallback;
import org.mule.api.store.ObjectStore;
import org.mule.api.store.ObjectStoreException;
import org.mule.api.store.ObjectStoreManager;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The Salesforce Connector will allow to connect to the Salesforce application. Almost every operation that can be
 * done via the Salesforce's API can be done thru this connector. This connector will also work if your Salesforce
 * objects are customized with additional fields or even you are working with custom objects.
 * <p/>
 * Integrating with Salesforce consists of web service calls utilizing XML request/response setup
 * over an HTTPS connection. The technical details of this connection such as request headers,
 * error handling, HTTPS connection, etc. are all abstracted from the user to make implementation
 * quick and easy.
 * <p/>
 * {@sample.config ../../../doc/mule-module-sfdc.xml.sample sfdc:config}
 *
 * @author MuleSoft, Inc.
 */
@org.mule.api.annotations.Connector(name = "sfdc", schemaVersion = "4.0", friendlyName = "Salesforce")
public class SalesforceModule {
    private static final Logger LOGGER = Logger.getLogger(SalesforceModule.class);

    /**
     * SalesForce SOAP endpoint
     */
    @Configurable
    @Optional
    @Default("https://login.salesforce.com/services/Soap/u/23.0")
    @Placement(group = "Salesforce endpoint")
    private URL url;

    /**
     * Proxy host
     */
    @Configurable
    @Optional
    @Placement(group = "Proxy settings")
    private String proxyHost;

    /**
     * Proxy port
     */
    @Configurable
    @Optional
    @Default("80")
    @Placement(group = "Proxy settings")
    private int proxyPort = -1;

    /**
     * Proxy username
     */
    @Configurable
    @Optional
    @Placement(group = "Proxy settings")
    private String proxyUsername;

    /**
     * Proxy password
     */
    @Configurable
    @Optional
    @Placement(group = "Proxy settings")
    @Password
    private String proxyPassword;

    /**
     * Object store manager to obtain a store to support {@link this#getUpdatedObjects}
     */
    @Inject
    private ObjectStoreManager objectStoreManager;

    /**
     * A ObjectStore instance to use in {@link this#getUpdatedObjects}
     */
    @Configurable
    @Optional
    private ObjectStore objectStore;

    private ObjectStoreHelper objectStoreHelper;

    /**
     * Bayeux client
     */
    private SalesforceBayeuxClient bc;

    /**
     * Partner connection
     */
    private PartnerConnection connection;

    /**
     * REST connection to the bulk API
     */
    private RestConnection restConnection;

    /**
     * Login result
     */
    private LoginResult loginResult;

    /**
     * Adds one or more new records to your organization's data.
     * <p/>
     * <p class="caution">
     * IMPORTANT: When you map your objects to the input of this message processor keep in mind that they need
     * to match the expected type of the object at Salesforce.
     * <p/>
     * Take the CloseDate of an Opportunity as an example, if you set that field to a string of value "2011-12-13"
     * it will be sent to Salesforce as a string and operation will be rejected on the basis that CloseDate is not
     * of the expected type.
     * <p/>
     * The proper way to actually map it is to generate a Java Date object, you can do so using Groovy expression
     * evaluator as <i>#[groovy:Date.parse("yyyy-MM-dd", "2011-12-13")]</i>.
     * </p>
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:create}
     *
     * @param objects An array of one or more sObjects objects.
     * @param type    Type of object to create
     * @return An array of {@link SaveResult} if async is false
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_create.htm">create()</a>
     * @since 4.0
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public List<SaveResult> create(@Placement(group = "Information") @FriendlyName("sObject Type") String type,
                                   @Placement(group = "sObject Field Mappings") @FriendlyName("sObjects") @Default("#[payload]") List<Map<String, Object>> objects) throws Exception {
        return Arrays.asList(connection.create(toSObjectList(type, objects)));
    }

    /**
     * Creates a Job in order to perform one or more batches through Bulk API Operations.
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:create-job}
     * 
     * @param operation The {@link OperationEnum} that will be executed by the job.
     * @param type The type of Salesforce object that the job will process.
     * @param externalIdFieldName Contains the name of the field on this object with the external ID field attribute
     *                            for custom objects or the idLookup field property for standard objects 
     *                            (only required for Upsert Operations).
     * @return A {@link JobInfo} that identifies the created Job. {@link http://www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_reference_jobinfo.htm}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_jobs_create.htm">createJob()</a>
     * @since 4.2.1
     */
    @Processor
    @InvalidateConnectionOn(exception = AsyncApiException.class)
    public JobInfo createJob(OperationEnum operation, String type, @Optional String externalIdFieldName) throws Exception {
        return createJobInfo(operation, type, externalIdFieldName);
    }
    
    /**
     * Closes an open Job given its ID.
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:close-job}
     * 
     * @param jobId The Job ID identifying the Job to be closed.
     * @return A {@link JobInfo} that identifies the closed Job. {@link http://www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_reference_jobinfo.htm}
     * @throws Exception
     * @api.doc <a href="www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_jobs_close.htm">closeJob()</a>
     * @since 4.2.1
     */
    @Processor
    @InvalidateConnectionOn(exception = AsyncApiException.class)
    public JobInfo closeJob(String jobId) throws Exception {
        return restConnection.closeJob(jobId);
    }
    
    /**
     * Creates a Batch using the given objects within the specified Job.
     * <p/>
     * This call uses the Bulk API. The operation will be done in asynchronous fashion.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:create-batch}
     * 
     * @param jobInfo The {@link JobInfo} in which the batch will be created.
     * @param objects A list of one or more sObjects objects. This parameter defaults to payload content.
     * @return A {@link BatchInfo} that identifies the batch job. {@link http://www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_reference_batchinfo.htm}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_batches_create.htm">createBatch()</a>
     * @since 4.2.1
     */
    @Processor
    @InvalidateConnectionOn(exception = ConnectionException.class)
    public BatchInfo createBatch(JobInfo jobInfo, @Optional @Default("#[payload]") List<Map<String, Object>> objects) throws Exception {
        return createBatchAndCompleteRequest(jobInfo, objects);
    }

    /**
     * Creates a Batch using the given query.
     * <p/>
     * This call uses the Bulk API. The operation will be done in asynchronous fashion.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:create-batch-for-query}
     *
     * @param jobInfo   The {@link JobInfo} in which the batch will be created.
     * @param query     The query to be executed.
     * @return A {@link BatchInfo} that identifies the batch job. {@link http://www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_reference_batchinfo.htm}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_batches_create.htm">createBatch()</a>
     * @since 4.2.2
     */
    @Processor
    @InvalidateConnectionOn(exception = ConnectionException.class)
    public BatchInfo createBatchForQuery(JobInfo jobInfo, @Optional @Default("#[payload]") String query) throws Exception {
        InputStream queryStream = new ByteArrayInputStream(query.getBytes());
        return createBatchForQuery(jobInfo, queryStream);
    }
    
    /**
     * Adds one or more new records to your organization's data.
     * <p/>
     * This call uses the Bulk API. The creation will be done in asynchronous fashion.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:create-bulk}
     *
     * @param objects An array of one or more sObjects objects.
     * @param type    Type of object to create
     * @return A {@link BatchInfo} that identifies the batch job. {@link http://www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_reference_batchinfo.htm}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_batches_create.htm">createBatch()</a>
     * @since 4.1
     */
    @Processor
    @InvalidateConnectionOn(exception = ConnectionException.class)
    public BatchInfo createBulk(@Placement(group = "Information") @FriendlyName("sObject Type") String type,
                                @Placement(group = "sObject Field Mappings") @FriendlyName("sObjects") @Default("#[payload]") List<Map<String, Object>> objects) throws Exception {

        return createBatchAndCompleteRequest(createJobInfo(OperationEnum.insert, type), objects);
    }

    private BatchInfo createBatchAndCompleteRequest(JobInfo jobInfo, List<Map<String, Object>> objects) throws ConnectionException {
        try {
            BatchRequest batchRequest = restConnection.createBatch(jobInfo);
            batchRequest.addSObjects(toAsyncSObjectList(objects));
            return batchRequest.completeRequest();
        } catch (AsyncApiException e) {
            if (e.getExceptionCode() == AsyncExceptionCode.InvalidSessionId) {
                throw new ConnectionException(e.getMessage(), e);
            }
        }

        return null;
    }

    private BatchInfo createBatchForQuery(JobInfo jobInfo, InputStream query) throws ConnectionException {
        try {
            return restConnection.createBatchFromStream(jobInfo, query);
        } catch (AsyncApiException e) {
            if (e.getExceptionCode() == AsyncExceptionCode.InvalidSessionId) {
                throw new ConnectionException(e.getMessage(), e);
            }
        }
        return null;
    }

    private JobInfo createJobInfo(OperationEnum op, String type) throws AsyncApiException {
        return createJobInfo(op, type, null);
    }

    private JobInfo createJobInfo(OperationEnum op, String type, String externalIdFieldName) throws AsyncApiException {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setOperation(op);
        jobInfo.setObject(type);
        if (externalIdFieldName != null) {
            jobInfo.setExternalIdFieldName(externalIdFieldName);
        }
        return restConnection.createJob(jobInfo);
    }

    /**
     * Adds one new records to your organization's data.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:create-single}
     * {@sample.java ../../../doc/mule-module-sfdc.java.sample sfdc:create-single}
     *
     * @param object SObject to create
     * @param type   Type of object to create
     * @return An array of {@link SaveResult}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_create.htm">create()</a>
     * @since 4.1
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public SaveResult createSingle(@Placement(group = "Information") @FriendlyName("sObject Type") String type,
                                   @Placement(group = "sObject Field Mappings") @FriendlyName("sObject") @Default("#[payload]") Map<String, Object> object) throws Exception {
        SaveResult[] saveResults = connection.create(new SObject[]{toSObject(type, object)});
        if (saveResults.length > 0) {
            return saveResults[0];
        }

        return null;
    }

    @ValidateConnection
    public boolean isConnected() {
        if (restConnection != null) {
            if (connection != null) {
                if (loginResult != null) {
                    if (loginResult.getSessionId() != null) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Returns the session id for the current connection
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:get-session-id}
     *
     * @return the session id for the current connection
     */
    @Processor
    @ConnectionIdentifier
    public String getSessionId() {
        if (connection != null) {
            if (loginResult != null) {
                return loginResult.getSessionId();
            }
        }

        return null;
    }


    /**
     * End the current session
     *
     * @throws Exception
     */
    @Disconnect
    public synchronized void destroySession() {
        if (bc != null) {
            if (bc.isConnected()) {
                bc.disconnect();
            }
        }

        if (connection != null && loginResult != null) {
            try {
                connection.logout();
                loginResult = null;
                connection = null;
            } catch (ConnectionException ce) {
                LOGGER.error(ce);
            }
        }
    }

    /**
     * Updates one or more existing records in your organization's data.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:update}
     *
     * @param objects An array of one or more sObjects objects.
     * @param type    Type of object to update
     * @return An array of {@link SaveResult}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_update.htm">update()</a>
     * @since 4.0
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public List<SaveResult> update(@Placement(group = "Information") @FriendlyName("sObject Type") String type,
                                   @Placement(group = "Salesforce sObjects list") @FriendlyName("sObjects") @Default("#[payload]") List<Map<String, Object>> objects) throws Exception {
        return Arrays.asList(connection.update(toSObjectList(type, objects)));
    }

    /**
     * Updates one or more existing records in your organization's data.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:update-single}
     *
     * @param object The object to be updated.
     * @param type    Type of object to update
     * @return A {@link SaveResult}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_update.htm">update()</a>
     * @since 4.0
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public SaveResult updateSingle(@Placement(group = "Information") @FriendlyName("sObject Type") String type,
                                   @Placement(group = "Salesforce Object") @FriendlyName("sObject") @Optional @Default("#[payload]") Map<String, Object> object) throws Exception {
        return connection.update(new SObject[] { toSObject(type, object) })[0];
    }

    /**
     * Updates one or more existing records in your organization's data.
     * <p/>
     * This call uses the Bulk API. The creation will be done in asynchronous fashion.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:update-bulk}
     *
     * @param objects An array of one or more sObjects objects.
     * @param type    Type of object to update
     * @return A {@link BatchInfo} that identifies the batch job. {@link http://www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_reference_batchinfo.htm}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_batches_create.htm">createBatch()</a>
     * @since 4.1
     */
    @Processor
    @InvalidateConnectionOn(exception = ConnectionException.class)
    public BatchInfo updateBulk(@Placement(group = "Information") @FriendlyName("sObject Type") String type,
                                @Placement(group = "Salesforce sObjects list") @FriendlyName("sObjects") @Default("#[payload]") List<Map<String, Object>> objects) throws Exception {
        return createBatchAndCompleteRequest(createJobInfo(OperationEnum.update, type), objects);
    }

    /**
     * <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_upsert.htm">Upserts</a>
     * an homogeneous list of objects: creates new records and updates existing records, using a custom field to determine the presence of existing records.
     * In most cases, prefer {@link #upsert(String, String, List)} over {@link #create(String, List)},
     * to avoid creating unwanted duplicate records.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:upsert}
     *
     * @param externalIdFieldName Contains the name of the field on this object with the external ID field attribute
     *                            for custom objects or the idLookup field property for standard objects.
     * @param type                the type of the given objects. The list of objects to upsert must be homogeneous
     * @param objects             the objects to upsert
     * @return a list of {@link UpsertResult}, one for each passed object
     * @throws Exception if a connection error occurs
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_upsert.htm">upsert()</a>
     * @since 4.0
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public List<UpsertResult> upsert(@Placement(group = "Information") String externalIdFieldName,
                                     @Placement(group = "Information") @FriendlyName("sObject Type") String type,
                                     @Placement(group = "Salesforce sObjects list") @FriendlyName("sObjects") @Default("#[payload]") List<Map<String, Object>> objects) throws Exception {
        return Arrays.asList(connection.upsert(externalIdFieldName, toSObjectList(type, objects)));
    }

    /**
     * <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_upsert.htm">Upserts</a>
     * an homogeneous list of objects: creates new records and updates existing records, using a custom field to determine the presence of existing records.
     * In most cases, prefer {@link #upsert(String, String, List)} over {@link #create(String, List)},
     * to avoid creating unwanted duplicate records.
     * <p/>
     * This call uses the Bulk API. The creation will be done in asynchronous fashion.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:upsert-bulk}
     *
     * @param externalIdFieldName Contains the name of the field on this object with the external ID field attribute
     *                            for custom objects or the idLookup field property for standard objects.
     * @param type                the type of the given objects. The list of objects to upsert must be homogeneous
     * @param objects             the objects to upsert
     * @return A {@link BatchInfo} that identifies the batch job. {@link http://www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_reference_batchinfo.htm}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_batches_create.htm">createBatch()</a>
     * @since 4.1
     */
    @Processor
    @InvalidateConnectionOn(exception = ConnectionException.class)
    public BatchInfo upsertBulk(@Placement(group = "Information", order = 1) @FriendlyName("sObject Type") String type,
                                @Placement(group = "Information", order = 2) String externalIdFieldName,
                                @Placement(group = "Salesforce sObjects list") @FriendlyName("sObjects") @Default("#[payload]") List<Map<String, Object>> objects) throws Exception {
        return createBatchAndCompleteRequest(createJobInfo(OperationEnum.upsert, type, externalIdFieldName), objects);
    }

    /**
     * Access latest {@link BatchInfo} of a submitted {@link BatchInfo}. Allows to track execution status.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:batch-info}
     *
     * @param batchInfo the {@link BatchInfo} being monitored
     * @return Latest {@link BatchInfo} representing status of the batch job result.
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_batches_get_info.htm">getBatchInfo()</a>
     * @since 4.1
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public BatchInfo batchInfo(BatchInfo batchInfo) throws Exception {
        return restConnection.getBatchInfo(batchInfo.getJobId(), batchInfo.getId());
    }

    /**
     * Access {@link BatchResult} of a submitted {@link BatchInfo}.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:batch-result}
     *
     * @param batchInfo the {@link BatchInfo} being monitored
     * @return {@link BatchResult} representing result of the batch job result.
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_batches_get_results.htm">getBatchResult()</a>
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_batches_interpret_status.htm">BatchInfo status</a>
     * @since 4.1
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public BatchResult batchResult(BatchInfo batchInfo) throws Exception {
        return restConnection.getBatchResult(batchInfo.getJobId(), batchInfo.getId());
    }

    /**
     * Retrieves a list of available objects for your organization's data.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:describe-global}
     *
     * @return A {@link DescribeGlobalResult}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_describeglobal.htm">describeGlobal()</a>
     * @since 4.0
     */
    @Processor
    public DescribeGlobalResult describeGlobal() throws Exception {
        return connection.describeGlobal();
    }

    /**
     * Retrieves one or more records based on the specified IDs.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:retrieve}
     *
     * @param type   Object type. The sp ecified value must be a valid object for your organization.
     * @param ids    The ids of the objects to retrieve
     * @param fields The fields to return for the matching objects
     * @return An array of {@link SObject}s
     * @throws Exception
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public List<Map<String, Object>> retrieve(@Placement(group = "Information", order = 1) @FriendlyName("sObject Type") String type,
                                              @Placement(group = "Ids to Retrieve") List<String> ids,
                                              @Placement(group = "Fields to Retrieve") List<String> fields) throws Exception {
        String fiedsCommaDelimited = StringUtils.collectionToCommaDelimitedString(fields);
        SObject[] sObjects = connection.retrieve(fiedsCommaDelimited, type, ids.toArray(new String[ids.size()]));
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        if (sObjects != null) {
            for (SObject sObject : sObjects) {
                result.add(sObject.toMap());
            }
        }
        return result;
    }

    /**
     * Executes a query against the specified object and returns data that matches the specified criteria.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:query}
     *
     * @param query Query string that specifies the object to query, the fields to return, and any conditions for
     *              including a specific object in the query. For more information, see Salesforce Object Query
     *              Language (SOQL).
     * @return An array of {@link SObject}s
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_query.htm">query()</a>
     * @since 4.0
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public List<Map<String, Object>> query(@Placement(group = "Query") String query) throws Exception {
        QueryResult queryResult = connection.query(query);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        while (queryResult != null) {
            for (SObject object : queryResult.getRecords()) {
                result.add(object.toMap());
            }
            if (queryResult.isDone()) {
                break;
            }
            queryResult = connection.queryMore(queryResult.getQueryLocator());
        }

        return result;
    }

    /**
     * Retrieves data from specified objects, whether or not they have been deleted.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:query}
     *
     * @param query Query string that specifies the object to query, the fields to return, and any conditions for including a specific object in the query. For more information, see Salesforce Object Query Language (SOQL).
     * @return An array of {@link SObject}s
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_query.htm">query()</a>
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public List<Map<String, Object>> queryAll(@Placement(group = "Query") String query) throws Exception {
        QueryResult queryResult = connection.queryAll(query);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        while (queryResult != null) {
            for (SObject object : queryResult.getRecords()) {
                result.add(object.toMap());
            }
            if (queryResult.isDone()) {
                break;
            }
            queryResult = connection.queryMore(queryResult.getQueryLocator());
        }

        return result;
    }

    /**
     * Executes a query against the specified object and returns the first record that matches the specified criteria.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:query-single}
     *
     * @param query Query string that specifies the object to query, the fields to return, and any conditions for
     *              including a specific object in the query. For more information, see Salesforce Object Query
     *              Language (SOQL).
     * @return A single {@link SObject}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_query.htm">query()</a>
     * @since 4.1
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public Map<String, Object> querySingle(@Placement(group = "Query") String query) throws Exception {
        SObject[] result = connection.query(query).getRecords();
        if (result.length > 0) {
            return result[0].toMap();
        }

        return null;
    }

    /**
     * Converts a Lead into an Account, Contact, or (optionally) an Opportunity.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:convert-lead}
     *
     * @param leadId                 ID of the Lead to convert. Required. For information on IDs, see ID Field Type.
     * @param contactId              ID of the Contact into which the lead will be merged (this contact must be
     *                               associated with the specified accountId, and an accountId must be specified).
     *                               Required only when updating an existing contact.IMPORTANT if you are converting
     *                               a lead into a person account, do not specify the contactId or an error will result.
     *                               Specify only the accountId of the person account. If no contactID is specified,
     *                               then the API creates a new contact that is implicitly associated with the Account.
     *                               To create a new contact, the client application must be logged in with sufficient
     *                               access rights. To merge a lead into an existing contact, the client application
     *                               must be logged in with read/write access to the specified contact. The contact
     *                               name and other existing data are not overwritten (unless overwriteLeadSource is
     *                               set to true, in which case only the LeadSource field is overwritten).
     *                               For information on IDs, see ID Field Type.
     * @param accountId              ID of the Account into which the lead will be merged. Required
     *                               only when updating an existing account, including person accounts.
     *                               If no accountID is specified, then the API creates a new account. To
     *                               create a new account, the client application must be logged in with
     *                               sufficient access rights. To merge a lead into an existing account,
     *                               the client application must be logged in with read/write access to the
     *                               specified account. The account name and other existing data are not overwritten.
     *                               For information on IDs, see ID Field Type.
     * @param overWriteLeadSource    Specifies whether to overwrite the LeadSource field on the target Contact object
     *                               with the contents of the LeadSource field in the source Lead object (true), or
     *                               not (false, the default). To set this field to true, the client application
     *                               must specify a contactId for the target contact.
     * @param doNotCreateOpportunity Specifies whether to create an Opportunity during lead conversion (false, the
     *                               default) or not (true). Set this flag to true only if you do not want to create
     *                               an opportunity from the lead. An opportunity is created by default.
     * @param opportunityName        Name of the opportunity to create. If no name is specified, then this value
     *                               defaults to the company name of the lead. The maximum length of this field is
     *                               80 characters. If doNotCreateOpportunity argument is true, then no Opportunity
     *                               is created and this field must be left blank; otherwise, an error is returned.
     * @param convertedStatus        Valid LeadStatus value for a converted lead. Required.
     *                               To obtain the list of possible values, the client application queries the
     *                               LeadStatus object, as in:
     *                               Select Id, MasterLabel from LeadStatus where IsConverted=true
     * @param sendEmailToOwner       Specifies whether to send a notification email to the owner specified in the
     *                               ownerId (true) or not (false, the default).
     * @return A list of {@link LeadConvertResult}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_convertlead.htm">convertLead()</a>
     * @since 4.0
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public LeadConvertResult convertLead(String leadId, String contactId,
                                         @Optional String accountId,
                                         @Optional @Default("false") Boolean overWriteLeadSource,
                                         @Optional @Default("false") Boolean doNotCreateOpportunity,
                                         @Optional String opportunityName,
                                         String convertedStatus,
                                         @Optional @Default("false") Boolean sendEmailToOwner)
            throws Exception {

        LeadConvert leadConvert = new LeadConvert();
        leadConvert.setLeadId(leadId);
        leadConvert.setContactId(contactId);
        leadConvert.setAccountId(accountId);
        leadConvert.setOverwriteLeadSource(overWriteLeadSource);
        leadConvert.setDoNotCreateOpportunity(doNotCreateOpportunity);
        if (opportunityName != null) {
            leadConvert.setOpportunityName(opportunityName);
        }
        leadConvert.setConvertedStatus(convertedStatus);
        leadConvert.setSendNotificationEmail(sendEmailToOwner);
        LeadConvert[] list = new LeadConvert[1];
        list[0] = leadConvert;

        return connection.convertLead(list)[0];
    }

    /**
     * The recycle bin lets you view and restore recently deleted records for 30 days before they are
     * permanently deleted. Your organization can have up to 5000 records per license in the Recycle Bin at any
     * one time. For example, if your organization has five user licenses, 25,000 records can be stored in the
     * Recycle Bin. If your organization reaches its Recycle Bin limit, Salesforce.com automatically removes
     * the oldest records, as long as they have been in the recycle bin for at least two hours.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:empty-recycle-bin}
     *
     * @param ids Array of one or more IDs associated with the records to delete from the recycle bin.
     *            Maximum number of records is 200.
     * @return A list of {@link EmptyRecycleBinResult}
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_emptyrecyclebin.htm">emptyRecycleBin()</a>
     * @since 4.0
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public List<EmptyRecycleBinResult> emptyRecycleBin(@Placement(group = "Ids to Delete") List<String> ids) throws Exception {
        return Arrays.asList(connection.emptyRecycleBin(ids.toArray(new String[]{})));
    }


    /**
     * Deletes one or more records from your organization's data.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:delete}
     *
     * @param ids Array of one or more IDs associated with the objects to delete.
     * @return An array of {@link DeleteResult}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_delete.htm">delete()</a>
     * @since 4.0
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public List<DeleteResult> delete(@Placement(group = "Ids to Delete") List<String> ids) throws Exception {
        return Arrays.asList(connection.delete(ids.toArray(new String[]{})));
    }

    /**
     * Deletes one or more records from your organization's data. 
     * The deleted records are not stored in the Recycle Bin. 
     * Instead, they become immediately eligible for deletion.
     * <p/>
     * This call uses the Bulk API. The creation will be done in asynchronous fashion.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:hard-delete-bulk}
     *
     * @param objects An array of one or more sObjects objects.
     * @param type    Type of object to update
     * @return A {@link BatchInfo} that identifies the batch job. {@link http://www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_reference_batchinfo.htm}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api_asynch/Content/asynch_api_batches_create.htm">createBatch()</a>
     * @since 4.3
     */
    @Processor
    @InvalidateConnectionOn(exception = ConnectionException.class)
    public BatchInfo hardDeleteBulk(@Placement(group = "Information") @FriendlyName("sObject Type") String type,
    								@Placement(group = "Salesforce sObjects list") @FriendlyName("sObjects") @Default("#[payload]") List<Map<String, Object>> objects) throws Exception {
    	return createBatchAndCompleteRequest(createJobInfo(OperationEnum.hardDelete, type), objects);
    }
    
    /**
     * Retrieves the list of individual records that have been created/updated within the given timespan for the specified object.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:get-updated-range}
     *
     * @param type      Object type. The specified value must be a valid object for your organization.
     * @param startTime Starting date/time (Coordinated Universal Time (UTC)not local timezone) of the timespan for
     *                  which to retrieve the data. The API ignores the seconds portion of the specified dateTime value '
     *                  (for example, 12:30:15 is interpreted as 12:30:00 UTC).
     * @param endTime   Ending date/time (Coordinated Universal Time (UTC)not local timezone) of the timespan for
     *                  which to retrieve the data. The API ignores the seconds portion of the specified dateTime value
     *                  (for example, 12:35:15 is interpreted as 12:35:00 UTC). If it is not provided, the current
     *                  server time will be used.
     * @return {@link GetUpdatedResult}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_getupdatedrange.htm">getUpdatedRange()</a>
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public GetUpdatedResult getUpdatedRange(@Placement(group = "Information") @FriendlyName("sObject Type") String type,
                                            @Placement(group = "Information") @FriendlyName("Start Time Reference") Calendar startTime,
                                            @Placement(group = "Information") @FriendlyName("End Time Reference") @Optional Calendar endTime) throws Exception {
        if (endTime == null) {
            Calendar serverTime = connection.getServerTimestamp().getTimestamp();
            endTime = (Calendar) serverTime.clone();
        }
        if (endTime.getTimeInMillis() - startTime.getTimeInMillis() < 60000) {
            endTime.add(Calendar.MINUTE, 1);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Getting updated " + type + " objects between " + startTime.getTime() + " and " + endTime.getTime());
        }
        return connection.getUpdated(type, startTime, endTime);
    }

    /**
     * Retrieves the list of individual records that have been deleted within the given timespan for the specified object.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:get-deleted-range}
     * {@sample.java ../../../doc/mule-module-sfdc.java.sample sfdc:get-deleted-range}
     *
     * @param type      Object type. The specified value must be a valid object for your organization.
     * @param startTime Starting date/time (Coordinated Universal Time (UTC)not local timezone) of the timespan for
     *                  which to retrieve the data. The API ignores the seconds portion of the specified dateTime value '
     *                  (for example, 12:30:15 is interpreted as 12:30:00 UTC).
     * @param endTime   Ending date/time (Coordinated Universal Time (UTC)not local timezone) of the timespan for
     *                  which to retrieve the data. The API ignores the seconds portion of the specified dateTime value
     *                  (for example, 12:35:15 is interpreted as 12:35:00 UTC). If not specific, the current server
     *                  time will be used.
     * @return {@link GetDeletedResult}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_getdeletedrange.htm">getDeletedRange()</a>
     * @since 4.0
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public GetDeletedResult getDeletedRange(@Placement(group = "Information") @FriendlyName("sObject Type") String type,
                                            @Placement(group = "Information") @FriendlyName("Start Time Reference") Calendar startTime,
                                            @Placement(group = "Information") @FriendlyName("End Time Reference") @Optional Calendar endTime) throws Exception {
        if (endTime == null) {
            Calendar serverTime = connection.getServerTimestamp().getTimestamp();
            endTime = (Calendar) serverTime.clone();
            if (endTime.getTimeInMillis() - startTime.getTimeInMillis() < 60000) {
                endTime.add(Calendar.MINUTE, 1);
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Getting deleted " + type + " objects between " + startTime.getTime() + " and " + endTime.getTime());
        }
        return connection.getDeleted(type, startTime, endTime);
    }

    /**
     * Describes metadata (field list and object properties) for the specified object.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:describe-sobject}
     *
     * @param type Object. The specified value must be a valid object for your organization. For a complete list
     *             of objects, see Standard Objects
     * @return {@link DescribeSObjectResult}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_describesobject.htm">describeSObject()</a>
     * @since 4.0
     */
    @Processor(name = "describe-sobject", friendlyName = "Describe sObject")
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public DescribeSObjectResult describeSObject(@Placement(group = "Information") @FriendlyName("sObject Type") String type) throws Exception {
        return connection.describeSObject(type);
    }

    /**
     * Retrieves the list of individual records that have been deleted between the range of now to the duration before now.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:get-deleted}
     *
     * @param type     Object type. The specified value must be a valid object for your organization.
     * @param duration The amount of time in minutes before now for which to return records from.
     * @return {@link GetDeletedResult}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_getdeleted.htm">getDeleted()</a>
     * @since 4.2
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public GetDeletedResult getDeleted(@Placement(group = "Information") @FriendlyName("sObject Type") String type,
                                       @Placement(group = "Information") int duration) throws Exception {
        Calendar serverTime = connection.getServerTimestamp().getTimestamp();
        Calendar startTime = (Calendar) serverTime.clone();
        Calendar endTime = (Calendar) serverTime.clone();

        endTime.add(Calendar.MINUTE, duration);
        return getDeletedRange(type, startTime, endTime);
    }

    /**
     * Retrieves the list of individual records that have been updated between the range of now to the duration before now.
     * <p/>
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:get-updated}
     *
     * @param type     Object type. The specified value must be a valid object for your organization.
     * @param duration The amount of time in minutes before now for which to return records from.
     * @return {@link GetDeletedResult}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_getupdated.htm">getUpdated()</a>
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public GetUpdatedResult getUpdated(@Placement(group = "Information") @FriendlyName("sObject Type") String type,
                                       @Placement(group = "Information") int duration) throws Exception {
        Calendar serverTime = connection.getServerTimestamp().getTimestamp();
        Calendar startTime = (Calendar) serverTime.clone();
        Calendar endTime = (Calendar) serverTime.clone();

        endTime.add(Calendar.MINUTE, duration);
        return getUpdatedRange(type, startTime, endTime);
    }

    /**
     * Retrieves the list of records that have been updated between the last time this method was called and now. This
     * method will save the timestamp of the latest date covered by Salesforce represented by {@link GetUpdatedResult#latestDateCovered}.
     * IMPORTANT: In order to use this method in a reliable way user must ensure that right after this method returns the result is
     * stored in a persistent way since the timestamp of the latest . In order to reset the latest update time
     * use {@link org.mule.modules.salesforce.SalesforceModule#resetUpdatedObjectsTimestamp()}
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:get-updated-objects}
     *
     * @param type              Object type. The specified value must be a valid object for your organization.
     * @param initialTimeWindow Time window (in minutes) to use the first time this method is called
     * @param fields            The fields to retrieve for the updated objects
     * @return {@link GetDeletedResult}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_getupdated.htm">getUpdated()</a>
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public List<Map<String, Object>> getUpdatedObjects(@Placement(group = "Information") @FriendlyName("sObject Type") String type,
                                                       @Placement(group = "Information") int initialTimeWindow,
                                                       @Placement(group = "Fields") List<String> fields) throws Exception {

        Calendar now = (Calendar) connection.getServerTimestamp().getTimestamp().clone();
        boolean initialTimeWindowUsed = false;
        ObjectStoreHelper objectStoreHelper = getObjectStoreHelper(connection.getConfig().getUsername());
        Calendar startTime = objectStoreHelper.getTimestamp();
        if (startTime == null) {
            startTime = (Calendar) now.clone();
            startTime.add(Calendar.MINUTE, -1 * initialTimeWindow);
            initialTimeWindowUsed = true;
        }

        GetUpdatedResult getUpdatedResult = getUpdatedRange(type, startTime, now);

        if (getUpdatedResult.getLatestDateCovered().equals(startTime)) {
            if (!initialTimeWindowUsed && getUpdatedResult.getIds().length > 0) {
                LOGGER.debug("Ignoring duplicated results from getUpdated() call");
                return Collections.emptyList();
            }
        }

        List<Map<String, Object>> updatedObjects = retrieve(type, Arrays.asList(getUpdatedResult.getIds()), fields);
        objectStoreHelper.updateTimestamp(getUpdatedResult);
        return updatedObjects;
    }

    /**
     * Resets the timestamp of the last updated object. After resetting this, a call to {@link this#getUpdatedObjects(String, int)} will
     * use the initialTimeWindow to get the updated objects. If no objectStore has been explicitly specified and {@link this#getUpdatedObjects(String, int)}
     * has not been called then calling this method has no effect.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:reset-updated-objects-timestamp}
     *
     * @throws ObjectStoreException
     */
    @Processor
    public void resetUpdatedObjectsTimestamp() throws ObjectStoreException {
        if (objectStore == null) {
            LOGGER.warn("Trying to reset updated objects timestamp but no object store has been set, was getUpdatedObjects ever executed?");
            return;
        }
        ObjectStoreHelper objectStoreHelper = getObjectStoreHelper(connection.getConfig().getUsername());
        objectStoreHelper.resetTimestamps();
    }

    /**
     * Creates a topic which represents a query that is the basis for notifying
     * listeners of changes to records in an organization.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:publish-topic}
     *
     * @param name        Descriptive name of the push topic, such as MyNewCases or TeamUpdatedContacts. The
     *                    maximum length is 25 characters. This value identifies the channel.
     * @param description Description of what kinds of records are returned by the query. Limit: 400 characters
     * @param query       The SOQL query statement that determines which records' changes trigger events to be sent to
     *                    the channel. Maximum length: 1200 characters
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/pushtopic.htm">Push Topic</a>
     * @since 4.0
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public void publishTopic(@Placement(group = "Information") String name,
                             @Placement(group = "Information") String query,
                             @Placement(group = "Information") @Optional String description) throws Exception {
        QueryResult result = connection.query("SELECT Id FROM PushTopic WHERE Name = '" + name + "'");
        if (result.getSize() == 0) {
            SObject pushTopic = new SObject();
            pushTopic.setType("PushTopic");
            pushTopic.setField("ApiVersion", "23.0");
            if (description != null) {
                pushTopic.setField("Description", description);
            }

            pushTopic.setField("Name", name);
            pushTopic.setField("Query", query);

            SaveResult[] saveResults = connection.create(new SObject[]{pushTopic});
            if (!saveResults[0].isSuccess()) {
                throw new SalesforceException(saveResults[0].getErrors()[0].getStatusCode(), saveResults[0].getErrors()[0].getMessage());
            }
        } else {
            SObject pushTopic = result.getRecords()[0];
            if (description != null) {
                pushTopic.setField("Description", description);
            }

            pushTopic.setField("Query", query);

            SaveResult[] saveResults = connection.update(new SObject[]{pushTopic});
            if (!saveResults[0].isSuccess()) {
                throw new SalesforceException(saveResults[0].getErrors()[0].getStatusCode(), saveResults[0].getErrors()[0].getMessage());
            }
        }
    }

    /**
     * Retrieves personal information for the user associated with the current session.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:get-user-info}
     *
     * @return {@link GetUserInfoResult}
     * @throws Exception
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api/Content/sforce_api_calls_getuserinfo.htm">getUserInfo()</a>
     * @since 4.0
     */
    @Processor
    @InvalidateConnectionOn(exception = SoapConnection.SessionTimedOutException.class)
    public GetUserInfoResult getUserInfo() throws Exception {
        return connection.getUserInfo();
    }

    /**
     * Subscribe to a topic.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-sfdc.xml.sample sfdc:subscribe-topic}
     *
     * @param topic    The name of the topic to subscribe to
     * @param callback The callback to be called when a message is received
     * @return {@link StopSourceCallBack}
     * @api.doc <a href="http://www.salesforce.com/us/developer/docs/api_streaming/index_Left.htm">Streaming API</a>
     * @since 4.0
     */
    @Source(primaryNodeOnly = true, threadingModel = SourceThreadingModel.NONE)
    public StopSourceCallback subscribeTopic(final String topic, final SourceCallback callback) {
        getBayeuxClient().subscribe("/topic" + topic, new SalesforceBayeuxMessageListener(callback));

        return new StopSourceCallback() {
            @Override
            public void stop() throws Exception {
                getBayeuxClient().unsubscribe("/topic" + topic);
            }
        };
    }

    /**
     * Creates a new Salesforce session
     *
     * @param username      Username used to initialize the session
     * @param password      Password used to authenticate the user
     * @param securityToken User's security token
     * @throws ConnectionException if a problem occurred while trying to create the session
     */
    @Connect
    public synchronized void connect(@ConnectionKey String username, @Password String password, String securityToken)
            throws org.mule.api.ConnectionException {

        ConnectorConfig connectorConfig = createConnectorConfig(url, username, password + securityToken, proxyHost, proxyPort, proxyUsername, proxyPassword);
        connectorConfig.addMessageHandler(new MessageHandler() {
            @Override
            public void handleRequest(URL endpoint, byte[] request) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Sending request to " + endpoint.toString());
                    LOGGER.debug(new String(request));
                }
            }

            @Override
            public void handleResponse(URL endpoint, byte[] response) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Receiving response from " + endpoint.toString());
                    LOGGER.debug(new String(response));
                }
            }
        });

        try {
            connection = Connector.newConnection(connectorConfig);
        } catch (ConnectionException e) {
            throw new org.mule.api.ConnectionException(ConnectionExceptionCode.UNKNOWN, null, e.getMessage(), e);
        }

        reconnect();

        try {
            String restEndpoint = "https://" + (new URL(connectorConfig.getServiceEndpoint())).getHost() + "/services/async/23.0";
            connectorConfig.setRestEndpoint(restEndpoint);
            restConnection = new RestConnection(connectorConfig);
        } catch (AsyncApiException e) {
            throw new org.mule.api.ConnectionException(ConnectionExceptionCode.UNKNOWN, e.getExceptionCode().toString(), e.getMessage(), e);
        } catch (MalformedURLException e) {
            throw new org.mule.api.ConnectionException(ConnectionExceptionCode.UNKNOWN_HOST, null, e.getMessage(), e);
        }
    }

    public void reconnect() throws org.mule.api.ConnectionException {
        try {
            LOGGER.debug("Creating a Salesforce session using " + connection.getConfig().getUsername());
            loginResult = connection.login(connection.getConfig().getUsername(), connection.getConfig().getPassword());

            if (loginResult.isPasswordExpired()) {
                try {
                    connection.logout();
                } catch (ConnectionException e) {
                    LOGGER.error(e.getMessage(), e);
                }
                String username = connection.getConfig().getUsername();
                connection = null;
                throw new org.mule.api.ConnectionException(ConnectionExceptionCode.CREDENTIALS_EXPIRED, null, "The password for the user " + username + " has expired");
            }

            LOGGER.debug("Session established successfully with ID " + loginResult.getSessionId() + " at instance " + loginResult.getServerUrl());
            connection.getSessionHeader().setSessionId(loginResult.getSessionId());
            connection.getConfig().setServiceEndpoint(loginResult.getServerUrl());
            connection.getConfig().setSessionId(loginResult.getSessionId());
        } catch (ConnectionException e) {
            if (e instanceof ApiFault) {
                throw new org.mule.api.ConnectionException(ConnectionExceptionCode.UNKNOWN, ((ApiFault) e).getExceptionCode().name(), ((ApiFault) e).getExceptionMessage(), e);
            } else {
                throw new org.mule.api.ConnectionException(ConnectionExceptionCode.UNKNOWN, null, e.getMessage(), e);
            }
        }
    }

    protected SObject[] toSObjectList(String type, List<Map<String, Object>> objects) {
        SObject[] sobjects = new SObject[objects.size()];
        int s = 0;
        for (Map<String, Object> map : objects) {
            sobjects[s] = toSObject(type, map);
            s++;
        }
        return sobjects;
    }

    private SObject toSObject(String type, Map<String, Object> map) {
        SObject sObject = new SObject();
        for (String key : map.keySet()) {
            sObject.setType(type);
            sObject.setField(key, map.get(key));
        }
        return sObject;
    }

    protected com.sforce.async.SObject[] toAsyncSObjectList(List<Map<String, Object>> objects) {
        com.sforce.async.SObject[] sobjects = new com.sforce.async.SObject[objects.size()];
        int s = 0;
        for (Map<String, Object> map : objects) {
            sobjects[s] = toAsyncSObject(map);
            s++;
        }
        return sobjects;
    }

    private com.sforce.async.SObject toAsyncSObject(Map<String, Object> map) {
        com.sforce.async.SObject sObject = new com.sforce.async.SObject();
        for (String key : map.keySet()) {
            if (map.get(key) != null) {
                sObject.setField(key, map.get(key).toString());
            } else {
                sObject.setField(key, null);
            }
        }
        return sObject;
    }

    /**
     * Retrieve host of proxy
     *
     * @return The host of the configured proxy
     */
    public String getProxyHost() {
        return proxyHost;
    }

    /**
     * Set proxy host
     *
     * @param proxyHost Proxy host to set
     */
    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    /**
     * Retrieve proxy port
     *
     * @return Configured proxy port
     */
    public int getProxyPort() {
        return proxyPort;
    }

    /**
     * Set proxy port
     *
     * @param proxyPort Proxy port to set
     */
    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    /**
     * Retrieve proxy username
     *
     * @return Configured proxy username
     */
    public String getProxyUsername() {
        return proxyUsername;
    }

    /**
     * Set proxy username
     *
     * @param proxyUsername Proxy username to set
     */
    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    /**
     * Retrieve proxy password
     *
     * @return Proxy password
     */
    public String getProxyPassword() {
        return proxyPassword;
    }

    /**
     * Set proxy password
     *
     * @param proxyPassword Proxy password to set
     */
    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public URL getUrl() {
        return url;
    }

    /**
     * Set Salesforce endpoint.
     *
     * @param url Web service endpoint
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * Create connector config
     *
     * @param endpoint      Salesforce endpoint
     * @param username      Username to use for authentication
     * @param password      Password to use for authentication
     * @param proxyHost
     * @param proxyPort
     * @param proxyUsername
     * @param proxyPassword
     * @return
     */
    protected ConnectorConfig createConnectorConfig(URL endpoint, String username, String password, String proxyHost, int proxyPort, String proxyUsername, String proxyPassword) {
        ConnectorConfig config = new ConnectorConfig();
        config.setUsername(username);
        config.setPassword(password);

        config.setAuthEndpoint(endpoint.toString());
        config.setServiceEndpoint(endpoint.toString());

        config.setManualLogin(true);

        config.setCompression(false);

        if (proxyHost != null) {
            config.setProxy(proxyHost, proxyPort);
            if (proxyUsername != null) {
                config.setProxyUsername(proxyUsername);
            }
            if (proxyPassword != null) {
                config.setProxyPassword(proxyPassword);
            }
        }

        return config;
    }

    public PartnerConnection getConnection() {
        return connection;
    }

    public LoginResult getLoginResult() {
        return loginResult;
    }

    public SalesforceBayeuxClient getBayeuxClient() {
        try {
            if (bc == null) {
                bc = new SalesforceBayeuxClient(this);

                if (!bc.isHandshook()) {
                    bc.handshake();
                }
            }
        } catch (MalformedURLException e) {
            LOGGER.error(e.getMessage());
        }

        return bc;
    }

    public void setObjectStoreManager(ObjectStoreManager objectStoreManager) {
        this.objectStoreManager = objectStoreManager;
    }

    public void setObjectStore(ObjectStore objectStore) {
        this.objectStore = objectStore;
    }

    protected void setConnection(PartnerConnection connection) {
        this.connection = connection;
    }

    protected void setLoginResult(LoginResult loginResult) {
        this.loginResult = loginResult;
    }

    protected void setRestConnection(RestConnection restConnection) {
        this.restConnection = restConnection;
    }

    protected void setBayeuxClient(SalesforceBayeuxClient bc) {
        this.bc = bc;
    }

    protected void setObjectStoreHelper(ObjectStoreHelper objectStoreHelper) {
        this.objectStoreHelper = objectStoreHelper;
    }

    private synchronized ObjectStoreHelper getObjectStoreHelper(String username) {
        if (objectStoreHelper == null) {
            if (objectStore == null) {
                objectStore = objectStoreManager.getObjectStore(username, true);
                if (objectStore == null) {
                    throw new IllegalStateException("Could not obtain a object store");
                }
            }
            objectStoreHelper = new ObjectStoreHelper(username, objectStore);
        }
        return objectStoreHelper;
    }
}
