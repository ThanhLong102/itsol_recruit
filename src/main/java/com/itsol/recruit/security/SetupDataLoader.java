package com.itsol.recruit.security;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.entity.Role;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.AuthenticateRepository;
import com.itsol.recruit.repository.RoleRepository;
import com.itsol.recruit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticateRepository authenticateRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }
        if (userRepository.findByUserName("admin") == null) {
            Set<Role> adminRole = roleRepository.findByCode(Constants.Role.ADMIN);
            User user = new User();
            user.setUserName("admin");
            user.setName("admin");
            user.setEmail("zexaldai@gmail.com");
            user.setPhoneNumber("0961130568");
            user.setBirthDay(new Date(2001 - 03 - 24));
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRoles(adminRole);
            user.setActive(true);
            user.setDelete(false);
            userRepository.save(user);
            System.out.println("Admin: " + user.toString());
            alreadySetup = true;
        }
    }
}