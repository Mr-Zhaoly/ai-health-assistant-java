package com.zly.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 问题
     */
    private String question;
}
