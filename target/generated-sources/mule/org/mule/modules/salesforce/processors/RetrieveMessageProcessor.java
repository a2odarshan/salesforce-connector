
package org.mule.modules.salesforce.processors;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import com.sforce.ws.ConnectionException;
import org.mule.api.MessagingException;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.config.ConfigurationException;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.api.process.ProcessAdapter;
import org.mule.api.process.ProcessCallback;
import org.mule.api.process.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.registry.RegistrationException;
import org.mule.common.metadata.ConnectorMetaDataEnabled;
import org.mule.common.metadata.DefaultListMetaDataModel;
import org.mule.common.metadata.DefaultMetaData;
import org.mule.common.metadata.DefaultMetaDataKey;
import org.mule.common.metadata.ListMetaDataModel;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.OperationMetaDataEnabled;
import org.mule.common.metadata.MetaDataKey;
import org.mule.config.i18n.CoreMessages;
import org.mule.modules.salesforce.BaseSalesforceConnector;
import org.mule.modules.salesforce.SalesforceConnector;


/**
 * RetrieveMessageProcessor invokes the {@link org.mule.modules.salesforce.BaseSalesforceConnector#retrieve(java.lang.String, java.util.List, java.util.List)} method in {@link BaseSalesforceConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-13T03:33:54-03:00", comments = "Build connectorMetaDataEnabled.1437.f6cd6a5")
public class RetrieveMessageProcessor
    extends AbstractMessageProcessor<Object>
    implements Disposable, Initialisable, Startable, Stoppable, MessageProcessor, OperationMetaDataEnabled
{

    protected Object type;
    protected String _typeType;
    protected Object ids;
    protected List<String> _idsType;
    protected Object fields;
    protected List<String> _fieldsType;

    /**
     * Obtains the expression manager from the Mule context and initialises the connector. If a target object  has not been set already it will search the Mule registry for a default one.
     * 
     * @throws InitialisationException
     */
    public void initialise()
        throws InitialisationException
    {
    }

    public void start()
        throws MuleException
    {
    }

    public void stop()
        throws MuleException
    {
    }

    public void dispose() {
    }

    /**
     * Set the Mule context
     * 
     * @param context Mule context to set
     */
    public void setMuleContext(MuleContext context) {
        super.setMuleContext(context);
    }

    /**
     * Sets flow construct
     * 
     * @param flowConstruct Flow construct to set
     */
    public void setFlowConstruct(FlowConstruct flowConstruct) {
        super.setFlowConstruct(flowConstruct);
    }

    /**
     * Sets ids
     * 
     * @param value Value to set
     */
    public void setIds(Object value) {
        this.ids = value;
    }

    /**
     * Sets type
     * 
     * @param value Value to set
     */
    public void setType(Object value) {
        this.type = value;
    }

    /**
     * Sets fields
     * 
     * @param value Value to set
     */
    public void setFields(Object value) {
        this.fields = value;
    }

    /**
     * Invokes the MessageProcessor.
     * 
     * @param event MuleEvent to be processed
     * @throws MuleException
     */
    public MuleEvent process(final MuleEvent event)
        throws MuleException
    {
        Object moduleObject = null;
        try {
            moduleObject = findOrCreate(ProcessAdapter.class, false, event);
            final String _transformedType = ((String) evaluateAndTransform(getMuleContext(), event, RetrieveMessageProcessor.class.getDeclaredField("_typeType").getGenericType(), null, type));
            final List<String> _transformedIds = ((List<String> ) evaluateAndTransform(getMuleContext(), event, RetrieveMessageProcessor.class.getDeclaredField("_idsType").getGenericType(), null, ids));
            final List<String> _transformedFields = ((List<String> ) evaluateAndTransform(getMuleContext(), event, RetrieveMessageProcessor.class.getDeclaredField("_fieldsType").getGenericType(), null, fields));
            Object resultPayload;
            ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            resultPayload = processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class> getManagedExceptions() {
                    return Arrays.asList(new Class[] {ConnectionException.class });
                }

                public boolean isProtected() {
                    return true;
                }

                public Object process(Object object)
                    throws Exception
                {
                    return ((BaseSalesforceConnector) object).retrieve(_transformedType, _transformedIds, _transformedFields);
                }

            }
            , this, event);
            overwritePayload(event, resultPayload);
            return event;
        } catch (MessagingException messagingException) {
            messagingException.setProcessedEvent(event);
            throw messagingException;
        } catch (Exception e) {
            throw new MessagingException(CoreMessages.failedToInvoke("retrieve"), event, e);
        }
    }

    @Override
    public MetaData getInputMetaData()
    {
        return null;
    }

//    @OutputMetaDataKey(type=org.mule.api.annotations.MetaDataKey.PARAMETER, values="type")
//    @OutputMetaDataModelDescription(modelDescription="LIST(MODEL)")
    @Override
    public MetaData getOutputMetaData(MetaData inputMetaData)
    {
        MetaDataKey metaDataKey = new DefaultMetaDataKey(type.toString(), null);
        ConnectorMetaDataEnabled connector;
        try
        {
            connector = (ConnectorMetaDataEnabled)findOrCreate(SalesforceConnector.class, true, null);
            MetaData metaData = connector.getMetaData(metaDataKey);
            ListMetaDataModel listMetaDataModel = new DefaultListMetaDataModel(metaData.getPayload());
            return new DefaultMetaData(listMetaDataModel);
        }
        catch (ConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (RegistrationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
