
package org.mule.modules.salesforce.oauth;

import java.util.Map;
import javax.annotation.Generated;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.mule.api.Capabilities;
import org.mule.api.Capability;
import org.mule.api.MetadataAware;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.config.MuleProperties;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.construct.FlowConstructAware;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.api.oauth.OAuthManager;
import org.mule.api.process.ProcessAdapter;
import org.mule.api.process.ProcessTemplate;
import org.mule.api.store.ObjectStore;
import org.mule.config.i18n.CoreMessages;
import org.mule.modules.salesforce.SalesforceOAuthConnector;
import org.mule.modules.salesforce.adapters.SalesforceOAuthConnectorHttpCallbackAdapter;
import org.mule.modules.salesforce.adapters.SalesforceOAuthConnectorOAuth2Adapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A {@code SalesforceOAuthConnectorOAuthManager} is a wrapper around {@link SalesforceOAuthConnector } that adds access token management capabilities to the pojo.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-13T03:33:54-03:00", comments = "Build connectorMetaDataEnabled.1437.f6cd6a5")
public class SalesforceOAuthConnectorOAuthManager
    extends SalesforceOAuthConnectorHttpCallbackAdapter
    implements Capabilities, MetadataAware, MuleContextAware, Initialisable, OAuthManager<SalesforceOAuthConnectorOAuth2Adapter> , ProcessAdapter<SalesforceOAuthConnectorOAuth2Adapter>
{

    private static Logger logger = LoggerFactory.getLogger(SalesforceOAuthConnectorOAuthManager.class);
    private SalesforceOAuthConnectorOAuth2Adapter defaultUnauthorizedConnector;
    private String consumerKey;
    private String consumerSecret;
    private ObjectStore timeObjectStore;
    private String clientId;
    private String assignmentRuleId;
    private Boolean useDefaultRule;
    private Boolean allowFieldTruncationSupport;
    /**
     * muleContext
     * 
     */
    protected MuleContext muleContext;
    /**
     * Flow Construct
     * 
     */
    protected FlowConstruct flowConstruct;
    private ObjectStore accessTokenObjectStore;
    private String authorizationUrl = null;
    private String accessTokenUrl = null;
    /**
     * Access Token Pool Factory
     * 
     */
    private KeyedPoolableObjectFactory accessTokenPoolFactory;
    /**
     * Access Token Pool
     * 
     */
    private GenericKeyedObjectPool accessTokenPool;
    private final static String MODULE_NAME = "Salesforce (OAuth)";
    private final static String MODULE_VERSION = "5.1.4-SNAPSHOT";
    private final static String DEVKIT_VERSION = "3.4-SNAPSHOT";
    private final static String DEVKIT_BUILD = "connectorMetaDataEnabled.1437.f6cd6a5";

    /**
     * Retrieves defaultUnauthorizedConnector
     * 
     */
    public SalesforceOAuthConnectorOAuth2Adapter getDefaultUnauthorizedConnector() {
        return this.defaultUnauthorizedConnector;
    }

    /**
     * Sets consumerKey
     * 
     * @param value Value to set
     */
    public void setConsumerKey(String value) {
        this.consumerKey = value;
    }

    /**
     * Retrieves consumerKey
     * 
     */
    public String getConsumerKey() {
        return this.consumerKey;
    }

    /**
     * Sets consumerSecret
     * 
     * @param value Value to set
     */
    public void setConsumerSecret(String value) {
        this.consumerSecret = value;
    }

    /**
     * Retrieves consumerSecret
     * 
     */
    public String getConsumerSecret() {
        return this.consumerSecret;
    }

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
     * Retrieves muleContext
     * 
     */
    public MuleContext getMuleContext() {
        return this.muleContext;
    }

    public void setMuleContext(MuleContext muleContext) {
        this.muleContext = muleContext;
        if (defaultUnauthorizedConnector instanceof MuleContextAware) {
            ((MuleContextAware) defaultUnauthorizedConnector).setMuleContext(muleContext);
        }
    }

    /**
     * Retrieves flowConstruct
     * 
     */
    public FlowConstruct getFlowConstruct() {
        return this.flowConstruct;
    }

    public void setFlowConstruct(FlowConstruct flowConstruct) {
        this.flowConstruct = flowConstruct;
        if (defaultUnauthorizedConnector instanceof FlowConstructAware) {
            ((FlowConstructAware) defaultUnauthorizedConnector).setFlowConstruct(flowConstruct);
        }
    }

    /**
     * Retrieves accessTokenObjectStore
     * 
     */
    public ObjectStore getAccessTokenObjectStore() {
        return this.accessTokenObjectStore;
    }

    /**
     * Sets accessTokenObjectStore
     * 
     * @param value Value to set
     */
    public void setAccessTokenObjectStore(ObjectStore value) {
        this.accessTokenObjectStore = value;
    }

    /**
     * Sets authorizationUrl
     * 
     * @param value Value to set
     */
    public void setAuthorizationUrl(String value) {
        this.authorizationUrl = value;
    }

    /**
     * Retrieves authorizationUrl
     * 
     */
    public String getAuthorizationUrl() {
        return this.authorizationUrl;
    }

    /**
     * Sets accessTokenUrl
     * 
     * @param value Value to set
     */
    public void setAccessTokenUrl(String value) {
        this.accessTokenUrl = value;
    }

    /**
     * Retrieves accessTokenUrl
     * 
     */
    public String getAccessTokenUrl() {
        return this.accessTokenUrl;
    }

    /**
     * Retrieves accessTokenPoolFactory
     * 
     */
    public KeyedPoolableObjectFactory getAccessTokenPoolFactory() {
        return this.accessTokenPoolFactory;
    }

    public void initialise()
        throws InitialisationException
    {
        GenericKeyedObjectPool.Config config = new GenericKeyedObjectPool.Config();
        config.testOnBorrow = true;
        if (accessTokenObjectStore == null) {
            accessTokenObjectStore = muleContext.getRegistry().lookupObject(MuleProperties.DEFAULT_USER_OBJECT_STORE_NAME);
            if (accessTokenObjectStore == null) {
                throw new InitialisationException(CoreMessages.createStaticMessage("There is no default user object store on this Mule instance."), this);
            }
        }
        accessTokenPoolFactory = new SalesforceOAuthConnectorOAuthClientFactory(this);
        accessTokenPool = new GenericKeyedObjectPool(accessTokenPoolFactory, config);
        defaultUnauthorizedConnector = new SalesforceOAuthConnectorOAuth2Adapter();
        if (defaultUnauthorizedConnector instanceof Initialisable) {
            ((Initialisable) defaultUnauthorizedConnector).initialise();
        }
    }

    public void start()
        throws MuleException
    {
        if (defaultUnauthorizedConnector instanceof Startable) {
            ((Startable) defaultUnauthorizedConnector).start();
        }
    }

    public void stop()
        throws MuleException
    {
        if (defaultUnauthorizedConnector instanceof Stoppable) {
            ((Stoppable) defaultUnauthorizedConnector).stop();
        }
    }

    public void dispose() {
        if (defaultUnauthorizedConnector instanceof Disposable) {
            ((Disposable) defaultUnauthorizedConnector).dispose();
        }
    }

    public SalesforceOAuthConnectorOAuth2Adapter createAccessToken(String verifier)
        throws Exception
    {
        SalesforceOAuthConnectorOAuth2Adapter connector = new SalesforceOAuthConnectorOAuth2Adapter();
        connector.setOauthVerifier(verifier);
        connector.setAuthorizationUrl(getAuthorizationUrl());
        connector.setAccessTokenUrl(getAccessTokenUrl());
        connector.setConsumerKey(getConsumerKey());
        connector.setConsumerSecret(getConsumerSecret());
        connector.setTimeObjectStore(getTimeObjectStore());
        connector.setClientId(getClientId());
        connector.setAssignmentRuleId(getAssignmentRuleId());
        connector.setUseDefaultRule(getUseDefaultRule());
        connector.setAllowFieldTruncationSupport(getAllowFieldTruncationSupport());
        if (connector instanceof MuleContextAware) {
            connector.setMuleContext(muleContext);
        }
        if (connector instanceof Initialisable) {
            connector.initialise();
        }
        if (connector instanceof Startable) {
            connector.start();
        }
        return connector;
    }

    public SalesforceOAuthConnectorOAuth2Adapter acquireAccessToken(String userId)
        throws Exception
    {
        if (logger.isDebugEnabled()) {
            StringBuilder messageStringBuilder = new StringBuilder();
            messageStringBuilder.append("Pool Statistics before acquiring [key ");
            messageStringBuilder.append(userId);
            messageStringBuilder.append("] [active=");
            messageStringBuilder.append(accessTokenPool.getNumActive(userId));
            messageStringBuilder.append("] [idle=");
            messageStringBuilder.append(accessTokenPool.getNumIdle(userId));
            messageStringBuilder.append("]");
            logger.debug(messageStringBuilder.toString());
        }
        SalesforceOAuthConnectorOAuth2Adapter object = ((SalesforceOAuthConnectorOAuth2Adapter) accessTokenPool.borrowObject(userId));
        if (logger.isDebugEnabled()) {
            StringBuilder messageStringBuilder = new StringBuilder();
            messageStringBuilder.append("Pool Statistics after acquiring [key ");
            messageStringBuilder.append(userId);
            messageStringBuilder.append("] [active=");
            messageStringBuilder.append(accessTokenPool.getNumActive(userId));
            messageStringBuilder.append("] [idle=");
            messageStringBuilder.append(accessTokenPool.getNumIdle(userId));
            messageStringBuilder.append("]");
            logger.debug(messageStringBuilder.toString());
        }
        return object;
    }

    public void releaseAccessToken(String userId, SalesforceOAuthConnectorOAuth2Adapter connector)
        throws Exception
    {
        if (logger.isDebugEnabled()) {
            StringBuilder messageStringBuilder = new StringBuilder();
            messageStringBuilder.append("Pool Statistics before releasing [key ");
            messageStringBuilder.append(userId);
            messageStringBuilder.append("] [active=");
            messageStringBuilder.append(accessTokenPool.getNumActive(userId));
            messageStringBuilder.append("] [idle=");
            messageStringBuilder.append(accessTokenPool.getNumIdle(userId));
            messageStringBuilder.append("]");
            logger.debug(messageStringBuilder.toString());
        }
        accessTokenPool.returnObject(userId, connector);
        if (logger.isDebugEnabled()) {
            StringBuilder messageStringBuilder = new StringBuilder();
            messageStringBuilder.append("Pool Statistics after releasing [key ");
            messageStringBuilder.append(userId);
            messageStringBuilder.append("] [active=");
            messageStringBuilder.append(accessTokenPool.getNumActive(userId));
            messageStringBuilder.append("] [idle=");
            messageStringBuilder.append(accessTokenPool.getNumIdle(userId));
            messageStringBuilder.append("]");
            logger.debug(messageStringBuilder.toString());
        }
    }

    public void destroyAccessToken(String userId, SalesforceOAuthConnectorOAuth2Adapter connector)
        throws Exception
    {
        if (logger.isDebugEnabled()) {
            StringBuilder messageStringBuilder = new StringBuilder();
            messageStringBuilder.append("Pool Statistics before destroying [key ");
            messageStringBuilder.append(userId);
            messageStringBuilder.append("] [active=");
            messageStringBuilder.append(accessTokenPool.getNumActive(userId));
            messageStringBuilder.append("] [idle=");
            messageStringBuilder.append(accessTokenPool.getNumIdle(userId));
            messageStringBuilder.append("]");
            logger.debug(messageStringBuilder.toString());
        }
        accessTokenPool.invalidateObject(userId, connector);
        if (logger.isDebugEnabled()) {
            StringBuilder messageStringBuilder = new StringBuilder();
            messageStringBuilder.append("Pool Statistics after destroying [key ");
            messageStringBuilder.append(userId);
            messageStringBuilder.append("] [active=");
            messageStringBuilder.append(accessTokenPool.getNumActive(userId));
            messageStringBuilder.append("] [idle=");
            messageStringBuilder.append(accessTokenPool.getNumIdle(userId));
            messageStringBuilder.append("]");
            logger.debug(messageStringBuilder.toString());
        }
    }

    /**
     * Returns true if this module implements such capability
     * 
     */
    public boolean isCapableOf(Capability capability) {
        if (capability == Capability.LIFECYCLE_CAPABLE) {
            return true;
        }
        if (capability == Capability.OAUTH2_CAPABLE) {
            return true;
        }
        if (capability == Capability.OAUTH_ACCESS_TOKEN_MANAGEMENT_CAPABLE) {
            return true;
        }
        return false;
    }

    @Override
    public<P >ProcessTemplate<P, SalesforceOAuthConnectorOAuth2Adapter> getProcessTemplate() {
        return new ManagedAccessTokenProcessTemplate(this, getMuleContext());
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

    public String authorize(Map<String, String> extraParameters, String authorizationUrl, String redirectUri) {
        StringBuilder urlBuilder = new StringBuilder();
        if (authorizationUrl!= null) {
            urlBuilder.append(authorizationUrl);
        } else {
            urlBuilder.append(this.authorizationUrl);
        }
        urlBuilder.append("?");
        urlBuilder.append("response_type=code&");
        urlBuilder.append("client_id=");
        urlBuilder.append(getConsumerKey());
        urlBuilder.append("&redirect_uri=");
        urlBuilder.append(redirectUri);
        for (String parameter: extraParameters.keySet()) {
            urlBuilder.append("&");
            urlBuilder.append(parameter);
            urlBuilder.append("=");
            urlBuilder.append(extraParameters.get(parameter));
        }
        logger.debug(("Authorization URL has been generated as follows: " + urlBuilder));
        return urlBuilder.toString();
    }

}
