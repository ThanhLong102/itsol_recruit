package com.itsol.recruit.web;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Constants.Api.Path.PUBLIC)

public class UserController {

   public final UserService userService ;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public ResponseEntity<List<User>> getAllUser(){
        return  ResponseEntity.ok().body( userService.getAllUser());
    }

    @GetMapping(value = "/user/id={id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id){
        return  ResponseEntity.ok().body( userService.findById(id));
    }

    @GetMapping(value = "/user/username={username}")
    public ResponseEntity<User> findUserByUserName(@PathVariable String username){
        return  ResponseEntity.ok().body( userService.findUserByUserName(username));
    }

    @GetMapping(value = "/user/role={role}")
    public ResponseEntity<List<User>> findUserByURole(@PathVariable String role){
        return  ResponseEntity.ok().body( userService.findUserByRole(role));
    }
}
