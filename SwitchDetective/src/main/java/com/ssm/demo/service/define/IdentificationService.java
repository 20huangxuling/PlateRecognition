package com.ssm.demo.service.define;

import com.ssm.demo.params.DotDetecteds_okPic;
import com.ssm.demo.vo.Dot;
import com.ssm.demo.vo.DotVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IdentificationService {

    DotDetecteds_okPic identification(MultipartFile file) throws IOException;

    List<DotVO> reviewDotDetected(List<Dot> detecteds, String cabinetId, String okPic);
}
