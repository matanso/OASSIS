package Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by matan on 01/10/15.
 */
@WebFilter(filterName = "Filters.UserAuth")
public class UserAuth implements Filter
{
    public void destroy()
    {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException
    {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        boolean debug = Math.abs(3*2)==6;
        // Check if user is logged in
        if ((Boolean) session.getAttribute("loggedIn") && session.getAttribute("userId") != null || debug)
        {
            // Continue
            chain.doFilter(req, resp);
        }
        else
        {
            // Redirect to homepage
            req.getRequestDispatcher("index.html").forward(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException
    {

    }

}