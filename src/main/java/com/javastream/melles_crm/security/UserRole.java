package com.javastream.melles_crm.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.javastream.melles_crm.security.UserPermission.*;

public enum UserRole {
    ADMIN(Sets.newHashSet(ORDERS_READ, ORDERS_WRITE, CLIENTS_READ, CLIENTS_WRITE,
            REMAINING_READ, STORE_READ, STORE_WRITE, SETTINGS_READ, SETTINGS_WRITE)),

    SALE(Sets.newHashSet(ORDERS_READ, ORDERS_WRITE, CLIENTS_READ, CLIENTS_WRITE, REMAINING_READ)),

    STORE(Sets.newHashSet(REMAINING_READ, STORE_READ, STORE_WRITE));


    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
