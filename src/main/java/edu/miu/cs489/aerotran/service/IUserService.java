package edu.miu.cs489.aerotran.service;

import edu.miu.cs489.aerotran.dto.UserDto;
import edu.miu.cs489.aerotran.exception.UserAlreadyExistsException;

public interface IUserService {
    public UserDto addUser(UserDto userDto) throws UserAlreadyExistsException;
    public UserDto getUser(Long userId);
    public UserDto updateUser(Long userId, UserDto userDto);
    public void deleteUser(Long userId);
}
