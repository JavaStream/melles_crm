package com.javastream.melles_crm.auth;


import com.google.common.collect.Lists;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.javastream.melles_crm.security.UserRole.*;

@Repository("fake")
public class FakeAppUserDaoService implements AppUserDao {

    private final PasswordEncoder passwordEncoder;

    public FakeAppUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<AppUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    private List<AppUser> getApplicationUsers() {

        List<AppUser> userDetails = Lists.newArrayList(
                new AppUser(
                        ADMIN.getGrantedAuthorities(),
                        passwordEncoder.encode("pas"),
                        "admin",
                        true,
                        true,
                        true,
                        true
                ),

                new AppUser(
                        SALE.getGrantedAuthorities(),
                        passwordEncoder.encode("pas"),
                        "sale",
                        true,
                        true,
                        true,
                        true
                ),

                new AppUser(
                        STORE.getGrantedAuthorities(),
                        passwordEncoder.encode("pas"),
                        "store",
                        true,
                        true,
                        true,
                        true
                )
        );

        return userDetails;
    }
}
