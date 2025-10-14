package com.zly.util;

import com.zly.util.splitter.DietaryGuidelinesTokenTextSplitter;
import org.springframework.ai.document.Document;

import java.util.List;

public class TextSplitterUtil {

    public static List<Document> splitDocuments(List<Document> documents) {
        DietaryGuidelinesTokenTextSplitter splitter = new DietaryGuidelinesTokenTextSplitter();
        return splitter.apply(documents);
    }

    public static List<Document> splitCustomized(List<Document> documents) {
        DietaryGuidelinesTokenTextSplitter splitter = new DietaryGuidelinesTokenTextSplitter(800, 10, 5, 5000, false);
        return splitter.apply(documents);
    }
}
