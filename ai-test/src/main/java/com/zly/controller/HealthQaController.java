package com.zly.controller;

import com.zly.common.vo.base.ResultT;
import com.zly.model.dto.QuestionRequestDTO;
import com.zly.service.IHealthQaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health/qa")
public class HealthQaController {

    @Autowired
    private IHealthQaService healthQaService;

    @PostMapping("/ask")
    public ResultT<String> askQuestion(@RequestBody QuestionRequestDTO request) {
        // 处理用户提出的健康问题
        String answer = healthQaService.getAnswer(request);
        return ResultT.success(answer);
    }
}
