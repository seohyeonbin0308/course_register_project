package service;

import controller.dto.UserResponseDto;
import controller.dto.UserSignUpDto;
import controller.dto.UserUpdateRequestDto;
import domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.MajorRepository;
import repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final MajorRepository majorRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Long join(UserSignUpDto signUpDto){
        userRepository.findByLoginId((signUpDto.getLoginId()))
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Failed: Already Exist ID!");
                });

        if(!signUpDto.getPassword().equals(signUpDto.getPasswordConfirm())){
            throw new IllegalArgumentException("Failed: Check Password!");
        }

        User user = User.signupBuilder()
                .loginId(signUpDto.getLoginId())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .username(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .phoneNumber(signUpDto.getPhoneNumber())
                .major(majorRepository.findById(signUpDto.getMajorId()).get())
                .build();

        return userRepository.save(user).getUserId();
    }

    public UserResponseDto findById(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Failed: No User Info"));

        return new UserResponseDto(user);
    }

    @Transactional
    public Long update(Long userId, UserUpdateRequestDto requestDto){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Failed: No User Info"));

        user.update(requestDto);

        return userId;
    }
}
