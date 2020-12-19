package cz.cvut.fit.tjv.chukavol.controller;

import cz.cvut.fit.tjv.chukavol.dto.StudentCreateDTO;
import cz.cvut.fit.tjv.chukavol.dto.StudentDTO;
import cz.cvut.fit.tjv.chukavol.entity.Student;
import cz.cvut.fit.tjv.chukavol.entity.Subject;
import cz.cvut.fit.tjv.chukavol.service.StudentService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void byId() throws Exception {
        StudentDTO student = new StudentDTO(11,"chukavol", "iLoveCoding1", 1);
        BDDMockito.given(studentService.findByIdAsDTO(student.getStudentId())).willReturn(student);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/student/{studentId}", student.getStudentId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.studentId", CoreMatchers.is(student.getStudentId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.studentUsername", CoreMatchers.is(student.getStudentUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", CoreMatchers.is(student.getPassword())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.grade", CoreMatchers.is(student.getGrade())));

        Mockito.verify(studentService, Mockito.atLeastOnce()).findByIdAsDTO(student.getStudentId());
    }

    @Test
    void all() throws Exception {
        StudentDTO student1 = new StudentDTO(11,"chukavol","iLoveCoding1",1);
        StudentDTO student2 = new StudentDTO(12,"chukavol2","iLoveCoding2",2);
        StudentDTO student3 = new StudentDTO(13,"chukavol3","iLoveCoding3",3);

        List<StudentDTO> subjectToReturn = Arrays.asList(student1,student2,student3);

        BDDMockito.given(studentService.findAll(PageRequest.of(0, 3))).willReturn(subjectToReturn);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/student/all?page=0&size=3"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(studentService, Mockito.atLeastOnce()).findAll(PageRequest.of(0, 3));
    }

    @Test
    void allBySubjectId() throws Exception {
        StudentDTO student1 = new StudentDTO(11,"chukavol","iLoveCoding1",1);
        StudentDTO student2 = new StudentDTO(12,"chukavol2","iLoveCoding2",2);
        StudentDTO student3 = new StudentDTO(13,"chukavol3","iLoveCoding3",3);

        List<StudentDTO> subjectToReturn = Arrays.asList(student1,student2,student3);

        Subject subject = new Subject("BI-AG1",6);
        ReflectionTestUtils.setField(subject,"subjectId", 31);

        BDDMockito.given(studentService.findAllStudentsBySubjectId(subject.getSubjectId())).willReturn(subjectToReturn);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/student/all/" + subject.getSubjectId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(studentService, Mockito.atLeastOnce()).findAllStudentsBySubjectId(subject.getSubjectId());

    }

    @Test
    void create() throws Exception {
        Student student = new Student("chukavol", "iLoveCoding1", 1);
        ReflectionTestUtils.setField(student,"studentId", 11);

        StudentDTO studentToReturn = new StudentDTO(11,"chukavol","iLoveCoding1",1);

        ResponseEntity<StudentDTO> response = ResponseEntity
                .created((Link.of("http://localhost:8080/subject/" + studentToReturn.getStudentId())).toUri())
                .build();

        BDDMockito.given(studentService.create(Mockito.any(StudentCreateDTO.class))).willReturn(studentToReturn);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentUsername\":\"chukavol\",\"password\":\"iLoveCoding1\",\"grade\":\"1\"}")
                        .accept(MediaType.ALL)
        ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.studentId", CoreMatchers.is(student.getStudentId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.studentUsername", CoreMatchers.is(student.getStudentUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", CoreMatchers.is(student.getPassword())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.grade", CoreMatchers.is(student.getGrade())));
    }

    @Test
    void update() throws Exception {
        StudentCreateDTO student = new StudentCreateDTO("chukavol", "iLoveCoding1", 1);
        StudentDTO studentToReturn = new StudentDTO(11,"chukavol","iLoveCoding1",2
        );
        BDDMockito.given(studentService.update(11,student)).willReturn(studentToReturn);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/student/" + studentToReturn.getStudentId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentUsername\":\"chukavol\",\"password\":\"iLoveCoding1\",\"grade\":\"2\"}")
                        .accept(MediaType.ALL)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        Student student = new Student("chukavol", "iLoveCoding1", 1);
        ReflectionTestUtils.setField(student,"studentId", 11);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/student/"+ student.getStudentId())
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(studentService, Mockito.atLeastOnce()).deleteById(11);
    }
}
