
package org.mule.modules.salesforce.config;

import java.util.Map;
import javax.annotation.Generated;
import org.mule.modules.salesforce.config.AbstractDefinitionParser.ParseDelegate;
import org.mule.modules.salesforce.config.AbstractDefinitionParser.ParseDelegate;
import org.mule.modules.salesforce.processors.UpsertMessageProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-11T04:22:03-03:00", comments = "Build master.1429.6fd1145")
public class UpsertDefinitionParser
    extends AbstractDefinitionParser
{


    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(UpsertMessageProcessor.class.getName());
        builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        parseConfigRef(element, builder);
        parseProperty(builder, element, "externalIdFieldName", "externalIdFieldName");
        parseProperty(builder, element, "type", "type");
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
