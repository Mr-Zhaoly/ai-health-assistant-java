import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.zly.AiTestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = AiTestApplication.class)
public class PromptTest {

    @Test
    public void test1(@Autowired VectorStore vectorStore, @Autowired DashScopeChatModel dashScopeChatModel) {
        String question = "中午吃什么？";
        // Step 1: 检索相关文档
        List<Document> relevantDocs = vectorStore.similaritySearch(question);
        // Step 2: 构建上下文
        String context = relevantDocs.stream()
                .map(Document::getText)
                .collect(Collectors.joining(""));
        // Step 3: 使用上下文生成答案
        ChatClient chatClient = ChatClient.builder(dashScopeChatModel).defaultSystem("你是一个饮食助手，请根据用户的提问回答问题。背景知识： {context}").build();
        String answer = chatClient.prompt()
                .system(p->p.param("context", context))
                .user(u -> u.text(question))
                .call()
                .content();
        System.out.println(answer);
    }

    @Test
    public void test2(@Autowired VectorStore vectorStore, @Autowired DashScopeChatModel dashScopeChatModel, @Value("classpath:/prompt/diet.st")Resource resource) {
        String question = "中午吃什么？";
        // Step 1: 检索相关文档
        List<Document> relevantDocs = vectorStore.similaritySearch(question);
        // Step 2: 构建上下文
        String context = relevantDocs.stream()
                .map(Document::getText)
                .collect(Collectors.joining(""));
        // Step 3: 使用上下文生成答案
        ChatClient chatClient = ChatClient.builder(dashScopeChatModel).defaultSystem(resource).build();
        String answer = chatClient.prompt()
                .system(p->p.param("context", context))
                .user(u -> u.text(question))
                .call()
                .content();
        System.out.println(answer);
    }
}
