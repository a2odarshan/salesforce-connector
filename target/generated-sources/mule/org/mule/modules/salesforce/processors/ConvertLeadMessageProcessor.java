
package org.mule.modules.salesforce.processors;

import java.util.Arrays;
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
 * ConvertLeadMessageProcessor invokes the {@link org.mule.modules.salesforce.BaseSalesforceConnector#convertLead(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, java.lang.Boolean, java.lang.String, java.lang.String, java.lang.Boolean)} method in {@link BaseSalesforceConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-11T04:22:03-03:00", comments = "Build master.1429.6fd1145")
public class ConvertLeadMessageProcessor
    extends AbstractMessageProcessor<Object>
    implements Disposable, Initialisable, Startable, Stoppable, MessageProcessor
{

    protected Object leadId;
    protected String _leadIdType;
    protected Object contactId;
    protected String _contactIdType;
    protected Object accountId;
    protected String _accountIdType;
    protected Object overWriteLeadSource;
    protected Boolean _overWriteLeadSourceType;
    protected Object doNotCreateOpportunity;
    protected Boolean _doNotCreateOpportunityType;
    protected Object opportunityName;
    protected String _opportunityNameType;
    protected Object convertedStatus;
    protected String _convertedStatusType;
    protected Object sendEmailToOwner;
    protected Boolean _sendEmailToOwnerType;

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
     * Sets sendEmailToOwner
     * 
     * @param value Value to set
     */
    public void setSendEmailToOwner(Object value) {
        this.sendEmailToOwner = value;
    }

    /**
     * Sets accountId
     * 
     * @param value Value to set
     */
    public void setAccountId(Object value) {
        this.accountId = value;
    }

    /**
     * Sets contactId
     * 
     * @param value Value to set
     */
    public void setContactId(Object value) {
        this.contactId = value;
    }

    /**
     * Sets doNotCreateOpportunity
     * 
     * @param value Value to set
     */
    public void setDoNotCreateOpportunity(Object value) {
        this.doNotCreateOpportunity = value;
    }

    /**
     * Sets opportunityName
     * 
     * @param value Value to set
     */
    public void setOpportunityName(Object value) {
        this.opportunityName = value;
    }

    /**
     * Sets leadId
     * 
     * @param value Value to set
     */
    public void setLeadId(Object value) {
        this.leadId = value;
    }

    /**
     * Sets overWriteLeadSource
     * 
     * @param value Value to set
     */
    public void setOverWriteLeadSource(Object value) {
        this.overWriteLeadSource = value;
    }

    /**
     * Sets convertedStatus
     * 
     * @param value Value to set
     */
    public void setConvertedStatus(Object value) {
        this.convertedStatus = value;
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
            final String _transformedLeadId = ((String) evaluateAndTransform(getMuleContext(), event, ConvertLeadMessageProcessor.class.getDeclaredField("_leadIdType").getGenericType(), null, leadId));
            final String _transformedContactId = ((String) evaluateAndTransform(getMuleContext(), event, ConvertLeadMessageProcessor.class.getDeclaredField("_contactIdType").getGenericType(), null, contactId));
            final String _transformedAccountId = ((String) evaluateAndTransform(getMuleContext(), event, ConvertLeadMessageProcessor.class.getDeclaredField("_accountIdType").getGenericType(), null, accountId));
            final Boolean _transformedOverWriteLeadSource = ((Boolean) evaluateAndTransform(getMuleContext(), event, ConvertLeadMessageProcessor.class.getDeclaredField("_overWriteLeadSourceType").getGenericType(), null, overWriteLeadSource));
            final Boolean _transformedDoNotCreateOpportunity = ((Boolean) evaluateAndTransform(getMuleContext(), event, ConvertLeadMessageProcessor.class.getDeclaredField("_doNotCreateOpportunityType").getGenericType(), null, doNotCreateOpportunity));
            final String _transformedOpportunityName = ((String) evaluateAndTransform(getMuleContext(), event, ConvertLeadMessageProcessor.class.getDeclaredField("_opportunityNameType").getGenericType(), null, opportunityName));
            final String _transformedConvertedStatus = ((String) evaluateAndTransform(getMuleContext(), event, ConvertLeadMessageProcessor.class.getDeclaredField("_convertedStatusType").getGenericType(), null, convertedStatus));
            final Boolean _transformedSendEmailToOwner = ((Boolean) evaluateAndTransform(getMuleContext(), event, ConvertLeadMessageProcessor.class.getDeclaredField("_sendEmailToOwnerType").getGenericType(), null, sendEmailToOwner));
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
                    return ((BaseSalesforceConnector) object).convertLead(_transformedLeadId, _transformedContactId, _transformedAccountId, _transformedOverWriteLeadSource, _transformedDoNotCreateOpportunity, _transformedOpportunityName, _transformedConvertedStatus, _transformedSendEmailToOwner);
                }

            }
            , this, event);
            overwritePayload(event, resultPayload);
            return event;
        } catch (MessagingException messagingException) {
            messagingException.setProcessedEvent(event);
            throw messagingException;
        } catch (Exception e) {
            throw new MessagingException(CoreMessages.failedToInvoke("convertLead"), event, e);
        }
    }

}
