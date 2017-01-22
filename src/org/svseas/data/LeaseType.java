package org.svseas.data;

/**
 * Coded by Seong Chee Ken on 22/01/2017, 18:13.
 */
public enum LeaseType {
    CONTRACT ("contract"),
    OPERATION ("operation");

    private String leaseType;

    LeaseType(String leaseType) {
        this.leaseType = leaseType;
    }

    @Override
    public String toString() {
        return  leaseType;
    }
}
