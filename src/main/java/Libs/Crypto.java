package Libs;

import java.security.MessageDigest;

/**
 * Created by matan on 06/10/15.
 */
public class Crypto
{
    public static String md5Hash(String text)
    {
        try{
        return new String(MessageDigest.getInstance("md5").digest(text.getBytes()));
        }catch (Exception e)
        {
            return "";
        }
    }
}
