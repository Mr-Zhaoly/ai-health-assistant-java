import com.zly.AiTestApplication;
import com.zly.service.IOcrService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

@SpringBootTest(classes = AiTestApplication.class)
public class TesseractTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testTesseract(@Autowired IOcrService ocrService) throws IOException {
        Resource resource = applicationContext.getResource("classpath:images/中国居民膳食指南（2022）.png中国居民膳食指南（2022）_10.png");
        File file = resource.getFile();
        System.out.println(ocrService.extractTextFromImage(file));
    }
}
