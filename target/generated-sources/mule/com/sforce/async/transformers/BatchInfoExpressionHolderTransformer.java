
package com.sforce.async.transformers;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Generated;
import com.sforce.async.BatchInfo;
import com.sforce.async.BatchStateEnum;
import com.sforce.async.holders.BatchInfoExpressionHolder;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.transformer.DataType;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.MessageTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transformer.TransformerMessagingException;
import org.mule.config.i18n.CoreMessages;
import org.mule.modules.salesforce.processors.AbstractExpressionEvaluator;
import org.mule.transformer.types.DataTypeFactory;

@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-11T04:22:03-03:00", comments = "Build master.1429.6fd1145")
public class BatchInfoExpressionHolderTransformer
    extends AbstractExpressionEvaluator
    implements DiscoverableTransformer, MessageTransformer
{

    private int weighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING;
    private ImmutableEndpoint endpoint;
    private MuleContext muleContext;
    private String name;

    public int getPriorityWeighting() {
        return weighting;
    }

    public void setPriorityWeighting(int weighting) {
        this.weighting = weighting;
    }

    public boolean isSourceTypeSupported(Class<?> aClass) {
        return (aClass == BatchInfoExpressionHolder.class);
    }

    public boolean isSourceDataTypeSupported(DataType<?> dataType) {
        return (dataType.getType() == BatchInfoExpressionHolder.class);
    }

    public List<Class<?>> getSourceTypes() {
        return Arrays.asList(new Class<?> [] {BatchInfoExpressionHolder.class });
    }

    public List<DataType<?>> getSourceDataTypes() {
        return Arrays.asList(new DataType<?> [] {DataTypeFactory.create(BatchInfoExpressionHolder.class)});
    }

    public boolean isAcceptNull() {
        return false;
    }

    public boolean isIgnoreBadInput() {
        return false;
    }

    public Object transform(Object src)
        throws TransformerException
    {
        throw new UnsupportedOperationException();
    }

    public Object transform(Object src, String encoding)
        throws TransformerException
    {
        throw new UnsupportedOperationException();
    }

    public void setReturnClass(Class<?> theClass) {
        throw new UnsupportedOperationException();
    }

    public Class<?> getReturnClass() {
        return BatchInfo.class;
    }

    public void setReturnDataType(DataType<?> type) {
        throw new UnsupportedOperationException();
    }

    public DataType<?> getReturnDataType() {
        return DataTypeFactory.create(BatchInfo.class);
    }

    public void setEndpoint(ImmutableEndpoint ep) {
        endpoint = ep;
    }

    public ImmutableEndpoint getEndpoint() {
        return endpoint;
    }

    public void dispose() {
    }

    public void initialise()
        throws InitialisationException
    {
    }

    public void setMuleContext(MuleContext context) {
        muleContext = context;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getName() {
        return name;
    }

    public Object transform(Object src, MuleEvent event)
        throws TransformerMessagingException
    {
        return transform(src, null, event);
    }

    public Object transform(Object src, String encoding, MuleEvent event)
        throws TransformerMessagingException
    {
        if (src == null) {
            return null;
        }
        BatchInfoExpressionHolder holder = ((BatchInfoExpressionHolder) src);
        BatchInfo result = new BatchInfo();
        try {
            final String _transformedId = ((String) evaluateAndTransform(this.muleContext, event, BatchInfoExpressionHolder.class.getDeclaredField("_idType").getGenericType(), null, holder.getId()));
            result.setId(_transformedId);
            final String _transformedJobId = ((String) evaluateAndTransform(this.muleContext, event, BatchInfoExpressionHolder.class.getDeclaredField("_jobIdType").getGenericType(), null, holder.getJobId()));
            result.setJobId(_transformedJobId);
            final BatchStateEnum _transformedState = ((BatchStateEnum) evaluateAndTransform(this.muleContext, event, BatchInfoExpressionHolder.class.getDeclaredField("_stateType").getGenericType(), null, holder.getState()));
            result.setState(_transformedState);
            final String _transformedStateMessage = ((String) evaluateAndTransform(this.muleContext, event, BatchInfoExpressionHolder.class.getDeclaredField("_stateMessageType").getGenericType(), null, holder.getStateMessage()));
            result.setStateMessage(_transformedStateMessage);
            final Calendar _transformedCreatedDate = ((Calendar) evaluateAndTransform(this.muleContext, event, BatchInfoExpressionHolder.class.getDeclaredField("_createdDateType").getGenericType(), null, holder.getCreatedDate()));
            result.setCreatedDate(_transformedCreatedDate);
            final Calendar _transformedSystemModstamp = ((Calendar) evaluateAndTransform(this.muleContext, event, BatchInfoExpressionHolder.class.getDeclaredField("_systemModstampType").getGenericType(), null, holder.getSystemModstamp()));
            result.setSystemModstamp(_transformedSystemModstamp);
            final Integer _transformedNumberRecordsProcessed = ((Integer) evaluateAndTransform(this.muleContext, event, BatchInfoExpressionHolder.class.getDeclaredField("_numberRecordsProcessedType").getGenericType(), null, holder.getNumberRecordsProcessed()));
            result.setNumberRecordsProcessed(_transformedNumberRecordsProcessed);
            final Integer _transformedNumberRecordsFailed = ((Integer) evaluateAndTransform(this.muleContext, event, BatchInfoExpressionHolder.class.getDeclaredField("_numberRecordsFailedType").getGenericType(), null, holder.getNumberRecordsFailed()));
            result.setNumberRecordsFailed(_transformedNumberRecordsFailed);
            final Long _transformedTotalProcessingTime = ((Long) evaluateAndTransform(this.muleContext, event, BatchInfoExpressionHolder.class.getDeclaredField("_totalProcessingTimeType").getGenericType(), null, holder.getTotalProcessingTime()));
            result.setTotalProcessingTime(_transformedTotalProcessingTime);
            final Long _transformedApiActiveProcessingTime = ((Long) evaluateAndTransform(this.muleContext, event, BatchInfoExpressionHolder.class.getDeclaredField("_apiActiveProcessingTimeType").getGenericType(), null, holder.getApiActiveProcessingTime()));
            result.setApiActiveProcessingTime(_transformedApiActiveProcessingTime);
            final Long _transformedApexProcessingTime = ((Long) evaluateAndTransform(this.muleContext, event, BatchInfoExpressionHolder.class.getDeclaredField("_apexProcessingTimeType").getGenericType(), null, holder.getApexProcessingTime()));
            result.setApexProcessingTime(_transformedApexProcessingTime);
        } catch (NoSuchFieldException e) {
            throw new TransformerMessagingException(CoreMessages.createStaticMessage("internal error"), event, this, e);
        } catch (TransformerException e) {
            throw new TransformerMessagingException(e.getI18nMessage(), event, this, e);
        }
        return result;
    }

    public MuleEvent process(MuleEvent event) {
        return null;
    }

    public String getMimeType() {
        return null;
    }

    public String getEncoding() {
        return null;
    }

    public MuleContext getMuleContext() {
        return muleContext;
    }

}
