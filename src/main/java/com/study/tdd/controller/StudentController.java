package com.study.tdd.controller;

import com.study.tdd.dto.ReqAddStudentDto;
import com.study.tdd.dto.RespSaveDto;
import com.study.tdd.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/student")
    public ResponseEntity<?> addStudent(@RequestBody ReqAddStudentDto dto) {
//        RespSaveDto respSaveDto = new RespSaveDto("학생 추가", true);
        return ResponseEntity.ok().body(studentService.saveStudent(dto));
    }
}
