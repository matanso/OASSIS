package Servlets;

import Libs.mysqlConnector;
import Libs.success_codes;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by matan on 01/10/15.
 */
@WebServlet(name = "submitQuery")
public class submitQuery extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        JSONObject result = new JSONObject();
        if (!(session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn")))  // Check if user is logged in
        {
            try
            {
                result.put("success", 2);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            response.getWriter().print(result.toString());
            return;
        }
        boolean success = false;
        try
        {
            mysqlConnector connector = new mysqlConnector();
            success = connector.submitQuery(request.getParameter("name"), (Integer) session.getAttribute("userId"), request.getParameter("sparql"));
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            result.put("success", success ? success_codes.SUCCESS : success_codes.SERVER_ERROR);
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
