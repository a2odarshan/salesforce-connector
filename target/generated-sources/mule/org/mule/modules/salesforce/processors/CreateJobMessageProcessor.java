
package org.mule.modules.salesforce.processors;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import com.sforce.async.AsyncApiException;
import com.sforce.async.ConcurrencyMode;
import com.sforce.async.ContentType;
import com.sforce.async.OperationEnum;
import org.mule.api.MessagingException;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
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
import org.mule.config.i18n.CoreMessages;
import org.mule.modules.salesforce.BaseSalesforceConnector;


/**
 * CreateJobMessageProcessor invokes the {@link org.mule.modules.salesforce.BaseSalesforceConnector#createJob(com.sforce.async.OperationEnum, java.lang.String, java.lang.String, com.sforce.async.ContentType, com.sforce.async.ConcurrencyMode)} method in {@link BaseSalesforceConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-13T03:33:54-03:00", comments = "Build connectorMetaDataEnabled.1437.f6cd6a5")
public class CreateJobMessageProcessor
    extends AbstractMessageProcessor<Object>
    implements Disposable, Initialisable, Startable, Stoppable, MessageProcessor
{

    protected Object operation;
    protected OperationEnum _operationType;
    protected Object type;
    protected String _typeType;
    protected Object externalIdFieldName;
    protected String _externalIdFieldNameType;
    protected Object contentType;
    protected ContentType _contentTypeType;
    protected Object concurrencyMode;
    protected ConcurrencyMode _concurrencyModeType;

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
     * Sets operation
     * 
     * @param value Value to set
     */
    public void setOperation(Object value) {
        this.operation = value;
    }

    /**
     * Sets externalIdFieldName
     * 
     * @param value Value to set
     */
    public void setExternalIdFieldName(Object value) {
        this.externalIdFieldName = value;
    }

    /**
     * Sets contentType
     * 
     * @param value Value to set
     */
    public void setContentType(Object value) {
        this.contentType = value;
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
     * Sets concurrencyMode
     * 
     * @param value Value to set
     */
    public void setConcurrencyMode(Object value) {
        this.concurrencyMode = value;
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
            final OperationEnum _transformedOperation = ((OperationEnum) evaluateAndTransform(getMuleContext(), event, CreateJobMessageProcessor.class.getDeclaredField("_operationType").getGenericType(), null, operation));
            final String _transformedType = ((String) evaluateAndTransform(getMuleContext(), event, CreateJobMessageProcessor.class.getDeclaredField("_typeType").getGenericType(), null, type));
            final String _transformedExternalIdFieldName = ((String) evaluateAndTransform(getMuleContext(), event, CreateJobMessageProcessor.class.getDeclaredField("_externalIdFieldNameType").getGenericType(), null, externalIdFieldName));
            final ContentType _transformedContentType = ((ContentType) evaluateAndTransform(getMuleContext(), event, CreateJobMessageProcessor.class.getDeclaredField("_contentTypeType").getGenericType(), null, contentType));
            final ConcurrencyMode _transformedConcurrencyMode = ((ConcurrencyMode) evaluateAndTransform(getMuleContext(), event, CreateJobMessageProcessor.class.getDeclaredField("_concurrencyModeType").getGenericType(), null, concurrencyMode));
            Object resultPayload;
            ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            resultPayload = processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class> getManagedExceptions() {
                    return Arrays.asList(new Class[] {AsyncApiException.class });
                }

                public boolean isProtected() {
                    return true;
                }

                public Object process(Object object)
                    throws Exception
                {
                    return ((BaseSalesforceConnector) object).createJob(_transformedOperation, _transformedType, _transformedExternalIdFieldName, _transformedContentType, _transformedConcurrencyMode);
                }

            }
            , this, event);
            overwritePayload(event, resultPayload);
            return event;
        } catch (MessagingException messagingException) {
            messagingException.setProcessedEvent(event);
            throw messagingException;
        } catch (Exception e) {
            throw new MessagingException(CoreMessages.failedToInvoke("createJob"), event, e);
        }
    }

}
