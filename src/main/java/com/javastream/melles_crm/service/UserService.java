package com.javastream.melles_crm.service;

import com.javastream.melles_crm.model.User;
import com.javastream.melles_crm.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        List<User> sortedUsers = users.stream().sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
        return sortedUsers;
    }

    public User findById(String id) {
        return userRepository.findById(Long.parseLong(id)).orElseThrow(IllegalStateException::new);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void deleteById(String id)  {
       long userId = Long.parseLong(id);
        userRepository.deleteById(userId);
    }
}
