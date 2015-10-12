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
        if (session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn"))
        {
            System.out.println("Authorized");
            // Continue
            chain.doFilter(req, resp);
        } else if (path.startsWith("/pages/assets/") || path.startsWith("/pages/images/") || path.startsWith("/pages/index.html") || path.startsWith("/pages/login.html") || path.startsWith("/app/login"))
        {
            System.out.println("Public");
            chain.doFilter(req, resp);
        } else
        {
            System.out.println("Unauthorized, redirecting..." + path);
            // Redirect to homepage
            req.getRequestDispatcher("/pages/index.html").forward(req, resp);
        }

    }

    public void init(FilterConfig config) throws ServletException
    {

    }

}