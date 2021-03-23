package com.javastream.melles_crm.auth;

import java.util.Optional;

public interface AppUserDao {
    Optional<AppUser> selectApplicationUserByUsername(String username);
}
