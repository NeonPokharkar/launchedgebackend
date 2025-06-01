package org.launch.edge.service;

import org.launch.edge.entities.User;

import java.util.List;

public interface UserService {
    public List<String> getUsers();
    public boolean addUser(String username, String password);
    public boolean authenticateUser(String username, String password);
    public boolean userExists(String username);
}
