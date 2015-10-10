package Servlets;

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
@WebServlet(name = "Init")
public class Init extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean success = false;
        mysqlConnector connector = null;
        try
        {
            connector = new mysqlConnector();
            connector.init();
            success = true;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        JSONObject result = new JSONObject();
        try
        {
            result.put("success", success ? 0 : 1);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        response.getWriter().print(result.toString());
    }
}
