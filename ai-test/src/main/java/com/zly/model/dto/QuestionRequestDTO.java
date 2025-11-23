package com.zly.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class QuestionRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 问题
     */
    @NotNull(message = "问题不能为空")
    private String question;

    @NotNull(message = "用户ID不能为空")
    private String userId;
}
