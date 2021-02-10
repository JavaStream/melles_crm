package com.javastream.melles_crm.service;

import com.javastream.melles_crm.model.User;
import com.javastream.melles_crm.repo.UserRepositorie;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepositorie userRepositorie;

    public UserService(UserRepositorie userRepositorie) {
        this.userRepositorie = userRepositorie;
    }

    public List<User> findAll() {
        List<User> users = userRepositorie.findAll();
        List<User> sortedUsers = users.stream().sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
        return sortedUsers;
    }

    public User findById(String id) {
        return userRepositorie.findById(Long.parseLong(id)).orElseThrow(IllegalStateException::new);
    }

    public void save(User user) {
        userRepositorie.save(user);
    }

    public void deleteById(String id)  {
       long userId = Long.parseLong(id);
        userRepositorie.deleteById(userId);
    }
}
