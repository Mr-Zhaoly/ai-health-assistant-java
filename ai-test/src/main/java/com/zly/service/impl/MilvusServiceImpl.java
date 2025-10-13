package com.zly.service.impl;

import cn.hutool.core.util.StrUtil;
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
            // 添加到向量存储中
            vectorStore.add(documents);
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
                String newText = text.replace("\n", "");
                String lastChar = newText.substring(newText.length() - 1);
                Map<String, Object> metadata = new HashMap<>();
                metadata.put("source", imagePath.getFileName().toString());
                metadata.put("page", lastChar);

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
}
