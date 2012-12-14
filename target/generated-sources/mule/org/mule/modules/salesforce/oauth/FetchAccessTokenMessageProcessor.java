
package org.mule.modules.salesforce.oauth;

import javax.annotation.Generated;
import org.mule.api.MessagingException;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.oauth.OAuthManager;
import org.mule.api.processor.MessageProcessor;
import org.mule.config.i18n.MessageFactory;
import org.mule.modules.salesforce.adapters.SalesforceOAuthConnectorOAuth2Adapter;

@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-13T03:33:54-03:00", comments = "Build connectorMetaDataEnabled.1437.f6cd6a5")
public class FetchAccessTokenMessageProcessor implements MessageProcessor
{

    public String redirectUri;
    private String accessTokenUrl = null;
    private OAuthManager oauthManager;

    public FetchAccessTokenMessageProcessor(OAuthManager oauthManager) {
        this.oauthManager = oauthManager;
    }

    /**
     * Sets redirectUri
     * 
     * @param value Value to set
     */
    public void setRedirectUri(String value) {
        this.redirectUri = value;
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

    public MuleEvent process(MuleEvent event)
        throws MuleException
    {
        try {
            SalesforceOAuthConnectorOAuth2Adapter oauthAdapter = ((SalesforceOAuthConnectorOAuth2Adapter) oauthManager.createAccessToken(((String) event.getMessage().getInvocationProperty("_oauthVerifier"))));
            oauthAdapter.setAccessTokenUrl(accessTokenUrl);
            oauthAdapter.fetchAccessToken(accessTokenUrl, redirectUri);
            ((SalesforceOAuthConnectorOAuthManager) oauthManager).getAccessTokenPoolFactory().passivateObject(((SalesforceOAuthConnectorOAuth2Adapter) oauthAdapter).getUserId(), oauthAdapter);
            event.getMessage().setInvocationProperty("OAuthAccessTokenId", ((SalesforceOAuthConnectorOAuth2Adapter) oauthAdapter).getUserId());
        } catch (Exception e) {
            throw new MessagingException(MessageFactory.createStaticMessage("Unable to fetch access token"), event, e);
        }
        return event;
    }

}
