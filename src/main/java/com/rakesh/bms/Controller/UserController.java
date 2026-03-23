package com.rakesh.bms.Controller;


import com.rakesh.bms.Dto.TheaterDto;
import com.rakesh.bms.Dto.UserDto;
import com.rakesh.bms.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {


    @Autowired
    UserService userService;


    @PostMapping
    public ResponseEntity<UserDto> CreateUser(@Valid @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.CreateUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById( @PathVariable Long id)
    {
        return ResponseEntity.ok(userService.getUserById(id));
    }


    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //getAllUsers()


}
