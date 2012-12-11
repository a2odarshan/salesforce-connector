
package org.mule.modules.salesforce.transformers;

import javax.annotation.Generated;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.modules.salesforce.SalesforceOAuthImmediate;
import org.mule.transformer.AbstractTransformer;
import org.mule.transformer.types.DataTypeFactory;

@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-11T04:22:03-03:00", comments = "Build master.1429.6fd1145")
public class SalesforceOAuthImmediateEnumTransformer
    extends AbstractTransformer
    implements DiscoverableTransformer
{

    private int weighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING;

    public SalesforceOAuthImmediateEnumTransformer() {
        registerSourceType(DataTypeFactory.create(String.class));
        setReturnClass(SalesforceOAuthImmediate.class);
        setName("SalesforceOAuthImmediateEnumTransformer");
    }

    protected Object doTransform(Object src, String encoding)
        throws TransformerException
    {
        SalesforceOAuthImmediate result = null;
        result = Enum.valueOf(SalesforceOAuthImmediate.class, ((String) src));
        return result;
    }

    public int getPriorityWeighting() {
        return weighting;
    }

    public void setPriorityWeighting(int weighting) {
        this.weighting = weighting;
    }

}
