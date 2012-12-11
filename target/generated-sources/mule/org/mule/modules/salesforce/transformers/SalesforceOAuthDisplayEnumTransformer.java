
package org.mule.modules.salesforce.transformers;

import javax.annotation.Generated;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.modules.salesforce.SalesforceOAuthDisplay;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;

@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-11T04:22:03-03:00", comments = "Build master.1429.6fd1145")
public class SalesforceOAuthDisplayEnumTransformer
    extends AbstractTransformer
    implements DiscoverableTransformer
{

    private int weighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING;

    public SalesforceOAuthDisplayEnumTransformer() {
        registerSourceType(DataTypeFactory.create(String.class));
        setReturnClass(SalesforceOAuthDisplay.class);
        setName("SalesforceOAuthDisplayEnumTransformer");
    }

    protected Object doTransform(Object src, String encoding)
        throws TransformerException
    {
        SalesforceOAuthDisplay result = null;
        result = Enum.valueOf(SalesforceOAuthDisplay.class, ((String) src));
        return result;
    }

    public int getPriorityWeighting() {
        return weighting;
    }

    public void setPriorityWeighting(int weighting) {
        this.weighting = weighting;
    }

}
