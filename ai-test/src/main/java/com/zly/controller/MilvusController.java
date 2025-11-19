package com.zly.controller;

import com.zly.common.vo.base.ResultT;
import com.zly.service.IMilvusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/milvus")
public class MilvusController {

    @Autowired
    private IMilvusService milvusService;

    @PostMapping("/init")
    public ResultT<Boolean> init() {
        return ResultT.success(milvusService.init());
    }

//    @PostMapping("/askWithHistory")
//    public ResultT<String> askQuestionWithHistory(@RequestBody ChatHistoryRequest request) {
//        // 支持对话历史的健康问答
//        String answer = milvusService.getAnswerWithContext(request.getQuestion(), request.getHistory());
//        return ResultT.success(answer);
//    }
//
//    @GetMapping("/healthTips")
//    public ResultT<List<String>> getHealthTips(@RequestParam(required = false) String category) {
//        // 获取健康小贴士
//        List<String> tips = milvusService.getHealthTips(category);
//        return ResultT.success(tips);
//    }
//
//    @PostMapping("/symptomCheck")
//    public ResultT<SymptomAnalysisResult> symptomCheck(@RequestBody SymptomRequest request) {
//        // 症状自查功能
//        SymptomAnalysisResult result = milvusService.analyzeSymptoms(request.getSymptoms());
//        return ResultT.success(result);
//    }
}
