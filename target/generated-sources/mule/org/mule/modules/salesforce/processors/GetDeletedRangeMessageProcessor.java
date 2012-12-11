
package org.mule.modules.salesforce.processors;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Generated;
import com.sforce.ws.ConnectionException;
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
 * GetDeletedRangeMessageProcessor invokes the {@link org.mule.modules.salesforce.BaseSalesforceConnector#getDeletedRange(java.lang.String, java.util.Calendar, java.util.Calendar)} method in {@link BaseSalesforceConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-11T04:22:03-03:00", comments = "Build master.1429.6fd1145")
public class GetDeletedRangeMessageProcessor
    extends AbstractMessageProcessor<Object>
    implements Disposable, Initialisable, Startable, Stoppable, MessageProcessor
{

    protected Object type;
    protected String _typeType;
    protected Object startTime;
    protected Calendar _startTimeType;
    protected Object endTime;
    protected Calendar _endTimeType;

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
     * Sets startTime
     * 
     * @param value Value to set
     */
    public void setStartTime(Object value) {
        this.startTime = value;
    }

    /**
     * Sets endTime
     * 
     * @param value Value to set
     */
    public void setEndTime(Object value) {
        this.endTime = value;
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
            final String _transformedType = ((String) evaluateAndTransform(getMuleContext(), event, GetDeletedRangeMessageProcessor.class.getDeclaredField("_typeType").getGenericType(), null, type));
            final Calendar _transformedStartTime = ((Calendar) evaluateAndTransform(getMuleContext(), event, GetDeletedRangeMessageProcessor.class.getDeclaredField("_startTimeType").getGenericType(), null, startTime));
            final Calendar _transformedEndTime = ((Calendar) evaluateAndTransform(getMuleContext(), event, GetDeletedRangeMessageProcessor.class.getDeclaredField("_endTimeType").getGenericType(), null, endTime));
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
                    return ((BaseSalesforceConnector) object).getDeletedRange(_transformedType, _transformedStartTime, _transformedEndTime);
                }

            }
            , this, event);
            overwritePayload(event, resultPayload);
            return event;
        } catch (MessagingException messagingException) {
            messagingException.setProcessedEvent(event);
            throw messagingException;
        } catch (Exception e) {
            throw new MessagingException(CoreMessages.failedToInvoke("getDeletedRange"), event, e);
        }
    }

}
