package com.lc.tax.controller;


import com.lc.tax.model.User;
import com.lc.tax.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: pingfuli
 * @Description:
 * @Date:Created at 2017/8/10 下午10:21
 * @Modified By:
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;
    @RequestMapping("/showUser.do")
    public void selectUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        long userId = Long.parseLong(request.getParameter("id"));
        User user = this.userService.selectUser(userId);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(""+user.getRegTime()+","+user.getEmail());
        response.getWriter().write(mapper.writeValueAsString(user));
        response.getWriter().close();
    }
}