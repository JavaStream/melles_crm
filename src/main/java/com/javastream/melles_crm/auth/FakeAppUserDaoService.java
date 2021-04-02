package com.javastream.melles_crm.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.javastream.melles_crm.security.UserRole.*;

@Repository("fake")
public class FakeAppUserDaoService implements AppUseDao {

    private final PasswordEncoder passwordEncoder;


    public FakeAppUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<AppUser> selectAppUserByUsername(String username) {
        return getAppUsers()
                .stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst();
    }

    private List<AppUser> getAppUsers() {
        List<AppUser> appUsers = List.of(
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
                        ADMIN.getGrantedAuthorities(),
                        passwordEncoder.encode("pas"),
                        "admin",
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

        return appUsers;
    }
}
