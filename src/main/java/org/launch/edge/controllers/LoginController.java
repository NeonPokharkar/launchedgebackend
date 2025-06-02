package org.launch.edge.controllers;

import com.google.gson.Gson;
import org.launch.edge.entities.User;
import org.launch.edge.models.ResponseModel;
import org.launch.edge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/users")
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>((new Gson()).toJson(userService.getUsers()), HttpStatus.OK);
    }

    @PostMapping(value = "/user")
    public ResponseEntity<?> addUser(@RequestBody User user)
    {
        if(userService.addUser(user.getUserName(), user.getPassword()))
        {
            return new ResponseEntity<>(new ResponseModel("User Added Successfully"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseModel("There was some error!"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "/auth")
    public ResponseEntity<?> authenticateUser(@RequestBody User user)
    {
        if(userService.userExists(user.getUserName()))
        {
            if(userService.authenticateUser(user.getUserName(), user.getPassword()))
            {
                return new ResponseEntity<>(new ResponseModel("User Validated Successfully"), HttpStatus.OK);
            }

            return new ResponseEntity<>(new ResponseModel("Invalid Password!"), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(new ResponseModel("Username does not exist!"), HttpStatus.BAD_REQUEST);
    }
}
