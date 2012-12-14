
package org.mule.modules.salesforce.processors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.annotation.Generated;
import org.mule.api.DefaultMuleException;
import org.mule.api.MessagingException;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.callback.HttpCallback;
import org.mule.api.construct.FlowConstructAware;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.api.processor.InterceptingMessageProcessor;
import org.mule.api.processor.MessageProcessor;
import org.mule.config.i18n.CoreMessages;
import org.mule.modules.salesforce.SalesforceOAuthDisplay;
import org.mule.modules.salesforce.SalesforceOAuthImmediate;
import org.mule.modules.salesforce.SalesforceOAuthPrompt;
import org.mule.modules.salesforce.oauth.ExtractAuthorizationCodeMessageProcessor;
import org.mule.modules.salesforce.oauth.FetchAccessTokenMessageProcessor;
import org.mule.modules.salesforce.oauth.SalesforceOAuthConnectorOAuthManager;
import org.mule.modules.salesforce.process.DefaultHttpCallback;

@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-13T03:33:54-03:00", comments = "Build connectorMetaDataEnabled.1437.f6cd6a5")
public class AuthorizeMessageProcessor
    extends AbstractMessageProcessor<SalesforceOAuthConnectorOAuthManager>
    implements FlowConstructAware, MuleContextAware, Initialisable, Startable, Stoppable, InterceptingMessageProcessor
{

    private MessageProcessor listener;
    private String authorizationUrl = null;
    private String accessTokenUrl = null;
    private HttpCallback oauthCallback;
    private final static Pattern AUTH_CODE_PATTERN = Pattern.compile("code=([^&]+)");
    private String state;
    private Object display;
    private SalesforceOAuthDisplay _displayType;
    private Object immediate;
    private SalesforceOAuthImmediate _immediateType;
    private Object prompt;
    private SalesforceOAuthPrompt _promptType;

    /**
     * Sets listener
     * 
     * @param value Value to set
     */
    public void setListener(MessageProcessor value) {
        this.listener = value;
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
     * Sets state
     * 
     * @param value Value to set
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Sets display
     * 
     * @param value Value to set
     */
    public void setDisplay(Object value) {
        this.display = value;
    }

    /**
     * Sets immediate
     * 
     * @param value Value to set
     */
    public void setImmediate(Object value) {
        this.immediate = value;
    }

    /**
     * Sets prompt
     * 
     * @param value Value to set
     */
    public void setPrompt(Object value) {
        this.prompt = value;
    }

    public void initialise()
        throws InitialisationException
    {
    }

    public void start()
        throws MuleException
    {
        SalesforceOAuthConnectorOAuthManager moduleObject = null;
        try {
            moduleObject = findOrCreate(SalesforceOAuthConnectorOAuthManager.class, false, null);
        } catch (IllegalAccessException e) {
            throw new DefaultMuleException(CoreMessages.failedToStart("authorize"), e);
        } catch (InstantiationException e) {
            throw new DefaultMuleException(CoreMessages.failedToStart("authorize"), e);
        }
        if (oauthCallback == null) {
            FetchAccessTokenMessageProcessor fetchAccessTokenMessageProcessor = new FetchAccessTokenMessageProcessor(moduleObject);
            oauthCallback = new DefaultHttpCallback(Arrays.asList(new ExtractAuthorizationCodeMessageProcessor(AUTH_CODE_PATTERN), fetchAccessTokenMessageProcessor, listener), getMuleContext(), moduleObject.getDomain(), moduleObject.getLocalPort(), moduleObject.getRemotePort(), moduleObject.getPath(), moduleObject.getAsync(), getFlowConstruct().getExceptionListener(), moduleObject.getConnector());
            fetchAccessTokenMessageProcessor.setRedirectUri(oauthCallback.getUrl());
            if (accessTokenUrl!= null) {
                fetchAccessTokenMessageProcessor.setAccessTokenUrl(accessTokenUrl);
            } else {
                fetchAccessTokenMessageProcessor.setAccessTokenUrl(moduleObject.getAccessTokenUrl());
            }
            oauthCallback.start();
        }
    }

    public void stop()
        throws MuleException
    {
        if (oauthCallback!= null) {
            oauthCallback.stop();
        }
    }

    /**
     * Starts the OAuth authorization process
     * 
     * @param event MuleEvent to be processed
     * @throws MuleException
     */
    public MuleEvent process(MuleEvent event)
        throws MuleException
    {
        SalesforceOAuthConnectorOAuthManager moduleObject = null;
        try {
            moduleObject = findOrCreate(SalesforceOAuthConnectorOAuthManager.class, false, null);
            Map<String, String> extraParameters = new HashMap<String, String>();
            if (state!= null) {
                try {
                    String transformerState = ((String) evaluateAndTransform(getMuleContext(), event, AuthorizeMessageProcessor.class.getDeclaredField("state").getGenericType(), null, state));
                    extraParameters.put("state", transformerState);
                } catch (NoSuchFieldException e) {
                    throw new MessagingException(CoreMessages.createStaticMessage("internal error"), event, e);
                }
            }
            if (display!= null) {
                try {
                    Object first = evaluateAndTransform(getMuleContext(), event, AuthorizeMessageProcessor.class.getDeclaredField("_displayType").getGenericType(), null, display);
                    String second = ((String) evaluateAndTransform(getMuleContext(), event, AuthorizeMessageProcessor.class.getDeclaredField("state").getGenericType(), null, first));
                    extraParameters.put("display", second.toLowerCase());
                } catch (NoSuchFieldException e) {
                    throw new MessagingException(CoreMessages.createStaticMessage("internal error"), event, e);
                }
            }
            if (immediate!= null) {
                try {
                    Object first = evaluateAndTransform(getMuleContext(), event, AuthorizeMessageProcessor.class.getDeclaredField("_immediateType").getGenericType(), null, immediate);
                    String second = ((String) evaluateAndTransform(getMuleContext(), event, AuthorizeMessageProcessor.class.getDeclaredField("state").getGenericType(), null, first));
                    extraParameters.put("immediate", second.toLowerCase());
                } catch (NoSuchFieldException e) {
                    throw new MessagingException(CoreMessages.createStaticMessage("internal error"), event, e);
                }
            }
            if (prompt!= null) {
                try {
                    Object first = evaluateAndTransform(getMuleContext(), event, AuthorizeMessageProcessor.class.getDeclaredField("_promptType").getGenericType(), null, prompt);
                    String second = ((String) evaluateAndTransform(getMuleContext(), event, AuthorizeMessageProcessor.class.getDeclaredField("state").getGenericType(), null, first));
                    extraParameters.put("prompt", second.toLowerCase());
                } catch (NoSuchFieldException e) {
                    throw new MessagingException(CoreMessages.createStaticMessage("internal error"), event, e);
                }
            }
            String location = moduleObject.authorize(extraParameters, authorizationUrl, oauthCallback.getUrl());
            event.getMessage().setOutboundProperty("http.status", "302");
            event.getMessage().setOutboundProperty("Location", location);
            return event;
        } catch (Exception e) {
            throw new MessagingException(CoreMessages.failedToInvoke("authorize"), event, e);
        }
    }

}
