package com.study.tdd.service;

import com.study.tdd.dto.ReqAddStudentDto;
import com.study.tdd.dto.RespSaveDto;
import com.study.tdd.entity.Student;
import com.study.tdd.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

//    private StudentService studentService; // 이녀석을 가져오는 것이 아니라 이녀석 안에 잇는 기능들을 가지고 온다

    @Mock
    private StudentMapper studentMapper;

    // service는 진짜인데 가짜 MoxkMapper 를 의존하게 하는것
    @InjectMocks // 실제 기능에서도 service가 mapper를 의존하고 있기 때문에 Mock 객체를 service에 주입 시키기 위해 injectMocks를 사용한다
    private StudentService studentService;

    @Test
    void saveStudent() {
        ReqAddStudentDto dto = new ReqAddStudentDto();
        dto.setName("홍길동");
        dto.setAge(20);

        Student student = new Student();
        student.setName(dto.getName());
        student.setAge(dto.getAge());

        given(studentMapper.save(student)).willReturn(1); // 가짜기 때문에 값이 없어서 given을 이용해 넣어준다

        student.setId(1L);
//        studentMapper.save(student);  // Mapper가 없기 때문에 Mock을 만든다
        verify(studentMapper.save(student)); // 실행됐는지만 확인하면 되기 때문에 verify() 사용

        Optional<Student> optional = Optional.of(student);
        RespSaveDto respSaveDto = null;
        if(studentMapper.save(student) < 0) {
            respSaveDto = new RespSaveDto("학생 등록 실패", false);
        } else {
            respSaveDto = new RespSaveDto("학생 등록 완료", true);
        }

        assertEquals(new RespSaveDto("학생 등록 완료", true), respSaveDto);
    }

    @Test // 이렇게도 테스트 할 수 있다
    public void saveStudent2() {
        given(studentMapper.save(any(Student.class))).willReturn(0); // Student 클래스의 타입 어떤 것이든 받게끔 설정하는 것

        ReqAddStudentDto dto = new ReqAddStudentDto();
        dto.setName("홍길동");
        dto.setAge(20);

        RespSaveDto respSaveDto = studentService.saveStudent(dto);

        assertEquals(new RespSaveDto("학생 등록 완료", true), respSaveDto);
    }
}