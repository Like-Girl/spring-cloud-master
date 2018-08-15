package cn.likegirl.rt.config.security.service;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderService extends Md5PasswordEncoder {

    /**
     * the number of iterations which will be executed on the hashed.(散列上执行的迭代次数)
     */
    private final static int DEFAULT_HASH_ITERATIONS = 1024;

    public PasswordEncoderService(){
        super.setIterations(DEFAULT_HASH_ITERATIONS);
    }

    @Override
    public String encodePassword(String rawPass, Object salt) {
        return super.encodePassword(rawPass, salt);
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        return super.isPasswordValid(encPass, rawPass, salt);
    }
}
