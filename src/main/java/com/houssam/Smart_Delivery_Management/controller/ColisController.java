package com.houssam.Smart_Delivery_Management.controller;

import com.houssam.Smart_Delivery_Management.model.Colis;
import com.houssam.Smart_Delivery_Management.service.ColisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.util.List;

public class ColisController implements Controller {
    private ColisService colisService;

    public void setColisService(ColisService colisService) {
        this.colisService = colisService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String method = request.getMethod();
        if(method.equals("GET")){
            return list();
        }
        return null;
    }

    public ModelAndView list(){
        List<Colis> colis = colisService.findAll();

        ModelAndView mv = new ModelAndView("jsonView");
        mv.addObject("colis",colis);
        return mv;
    }
}
