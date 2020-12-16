package cn.web.servlet;

import cn.domain.User;
import cn.service.UserService;
import cn.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");

        //2.获取数据
        String verifycode = request.getParameter("verifycode");
        Map<String, String[]> map = request.getParameterMap();

        //3.验证码校验
        HttpSession session = request.getSession();
        String checkcode = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode.equalsIgnoreCase(verifycode)) {
            //验证码正确
            //4.封装User对象
            User user = new User();
            try {
                BeanUtils.populate(user, map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            //5.调用service查询
            UserService service = new UserServiceImpl();
            User loginUser = service.login(user);

            //6.判断是否登录成功
            if (loginUser != null) {
                //登录成功
                //将用户信息存入session
                session.setAttribute("user", loginUser);
                //重定向到list页面
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            } else {
                //登录失败
                request.setAttribute("login_msg", "用户名或密码错误");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }


        } else {
            //验证码错误
            request.setAttribute("login_msg", "验证码错误！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
