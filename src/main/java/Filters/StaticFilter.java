package Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by matan on 01/10/15.
 */
@WebFilter(filterName = "StaticFilter")
public class StaticFilter implements Filter
{
    public void destroy()
    {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException
    {
        HttpServletRequest request = (HttpServletRequest) req;
        String path = request.getRequestURI().substring(request.getContextPath().length());

        if (path.startsWith("/app")) {
            chain.doFilter(req, resp); // Goes to default servlet.
        } else {
            request.getRequestDispatcher("/pages" + path).forward(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException
    {

    }

}
