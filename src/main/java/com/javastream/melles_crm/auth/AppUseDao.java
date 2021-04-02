package com.javastream.melles_crm.auth;

import java.util.Optional;

public interface AppUseDao {

    Optional<AppUser> selectAppUserByUsername(String username);
}
