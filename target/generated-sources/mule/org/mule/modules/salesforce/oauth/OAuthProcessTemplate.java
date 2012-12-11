
package org.mule.modules.salesforce.oauth;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.oauth.OAuthAdapter;
import org.mule.api.process.ProcessCallback;
import org.mule.api.process.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.modules.salesforce.adapters.SalesforceOAuthConnectorCapabilitiesAdapter;

@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-11T04:22:03-03:00", comments = "Build master.1429.6fd1145")
public class OAuthProcessTemplate<P >implements ProcessTemplate<P, SalesforceOAuthConnectorCapabilitiesAdapter>
{

    private final SalesforceOAuthConnectorCapabilitiesAdapter object;

    public OAuthProcessTemplate(SalesforceOAuthConnectorCapabilitiesAdapter object) {
        this.object = object;
    }

    public P execute(ProcessCallback<P, SalesforceOAuthConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
        throws Exception
    {
        if (processCallback.isProtected()) {
            ((OAuthAdapter) object).hasBeenAuthorized();
        }
        return processCallback.process(object);
    }

    public P execute(ProcessCallback<P, SalesforceOAuthConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
        throws Exception
    {
        if (processCallback.isProtected()) {
            ((OAuthAdapter) object).hasBeenAuthorized();
        }
        return processCallback.process(object);
    }

}
