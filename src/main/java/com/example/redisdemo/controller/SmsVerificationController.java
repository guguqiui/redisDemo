package com.example.redisdemo.controller;

import com.example.redisdemo.entity.SendSmsRequest;
import com.example.redisdemo.service.SmsVerificationService;
import com.example.redisdemo.entity.VerifySmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SmsVerificationController {

    @Autowired
    private SmsVerificationService smsVerificationService;

    // 发送短信验证码接口
    @PostMapping("/sendSmsVerificationCode")
    public String sendSmsVerificationCode(@RequestBody SendSmsRequest request) {
        String code = smsVerificationService.sendSmsVerificationCode(request.getSessionId());
        return "短信验证码已发送：" + code;
    }

    // 验证短信验证码接口
    @PostMapping("/verifySmsVerificationCode")
    public String verifySmsVerificationCode(@RequestBody VerifySmsRequest request) {
        boolean result = smsVerificationService.verifySmsVerificationCode(request.getSessionId(), request.getCode());
        if (result) {
            return "短信验证码验证通过";
        } else {
            return "短信验证码验证失败";
        }
    }
}

