package com.zly.service.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.zly.service.IMilvusService;
import com.zly.service.IOcrService;
import com.zly.util.TextSplitterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class MilvusServiceImpl implements IMilvusService {

    @Autowired
    private IOcrService ocrService;

    @Autowired
    private VectorStore vectorStore;

    @Override
    public Boolean init() {
        try {
            List<Document> documents = this.loadAndProcessImages();
            // 对文档进行切割
            documents = TextSplitterUtil.splitDocuments(documents);
            List<List<Document>> batches = Lists.partition(documents, 10);
            for (List<Document> batch : batches){
                // 添加到向量库存储
                vectorStore.add(batch);
            }
            return true;
        } catch (Exception e) {
            log.error("初始化Milvus异常",e);
            return false;
        }
    }

    private List<Document> loadAndProcessImages() throws IOException {
        List<Document> documents = new ArrayList<>();
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
                this.processImage(imagePath, documents);
            }
        }
        return documents;
    }

    private void processImage(Path imagePath, List<Document> documents) {
        try {
            // 调用OCR服务解析图片
            String text = ocrService.extractTextFromImage(imagePath.toFile());
            if(StrUtil.isNotBlank(text)){
                //获取页码
                Integer pageNum = this.getPageNum(text);
                Map<String, Object> metadata = new HashMap<>();
                metadata.put("source", "中国居民膳食指南（2022）.pdf");
                metadata.put("page", pageNum);

                Document document = Document.builder()
                        .text(text)
                        .metadata(metadata)
                        .build();
                documents.add(document);
            }
            log.info("图片处理完成：{}", text);
        }catch (Exception e){
            log.error("图片处理异常",e);
        }
    }

    private Integer getPageNum(String text) {
        if(StrUtil.isBlank(text)){
            return 0;
        }
        String newText = text.replace("\n", "");

        String lastChar = newText.substring(newText.length() - 3);
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(lastChar);
        if(matcher.find()){
            return Integer.valueOf(matcher.group());
        }else{
            return 0;
        }
    }
}
