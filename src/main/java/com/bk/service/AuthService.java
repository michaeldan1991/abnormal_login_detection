package com.bk.service;

import com.bk.dto.LoginDto;

import java.util.Map;

public interface AuthService {
    String login(LoginDto dto);
    Map<String, Object> report(String model, int type);
}
