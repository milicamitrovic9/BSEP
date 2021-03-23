package com.ftn.bsep.model;

import java.security.cert.X509Extension;
import java.util.Set;


public class Extension  implements X509Extension {

    private  int extensionID;
    private boolean critical;
    private String  extensionValue;

    public Extension(int extensionID, boolean critical, String extensionValue) {
        this.extensionID = extensionID;
        this.critical = critical;
        this.extensionValue = extensionValue;
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        return false;
    }

    @Override
    public Set<String> getCriticalExtensionOIDs() {
        return null;
    }

    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        return null;
    }

    @Override
    public byte[] getExtensionValue(String oid) {
        return new byte[0];
    }
}
