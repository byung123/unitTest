package com.study.tdd.service;

import com.study.tdd.dto.ReqAddStudentDto;
import com.study.tdd.dto.RespSaveDto;
import com.study.tdd.entity.Student;
import com.study.tdd.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    // 단위테스틀란 의존성 없이 기능이 되어야 하기 때문에 (service가 mapper를 의존하는 것처럼)
    // 이럴 때 그래서 가짜 객체 Mocks 를 사용한다
    public RespSaveDto saveStudent(ReqAddStudentDto dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setAge(dto.getAge());
//        studentMapper.save(student);  // 이게 정상 동작이 안하더라도, 현재 이 saveStudent 메서드를 실행ㅅ기킬 수 잇어야한다( 단위테스트)

        if(studentMapper.save(student) < 1) {
            return new RespSaveDto("학생 등록 실패", false);
        }
        return new RespSaveDto("학생 등록 완료", true);
    }
}
