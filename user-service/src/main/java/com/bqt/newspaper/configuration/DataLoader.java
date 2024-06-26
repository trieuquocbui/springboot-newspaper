package com.bqt.newspaper.configuration;

import com.bqt.newspaper.constant.RoleConstant;
import com.bqt.newspaper.entities.Role;
import com.bqt.newspaper.entities.SocialProvider;
import com.bqt.newspaper.entities.User;
import com.bqt.newspaper.repository.RoleRepository;
import com.bqt.newspaper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${account.username}")
    private String username;

    @Value("${account.password}")
    private String password;

    @Value("${account.fullname}")
    private String fullName;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean isUserRole = roleRepository.existsById(RoleConstant.USER);
        if(!isUserRole){
            Role role = Role.builder()
                    .id(RoleConstant.USER)
                    .name(RoleConstant.USER)
                    .build();
            roleRepository.save(role);
        }

        boolean isAdminRole = roleRepository.existsById(RoleConstant.ADMIN);
        if(!isAdminRole){
            Role role = Role.builder()
                    .id(RoleConstant.ADMIN)
                    .name(RoleConstant.ADMIN)
                    .build();
            roleRepository.save(role);
        }

        boolean isAdminAccount = userRepository.existsById(username);
        if(!isAdminAccount){
            Role adminRole = roleRepository.findById(RoleConstant.ADMIN).orElse(
                    Role.builder()
                    .id(RoleConstant.ADMIN)
                    .name(RoleConstant.ADMIN)
                    .build()
            );

            User adminAccount = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .fullName(fullName)
                    .socialProvider(SocialProvider.NONE)
                    .role(adminRole)
                    .build();

            userRepository.save(adminAccount);
        }
    }
}
