import com.zly.AiTestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = AiTestApplication.class)
public class PdfDocumentReaderTest {

    @Test
    public void testPdfDocumentReader() {
        ParagraphPdfDocumentReader pdfReader = new ParagraphPdfDocumentReader("classpath:/pdf/中国居民膳食指南（2022）.pdf",
                PdfDocumentReaderConfig.builder()
                        .withPageTopMargin(0)
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                .withNumberOfTopTextLinesToDelete(0)
                                .build())
                        .withPagesPerDocument(1)
                        .build());

        List<Document> documents = pdfReader.read();
        System.out.println(documents);
    }
}
