package com.study.tdd.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.study.tdd.dto.ReqAddStudentDto;
import com.study.tdd.dto.RespSaveDto;
import com.study.tdd.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 요청과 응답에 대한 테스팅을 하기 위해선 이 어노테이션이 필요함 -> controller 테스트에서 필수
// web에 필요한 부품들을 test용으로 가져올 수 있게끔 설정할 수 있게 해주는 것
@WebMvcTest(controllers =  StudentController.class)
// 서버 전체 통합 테스트 -> 이걸 하면 서버 안에 있는 모든 BEAN이 등록이 된다. -> 이걸 사용하면 Mock이 아닌 MockBean을 사용할 수 있게 된다
// 빈으로 등록된 객체를 가짜 객체로 만드는 행위인것 같음
//@SpringBootTest
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // 우리가 직접 주입 시키는게 아니라 자동으로 IoC 컨트롤러에 자동으로 주입시켜 주는 것
    @MockBean // 그냥 Mock으로 하면 안됨(injection을 주입할 수 없음) - autowired되는 Mockbean으로 생성해야 하기 때문에 MockBean을 사용
    private StudentService studentService; // 이렇게 해도 각자 given 설정을 해줘야함

    @Test
    void addStudent() throws Exception {
        ReqAddStudentDto reqAddStudentDto = new ReqAddStudentDto();
        reqAddStudentDto.setName("홍길동");
        reqAddStudentDto.setAge(18);

        ObjectMapper objectMapper = new ObjectMapper(); // 이걸로 객체를 만들자
        String reqJsonBody = objectMapper.writeValueAsString(reqAddStudentDto); // 여기에 객체를 넣어주면 String으로 바꿔줌

        given(studentService.saveStudent(reqAddStudentDto))
                .willReturn(new RespSaveDto("학생 등록 완료", true));

        // andExpect : 여러가지 결과를 확인해볼 수 있다
        mockMvc.perform(post("/student") // 어떤 요청을 날릴 것인가?
                        .contentType(MediaType.APPLICATION_JSON)  // Json 형식의 데이터 타입으로 설정
                        .content(reqJsonBody) // body에 들어올 데이터
                )
                // jsonPath : 실제 결과, value : 내가 예상할 결과
                .andExpect(status().isOk()) // 응답이 200이 왔는지 확인 isBadRequest면 400이 왔는지 확인하는 것
                .andExpect(jsonPath("$.isSaveSuccess").value(true)) // 그결과가 이건 true가 나와야 하고
                .andExpect(jsonPath("$.message").value("학생 등록 완료")) // 값은 이것이 와야 한다
                .andDo(print());
    }
}