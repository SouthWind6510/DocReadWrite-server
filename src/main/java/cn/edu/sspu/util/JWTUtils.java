package cn.edu.sspu.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;

public class JWTUtils {
    private static final String SING = "!@#$%^^3e4";
    public static String getToken (String studentSno,String studentName){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,7);
        return JWT.create()
                .withClaim("studentSno",studentSno)
                .withClaim("studentName",studentName)
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SING));
    }

    public static DecodedJWT verifyToken(String token){
        try {
           return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        }catch (Exception ex){
            return null;
        }
    }
}
