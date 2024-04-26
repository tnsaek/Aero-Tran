package edu.miu.cs489.aerotran.service.impl;

import edu.miu.cs489.aerotran.dto.UserDto;
import edu.miu.cs489.aerotran.entity.User;
import edu.miu.cs489.aerotran.repository.UserRepository;
import edu.miu.cs489.aerotran.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return mapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User userToBeUpdated = userRepository.findById(userId).orElse(null);
        User user = mapper.map(userDto, User.class);
        user.setUserId(userToBeUpdated.getUserId());
        User savedUser = userRepository.save(user);
        return mapper.map(savedUser, UserDto.class);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
