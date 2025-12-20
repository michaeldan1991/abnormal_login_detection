package com.bk.controller;

import com.bk.dto.LoginDto;
import com.bk.service.AuthService;
import com.bk.service.FileService;
import com.bk.service.UserService;
import com.bk.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/auth/login")
    public String loing(@RequestBody LoginDto dto) {
        return authService.login(dto);
    }

    @GetMapping("/auth/logs")
    public Map<String, Object> auditLog(@RequestParam("model") String model, @RequestParam("type") int type) {
        return authService.report(model, type);
    }

    @PostMapping("/upload")
    public String upload() {
        fileService.uploadCsv(Constants.CSV_UPLOAD_FILE_PATH);
        return "ok";
    }

    @PostMapping("/sync-user")
    public String syncUser() {
        fileService.uploadCsv(Constants.CSV_UPLOAD_FILE_PATH);
        return "ok";
    }

    @PostMapping("/trigger")
    public String trigger() {
        userService.detectAbnormalParallel();
        return "ok";
    }

}
