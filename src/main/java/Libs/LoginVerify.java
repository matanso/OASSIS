package Libs;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Created by matan on 01/10/15.
 */
public class LoginVerify
{
    /*
     Returns a userId or -1 if authorization failed.
     */
    public static int validateLogin(HttpServletRequest request)
    {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        try
        {
            return new mysqlConnector().loginVerify(email, Crypto.md5Hash(email + password));
        } catch (SQLException e)
        {
            e.printStackTrace();
            return -1;
        }
    }
}
