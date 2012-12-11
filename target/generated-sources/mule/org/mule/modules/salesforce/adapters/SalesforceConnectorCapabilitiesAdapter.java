
package org.mule.modules.salesforce.adapters;

import javax.annotation.Generated;
import org.mule.api.Capabilities;
import org.mule.api.Capability;
import org.mule.modules.salesforce.SalesforceConnector;


/**
 * A <code>SalesforceConnectorCapabilitiesAdapter</code> is a wrapper around {@link SalesforceConnector } that implements {@link org.mule.api.Capabilities} interface.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-11T04:22:03-03:00", comments = "Build master.1429.6fd1145")
public class SalesforceConnectorCapabilitiesAdapter
    extends SalesforceConnector
    implements Capabilities
{


    /**
     * Returns true if this module implements such capability
     * 
     */
    public boolean isCapableOf(Capability capability) {
        if (capability == Capability.LIFECYCLE_CAPABLE) {
            return true;
        }
        if (capability == Capability.CONNECTION_MANAGEMENT_CAPABLE) {
            return true;
        }
        return false;
    }

}
