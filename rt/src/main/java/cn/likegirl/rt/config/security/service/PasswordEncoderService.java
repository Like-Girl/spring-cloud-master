package cn.likegirl.rt.config.security.service;

import cn.likegirl.rt.model.User;
import cn.likegirl.rt.utils.PasswordEncoderUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *  密码凭证器（依赖shiro-core）
 *
 */
@Service
public class PasswordEncoderService extends Md5PasswordEncoder {

    /**
     * the number of iterations which will be executed on the hashed.(散列上执行的迭代次数)
     */
    private final static int DEFAULT_HASH_ITERATIONS = 1024;

    private final RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    public PasswordEncoderService(){
        super.setIterations(DEFAULT_HASH_ITERATIONS);
    }

    @Override
    public String encodePassword(String rawPass, Object salt) {
        return new SimpleHash(
                super.getAlgorithm(),
                rawPass,
                ByteSource.Util.bytes(salt),
                DEFAULT_HASH_ITERATIONS).toHex();
//        return super.encodePassword(rawPass, salt);
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        String pass1 = "" + encPass;
        String pass2 = new SimpleHash(
                super.getAlgorithm(),
                rawPass,
                ByteSource.Util.bytes(salt),
                DEFAULT_HASH_ITERATIONS).toHex();
        return PasswordEncoderUtils.equals(pass1, pass2);
//        return super.isPasswordValid(encPass, rawPass, salt);
    }

    /**
     * 生成盐值及密码
     */
    public void encryptPassword(User user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(
                super.getAlgorithm(),
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                DEFAULT_HASH_ITERATIONS).toHex();
        user.setPassword(newPassword);
    }

    public RandomNumberGenerator getRandomNumberGenerator() {
        return randomNumberGenerator;
    }
}
