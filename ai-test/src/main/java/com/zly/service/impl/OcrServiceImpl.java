package com.zly.service.impl;

import com.zly.service.IOcrService;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class OcrServiceImpl implements IOcrService {

    @Value("${tesseract.data-path}")
    private String dataPath;

    @Value("${tesseract.language}")
    private String language;

    @Override
    public String extractTextFromImage(File imageFile) {
        try {
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(dataPath);
            tesseract.setLanguage(language);
            return tesseract.doOCR(imageFile);
        }catch (Exception e){
            log.error("图片识别异常",e);
            return "";
        }
    }
}
