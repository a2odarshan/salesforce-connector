
package org.mule.modules.salesforce.connectivity;

import javax.annotation.Generated;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.modules.salesforce.adapters.SalesforceConnectorConnectionIdentifierAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Generated(value = "Mule DevKit Version 3.4-SNAPSHOT", date = "2012-12-11T04:22:03-03:00", comments = "Build master.1429.6fd1145")
public class SalesforceConnectorConnectionFactory implements KeyedPoolableObjectFactory
{

    private static Logger logger = LoggerFactory.getLogger(SalesforceConnectorConnectionFactory.class);
    private SalesforceConnectorConnectionManager connectionManager;

    public SalesforceConnectorConnectionFactory(SalesforceConnectorConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public Object makeObject(Object key)
        throws Exception
    {
        if (!(key instanceof SalesforceConnectorConnectionKey)) {
            throw new RuntimeException("Invalid key type");
        }
        SalesforceConnectorConnectionIdentifierAdapter connector = new SalesforceConnectorConnectionIdentifierAdapter();
        connector.setTimeObjectStore(connectionManager.getTimeObjectStore());
        connector.setClientId(connectionManager.getClientId());
        connector.setAssignmentRuleId(connectionManager.getAssignmentRuleId());
        connector.setUseDefaultRule(connectionManager.getUseDefaultRule());
        connector.setAllowFieldTruncationSupport(connectionManager.getAllowFieldTruncationSupport());
        if (connector instanceof Initialisable) {
            ((Initialisable) connector).initialise();
        }
        if (connector instanceof MuleContextAware) {
            ((MuleContextAware) connector).setMuleContext(connectionManager.getMuleContext());
        }
        if (connector instanceof Startable) {
            ((Startable) connector).start();
        }
        return connector;
    }

    public void destroyObject(Object key, Object obj)
        throws Exception
    {
        if (!(key instanceof SalesforceConnectorConnectionKey)) {
            throw new RuntimeException("Invalid key type");
        }
        if (!(obj instanceof SalesforceConnectorConnectionIdentifierAdapter)) {
            throw new RuntimeException("Invalid connector type");
        }
        try {
            ((SalesforceConnectorConnectionIdentifierAdapter) obj).destroySession();
        } catch (Exception e) {
            throw e;
        } finally {
            if (((SalesforceConnectorConnectionIdentifierAdapter) obj) instanceof Stoppable) {
                ((Stoppable) obj).stop();
            }
            if (((SalesforceConnectorConnectionIdentifierAdapter) obj) instanceof Disposable) {
                ((Disposable) obj).dispose();
            }
        }
    }

    public boolean validateObject(Object key, Object obj) {
        if (!(obj instanceof SalesforceConnectorConnectionIdentifierAdapter)) {
            throw new RuntimeException("Invalid connector type");
        }
        try {
            return ((SalesforceConnectorConnectionIdentifierAdapter) obj).isConnected();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public void activateObject(Object key, Object obj)
        throws Exception
    {
        if (!(key instanceof SalesforceConnectorConnectionKey)) {
            throw new RuntimeException("Invalid key type");
        }
        if (!(obj instanceof SalesforceConnectorConnectionIdentifierAdapter)) {
            throw new RuntimeException("Invalid connector type");
        }
        try {
            if (!((SalesforceConnectorConnectionIdentifierAdapter) obj).isConnected()) {
                ((SalesforceConnectorConnectionIdentifierAdapter) obj).connect(((SalesforceConnectorConnectionKey) key).getUsername(), ((SalesforceConnectorConnectionKey) key).getPassword(), ((SalesforceConnectorConnectionKey) key).getSecurityToken(), ((SalesforceConnectorConnectionKey) key).getUrl(), ((SalesforceConnectorConnectionKey) key).getProxyHost(), ((SalesforceConnectorConnectionKey) key).getProxyPort(), ((SalesforceConnectorConnectionKey) key).getProxyUsername(), ((SalesforceConnectorConnectionKey) key).getProxyPassword());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void passivateObject(Object key, Object obj)
        throws Exception
    {
    }

}
