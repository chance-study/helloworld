package org.chance.spring.feature.springboot;

import org.chance.spring.feature.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-24 14:50:11
 */
@Controller
@SessionAttributes(value = {"user"})
public class HelloController {

    @Autowired(required = false)
    HelloService helloService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @RequestMapping("/login")
    public String login(Model model, HttpSession session) {
        model.addAttribute("user", "model");
        session.setAttribute("sessionUser", "sessionUser");
        return "login...";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session, SessionStatus sessionStatus) {
        session.removeAttribute("user");
        // 清除session
        sessionStatus.setComplete();
        return "redirect:/login";
    }

    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        System.out.println(helloService); //com.fsx.service.HelloServiceImpl@512663b0
        return "hello...";
    }

}
