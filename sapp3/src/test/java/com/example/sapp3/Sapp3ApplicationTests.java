package com.example.sapp3;

import com.example.sapp3.answer.Answer;
import com.example.sapp3.answer.AnswerRepository;
import com.example.sapp3.question.Question;
import com.example.sapp3.question.QuestionRepository;
import com.example.sapp3.question.QuestionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class Sapp3ApplicationTests {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;
	@Test
	void testJPA() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

	@Test
	void testJPA1() {
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());

	}

	@Test
	void testJPAFindById() {
		Optional<Question> oq = this.questionRepository.findById(1);
		if (oq.isPresent()) {
			Question q = oq.get();
			assertEquals("sbb가 무엇인가요?", q.getSubject());
		}
	}

	@Test
	void testJPAFindBySubject() {
		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, q.getId());
	}

	@Test
	void testJPAFindBySubjectAndContent() {
		Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해 알고 싶습니다.");
		assertEquals(1, q.getId());
	}

	// sbb%	'sbb'로 시작하는 문자열
	// %sbb	'sbb'로 끝나는 문자열
	// %sbb%	'sbb'를 포함하는 문자열
	@Test
	void testJPAFindBySubjectLike() {
		List<Question> qList= this.questionRepository.findBySubjectLike("sbb%");
		Question q = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	void testJPAManiplate() {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정된 제목");
		this.questionRepository.save(q);
	}

	@Test
	void testJPADelete() {
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
	}

	// question id를 fk로 받아 테이블을 생성하므로 두번 실행하면 두번 테이블에 추가된다.
	@Test
	void testJPASavaAnswer() {
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(q);
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
	}

	// a.getQuestion()는 question 객체
	// Answer.java 참고
	// @ManyToOne
	// private Question question;
	@Test
	void testJPAGetQuestion() {
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId());
	}

	// Transactional 어노테이션은 작업이 끝날 때까지 원자성, 일관성 보장한다.
	// 중첩된 Transactional을 하려면  @Transactional(propagation = Propagation.NESTED) 를 붙이자.
	// 내부 트렌젝션의 롤백은 외부 트렌젝션에 영향을 미치지 않는다.
	@Transactional // 메서드가 종료될 때까지 db 연결 유지
	@Test
	void testJPAGetAnswer() {
		Optional<Question> oq = this.questionRepository.findById(2); // 시도후 바로 연결 종료 -> 에러 발생
		// 실제 서비스에선 db와의 연결이 끊기지 않아 오류 없음
		assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = q.getAnswerList();
		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());

	}

	@Test
	void testJPAAddDummy() {
		for (int i = 1; i <= 300; i ++) {
			String subject = String.format("테스트 데이터 입니다.[%03d]", i);
			String content = "내용";
			this.questionService.create(subject, content);
		}
	}




}
