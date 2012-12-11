
package org.mule.modules.salesforce.config;

import java.util.Map;
import javax.annotation.Generated;
import com.sforce.async.holders.JobInfoExpressionHolder;
import org.mule.modules.salesforce.config.AbstractDefinitionParser.ParseDelegate;
import org.mule.modules.salesforce.config.AbstractDefinitionParser.ParseDelegate;
import org.mule.modules.salesforce.processors.CreateBatchMessageProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-11T04:22:03-03:00", comments = "Build master.1429.6fd1145")
public class CreateBatchDefinitionParser
    extends AbstractDefinitionParser
{


    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(CreateBatchMessageProcessor.class.getName());
        builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        parseConfigRef(element, builder);
        if (!parseObjectRef(element, builder, "job-info", "jobInfo")) {
            BeanDefinitionBuilder jobInfoBuilder = BeanDefinitionBuilder.rootBeanDefinition(JobInfoExpressionHolder.class.getName());
            Element jobInfoChildElement = DomUtils.getChildElementByTagName(element, "job-info");
            if (jobInfoChildElement!= null) {
                parseProperty(jobInfoBuilder, jobInfoChildElement, "id", "id");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "operation", "operation");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "object", "object");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "createdById", "createdById");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "createdDate", "createdDate");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "systemModstamp", "systemModstamp");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "state", "state");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "externalIdFieldName", "externalIdFieldName");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "concurrencyMode", "concurrencyMode");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "numberBatchesQueued", "numberBatchesQueued");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "numberBatchesInProgress", "numberBatchesInProgress");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "numberBatchesCompleted", "numberBatchesCompleted");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "numberBatchesFailed", "numberBatchesFailed");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "numberBatchesTotal", "numberBatchesTotal");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "numberRecordsProcessed", "numberRecordsProcessed");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "numberRetries", "numberRetries");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "contentType", "contentType");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "apiVersion", "apiVersion");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "assignmentRuleId", "assignmentRuleId");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "numberRecordsFailed", "numberRecordsFailed");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "totalProcessingTime", "totalProcessingTime");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "apiActiveProcessingTime", "apiActiveProcessingTime");
                parseProperty(jobInfoBuilder, jobInfoChildElement, "apexProcessingTime", "apexProcessingTime");
                builder.addPropertyValue("jobInfo", jobInfoBuilder.getBeanDefinition());
            }
        }
        parseListWithDefaultAndSetProperty(element, builder, "objects", "objects", "object", "#[payload]", new ParseDelegate<Map>() {


            public Map parse(Element element) {
                return parseMap(element, "inner-object", new ParseDelegate<String>() {


                    public String parse(Element element) {
                        return element.getTextContent();
                    }

                }
                );
            }

        }
        );
        parseProperty(builder, element, "username", "username");
        parseProperty(builder, element, "password", "password");
        parseProperty(builder, element, "securityToken", "securityToken");
        parseProperty(builder, element, "url", "url");
        parseProperty(builder, element, "proxyHost", "proxyHost");
        parseProperty(builder, element, "proxyPort", "proxyPort");
        parseProperty(builder, element, "proxyUsername", "proxyUsername");
        parseProperty(builder, element, "proxyPassword", "proxyPassword");
        parseProperty(builder, element, "accessTokenId");
        BeanDefinition definition = builder.getBeanDefinition();
        setNoRecurseOnDefinition(definition);
        attachProcessorDefinition(parserContext, definition);
        return definition;
    }

}
