package cn.likegirl.rt;

import cn.likegirl.rt.utils.AESUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

/**
 * @author LikeGirl
 * @version v1.0
 * @title: AESTest
 * @description: TODO
 * @date 2018/12/4 21:00
 */
public class AESTest {
    private static final Logger logger = LoggerFactory.getLogger(AESTest.class);

    /**
     * 获取信息
     */
    public JSONObject getUserInfo(String encryptedData, String sessionkey, String iv){
        // 被加密的数据
        byte[] dataByte = Base64.decodeBase64(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decodeBase64(sessionkey);
        // 偏移量
        byte[] ivByte = Base64.decodeBase64(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            // 密钥
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");

//            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
//            parameters.init(new IvParameterSpec(ivByte));

            AlgorithmParameterSpec iv1 = new IvParameterSpec(ivByte);
            // 设置工作模式为加密模式，给出密钥和向量
            cipher.init(Cipher.DECRYPT_MODE, spec, iv1);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (NoSuchAlgorithmException e) {
            logger.info("NoSuchAlgorithmException{}" + e);
        } catch (NoSuchPaddingException e) {
            logger.info("NoSuchPaddingException{}" + e);
        } catch (IllegalBlockSizeException e) {
            logger.info("IllegalBlockSizeException{}" + e);
        } catch (BadPaddingException e) {
            logger.info("BadPaddingException{}" + e);
        } catch (UnsupportedEncodingException e) {
            logger.info("UnsupportedEncodingException{}" + e);
        } catch (InvalidKeyException e) {
            logger.info("InvalidKeyException{}" + e);
        } catch (InvalidAlgorithmParameterException e) {
            logger.info("InvalidAlgorithmParameterException{}" + e);
        } catch (NoSuchProviderException e) {
            logger.info("NoSuchProviderException{}" + e);
        }
        return null;
    }


    @Test
    public void test01(){
        String data = "zhdNQYybamYElEnJV3++ZGxQe+ve17HSXu4q6G1HnQs3rmZm/28kalzvakwHx6wSGMC7Z5ueDzKPcX8qsgsOKx/IKX+AjH8Om4+njZQYX5CCna+QAZ4OiYAESiWK7nUceyDxlpn8JNhNIKpSL8/cB6F/wZSFvOPZjQb0Kqqz0M0Mq4eFmB2YQcQS+6qD30Ggd5nkl48c8JHqnk162xZfiqhlHFj9RbpJ3Bbqf4IAewLiD+AH0XmoweBfnKGvqKtFUnZrbjMfqoJ7TdCJaeabcVfe/dXgb425k9B/XzeUa8aIcXDKju1/+Adv0FHt2Qu3OIVzoSlYFd+dfbODqKcPPQrYtzmnbJUXuUwxaG1UiwtX/dg4ob9gKnxvSB4sm/UChq1LPfKnldlEKMWBTuIqOfNuRVSyAplkTj/yXItoRb5Tv3ciVhaoZj31vesZgxxLFjKO+r4qNnULcyMcZD9Kfv9eSABN3kRn5zchP9rYoHxkeU1qG3RmobakwlMZc4K2AAASWMjP3DOZqxEbiJh/+Q";
        String key = "ZuQxAGk8HCERzaoAaKWSfg==";
        String iv = "n977Tm9wK67eWp6vrYAF2g";
        System.out.println(getUserInfo(data,key,iv));

//        System.out.println(AESUtil.decrypt(data,key));
    }

    @Test
    public void test02(){
        System.out.println(",,".split(",").length);
        System.out.println(",,".split(",")[0]);
    }

}
