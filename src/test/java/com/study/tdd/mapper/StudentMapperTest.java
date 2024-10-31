package com.study.tdd.mapper;

import com.study.tdd.entity.Student;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
// 실제 데이터베이스에 영향을 주지 않겟다.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// 테스트가 실행되기 전에 SQL을 실행해라 -> 테스트용 DB를 만듦
@Sql(scripts = "/student_schema.sql") // 이 경로에 잇는 파일을 참조하겠다
class StudentMapperTest {

    // myBatisTest 어노테이션이 있기 때문에 Autowired를 할 수 있다.
    @Autowired
    private StudentMapper studentMapper;

    @Test
    void save() {
        Student student = new Student(1L, "홍길동", 20);
        int result = studentMapper.save(student);
        assertThat(result).isEqualTo(1); // result 결과가 1이랑 같니?
    }

    @Test
    void findById() {
        Student student = new Student(1L, "홍길동", 20);
        int result = studentMapper.save(student);
        assertThat(result).isEqualTo(1); // 위에껄 한 번 해주고 해야한다(테스트기 때문에 위에 실행한 정보가 저장이 안되기 때문)

        Student foundStudent = studentMapper.findById(1L);
//        assertThat(foundStudent).isEqualTo(student); // 밑에 3개를 따로 안하고 한번에 하거나
        assertThat(foundStudent.getId()).isEqualTo(1L);
        assertThat(foundStudent.getName()).isEqualTo("홍길동");
        assertThat(foundStudent.getAge()).isEqualTo(20); // 안에 있는 값들을 하나하나 다 확인해줘야 함


    }
}