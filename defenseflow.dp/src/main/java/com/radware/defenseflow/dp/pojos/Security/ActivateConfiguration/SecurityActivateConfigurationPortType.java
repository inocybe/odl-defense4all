/**
 * SecurityActivateConfigurationPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.ActivateConfiguration;

public interface SecurityActivateConfigurationPortType extends java.rmi.Remote {

    /**
     * Updates active Security policies with all the changes, additions
     * or deletions made via the temporary Security policy database.
     */
    public void update_ActivePolicies() throws java.rmi.RemoteException;
}
