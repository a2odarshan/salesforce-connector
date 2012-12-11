
package org.mule.modules.salesforce.adapters;

import javax.annotation.Generated;
import org.mule.api.MetadataAware;
import org.mule.modules.salesforce.SalesforceConnector;


/**
 * A <code>SalesforceConnectorMetadataAdapater</code> is a wrapper around {@link SalesforceConnector } that adds support for querying metadata about the extension.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-11T04:22:03-03:00", comments = "Build master.1429.6fd1145")
public class SalesforceConnectorMetadataAdapater
    extends SalesforceConnectorCapabilitiesAdapter
    implements MetadataAware
{

    private final static String MODULE_NAME = "Salesforce";
    private final static String MODULE_VERSION = "5.1.4-SNAPSHOT";
    private final static String DEVKIT_VERSION = "3.4-SNAPSHOT";
    private final static String DEVKIT_BUILD = "master.1429.6fd1145";

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
