
package org.mule.modules.salesforce.adapters;

import javax.annotation.Generated;
import org.mule.api.MetadataAware;
import org.mule.modules.salesforce.SalesforceOAuthConnector;


/**
 * A <code>SalesforceOAuthConnectorMetadataAdapater</code> is a wrapper around {@link SalesforceOAuthConnector } that adds support for querying metadata about the extension.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-13T03:33:54-03:00", comments = "Build connectorMetaDataEnabled.1437.f6cd6a5")
public class SalesforceOAuthConnectorMetadataAdapater
    extends SalesforceOAuthConnectorCapabilitiesAdapter
    implements MetadataAware
{

    private final static String MODULE_NAME = "Salesforce (OAuth)";
    private final static String MODULE_VERSION = "5.1.4-SNAPSHOT";
    private final static String DEVKIT_VERSION = "3.4-SNAPSHOT";
    private final static String DEVKIT_BUILD = "connectorMetaDataEnabled.1437.f6cd6a5";

    public String getModuleName() {
        return MODULE_NAME;
    }

    public String getModuleVersion() {
        return MODULE_VERSION;
    }

    public String getDevkitVersion() {
        return DEVKIT_VERSION;
    }

    public String getDevkitBuild() {
        return DEVKIT_BUILD;
    }

}
