package Servlets;

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
@WebServlet(name = "loggedInCheck")
public class loggedInCheck extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        JSONObject result = new JSONObject();
        try
        {
            result.put("success", 0);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        if(session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn"))
        {
            try
            {
                result.put("loggedIn", true);
                result.put("userId", (Integer) session.getAttribute("userId"));
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                result.put("loggedIn", false);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        response.getWriter().print(result.toString());
    }
}
