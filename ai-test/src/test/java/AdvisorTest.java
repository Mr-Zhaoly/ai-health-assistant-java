import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.zly.AiTestApplication;
import com.zly.advisor.ReReadingAdvisor;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = AiTestApplication.class)
public class AdvisorTest {

    @Test
    public void testAdvisor(@Autowired VectorStore vectorStore, @Autowired DashScopeChatModel dashScopeChatModel, @Value("classpath:/prompt/diet.st") Resource resource) {
        String question = "中午吃什么？";
        // Step 1: 检索相关文档
        List<Document> relevantDocs = vectorStore.similaritySearch(question);
        // Step 2: 构建上下文
        String context = relevantDocs.stream()
                .map(Document::getText)
                .collect(Collectors.joining(""));
        // Step 3: 使用上下文生成答案
        ChatClient chatClient = ChatClient.builder(dashScopeChatModel)
                //日志拦截
                .defaultAdvisors(new SimpleLoggerAdvisor())
                //敏感词拦截
                .defaultAdvisors(new SafeGuardAdvisor(List.of("小王")))
                //重读拦截-自定义拦截器
                .defaultAdvisors(new ReReadingAdvisor()).
                defaultSystem(resource).build();
        String answer = chatClient.prompt()
                .system(p->p.param("context", context))
                .user(u -> u.text(question))
                .call()
                .content();
        System.out.println(answer);
    }
}
