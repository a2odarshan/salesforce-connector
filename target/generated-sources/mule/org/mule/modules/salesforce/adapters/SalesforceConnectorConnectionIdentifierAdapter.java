
package org.mule.modules.salesforce.adapters;

import javax.annotation.Generated;
import org.mule.api.Connection;
import org.mule.modules.salesforce.SalesforceConnector;


/**
 * A <code>SalesforceConnectorConnectionIdentifierAdapter</code> is a wrapper around {@link SalesforceConnector } that implements {@link org.mule.api.Connection} interface.
 * 
 */
@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-11T04:22:03-03:00", comments = "Build master.1429.6fd1145")
public class SalesforceConnectorConnectionIdentifierAdapter
    extends SalesforceConnectorProcessAdapter
    implements Connection
{


    public String getConnectionIdentifier() {
        return super.getSessionId();
    }

}
