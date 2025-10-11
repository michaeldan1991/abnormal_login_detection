package com.bk.service;

import com.bk.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}