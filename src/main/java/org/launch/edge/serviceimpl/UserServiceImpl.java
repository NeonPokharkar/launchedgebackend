package org.launch.edge.serviceimpl;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.launch.edge.entities.User;
import org.launch.edge.repositories.UserRepository;
import org.launch.edge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<String> getUsers() {
        List<User> data = userRepository.findAll();

        log.info("{}", (new Gson()).toJson(data));
        return data.stream().map(user -> {
            log.info("{}",user.getUserName());
            return user.getUserName();
        }).toList();
    }

    @Override
    public boolean addUser(String username, String password) {
        List<User> usersAlreadyExisting = userRepository.findByUserName(username);

        if(!usersAlreadyExisting.isEmpty())
        {
            log.warn("{} username already exists",username);
            return false;
        }

        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        User userSaved =  userRepository.save(user);
        return true;
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        return !userRepository.checkMatchingUserNameAndPass(username, password).isEmpty();
    }

    @Override
    public boolean userExists(String username) {
        List<String> users = getUsers();

        return users.contains(username);
    }
}
