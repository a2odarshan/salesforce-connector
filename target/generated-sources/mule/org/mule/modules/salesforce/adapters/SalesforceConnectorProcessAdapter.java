
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
import org.mule.modules.salesforce.SalesforceConnector;


/**
 * A <code>SalesforceConnectorProcessAdapter</code> is a wrapper around {@link SalesforceConnector } that enables custom processing strategies.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-11T04:22:03-03:00", comments = "Build master.1429.6fd1145")
public class SalesforceConnectorProcessAdapter
    extends SalesforceConnectorLifecycleAdapter
    implements ProcessAdapter<SalesforceConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, SalesforceConnectorCapabilitiesAdapter> getProcessTemplate() {
        final SalesforceConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,SalesforceConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, SalesforceConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, SalesforceConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
