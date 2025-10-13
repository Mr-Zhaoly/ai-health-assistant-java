package com.zly.service.impl;

import com.zly.service.IMilvusService;
import com.zly.service.IOcrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class MilvusServiceImpl implements IMilvusService {

    @Autowired
    private IOcrService ocrService;

    @Override
    public Boolean init() {
        try {
            this.loadAndProcessImages();
            return true;
        } catch (Exception e) {
            log.error("初始化Milvus异常",e);
            return false;
        }
    }

    private void loadAndProcessImages() throws IOException {
        // 获取images目录路径
        File imageDir = ResourceUtils.getFile("classpath:images");

        // 遍历目录下所有图片文件
        try (Stream<Path> paths = Files.walk(imageDir.toPath())) {
            List<Path> imageFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> {
                        String fileName = path.getFileName().toString().toLowerCase();
                        return fileName.endsWith(".jpg") || fileName.endsWith(".png") ||
                                fileName.endsWith(".jpeg") || fileName.endsWith(".bmp");
                    })
                    .toList();

            // 循环处理每个图片文件
            for (Path imagePath : imageFiles) {
                this.processImage(imagePath);
            }
        }
    }

    private void processImage(Path imagePath) {
        try {
            // 调用OCR服务解析图片
            String text = ocrService.extractTextFromImage(imagePath.toFile());
            log.info("图片处理完成：{}", text);
        }catch (Exception e){
            log.error("图片处理异常",e);
        }
    }
}
