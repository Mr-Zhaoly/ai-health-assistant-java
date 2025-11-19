package com.zly.service.impl;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.zly.service.IHealthQaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HealthQaServiceImpl implements IHealthQaService {

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private ChatModel chatModel;

    @Override
    public String getAnswer(String question) {
        // Step 1: 检索相关文档
        List<Document> relevantDocs = vectorStore.similaritySearch(question);
        // Step 2: 构建上下文
        String context = relevantDocs.stream()
                .map(Document::getText)
                .collect(Collectors.joining(""));
        // Step 3: 使用上下文生成答案
        ChatClient chatClient = ChatClient.builder(chatModel).build();
        String answer = chatClient.prompt()
                .user(u -> u.text("基于以下上下文回答问题： 上下文： " + context + " 问题：" + question))
                                .call()
                                .content();
        return answer;
    }
}
