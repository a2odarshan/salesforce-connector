
package org.mule.modules.salesforce.config;

import javax.annotation.Generated;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/sfdc</code>.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-13T03:33:54-03:00", comments = "Build connectorMetaDataEnabled.1437.f6cd6a5")
public class SfdcNamespaceHandler
    extends NamespaceHandlerSupport
{


    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        registerBeanDefinitionParser("config", new SalesforceConnectorConfigDefinitionParser());
        registerBeanDefinitionParser("create", new CreateDefinitionParser());
        registerBeanDefinitionParser("create-job", new CreateJobDefinitionParser());
        registerBeanDefinitionParser("close-job", new CloseJobDefinitionParser());
        registerBeanDefinitionParser("abort-job", new AbortJobDefinitionParser());
        registerBeanDefinitionParser("create-batch", new CreateBatchDefinitionParser());
        registerBeanDefinitionParser("create-batch-stream", new CreateBatchStreamDefinitionParser());
        registerBeanDefinitionParser("create-batch-for-query", new CreateBatchForQueryDefinitionParser());
        registerBeanDefinitionParser("create-bulk", new CreateBulkDefinitionParser());
        registerBeanDefinitionParser("create-single", new CreateSingleDefinitionParser());
        registerBeanDefinitionParser("update", new UpdateDefinitionParser());
        registerBeanDefinitionParser("update-single", new UpdateSingleDefinitionParser());
        registerBeanDefinitionParser("update-bulk", new UpdateBulkDefinitionParser());
        registerBeanDefinitionParser("upsert", new UpsertDefinitionParser());
        registerBeanDefinitionParser("upsert-bulk", new UpsertBulkDefinitionParser());
        registerBeanDefinitionParser("batch-info", new BatchInfoDefinitionParser());
        registerBeanDefinitionParser("batch-result", new BatchResultDefinitionParser());
        registerBeanDefinitionParser("batch-result-stream", new BatchResultStreamDefinitionParser());
        registerBeanDefinitionParser("query-result-stream", new QueryResultStreamDefinitionParser());
        registerBeanDefinitionParser("describe-global", new DescribeGlobalDefinitionParser());
        registerBeanDefinitionParser("retrieve", new RetrieveDefinitionParser());
        registerBeanDefinitionParser("paginated-query", new PaginatedQueryDefinitionParser());
        registerBeanDefinitionParser("query", new QueryDefinitionParser());
        registerBeanDefinitionParser("query-all", new QueryAllDefinitionParser());
        registerBeanDefinitionParser("search", new SearchDefinitionParser());
        registerBeanDefinitionParser("query-single", new QuerySingleDefinitionParser());
        registerBeanDefinitionParser("convert-lead", new ConvertLeadDefinitionParser());
        registerBeanDefinitionParser("empty-recycle-bin", new EmptyRecycleBinDefinitionParser());
        registerBeanDefinitionParser("delete", new DeleteDefinitionParser());
        registerBeanDefinitionParser("hard-delete-bulk", new HardDeleteBulkDefinitionParser());
        registerBeanDefinitionParser("get-updated-range", new GetUpdatedRangeDefinitionParser());
        registerBeanDefinitionParser("get-deleted-range", new GetDeletedRangeDefinitionParser());
        registerBeanDefinitionParser("describe-sobject", new DescribeSObjectDefinitionParser());
        registerBeanDefinitionParser("get-deleted", new GetDeletedDefinitionParser());
        registerBeanDefinitionParser("get-updated", new GetUpdatedDefinitionParser());
        registerBeanDefinitionParser("get-updated-objects", new GetUpdatedObjectsDefinitionParser());
        registerBeanDefinitionParser("reset-updated-objects-timestamp", new ResetUpdatedObjectsTimestampDefinitionParser());
        registerBeanDefinitionParser("publish-topic", new PublishTopicDefinitionParser());
        registerBeanDefinitionParser("get-user-info", new GetUserInfoDefinitionParser());
        registerBeanDefinitionParser("subscribe-topic", new SubscribeTopicDefinitionParser());
        registerBeanDefinitionParser("config-with-oauth", new SalesforceOAuthConnectorConfigDefinitionParser());
        registerBeanDefinitionParser("authorize", new AuthorizeDefinitionParser());
        registerBeanDefinitionParser("unauthorize", new UnauthorizeDefinitionParser());
        registerBeanDefinitionParser("create", new CreateDefinitionParser());
        registerBeanDefinitionParser("create-job", new CreateJobDefinitionParser());
        registerBeanDefinitionParser("close-job", new CloseJobDefinitionParser());
        registerBeanDefinitionParser("abort-job", new AbortJobDefinitionParser());
        registerBeanDefinitionParser("create-batch", new CreateBatchDefinitionParser());
        registerBeanDefinitionParser("create-batch-stream", new CreateBatchStreamDefinitionParser());
        registerBeanDefinitionParser("create-batch-for-query", new CreateBatchForQueryDefinitionParser());
        registerBeanDefinitionParser("create-bulk", new CreateBulkDefinitionParser());
        registerBeanDefinitionParser("create-single", new CreateSingleDefinitionParser());
        registerBeanDefinitionParser("update", new UpdateDefinitionParser());
        registerBeanDefinitionParser("update-single", new UpdateSingleDefinitionParser());
        registerBeanDefinitionParser("update-bulk", new UpdateBulkDefinitionParser());
        registerBeanDefinitionParser("upsert", new UpsertDefinitionParser());
        registerBeanDefinitionParser("upsert-bulk", new UpsertBulkDefinitionParser());
        registerBeanDefinitionParser("batch-info", new BatchInfoDefinitionParser());
        registerBeanDefinitionParser("batch-result", new BatchResultDefinitionParser());
        registerBeanDefinitionParser("batch-result-stream", new BatchResultStreamDefinitionParser());
        registerBeanDefinitionParser("query-result-stream", new QueryResultStreamDefinitionParser());
        registerBeanDefinitionParser("describe-global", new DescribeGlobalDefinitionParser());
        registerBeanDefinitionParser("retrieve", new RetrieveDefinitionParser());
        registerBeanDefinitionParser("paginated-query", new PaginatedQueryDefinitionParser());
        registerBeanDefinitionParser("query", new QueryDefinitionParser());
        registerBeanDefinitionParser("query-all", new QueryAllDefinitionParser());
        registerBeanDefinitionParser("search", new SearchDefinitionParser());
        registerBeanDefinitionParser("query-single", new QuerySingleDefinitionParser());
        registerBeanDefinitionParser("convert-lead", new ConvertLeadDefinitionParser());
        registerBeanDefinitionParser("empty-recycle-bin", new EmptyRecycleBinDefinitionParser());
        registerBeanDefinitionParser("delete", new DeleteDefinitionParser());
        registerBeanDefinitionParser("hard-delete-bulk", new HardDeleteBulkDefinitionParser());
        registerBeanDefinitionParser("get-updated-range", new GetUpdatedRangeDefinitionParser());
        registerBeanDefinitionParser("get-deleted-range", new GetDeletedRangeDefinitionParser());
        registerBeanDefinitionParser("describe-sobject", new DescribeSObjectDefinitionParser());
        registerBeanDefinitionParser("get-deleted", new GetDeletedDefinitionParser());
        registerBeanDefinitionParser("get-updated", new GetUpdatedDefinitionParser());
        registerBeanDefinitionParser("get-updated-objects", new GetUpdatedObjectsDefinitionParser());
        registerBeanDefinitionParser("reset-updated-objects-timestamp", new ResetUpdatedObjectsTimestampDefinitionParser());
        registerBeanDefinitionParser("publish-topic", new PublishTopicDefinitionParser());
        registerBeanDefinitionParser("get-user-info", new GetUserInfoDefinitionParser());
        registerBeanDefinitionParser("subscribe-topic", new SubscribeTopicDefinitionParser());
    }

}
