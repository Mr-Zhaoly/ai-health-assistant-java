package com.zly.service;

import com.zly.model.dto.QuestionRequestDTO;

public interface IHealthQaService {
    String getAnswer(QuestionRequestDTO requestDTO);
}
