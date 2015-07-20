package wmc.cn.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-3-20
 * Time: 上午12:33
 * To change this template use File | Settings | File Templates.
 */
public class MainFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String reqUrl = request.getRequestURI();
        if (!reqUrl.endsWith("checkLogin.do") && session.getAttribute("admin") == null &&!reqUrl.contains("/dfw/dfw/")) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
