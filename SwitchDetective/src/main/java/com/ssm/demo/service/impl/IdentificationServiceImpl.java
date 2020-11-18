package com.ssm.demo.service.impl;

import com.ssm.demo.mapper.BaseStationMapper;
import com.ssm.demo.mapper.CabinetMapper;
import com.ssm.demo.model.Switch;
import com.ssm.demo.params.DotDetecteds_okPic;
import com.ssm.demo.service.define.IdentificationService;
import com.ssm.demo.service.define.SwitchListService;
import com.ssm.demo.util.PathUtil;
import com.ssm.demo.util.SocketClient;
import com.ssm.demo.vo.Dot;
import com.ssm.demo.vo.DotVO;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class IdentificationServiceImpl implements IdentificationService {
    private SwitchListService switchListService;
    private CabinetMapper cabinetMapper;
    private BaseStationMapper stationMapper;
    private static Logger logger = Logger.getLogger(IdentificationServiceImpl.class);
    private String basePath = PathUtil.directory_toBeDetected;

    @Autowired
    public void setSwitchListService(SwitchListService switchListService) {
        this.switchListService = switchListService;
    }

    @Autowired
    public void setCabinetMapper(CabinetMapper cabinetMapper) {
        this.cabinetMapper = cabinetMapper;
    }

    @Autowired
    public void setStationMapper(BaseStationMapper stationMapper) {
        this.stationMapper = stationMapper;
    }

    @Override
    public DotDetecteds_okPic identification(MultipartFile file) throws IOException {
        SocketClient socketClient;
        String fileName = file.getOriginalFilename();
        File dest = new File(fileName);
        String path_Image_ToBeDetected = basePath + fileName;
        logger.info("path_Image_ToBeDetected: " + path_Image_ToBeDetected);
        socketClient = new SocketClient();
        String path_Image_Detected = "";
        file.transferTo(dest);
        socketClient.startConnection("127.0.0.1", 7000);
        String retMessage = socketClient.senMessage(path_Image_ToBeDetected);
        JSONObject retRoot = new JSONObject(retMessage);
        logger.debug("retRoot:" + retRoot);
        JSONArray labels_array = retRoot.getJSONArray("label");
        List labelsList = labels_array.toList();
        ArrayList<Dot> dots = new ArrayList<>();
        for (Object o : labels_array) {
            String str = (String) o;
            String[] strs = str.split(" ");
            String state = strs[0];
            float score = Float.parseFloat(strs[1]);
            int x = Integer.parseInt(strs[2]);
            int y = Integer.parseInt(strs[3]);
            dots.add(new Dot(state, score, x, y));
        }
        socketClient.stopConnection();
        dots = arrange(dots);
        path_Image_Detected = retRoot.getString("path_Image_Detected");
        return new DotDetecteds_okPic(dots, path_Image_Detected);
    }

    @Override
    public List<DotVO> reviewDotDetected(List<Dot> detecteds, String cabinetId, String okPic) {
        List<Switch> switches = switchListService.searchSwitch(cabinetId);
        HashMap<String, Switch> switchHashMap = new HashMap<>();
        ArrayList<DotVO> results = new ArrayList<>();
        for (Switch s : switches) {
            switchHashMap.put("" + s.getRow() + s.getColumn(), s);
        }
        for (Dot dot : detecteds) {
            Switch s = switchHashMap.get("" + dot.getRow() + dot.getColumn());
            boolean isCorrect = false;
            if (s != null && s.getStandardState().equalsIgnoreCase(dot.getState()))
                isCorrect = true;
            results.add(new DotVO(dot, s, isCorrect));
        }
        return results;
    }

    private ArrayList<Dot> arrange(ArrayList<Dot> dots) {
        ArrayList<Dot> flags = new ArrayList<>();
        int bias = 200;
        for (int i = 0; i < dots.size(); i++) {
            for (int j = 0; j < dots.size() - i - 1; j++) {
                if (dots.get(j).getY() > dots.get(j + 1).getY())
                    dots.add(j, dots.remove(j + 1));
            }
        }
        flags.add(dots.get(0));
        for (Dot curDot : dots) {
            boolean isMatcah = false;
            for (Dot flag : flags) {
                int distance = curDot.getY() > flag.getY() ? curDot.getY() - flag.getY() : -(curDot.getY() - flag.getY());
                if (distance < bias) {
                    curDot.setY(flag.getY());
                    isMatcah = true;
                    break;
                }
            }
            if (!isMatcah) {
                flags.add(curDot);
            }
        }
        logger.info("行数" + flags.size());
        logger.info("排序前：");
        for (Dot flag : dots) {
            logger.info(flag.toString());
        }
        int row = 0;
        int column;
        for (Dot flag : flags) {
            column = 0;
            int begin = 0;
            for (Dot curDot : dots) {
                if (curDot.getY() == flag.getY()) {
                    begin = dots.indexOf(curDot);
                    break;
                }
            }
            int end = dots.size();
            for (int i = begin + 1; i < dots.size(); i++) {
                if (dots.get(i).getY() > flag.getY()) {
                    end = i;
                    break;
                }
            }
            for (int i = begin; i < end; i++) {
                for (int j = begin; j < end - i + begin - 1; j++) {
                    if (dots.get(j).getX() > dots.get(j + 1).getX())
                        dots.add(j, dots.remove(j + 1));
                }
            }
            for (int i = begin; i < end; i++) {
                dots.get(i).setRow(row);
                dots.get(i).setColumn(column++);
            }
            row++;
        }
        logger.info("排序后：");
        for (Dot flag : dots) {
            logger.info(flag.toString());
        }
        return dots;
    }
}
