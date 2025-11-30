package com.zly.controller;

import com.zly.common.vo.base.ResultT;
import com.zly.model.dto.QuestionRequestDTO;
import com.zly.service.IMysqlQaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mysql/qa")
public class MysqlQaController {

    @Autowired
    private IMysqlQaService mysqlQaService;

    @PostMapping("/ask")
    public ResultT<String> askQuestion(@RequestBody QuestionRequestDTO request) {
        String answer = mysqlQaService.getAnswer(request);
        return ResultT.success(answer);
    }
}
