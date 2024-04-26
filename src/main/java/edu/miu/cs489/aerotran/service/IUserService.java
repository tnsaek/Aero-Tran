package edu.miu.cs489.aerotran.service;

import edu.miu.cs489.aerotran.dto.UserDto;

public interface IUserService {
    public UserDto addUser(UserDto userDto);
    public UserDto getUser(Long userId);
    public UserDto updateUser(Long userId, UserDto userDto);
    public void deleteUser(Long userId);
}
