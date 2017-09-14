package com.lc.tax.controller;


import com.lc.tax.dao.hx_zs.ZsJksMapper;
import com.lc.tax.pojo.hx_zs.ZsJks;
import com.lc.tax.service.IZsJksMapperService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IZsJksMapperService zsJksMapperService;
    @RequestMapping("/showUser.do")
    public void selectUser(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String spuuid = request.getParameter("id").toString();
        ZsJks zsJks = zsJksMapperService.selectByPrimaryKey(spuuid);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(""+zsJks.getZsuuid()+","+zsJks.getCkzhzhuuid());
        response.getWriter().write(mapper.writeValueAsString(zsJks));
        response.getWriter().close();
    }
}