import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.zly.AiTestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AiTestApplication.class)
public class DeepSeekTest {

    @Test
    public void testDashScopeChatModel(@Autowired DashScopeChatModel dashScopeChatModel) {
        String content = dashScopeChatModel.call("中午吃什么?");

        System.out.println(content);
        System.out.println("-----------------------------------------");
    }
}
