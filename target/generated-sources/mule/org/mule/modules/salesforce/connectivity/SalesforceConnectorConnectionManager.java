
package org.mule.modules.salesforce.connectivity;

import javax.annotation.Generated;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.mule.api.Capabilities;
import org.mule.api.Capability;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.ConnectionManager;
import org.mule.api.MetadataAware;
import org.mule.api.MuleContext;
import org.mule.api.config.MuleProperties;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.process.ProcessAdapter;
import org.mule.api.process.ProcessTemplate;
import org.mule.api.retry.RetryPolicyTemplate;
import org.mule.api.store.ObjectStore;
import org.mule.common.DefaultTestResult;
import org.mule.common.TestResult;
import org.mule.common.Testable;
import org.mule.config.PoolingProfile;
import org.mule.modules.salesforce.SalesforceConnector;
import org.mule.modules.salesforce.adapters.SalesforceConnectorConnectionIdentifierAdapter;


/**
 * A {@code SalesforceConnectorConnectionManager} is a wrapper around {@link SalesforceConnector } that adds connection management capabilities to the pojo.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-11T04:22:03-03:00", comments = "Build master.1429.6fd1145")
public class SalesforceConnectorConnectionManager implements Capabilities, ConnectionManager<SalesforceConnectorConnectionKey, SalesforceConnectorConnectionIdentifierAdapter> , MetadataAware, MuleContextAware, Initialisable, ProcessAdapter<SalesforceConnectorConnectionIdentifierAdapter> , Testable
{

    /**
     * 
     */
    private String username;
    /**
     * 
     */
    private String password;
    /**
     * 
     */
    private String securityToken;
    /**
     * 
     */
    private String url;
    /**
     * 
     */
    private String proxyHost;
    /**
     * 
     */
    private int proxyPort;
    /**
     * 
     */
    private String proxyUsername;
    /**
     * 
     */
    private String proxyPassword;
    private ObjectStore timeObjectStore;
    private String clientId;
    private String assignmentRuleId;
    private Boolean useDefaultRule;
    private Boolean allowFieldTruncationSupport;
    /**
     * Mule Context
     * 
     */
    protected MuleContext muleContext;
    /**
     * Flow Construct
     * 
     */
    protected FlowConstruct flowConstruct;
    /**
     * Connector Pool
     * 
     */
    private GenericKeyedObjectPool connectionPool;
    protected PoolingProfile connectionPoolingProfile;
    protected RetryPolicyTemplate retryPolicyTemplate;
    private final static String MODULE_NAME = "Salesforce";
    private final static String MODULE_VERSION = "5.1.4-SNAPSHOT";
    private final static String DEVKIT_VERSION = "3.4-SNAPSHOT";
    private final static String DEVKIT_BUILD = "master.1429.6fd1145";

    /**
     * Sets timeObjectStore
     * 
     * @param value Value to set
     */
    public void setTimeObjectStore(ObjectStore value) {
        this.timeObjectStore = value;
    }

    /**
     * Retrieves timeObjectStore
     * 
     */
    public ObjectStore getTimeObjectStore() {
        return this.timeObjectStore;
    }

    /**
     * Sets clientId
     * 
     * @param value Value to set
     */
    public void setClientId(String value) {
        this.clientId = value;
    }

    /**
     * Retrieves clientId
     * 
     */
    public String getClientId() {
        return this.clientId;
    }

    /**
     * Sets assignmentRuleId
     * 
     * @param value Value to set
     */
    public void setAssignmentRuleId(String value) {
        this.assignmentRuleId = value;
    }

    /**
     * Retrieves assignmentRuleId
     * 
     */
    public String getAssignmentRuleId() {
        return this.assignmentRuleId;
    }

    /**
     * Sets useDefaultRule
     * 
     * @param value Value to set
     */
    public void setUseDefaultRule(Boolean value) {
        this.useDefaultRule = value;
    }

    /**
     * Retrieves useDefaultRule
     * 
     */
    public Boolean getUseDefaultRule() {
        return this.useDefaultRule;
    }

    /**
     * Sets allowFieldTruncationSupport
     * 
     * @param value Value to set
     */
    public void setAllowFieldTruncationSupport(Boolean value) {
        this.allowFieldTruncationSupport = value;
    }

    /**
     * Retrieves allowFieldTruncationSupport
     * 
     */
    public Boolean getAllowFieldTruncationSupport() {
        return this.allowFieldTruncationSupport;
    }

    /**
     * Sets muleContext
     * 
     * @param value Value to set
     */
    public void setMuleContext(MuleContext value) {
        this.muleContext = value;
    }

    /**
     * Retrieves muleContext
     * 
     */
    public MuleContext getMuleContext() {
        return this.muleContext;
    }

    /**
     * Sets flowConstruct
     * 
     * @param value Value to set
     */
    public void setFlowConstruct(FlowConstruct value) {
        this.flowConstruct = value;
    }

    /**
     * Retrieves flowConstruct
     * 
     */
    public FlowConstruct getFlowConstruct() {
        return this.flowConstruct;
    }

    /**
     * Sets connectionPoolingProfile
     * 
     * @param value Value to set
     */
    public void setConnectionPoolingProfile(PoolingProfile value) {
        this.connectionPoolingProfile = value;
    }

    /**
     * Retrieves connectionPoolingProfile
     * 
     */
    public PoolingProfile getConnectionPoolingProfile() {
        return this.connectionPoolingProfile;
    }

    /**
     * Sets retryPolicyTemplate
     * 
     * @param value Value to set
     */
    public void setRetryPolicyTemplate(RetryPolicyTemplate value) {
        this.retryPolicyTemplate = value;
    }

    /**
     * Retrieves retryPolicyTemplate
     * 
     */
    public RetryPolicyTemplate getRetryPolicyTemplate() {
        return this.retryPolicyTemplate;
    }

    /**
     * Sets proxyUsername
     * 
     * @param value Value to set
     */
    public void setProxyUsername(String value) {
        this.proxyUsername = value;
    }

    /**
     * Retrieves proxyUsername
     * 
     */
    public String getProxyUsername() {
        return this.proxyUsername;
    }

    /**
     * Sets username
     * 
     * @param value Value to set
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Retrieves username
     * 
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets proxyHost
     * 
     * @param value Value to set
     */
    public void setProxyHost(String value) {
        this.proxyHost = value;
    }

    /**
     * Retrieves proxyHost
     * 
     */
    public String getProxyHost() {
        return this.proxyHost;
    }

    /**
     * Sets securityToken
     * 
     * @param value Value to set
     */
    public void setSecurityToken(String value) {
        this.securityToken = value;
    }

    /**
     * Retrieves securityToken
     * 
     */
    public String getSecurityToken() {
        return this.securityToken;
    }

    /**
     * Sets proxyPort
     * 
     * @param value Value to set
     */
    public void setProxyPort(int value) {
        this.proxyPort = value;
    }

    /**
     * Retrieves proxyPort
     * 
     */
    public int getProxyPort() {
        return this.proxyPort;
    }

    /**
     * Sets password
     * 
     * @param value Value to set
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Retrieves password
     * 
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets proxyPassword
     * 
     * @param value Value to set
     */
    public void setProxyPassword(String value) {
        this.proxyPassword = value;
    }

    /**
     * Retrieves proxyPassword
     * 
     */
    public String getProxyPassword() {
        return this.proxyPassword;
    }

    /**
     * Sets url
     * 
     * @param value Value to set
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Retrieves url
     * 
     */
    public String getUrl() {
        return this.url;
    }

    public void initialise() {
        GenericKeyedObjectPool.Config config = new GenericKeyedObjectPool.Config();
        if (connectionPoolingProfile!= null) {
            config.maxIdle = connectionPoolingProfile.getMaxIdle();
            config.maxActive = connectionPoolingProfile.getMaxActive();
            config.maxWait = connectionPoolingProfile.getMaxWait();
            config.whenExhaustedAction = ((byte) connectionPoolingProfile.getExhaustedAction());
            config.timeBetweenEvictionRunsMillis = connectionPoolingProfile.getEvictionCheckIntervalMillis();
            config.minEvictableIdleTimeMillis = connectionPoolingProfile.getMinEvictionMillis();
        }
        connectionPool = new GenericKeyedObjectPool(new SalesforceConnectorConnectionFactory(this), config);
        if (retryPolicyTemplate == null) {
            retryPolicyTemplate = muleContext.getRegistry().lookupObject(MuleProperties.OBJECT_DEFAULT_RETRY_POLICY_TEMPLATE);
        }
    }

    public SalesforceConnectorConnectionIdentifierAdapter acquireConnection(SalesforceConnectorConnectionKey key)
        throws Exception
    {
        return ((SalesforceConnectorConnectionIdentifierAdapter) connectionPool.borrowObject(key));
    }

    public void releaseConnection(SalesforceConnectorConnectionKey key, SalesforceConnectorConnectionIdentifierAdapter connection)
        throws Exception
    {
        connectionPool.returnObject(key, connection);
    }

    public void destroyConnection(SalesforceConnectorConnectionKey key, SalesforceConnectorConnectionIdentifierAdapter connection)
        throws Exception
    {
        connectionPool.invalidateObject(key, connection);
    }

    /**
     * Returns true if this module implements such capability
     * 
     */
    public boolean isCapableOf(Capability capability) {
        if (capability == Capability.LIFECYCLE_CAPABLE) {
            return true;
        }
        if (capability == Capability.CONNECTION_MANAGEMENT_CAPABLE) {
            return true;
        }
        return false;
    }

    @Override
    public<P >ProcessTemplate<P, SalesforceConnectorConnectionIdentifierAdapter> getProcessTemplate() {
        return new ManagedConnectionProcessTemplate(this, muleContext);
    }

    public SalesforceConnectorConnectionKey getDefaultConnectionKey() {
        return new SalesforceConnectorConnectionKey(getUsername(), getPassword(), getSecurityToken(), getUrl(), getProxyHost(), getProxyPort(), getProxyUsername(), getProxyPassword());
    }

    public String getModuleName() {
        return MODULE_NAME;
    }

    public String getModuleVersion() {
        return MODULE_VERSION;
    }

    public String getDevkitVersion() {
        return DEVKIT_VERSION;
    }

    public String getDevkitBuild() {
        return DEVKIT_BUILD;
    }

    public TestResult test() {
        SalesforceConnectorConnectionIdentifierAdapter connection = null;
        DefaultTestResult result;
        SalesforceConnectorConnectionKey key = getDefaultConnectionKey();
        try {
            connection = acquireConnection(key);
            result = new DefaultTestResult(TestResult.Status.SUCCESS);
        } catch (Exception e) {
            try {
                destroyConnection(key, connection);
            } catch (Exception ie) {
            }
            result = buildFailureTestResult(e);
        } finally {
            if (connection!= null) {
                try {
                    releaseConnection(key, connection);
                } catch (Exception ie) {
                }
            }
        }
        return result;
    }

    public DefaultTestResult buildFailureTestResult(Exception exception) {
        DefaultTestResult result;
        if (exception instanceof ConnectionException) {
            ConnectionExceptionCode code = ((ConnectionException) exception).getCode();
            if (code == ConnectionExceptionCode.UNKNOWN_HOST) {
                result = new DefaultTestResult(TestResult.Status.FAILURE, exception.getMessage(), TestResult.FailureType.UNKNOWN_HOST, exception);
            } else {
                if (code == ConnectionExceptionCode.CANNOT_REACH) {
                    result = new DefaultTestResult(TestResult.Status.FAILURE, exception.getMessage(), TestResult.FailureType.RESOURCE_UNAVAILABLE, exception);
                } else {
                    if (code == ConnectionExceptionCode.INCORRECT_CREDENTIALS) {
                        result = new DefaultTestResult(TestResult.Status.FAILURE, exception.getMessage(), TestResult.FailureType.INVALID_CREDENTIALS, exception);
                    } else {
                        if (code == ConnectionExceptionCode.CREDENTIALS_EXPIRED) {
                            result = new DefaultTestResult(TestResult.Status.FAILURE, exception.getMessage(), TestResult.FailureType.INVALID_CREDENTIALS, exception);
                        } else {
                            if (code == ConnectionExceptionCode.UNKNOWN) {
                                result = new DefaultTestResult(TestResult.Status.FAILURE, exception.getMessage(), TestResult.FailureType.UNSPECIFIED, exception);
                            } else {
                                result = new DefaultTestResult(TestResult.Status.FAILURE, exception.getMessage(), TestResult.FailureType.UNSPECIFIED, exception);
                            }
                        }
                    }
                }
            }
        } else {
            result = new DefaultTestResult(TestResult.Status.FAILURE, exception.getMessage(), TestResult.FailureType.UNSPECIFIED, exception);
        }
        return result;
    }

}
