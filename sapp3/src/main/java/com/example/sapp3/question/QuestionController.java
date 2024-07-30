package com.example.sapp3.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor // 롬복이 생성, final이 붙은 생성자를 자동으로 만들어준다. questionRepository
@Controller
public class QuestionController {
//    private final QuestionRepository questionRepository;

    private final QuestionService questionService;
    @GetMapping("/list")
//    @ResponseBody
    public String list(Model model) {
//        // Model은 자바 클래스와 템플릿 간의 연결고리이다.
//        List<Question> questionList = this.questionRepository.findAll();
//        model.addAttribute("questionList", questionList);
//

        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate() {
        return "question_form";
    }

    @PostMapping("create")
    public String quectionCreate(@RequestParam(value = "subject") String subject, @RequestParam(value = "content") String content) {
        this.questionService.create(subject, content);
        return "redirect:/question/list"; // 질문 저장후 페이지로 이동
    }

}
