package com.bk.service;

import com.bk.entity.Role;
import com.bk.entity.User;
import com.bk.repo.AuthLogRepository;
import com.bk.repo.RoleRepository;
import com.bk.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthLogRepository authLogRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public void syncUser() {
        List<User> users = new ArrayList<>();
        List<Integer> userIds = authLogRepository.findDistinctUserIds();
        Role userRole = roleRepository.findById(3L).get();
        for (Integer userId : userIds) {
            User user = new User();
            user.setId(Long.valueOf(userId));
            user.setUsername("username_" + userId);
            user.setPassword("password_" + userId);
            user.setRole(userRole);
            users.add(user);
        }
        userRepository.saveAll(users);
    }
}
