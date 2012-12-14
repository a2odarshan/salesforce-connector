
package org.mule.modules.salesforce.oauth;

import javax.annotation.Generated;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.api.store.ObjectStoreException;
import org.mule.modules.salesforce.adapters.SalesforceOAuthConnectorOAuth2Adapter;

@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-13T03:33:54-03:00", comments = "Build connectorMetaDataEnabled.1437.f6cd6a5")
public class SalesforceOAuthConnectorOAuthClientFactory implements KeyedPoolableObjectFactory
{

    private SalesforceOAuthConnectorOAuthManager oauthManager;

    public SalesforceOAuthConnectorOAuthClientFactory(SalesforceOAuthConnectorOAuthManager oauthManager) {
        this.oauthManager = oauthManager;
    }

    public Object makeObject(Object key)
        throws Exception
    {
        if (!(key instanceof String)) {
            throw new RuntimeException("Invalid key type");
        }
        SalesforceOAuthConnectorOAuthState state = null;
        if (!oauthManager.getAccessTokenObjectStore().contains(((String) key))) {
            throw new RuntimeException((("There is no access token stored under the key "+((String) key))+". You need to call the <authorize> message processor. The key will be given to you via a flow variable after the OAuth dance is completed. You can extract it using flowVars['tokenId']."));
        }
        state = ((SalesforceOAuthConnectorOAuthState) oauthManager.getAccessTokenObjectStore().retrieve(((String) key)));
        SalesforceOAuthConnectorOAuth2Adapter connector = new SalesforceOAuthConnectorOAuth2Adapter();
        connector.setConsumerKey(oauthManager.getConsumerKey());
        connector.setConsumerSecret(oauthManager.getConsumerSecret());
        connector.setTimeObjectStore(oauthManager.getTimeObjectStore());
        connector.setClientId(oauthManager.getClientId());
        connector.setAssignmentRuleId(oauthManager.getAssignmentRuleId());
        connector.setUseDefaultRule(oauthManager.getUseDefaultRule());
        connector.setAllowFieldTruncationSupport(oauthManager.getAllowFieldTruncationSupport());
        connector.setAccessToken(state.getAccessToken());
        connector.setAuthorizationUrl(state.getAuthorizationUrl());
        connector.setAccessTokenUrl(state.getAccessTokenUrl());
        connector.setRefreshToken(state.getRefreshToken());
        connector.setInstanceId(state.getInstanceId());
        connector.setUserId(state.getUserId());
        if (connector instanceof Initialisable) {
            ((Initialisable) connector).initialise();
        }
        if (connector instanceof MuleContextAware) {
            ((MuleContextAware) connector).setMuleContext(oauthManager.getMuleContext());
        }
        if (connector instanceof Startable) {
            ((Startable) connector).start();
        }
        connector.postAuthorize();
        return connector;
    }

    public void destroyObject(Object key, Object obj)
        throws Exception
    {
        if (!(key instanceof String)) {
            throw new RuntimeException("Invalid key type");
        }
        if (!(obj instanceof SalesforceOAuthConnectorOAuth2Adapter)) {
            throw new RuntimeException("Invalid connector type");
        }
        try {
        } catch (Exception e) {
            throw e;
        } finally {
            if (((SalesforceOAuthConnectorOAuth2Adapter) obj) instanceof Stoppable) {
                ((Stoppable) obj).stop();
            }
            if (((SalesforceOAuthConnectorOAuth2Adapter) obj) instanceof Disposable) {
                ((Disposable) obj).dispose();
            }
        }
    }

    public boolean validateObject(Object key, Object obj) {
        if (!(key instanceof String)) {
            throw new RuntimeException("Invalid key type");
        }
        if (!(obj instanceof SalesforceOAuthConnectorOAuth2Adapter)) {
            throw new RuntimeException("Invalid connector type");
        }
        SalesforceOAuthConnectorOAuthState state = null;
        try {
            if (!oauthManager.getAccessTokenObjectStore().contains(((String) key))) {
                return false;
            }
            state = ((SalesforceOAuthConnectorOAuthState) oauthManager.getAccessTokenObjectStore().retrieve(((String) key)));
            if (((SalesforceOAuthConnectorOAuth2Adapter) obj).getAccessToken() == null) {
                return false;
            }
            if (!((SalesforceOAuthConnectorOAuth2Adapter) obj).getAccessToken().equals(state.getAccessToken())) {
                return false;
            }
            if ((((SalesforceOAuthConnectorOAuth2Adapter) obj).getRefreshToken() == null)&&(state.getRefreshToken()!= null)) {
                return false;
            }
            if ((((SalesforceOAuthConnectorOAuth2Adapter) obj).getRefreshToken()!= null)&&(!((SalesforceOAuthConnectorOAuth2Adapter) obj).getRefreshToken().equals(state.getRefreshToken()))) {
                return false;
            }
        } catch (ObjectStoreException _x) {
            return false;
        }
        return true;
    }

    public void activateObject(Object key, Object obj)
        throws Exception
    {
    }

    public void passivateObject(Object key, Object obj)
        throws Exception
    {
        if (!(key instanceof String)) {
            throw new RuntimeException("Invalid key type");
        }
        if (!(obj instanceof SalesforceOAuthConnectorOAuth2Adapter)) {
            throw new RuntimeException("Invalid connector type");
        }
        SalesforceOAuthConnectorOAuthState state = null;
        if (oauthManager.getAccessTokenObjectStore().contains(((String) key))) {
            state = ((SalesforceOAuthConnectorOAuthState) oauthManager.getAccessTokenObjectStore().retrieve(((String) key)));
            oauthManager.getAccessTokenObjectStore().remove(((String) key));
        }
        if (state == null) {
            state = new SalesforceOAuthConnectorOAuthState();
        }
        state.setAccessToken(((SalesforceOAuthConnectorOAuth2Adapter) obj).getAccessToken());
        state.setAccessTokenUrl(((SalesforceOAuthConnectorOAuth2Adapter) obj).getAccessTokenUrl());
        state.setAuthorizationUrl(((SalesforceOAuthConnectorOAuth2Adapter) obj).getAuthorizationUrl());
        state.setRefreshToken(((SalesforceOAuthConnectorOAuth2Adapter) obj).getRefreshToken());
        state.setInstanceId(((SalesforceOAuthConnectorOAuth2Adapter) obj).getInstanceId());
        state.setUserId(((SalesforceOAuthConnectorOAuth2Adapter) obj).getUserId());
        oauthManager.getAccessTokenObjectStore().store(((String) key), state);
    }

}
