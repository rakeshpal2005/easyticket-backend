package com.rakesh.bms.Service;

import com.rakesh.bms.Dto.UserDto;
import com.rakesh.bms.Exception.ResourceNotFoundException;
import com.rakesh.bms.Model.User;
import com.rakesh.bms.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;


    public UserDto CreateUser(UserDto userDto){
        User user = MaptoEntity(userDto);
        User saveuser = userRepository.save(user);
        return MapToDto(saveuser);
    }

    public UserDto getUserById(Long id)
    {
        User user=userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Use not found with id: "+id));
        return MapToDto(user);
    }

    public List<UserDto> getAllUsers()
    {
        List<User> users=userRepository.findAll();
        return users.stream()
                .map(this::MapToDto)
                .collect(Collectors.toList());
    }

    private User MaptoEntity(UserDto userDto){

        User user=new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(userDto.getPassword());
        return  user;
    }

    private UserDto MapToDto(User user)
    {
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        return userDto;
    }
}