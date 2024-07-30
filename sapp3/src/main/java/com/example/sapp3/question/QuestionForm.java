package com.example.sapp3.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

// 사용자가 입력한 형식을 유효한지 검사
@Getter
@Setter
public class QuestionForm {
    @NotEmpty(message = "제목은 필수")
    @Size(max=200)
    private String subject;

    @NotEmpty(message = "내용 필수")
    private String content;
}
