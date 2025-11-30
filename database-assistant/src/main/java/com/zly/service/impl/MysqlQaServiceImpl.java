package com.zly.service.impl;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.zly.model.dto.QuestionRequestDTO;
import com.zly.service.IMysqlQaService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MysqlQaServiceImpl implements IMysqlQaService {

    @Autowired
    private DashScopeChatModel dashScopeChatModel;

    @Autowired
    private ChatMemory chatMemory;

    @Autowired
    private SyncMcpToolCallbackProvider toolCallbackProvider;

    private ChatClient chatClient;
    @PostConstruct
    public void init() {
        chatClient = ChatClient
                .builder(dashScopeChatModel)
                .defaultAdvisors(
                        PromptChatMemoryAdvisor.builder(chatMemory).build()
                )
                .defaultToolCallbacks(toolCallbackProvider)
                .build();
    }

    @Override
    public String getAnswer(QuestionRequestDTO requestDTO) {
        log.info("用户提问：{}", requestDTO.getQuestion());
        // Step 3: 使用上下文生成答案
        String answer = chatClient.prompt()
                .user(u -> u.text(requestDTO.getQuestion()))
                //根据用户ID保存会话
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID,requestDTO.getUserId()))
                .call()
                .content();
        log.info("答案：{}", answer);
        return answer;
    }
}
