package com.javastream.melles_crm.security;

public enum UserPermission {
    ORDERS_READ("orders:read"),
    ORDERS_WRITE("orders:write"),
    CLIENTS_READ("clients:read"),
    CLIENTS_WRITE("clients:write"),
    REMAINING_READ("remaining:read"),
    STORE_READ("store:read"),
    STORE_WRITE("store:write"),
    SETTINGS_READ("settings:read"),
    SETTINGS_WRITE("settings:write");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
