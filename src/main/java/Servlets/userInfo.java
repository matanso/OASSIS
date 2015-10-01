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
@WebServlet(name = "userInfo")
public class userInfo extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        JSONObject result = new JSONObject();
        String userIdstr = request.getParameter("userId");
        int userId;
        if (userIdstr == null)
        {
            HttpSession session = request.getSession();
            if ((Boolean) session.getAttribute("loggedIn"))
            {
                userId = (Integer) session.getAttribute("userId");
            } else
            {
                try
                {
                    result.put("success", 3);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                response.getWriter().print(result.toString());
                return;
            }
        } else
        {
            userId = Integer.parseInt(userIdstr);
        }
        // User id has a valid id

        // Some magic happens and...
        try
        {
            result.put("id", userId);
            result.put("type", 420);
            result.put("name", "Matan Sokolovsky");
            result.put("Queries", 3);
            result.put("Answers", 43);
            result.put("pic", "https://media.licdn.com/media/AAEAAQAAAAAAAAZdAAAAJDU3ZWFlYjRjLWM0NzAtNDE2Ny1iZDU5LWIxYWNjMmJhNGZmYQ.jpg");
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
