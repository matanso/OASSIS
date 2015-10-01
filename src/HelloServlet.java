import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by matan on 01/10/15.
 */
public class HelloServlet extends javax.servlet.http.HttpServlet
{
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException
    {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException
    {
        PrintWriter writer = response.getWriter();
        writer.print("Success");
    }
}
