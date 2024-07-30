package com.example.sapp3.answer;

import com.example.sapp3.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "Text")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;
}
