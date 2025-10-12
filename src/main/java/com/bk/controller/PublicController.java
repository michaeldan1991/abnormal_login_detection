package com.bk.controller;

import com.bk.service.FileService;
import com.bk.service.UserService;
import com.bk.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String home() {
        return "ok";
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
        userService.detectAbnormal();
        return "ok";
    }

}
