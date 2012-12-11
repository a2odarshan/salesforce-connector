
package com.sforce.async.transformers;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Generated;
import com.sforce.async.ConcurrencyMode;
import com.sforce.async.ContentType;
import com.sforce.async.JobInfo;
import com.sforce.async.JobStateEnum;
import com.sforce.async.OperationEnum;
import com.sforce.async.holders.JobInfoExpressionHolder;
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
public class JobInfoExpressionHolderTransformer
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
        return (aClass == JobInfoExpressionHolder.class);
    }

    public boolean isSourceDataTypeSupported(DataType<?> dataType) {
        return (dataType.getType() == JobInfoExpressionHolder.class);
    }

    public List<Class<?>> getSourceTypes() {
        return Arrays.asList(new Class<?> [] {JobInfoExpressionHolder.class });
    }

    public List<DataType<?>> getSourceDataTypes() {
        return Arrays.asList(new DataType<?> [] {DataTypeFactory.create(JobInfoExpressionHolder.class)});
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
        return JobInfo.class;
    }

    public void setReturnDataType(DataType<?> type) {
        throw new UnsupportedOperationException();
    }

    public DataType<?> getReturnDataType() {
        return DataTypeFactory.create(JobInfo.class);
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
        JobInfoExpressionHolder holder = ((JobInfoExpressionHolder) src);
        JobInfo result = new JobInfo();
        try {
            final String _transformedId = ((String) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_idType").getGenericType(), null, holder.getId()));
            result.setId(_transformedId);
            final OperationEnum _transformedOperation = ((OperationEnum) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_operationType").getGenericType(), null, holder.getOperation()));
            result.setOperation(_transformedOperation);
            final String _transformedObject = ((String) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_objectType").getGenericType(), null, holder.getObject()));
            result.setObject(_transformedObject);
            final String _transformedCreatedById = ((String) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_createdByIdType").getGenericType(), null, holder.getCreatedById()));
            result.setCreatedById(_transformedCreatedById);
            final Calendar _transformedCreatedDate = ((Calendar) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_createdDateType").getGenericType(), null, holder.getCreatedDate()));
            result.setCreatedDate(_transformedCreatedDate);
            final Calendar _transformedSystemModstamp = ((Calendar) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_systemModstampType").getGenericType(), null, holder.getSystemModstamp()));
            result.setSystemModstamp(_transformedSystemModstamp);
            final JobStateEnum _transformedState = ((JobStateEnum) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_stateType").getGenericType(), null, holder.getState()));
            result.setState(_transformedState);
            final String _transformedExternalIdFieldName = ((String) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_externalIdFieldNameType").getGenericType(), null, holder.getExternalIdFieldName()));
            result.setExternalIdFieldName(_transformedExternalIdFieldName);
            final ConcurrencyMode _transformedConcurrencyMode = ((ConcurrencyMode) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_concurrencyModeType").getGenericType(), null, holder.getConcurrencyMode()));
            result.setConcurrencyMode(_transformedConcurrencyMode);
            final Integer _transformedNumberBatchesQueued = ((Integer) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_numberBatchesQueuedType").getGenericType(), null, holder.getNumberBatchesQueued()));
            result.setNumberBatchesQueued(_transformedNumberBatchesQueued);
            final Integer _transformedNumberBatchesInProgress = ((Integer) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_numberBatchesInProgressType").getGenericType(), null, holder.getNumberBatchesInProgress()));
            result.setNumberBatchesInProgress(_transformedNumberBatchesInProgress);
            final Integer _transformedNumberBatchesCompleted = ((Integer) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_numberBatchesCompletedType").getGenericType(), null, holder.getNumberBatchesCompleted()));
            result.setNumberBatchesCompleted(_transformedNumberBatchesCompleted);
            final Integer _transformedNumberBatchesFailed = ((Integer) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_numberBatchesFailedType").getGenericType(), null, holder.getNumberBatchesFailed()));
            result.setNumberBatchesFailed(_transformedNumberBatchesFailed);
            final Integer _transformedNumberBatchesTotal = ((Integer) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_numberBatchesTotalType").getGenericType(), null, holder.getNumberBatchesTotal()));
            result.setNumberBatchesTotal(_transformedNumberBatchesTotal);
            final Integer _transformedNumberRecordsProcessed = ((Integer) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_numberRecordsProcessedType").getGenericType(), null, holder.getNumberRecordsProcessed()));
            result.setNumberRecordsProcessed(_transformedNumberRecordsProcessed);
            final Integer _transformedNumberRetries = ((Integer) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_numberRetriesType").getGenericType(), null, holder.getNumberRetries()));
            result.setNumberRetries(_transformedNumberRetries);
            final ContentType _transformedContentType = ((ContentType) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_contentTypeType").getGenericType(), null, holder.getContentType()));
            result.setContentType(_transformedContentType);
            final Double _transformedApiVersion = ((Double) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_apiVersionType").getGenericType(), null, holder.getApiVersion()));
            result.setApiVersion(_transformedApiVersion);
            final String _transformedAssignmentRuleId = ((String) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_assignmentRuleIdType").getGenericType(), null, holder.getAssignmentRuleId()));
            result.setAssignmentRuleId(_transformedAssignmentRuleId);
            final Integer _transformedNumberRecordsFailed = ((Integer) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_numberRecordsFailedType").getGenericType(), null, holder.getNumberRecordsFailed()));
            result.setNumberRecordsFailed(_transformedNumberRecordsFailed);
            final Long _transformedTotalProcessingTime = ((Long) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_totalProcessingTimeType").getGenericType(), null, holder.getTotalProcessingTime()));
            result.setTotalProcessingTime(_transformedTotalProcessingTime);
            final Long _transformedApiActiveProcessingTime = ((Long) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_apiActiveProcessingTimeType").getGenericType(), null, holder.getApiActiveProcessingTime()));
            result.setApiActiveProcessingTime(_transformedApiActiveProcessingTime);
            final Long _transformedApexProcessingTime = ((Long) evaluateAndTransform(this.muleContext, event, JobInfoExpressionHolder.class.getDeclaredField("_apexProcessingTimeType").getGenericType(), null, holder.getApexProcessingTime()));
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
