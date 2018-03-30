package com.watent.mvc.controller;

import com.watent.mvc.annotation.Controller;
import com.watent.mvc.annotation.Qualifier;
import com.watent.mvc.annotation.RequestMapping;
import com.watent.mvc.service.HelloService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Hello Controller
 *
 * @author Dylan
 * @date 2018/3/30 10:17
 */
@Controller("/hello")
public class HelloController {

    @Qualifier("helloServiceImpl")
    HelloService helloService;

    @RequestMapping("/test")
    public String test(HttpServletRequest request, HttpServletResponse response, String param) {

        System.out.println(request.getRequestURI() + "test" + param);

        helloService.insert(null);

        try {
            PrintWriter pw = response.getWriter();
            pw.println("<html>");
            pw.println("<body>");
            pw.println("<h3>");
            pw.println("Hello ok");
            pw.println("</h3>");
            pw.println("</body>");
            pw.println("</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
