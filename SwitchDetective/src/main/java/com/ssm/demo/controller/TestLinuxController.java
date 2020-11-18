package com.ssm.demo.controller;

import com.ssm.demo.service.define.SwitchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("/test")
public class TestLinuxController {

    @Autowired
    SwitchListService switchListService;

    @RequestMapping("/testUploadFile")
    public ModelAndView uploadFile(MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView();
        String path = "/usr/local/picture/" + file.getOriginalFilename();
        try {
            File dest = new File(Objects.requireNonNull(file.getOriginalFilename()));
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        modelAndView.addObject(path);
        modelAndView.setView(new MappingJackson2JsonView());
        File pic = new File(path);
        modelAndView.addObject(pic.exists());
        return modelAndView;
    }

    @RequestMapping("/test_insert")
    public ModelAndView insert() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new MappingJackson2JsonView());
        modelAndView.addObject("success", true);
        return modelAndView;
    }
}