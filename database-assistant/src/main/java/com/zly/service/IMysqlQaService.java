package com.zly.service;

import com.zly.model.dto.QuestionRequestDTO;

public interface IMysqlQaService {
    String getAnswer(QuestionRequestDTO request);
}
