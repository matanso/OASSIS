package Servlets;

import Libs.LoginVerify;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by matan on 01/10/15.
 */
@WebServlet(name = "login")
public class login extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        JSONObject result = new JSONObject();

        if(LoginVerify.validateLogin(request))
        {
            session.setAttribute("loggedIn", true);
            session.setAttribute("userId", 1);
            try
            {
                result.put("success", 0);
                result.put("userId", 1);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        else{
            try
            {
                result.put("success", 2);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        response.getWriter().print(result.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
