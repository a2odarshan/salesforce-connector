
package com.sforce.async.holders;

import java.util.Calendar;
import javax.annotation.Generated;
import com.sforce.async.BatchStateEnum;

@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-11T04:22:03-03:00", comments = "Build master.1429.6fd1145")
public class BatchInfoExpressionHolder {

    protected Object id;
    protected String _idType;
    protected Object jobId;
    protected String _jobIdType;
    protected Object state;
    protected BatchStateEnum _stateType;
    protected Object stateMessage;
    protected String _stateMessageType;
    protected Object createdDate;
    protected Calendar _createdDateType;
    protected Object systemModstamp;
    protected Calendar _systemModstampType;
    protected Object numberRecordsProcessed;
    protected int _numberRecordsProcessedType;
    protected Object numberRecordsFailed;
    protected int _numberRecordsFailedType;
    protected Object totalProcessingTime;
    protected long _totalProcessingTimeType;
    protected Object apiActiveProcessingTime;
    protected long _apiActiveProcessingTimeType;
    protected Object apexProcessingTime;
    protected long _apexProcessingTimeType;

    /**
     * Sets id
     * 
     * @param value Value to set
     */
    public void setId(Object value) {
        this.id = value;
    }

    /**
     * Retrieves id
     * 
     */
    public Object getId() {
        return this.id;
    }

    /**
     * Sets jobId
     * 
     * @param value Value to set
     */
    public void setJobId(Object value) {
        this.jobId = value;
    }

    /**
     * Retrieves jobId
     * 
     */
    public Object getJobId() {
        return this.jobId;
    }

    /**
     * Sets state
     * 
     * @param value Value to set
     */
    public void setState(Object value) {
        this.state = value;
    }

    /**
     * Retrieves state
     * 
     */
    public Object getState() {
        return this.state;
    }

    /**
     * Sets stateMessage
     * 
     * @param value Value to set
     */
    public void setStateMessage(Object value) {
        this.stateMessage = value;
    }

    /**
     * Retrieves stateMessage
     * 
     */
    public Object getStateMessage() {
        return this.stateMessage;
    }

    /**
     * Sets createdDate
     * 
     * @param value Value to set
     */
    public void setCreatedDate(Object value) {
        this.createdDate = value;
    }

    /**
     * Retrieves createdDate
     * 
     */
    public Object getCreatedDate() {
        return this.createdDate;
    }

    /**
     * Sets systemModstamp
     * 
     * @param value Value to set
     */
    public void setSystemModstamp(Object value) {
        this.systemModstamp = value;
    }

    /**
     * Retrieves systemModstamp
     * 
     */
    public Object getSystemModstamp() {
        return this.systemModstamp;
    }

    /**
     * Sets numberRecordsProcessed
     * 
     * @param value Value to set
     */
    public void setNumberRecordsProcessed(Object value) {
        this.numberRecordsProcessed = value;
    }

    /**
     * Retrieves numberRecordsProcessed
     * 
     */
    public Object getNumberRecordsProcessed() {
        return this.numberRecordsProcessed;
    }

    /**
     * Sets numberRecordsFailed
     * 
     * @param value Value to set
     */
    public void setNumberRecordsFailed(Object value) {
        this.numberRecordsFailed = value;
    }

    /**
     * Retrieves numberRecordsFailed
     * 
     */
    public Object getNumberRecordsFailed() {
        return this.numberRecordsFailed;
    }

    /**
     * Sets totalProcessingTime
     * 
     * @param value Value to set
     */
    public void setTotalProcessingTime(Object value) {
        this.totalProcessingTime = value;
    }

    /**
     * Retrieves totalProcessingTime
     * 
     */
    public Object getTotalProcessingTime() {
        return this.totalProcessingTime;
    }

    /**
     * Sets apiActiveProcessingTime
     * 
     * @param value Value to set
     */
    public void setApiActiveProcessingTime(Object value) {
        this.apiActiveProcessingTime = value;
    }

    /**
     * Retrieves apiActiveProcessingTime
     * 
     */
    public Object getApiActiveProcessingTime() {
        return this.apiActiveProcessingTime;
    }

    /**
     * Sets apexProcessingTime
     * 
     * @param value Value to set
     */
    public void setApexProcessingTime(Object value) {
        this.apexProcessingTime = value;
    }

    /**
     * Retrieves apexProcessingTime
     * 
     */
    public Object getApexProcessingTime() {
        return this.apexProcessingTime;
    }

}
