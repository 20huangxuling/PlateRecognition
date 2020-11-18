package com.ssm.demo.controller;

import com.ssm.demo.mapper.AllRecordRowMapper;
import com.ssm.demo.model.ResultExcelRow;
import com.ssm.demo.service.define.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

@Controller
@RequestMapping("/download")
public class DownloadController {

    private ExcelService excelService;

    @Autowired
    public void setExcelService(ExcelService excelService) {
        this.excelService = excelService;
    }

    @GetMapping("/searchAllRecords/{baseId}")
    public ModelAndView searchAllRecords(@PathVariable int baseId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new MappingJackson2JsonView());
        String path = excelService.searchIdentificationRecords(baseId);
        if (path == null) {
            modelAndView.addObject("message", "出错");
            return modelAndView;
        }
        modelAndView.addObject("path", path);
        return modelAndView;
    }

    @GetMapping("/searchAllMistakeRecords/{baseId}")
    public ModelAndView searchAllMistakeRecords(@PathVariable int baseId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new MappingJackson2JsonView());
        String path = excelService.searchMistakeRecords(baseId);
//        if(path==null){
//            modelAndView.addObject("message","出错");
//            return modelAndView;
//        }
        modelAndView.addObject("path", path);
        return modelAndView;
    }
}
