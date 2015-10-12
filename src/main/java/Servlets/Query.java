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
 * Created by matan on 12/10/15.
 */
@WebServlet(name = "Query")
public class Query extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        JSONObject result = new JSONObject();
        int success_code;
        if(session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn"))
        {
            if(request.getParameter("queryId") != null)
            {
                try
                {
                    mysqlConnector connector = new mysqlConnector();
                    result = connector.getQuery(Integer.parseInt(request.getParameter("queryId"))).toJSON();
                    success_code = success_codes.SUCCESS;
                } catch (SQLException e)
                {
                    success_code = success_codes.SERVER_ERROR;
                    e.printStackTrace();
                }
            }
            else
            {
                success_code = success_codes.INVALID_INPUT;
            }
        }
        else
        {
            success_code = success_codes.CREDENTIAL_ERROR;
        }
        try
        {
            result.put("success", success_code);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        response.getWriter().print(result.toString());
    }
}
