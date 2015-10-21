package Servlets;

import Libs.mysqlConnector;
import Libs.success_codes;
import org.json.JSONArray;
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
@WebServlet(name = "queries")
public class Queries extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        JSONObject result = new JSONObject();
        if (session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn"))
        {
            try
            {
                mysqlConnector connector = new mysqlConnector();
                JSONArray queries = new JSONArray();
                for (Structs.Query query : connector.getQueries((Integer) session.getAttribute("userId")))
                {
                    queries.put(query.toJSON());
                }
                result.put("queries", queries);
                result.put("success", success_codes.SUCCESS);
            } catch (SQLException e)
            {
                e.printStackTrace();
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        } else
        {
            try
            {
                result.put("success", success_codes.CREDENTIAL_ERROR);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        response.getWriter().print(result.toString());
    }
}
