package com.ssm.demo.controller;


import com.ssm.demo.model.Switch;
import com.ssm.demo.service.define.SwitchListService;
import com.ssm.demo.util.ExcelUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

@Controller("searchSwitchController")
@RequestMapping("/searchSwitch")
public class SearchSwitchController {

    private static Logger logger = Logger.getLogger(SearchSwitchController.class);

    @Autowired
    SwitchListService service;

    @RequestMapping("213P")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
//        List<Switch> switches = service.searchSwitch(7);
//        logger.debug(switches);
//        modelAndView.addObject(switches);
        ExcelUtil excelUtil = new ExcelUtil(service);
        excelUtil.insetData();
        modelAndView.setView(new MappingJackson2JsonView());
        return modelAndView;
    }
}
