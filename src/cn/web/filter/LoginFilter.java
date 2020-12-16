package cn.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //1.强转req
        HttpServletRequest request = (HttpServletRequest) req;
        //2.获取资源路径
        String URI = request.getRequestURI();
        //3.判断路径是否为登录相关的路径，如：登录界面 cs/js/fonts 验证码等
        if (URI.contains("/login.jsp") || URI.contains("/loginServlet") || URI.contains("/css/") || URI.contains("/js/") || URI.contains("/fonts/") || URI.contains("/checkCodeServlet")) {
            //是，放行
            chain.doFilter(req, resp);
        } else {
           //判断是否登录了
            Object user = request.getSession().getAttribute("user");
            if (user != null) {
                //已经登录了
                chain.doFilter(request, resp);
            } else {
                //没有登录，拦截
                request.setAttribute("login_msg", "您还没有登录，请登录！");
                request.getRequestDispatcher("/login.jsp").forward(request, resp);
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }


}
