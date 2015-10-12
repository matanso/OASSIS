package Servlets;

import Libs.Crypto;
import Libs.mysqlConnector;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by matan on 10/10/15.
 */
@WebServlet(name = "Signup")
public class Signup extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        JSONObject result = new JSONObject();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        try
        {
            mysqlConnector connector = new mysqlConnector();
            boolean success = connector.signup(email, Crypto.md5Hash(email + password), name);
            if (success)
                result.put("success", 0);
            else
                result.put("success", 2);
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        response.getWriter().print(result.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
