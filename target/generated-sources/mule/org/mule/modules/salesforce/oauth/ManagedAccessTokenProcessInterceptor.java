
package org.mule.modules.salesforce.oauth;

import java.util.List;
import javax.annotation.Generated;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.UnableToAcquireConnectionException;
import org.mule.api.UnableToReleaseConnectionException;
import org.mule.api.oauth.OAuthManager;
import org.mule.api.process.ProcessCallback;
import org.mule.api.process.ProcessInterceptor;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.modules.salesforce.adapters.SalesforceOAuthConnectorOAuth2Adapter;
import org.mule.modules.salesforce.processors.AbstractConnectedProcessor;
import org.mule.modules.salesforce.processors.AbstractExpressionEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-11T04:22:03-03:00", comments = "Build master.1429.6fd1145")
public class ManagedAccessTokenProcessInterceptor<T >
    extends AbstractExpressionEvaluator
    implements ProcessInterceptor<T, SalesforceOAuthConnectorOAuth2Adapter>
{

    private static Logger logger = LoggerFactory.getLogger(ManagedAccessTokenProcessInterceptor.class);
    private final OAuthManager<SalesforceOAuthConnectorOAuth2Adapter> oauthManager;
    private final MuleContext muleContext;
    private final ProcessInterceptor<T, SalesforceOAuthConnectorOAuth2Adapter> next;

    public ManagedAccessTokenProcessInterceptor(ProcessInterceptor<T, SalesforceOAuthConnectorOAuth2Adapter> next, OAuthManager<SalesforceOAuthConnectorOAuth2Adapter> oauthManager, MuleContext muleContext) {
        this.next = next;
        this.oauthManager = oauthManager;
        this.muleContext = muleContext;
    }

    public T execute(ProcessCallback<T, SalesforceOAuthConnectorOAuth2Adapter> processCallback, SalesforceOAuthConnectorOAuth2Adapter object, MessageProcessor messageProcessor, MuleEvent event)
        throws Exception
    {
        SalesforceOAuthConnectorOAuth2Adapter connector = null;
        if (!processCallback.isProtected()) {
            return processCallback.process(oauthManager.getDefaultUnauthorizedConnector());
        }
        if (((AbstractConnectedProcessor) messageProcessor).getAccessTokenId() == null) {
            throw new IllegalArgumentException("The accessTokenId cannot be null");
        }
        String _transformedToken = ((String) evaluateAndTransform(muleContext, event, AbstractConnectedProcessor.class.getDeclaredField("_accessTokenIdType").getGenericType(), null, ((AbstractConnectedProcessor) messageProcessor).getAccessTokenId()));
        try {
            if (logger.isDebugEnabled()) {
                logger.debug(("Attempting to acquire access token using from store for [accessTokenId="+ _transformedToken.toString()));
            }
            connector = oauthManager.acquireAccessToken(_transformedToken);
            if (connector == null) {
                throw new UnableToAcquireConnectionException();
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug((("Access token has been acquired for [accessTokenId="+ connector.getUserId())+"]"));
                }
            }
            return next.execute(processCallback, connector, messageProcessor, event);
        } catch (Exception e) {
            if ((processCallback.getManagedExceptions()!= null)&&(connector!= null)) {
                for (Class exceptionClass: ((List<Class> ) processCallback.getManagedExceptions())) {
                    if (exceptionClass.isInstance(e)) {
                        if (logger.isDebugEnabled()) {
                            logger.debug((((("An exception ( "+ exceptionClass.getName())+") has been thrown. Destroying the access token with [accessTokenId=")+ connector.getUserId())+"]"));
                        }
                        try {
                            oauthManager.destroyAccessToken(_transformedToken, connector);
                            connector = null;
                        } catch (Exception innerException) {
                            logger.error(innerException.getMessage(), innerException);
                        }
                    }
                }
            }
            throw e;
        } finally {
            try {
                if (connector!= null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug((("Releasing the access token back into the pool [accessTokenId="+ connector.getUserId())+"]"));
                    }
                    oauthManager.releaseAccessToken(_transformedToken, connector);
                }
            } catch (Exception e) {
                throw new UnableToReleaseConnectionException(e);
            }
        }
    }

    public T execute(ProcessCallback<T, SalesforceOAuthConnectorOAuth2Adapter> processCallback, SalesforceOAuthConnectorOAuth2Adapter object, Filter filter, MuleMessage message)
        throws Exception
    {
        throw new UnsupportedOperationException();
    }

}
