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
        String path = request.getRequestURI().substring(request.getContextPath().length());
        try
        {
            if ((Boolean) session.getAttribute("loggedIn") && session.getAttribute("userId") != null)
            {
                // Continue
                chain.doFilter(req, resp);
            }
            else if(path.startsWith("/pages/assets/") || path.startsWith("/pages/images/") || path.startsWith("/pages/index.html"))
            {
                chain.doFilter(req, resp);
            }
            else
            {
                // Redirect to homepage
                req.getRequestDispatcher("index.html").forward(req, resp);
            }
        }
        catch (NullPointerException e)
        {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException
    {

    }

}