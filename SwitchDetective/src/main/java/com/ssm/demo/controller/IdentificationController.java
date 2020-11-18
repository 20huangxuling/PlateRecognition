package com.ssm.demo.controller;

import com.ssm.demo.mapper.BaseStationMapper;
import com.ssm.demo.model.BaseStation;
import com.ssm.demo.params.DotDetecteds_okPic;
import com.ssm.demo.service.define.ExcelService;
import com.ssm.demo.service.define.IdentificationRecordService;
import com.ssm.demo.service.define.IdentificationService;
import com.ssm.demo.service.define.SwitchListService;
import com.ssm.demo.vo.Dot;
import com.ssm.demo.vo.DotVO;
import com.ssm.demo.vo.OneDetected;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/image")
@PropertySource({"classpath:directory.properties", "classpath:url.properties"})
//@SessionAttributes(value = {"result", "dateTime", "path_Image_Detected", "cabinetId"})
public class IdentificationController {

    private static Logger logger = Logger.getLogger(IdentificationController.class);
    private final Object lock = new Object();
    //    private SocketClient socketClient;  放在这里非线程安全

    SwitchListService switchListService;
    IdentificationRecordService identificationRecordService;
    IdentificationService identificationService;
    BaseStationMapper stationMapper;
    ExcelService excelService;

    @Autowired
    public void setExcelService(ExcelService excelService) {
        this.excelService = excelService;
    }

    @Autowired
    public void setStationMapper(BaseStationMapper stationMapper) {
        this.stationMapper = stationMapper;
    }

    @Autowired
    public void setSwitchListService(SwitchListService switchListService) {
        this.switchListService = switchListService;
    }

    @Autowired
    public void setIdentificationRecordService(IdentificationRecordService identificationRecordService) {
        this.identificationRecordService = identificationRecordService;
    }

    @Autowired
    public void setIdentificationService(IdentificationService identificationService) {
        this.identificationService = identificationService;
    }


    @RequestMapping("/ident/{cabinetId}")
    public ModelAndView ident(@PathVariable String cabinetId,
                              HttpServletResponse response,
                              MultipartFile file,
                              HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new MappingJackson2JsonView());
        List<Dot> dots;
        List<DotVO> results;
        String path_Image_Detected;
        try {
            Timestamp dateTime = new Timestamp(System.currentTimeMillis());
            DotDetecteds_okPic dotDetecteds_okPic = identificationService.identification(file);
            dots = dotDetecteds_okPic.getDots();
            path_Image_Detected = dotDetecteds_okPic.getOkPic();
            results = identificationService.reviewDotDetected(dots, cabinetId, path_Image_Detected);
            ConcurrentHashMap<String, OneDetected> manyDetected;
            synchronized (lock) {
                manyDetected = (ConcurrentHashMap<String, OneDetected>) session.getAttribute("manyDetected");
                if (manyDetected == null) {
                    manyDetected = new ConcurrentHashMap<>();
                }
                session.setAttribute("manyDetected", manyDetected);
            }
            OneDetected oneDetected = new OneDetected(results, dateTime, path_Image_Detected, cabinetId);
            manyDetected.put(cabinetId, oneDetected);
            modelAndView.addObject("success", true);
            modelAndView.addObject("msg", "上传图片成功");
            modelAndView.addObject("dateTime", dateTime.toString());
            modelAndView.addObject("result", results);
            modelAndView.addObject("path_Image_Detected", path_Image_Detected);
            modelAndView.addObject("cabinetId", cabinetId);
        } catch (IOException e) {
            e.printStackTrace();
            modelAndView.addObject("success", false);
            modelAndView.addObject("msg", "上传图片失败");
        }
        return modelAndView;
    }

    @RequestMapping("/confirm")
    public ModelAndView confirm(@RequestParam("cabinetId") String cabinetId,
                                HttpSession session,
                                SessionStatus status) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new MappingJackson2JsonView());
        if (status.isComplete()) {
            modelAndView.addObject("success", true);
            modelAndView.addObject("massage", "session已过期");
            return modelAndView;
        }
        ConcurrentHashMap<String, OneDetected> manyDetected = (ConcurrentHashMap<String, OneDetected>) session.getAttribute("manyDetected");
        OneDetected oneDetected = manyDetected.remove(cabinetId);
        String path_Image_Detected = oneDetected.getPath_Image_Detected();
        Timestamp dateTime = oneDetected.getDateTime();
        identificationRecordService.insertToDataBase(oneDetected.getResults(), path_Image_Detected, cabinetId, dateTime);
        BaseStation station = stationMapper.getStationByCabinetID(cabinetId);
        modelAndView.addObject("success", true);
        modelAndView.addObject("dateTime", dateTime.toString());
        modelAndView.addObject("path_Image_Detected", path_Image_Detected);
        modelAndView.addObject("massage", "成功");
        modelAndView.addObject("station", station);
        modelAndView.addObject("excelPath", excelService.createResultTable(oneDetected.getResults(), path_Image_Detected, dateTime, cabinetId));
        return modelAndView;
    }
}