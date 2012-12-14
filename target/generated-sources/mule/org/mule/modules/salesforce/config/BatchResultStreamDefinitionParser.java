
package org.mule.modules.salesforce.config;

import javax.annotation.Generated;
import com.sforce.async.holders.BatchInfoExpressionHolder;
import org.mule.modules.salesforce.processors.BatchResultStreamMessageProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-13T03:33:54-03:00", comments = "Build connectorMetaDataEnabled.1437.f6cd6a5")
public class BatchResultStreamDefinitionParser
    extends AbstractDefinitionParser
{


    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(BatchResultStreamMessageProcessor.class.getName());
        builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        parseConfigRef(element, builder);
        if (!parseObjectRef(element, builder, "batch-info", "batchInfo")) {
            BeanDefinitionBuilder batchInfoBuilder = BeanDefinitionBuilder.rootBeanDefinition(BatchInfoExpressionHolder.class.getName());
            Element batchInfoChildElement = DomUtils.getChildElementByTagName(element, "batch-info");
            if (batchInfoChildElement!= null) {
                parseProperty(batchInfoBuilder, batchInfoChildElement, "id", "id");
                parseProperty(batchInfoBuilder, batchInfoChildElement, "jobId", "jobId");
                parseProperty(batchInfoBuilder, batchInfoChildElement, "state", "state");
                parseProperty(batchInfoBuilder, batchInfoChildElement, "stateMessage", "stateMessage");
                parseProperty(batchInfoBuilder, batchInfoChildElement, "createdDate", "createdDate");
                parseProperty(batchInfoBuilder, batchInfoChildElement, "systemModstamp", "systemModstamp");
                parseProperty(batchInfoBuilder, batchInfoChildElement, "numberRecordsProcessed", "numberRecordsProcessed");
                parseProperty(batchInfoBuilder, batchInfoChildElement, "numberRecordsFailed", "numberRecordsFailed");
                parseProperty(batchInfoBuilder, batchInfoChildElement, "totalProcessingTime", "totalProcessingTime");
                parseProperty(batchInfoBuilder, batchInfoChildElement, "apiActiveProcessingTime", "apiActiveProcessingTime");
                parseProperty(batchInfoBuilder, batchInfoChildElement, "apexProcessingTime", "apexProcessingTime");
                builder.addPropertyValue("batchInfo", batchInfoBuilder.getBeanDefinition());
            }
        }
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
