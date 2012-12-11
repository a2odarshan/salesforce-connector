
package org.mule.modules.salesforce.oauth;

import javax.annotation.Generated;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.oauth.OAuthManager;
import org.mule.api.process.ProcessCallback;
import org.mule.api.process.ProcessInterceptor;
import org.mule.api.process.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.modules.salesforce.adapters.SalesforceOAuthConnectorOAuth2Adapter;
import org.mule.modules.salesforce.process.ProcessCallbackProcessInterceptor;

@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-11T04:22:03-03:00", comments = "Build master.1429.6fd1145")
public class ManagedAccessTokenProcessTemplate<P >implements ProcessTemplate<P, SalesforceOAuthConnectorOAuth2Adapter>
{

    private final ProcessInterceptor<P, SalesforceOAuthConnectorOAuth2Adapter> processInterceptor;

    public ManagedAccessTokenProcessTemplate(OAuthManager<SalesforceOAuthConnectorOAuth2Adapter> oauthManager, MuleContext muleContext) {
        ProcessInterceptor<P, SalesforceOAuthConnectorOAuth2Adapter> processCallbackProcessInterceptor = new ProcessCallbackProcessInterceptor<P, SalesforceOAuthConnectorOAuth2Adapter>();
        ProcessInterceptor<P, SalesforceOAuthConnectorOAuth2Adapter> refreshTokenProcessInterceptor = new RefreshTokenProcessInterceptor<P>(processCallbackProcessInterceptor);
        ProcessInterceptor<P, SalesforceOAuthConnectorOAuth2Adapter> managedAccessTokenProcessInterceptor = new ManagedAccessTokenProcessInterceptor<P>(refreshTokenProcessInterceptor, oauthManager, muleContext);
        processInterceptor = managedAccessTokenProcessInterceptor;
    }

    public P execute(ProcessCallback<P, SalesforceOAuthConnectorOAuth2Adapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
        throws Exception
    {
        return processInterceptor.execute(processCallback, null, messageProcessor, event);
    }

    public P execute(ProcessCallback<P, SalesforceOAuthConnectorOAuth2Adapter> processCallback, Filter filter, MuleMessage message)
        throws Exception
    {
        return processInterceptor.execute(processCallback, null, filter, message);
    }

}
