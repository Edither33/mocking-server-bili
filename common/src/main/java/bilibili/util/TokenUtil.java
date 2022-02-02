package bilibili.util;


import bilibili.exception.ConditionException;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;

import java.security.KeyPair;
import java.util.Calendar;

public class TokenUtil {
    private static final String ISSUER = "day day up";
    public static final String USERID = "userId";

    /**
     * 生成令牌
     * @param id 用户id
     * @return 令牌
     * @throws Exception
     */
    public static String generateToken(Long id) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);

        JWT rs256 = JWT.create()
                .setSigner("RS256"
                        , new KeyPair(RSAUtil.getPublicKey(),RSAUtil.getPrivateKey()))
                .setExpiresAt(calendar.getTime())
                .setIssuer(ISSUER)
                .setPayload(USERID, String.valueOf(id));

        return rs256.sign();
    }

    /**
     * 验证令牌是否有效
     * @param token 令牌
     * @return 是否有效
     * @throws Exception
     */
    public static boolean verifyToken(String token) throws Exception {
        JWTSigner rs256 = JWTSignerUtil
                .createSigner("RS256"
                        , new KeyPair(RSAUtil.getPublicKey(),RSAUtil.getPrivateKey()));
        return JWTUtil.verify(token, rs256);
    }

    /**
     * 判断token是否过期
     * @param token 令牌
     * @return 是否过期
     */
    public static boolean isExpire(String token) {
        try{
            JWTSigner rs256 = JWTSignerUtil
                    .createSigner("RS256"
                            , new KeyPair(RSAUtil.getPublicKey(),RSAUtil.getPrivateKey()));
            JWT jwt = JWTUtil.parseToken(token).setSigner(rs256);

            JWTValidator validator = JWTValidator.of(jwt);
            validator.validateDate();
            validator.validateAlgorithm(rs256);
        }catch (ValidateException e) {
            return true;
        } catch (Exception e){
            throw new ConditionException("55501", "签名算法生成失败");
        }
        return false;
    }

    /**
     * 获取JWT信息
     * @param token 令牌
     * @return 令牌中携带的信息
     * @throws Exception
     */
    public static JWT getJWTInfo(String token) {
        JWT jwt = null;
        try {
            jwt = JWTUtil.parseToken(token);
        } catch (Exception e) {
            throw new ConditionException("55502", "非法的token");
        }
        return jwt;
    }

    public static void main(String[] args) throws Exception {
        String token = TokenUtil.generateToken(1L);
        System.out.println(token);
        System.out.println(verifyToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJleHAiOjE2NDM0Mjg0MzksImlzcyI6ImRheSBkYXkgdXAiLCJ1c2VySWQiOiIxIn0.HC6Fu60xV2IaMBevP3fRyX2WwEWMAdv4aZV0-598wlr_vHjni7OOZMMyspjMfGKTVpcDhgEW0rOtjzmUdQYeziAr0mwkvcLRDtnrCBmcOGEjQlDzrHjYCe1ii2w4oJaAfGwN7n1tQbYn0WeuLyWwKLKiiG9BpcX35_FOYLH2JTg"));
        System.out.println(isExpire("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJleHAiOjE2NDM0Mjg0MzksImlzcyI6ImRheSBkYXkgdXAiLCJ1c2VySWQiOiIxIn0.HC6Fu60xV2IaMBevP3fRyX2WwEWMAdv4aZV0-598wlr_vHjni7OOZMMyspjMfGKTVpcDhgEW0rOtjzmUdQYeziAr0mwkvcLRDtnrCBmcOGEjQlDzrHjYCe1ii2w4oJaAfGwN7n1tQbYn0WeuLyWwKLKiiG9BpcX35_FOYLH2JTg"));
        System.out.println(getJWTInfo(token).getPayload(USERID));
    }

    public static String generateRefreshToken(Long id) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);

        JWT rs256 = JWT.create()
                .setSigner("RS256"
                        , new KeyPair(RSAUtil.getPublicKey(),RSAUtil.getPrivateKey()))
                .setExpiresAt(calendar.getTime())
                .setIssuer(ISSUER)
                .setPayload(USERID, String.valueOf(id));

        return rs256.sign();
    }
}
