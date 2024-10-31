package com.study.tdd.beforeAndAfter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@Slf4j
public class BeforeAndAfterTest {

    @Mock // 실제 객체를 생성하는 것이 아니라 가짜(모의) 객체를 생성하는 행위
    private AddTest addTest;

    // openMock: openMocks가 실행되면 해당 this에 존재하는 Mock 어노테이션 달려 있는 모든 객체들을 가짜 객체로 하나씩 만들겠다

    /**
     * Each : 각각의 메소드가 실행될 때 마다 한 번 실행한다는 뜻
     * All : 모든 테스트가 실행될 때 한 번 실행한다는 뜻 (이 클래스가 한 번 실행될 때 한 번)
     * @BeforeEach
     * @AfterEach
     * @BeforeAll
     * @AfterAll
     */
    @BeforeEach
    void setAddTest() {
//        this.addTest = new AddTest(); // 실제 객체 행위를 만드는 것
        log.info("Mock 생성");
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void beforeEachTest() {
        log.info("Before each 실행");
    }

    @AfterEach
    void afterEachTest() {
        log.info("After each 실행");
    }

    // All들은 전부 static으로 존재해야 한다
    @BeforeAll
    static void beforeAllTest() {
        log.info("Before All 실행");
    }

    @AfterAll
    static void afterAllTest() {
        log.info("After All 실행");
    }

    @Test
    void test1() {
        /**
         *  given when then (테스트에서 중요한 개념 3가지) - 총 이 3가지 단계로 테스팅이 이뤄진다
         *  given : 어떠한 것을 테스트하기 위해서 제공해줘야 하는 것
         *  when : 어떠한 동작을 했을 때
         *  then : 동작한 결과
         */
        given(addTest.add()).willReturn(true); // add() 메서드를 실행하면 true라는 것을 리턴할거야 라는 것을 정의한다.

        Boolean result = addTest.add();

        log.info("test1 메소드 실행!!!");
        assertEquals(true, result);
    }

    @Test
    void test2() {
        log.info("test2 메소드 실행!!!!");
    }
}
