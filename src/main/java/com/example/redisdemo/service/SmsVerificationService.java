package com.example.redisdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class SmsVerificationService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 发送短信验证码并存储到Redis中
    public String sendSmsVerificationCode(String sessionId) {
        // 生成6位随机数字验证码
        String code = generateRandomCode(6);

        // 将验证码存储到Redis中，设置有效期为3分钟
        redisTemplate.opsForValue().set(sessionId, code, 3, TimeUnit.MINUTES);

        // 返回生成的验证码
        return code;
    }

    // 验证短信验证码
    public boolean verifySmsVerificationCode(String sessionId, String code) {
        // 从Redis中获取验证码
        String storedCode = redisTemplate.opsForValue().get(sessionId);

        // 判断验证码是否存在且与用户传入的验证码匹配
        return storedCode != null && storedCode.equals(code);
    }

    // 生成指定长度的随机数字验证码
    private String generateRandomCode(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // 生成0-9之间的随机数字
        }
        return sb.toString();
    }
}

