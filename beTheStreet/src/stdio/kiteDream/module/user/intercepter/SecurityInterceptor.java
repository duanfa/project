package stdio.kiteDream.module.user.intercepter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

public class SecurityInterceptor  extends HttpServlet implements Filter {
    private static final long serialVersionUID = 1L;

    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
             HttpServletRequest request=(HttpServletRequest)arg0;  
           HttpServletResponse response  =(HttpServletResponse) arg1;   
           HttpSession session = request.getSession(true);    
           String user= (String)session.getAttribute("user");//µÇÂ¼ÈË½ÇÉ«
           String url=request.getRequestURI();  
           if(StringUtils.isBlank(user)) {     
                if(url!=null && !url.equals("") && ( url.indexOf("Login")<0 && url.indexOf("login")<0 ) && ( url.indexOf("policy")<0)) {  
                    response.sendRedirect(request.getContextPath() + "/login.html");  
                    return ; 
                }
            }
            arg2.doFilter(arg0, arg1);  
            return;  
    }
    
    public void init(FilterConfig arg0) throws ServletException {
    	
    }

}