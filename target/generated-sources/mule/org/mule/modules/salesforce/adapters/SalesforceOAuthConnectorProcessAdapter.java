
package org.mule.modules.salesforce.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.process.ProcessAdapter;
import org.mule.api.process.ProcessCallback;
import org.mule.api.process.ProcessTemplate;
import org.mule.api.process.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.modules.salesforce.SalesforceOAuthConnector;


/**
 * A <code>SalesforceOAuthConnectorProcessAdapter</code> is a wrapper around {@link SalesforceOAuthConnector } that enables custom processing strategies.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-11T04:22:03-03:00", comments = "Build master.1429.6fd1145")
public class SalesforceOAuthConnectorProcessAdapter
    extends SalesforceOAuthConnectorLifecycleAdapter
    implements ProcessAdapter<SalesforceOAuthConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, SalesforceOAuthConnectorCapabilitiesAdapter> getProcessTemplate() {
        final SalesforceOAuthConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,SalesforceOAuthConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, SalesforceOAuthConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, SalesforceOAuthConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
