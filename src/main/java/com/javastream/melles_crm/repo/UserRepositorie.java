package com.javastream.melles_crm.repo;

import com.javastream.melles_crm.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositorie extends CrudRepository<User, Long> {
    List<User> findAll();
}
