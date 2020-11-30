package cz.cvut.fit.tjv.chukavol.service;

import cz.cvut.fit.tjv.chukavol.dto.StudentCreateDTO;
import cz.cvut.fit.tjv.chukavol.dto.StudentDTO;
import cz.cvut.fit.tjv.chukavol.entity.Student;
import cz.cvut.fit.tjv.chukavol.entity.Subject;
import cz.cvut.fit.tjv.chukavol.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepositoryMock;

    @Test
    void findAll(){
        Student student1 = new Student("chukavol","iLoveCoding1",1,null);
        Student student2 = new Student("chukavol2","iLoveCoding2",2, null);
        Student student3 = new Student("chukavol3","iLoveCoding3",3, null);

        ReflectionTestUtils.setField(student1, "studentId", 31);
        ReflectionTestUtils.setField(student2, "studentId", 32);
        ReflectionTestUtils.setField(student3, "studentId", 33);

        List<Student> studentToReturn = Arrays.asList(student1,student2,student3);
        BDDMockito.given(studentRepositoryMock.findAll()).willReturn(studentToReturn);
        List<StudentDTO> returnedStudent = studentService.findAll();


        Assertions.assertEquals(returnedStudent.get(0).getStudentId(), 31);
        Assertions.assertEquals(returnedStudent.get(0).getStudentUsername(), "chukavol");
        Assertions.assertEquals(returnedStudent.get(0).getPassword(), "iLoveCoding1");
        Assertions.assertEquals(returnedStudent.get(0).getGrade(), 1);
        Assertions.assertEquals(returnedStudent.get(1).getStudentId(), 32);
        Assertions.assertEquals(returnedStudent.get(1).getStudentUsername(), "chukavol2");
        Assertions.assertEquals(returnedStudent.get(1).getPassword(), "iLoveCoding2");
        Assertions.assertEquals(returnedStudent.get(1).getGrade(), 2);
        Assertions.assertEquals(returnedStudent.get(2).getStudentId(), 33);
        Assertions.assertEquals(returnedStudent.get(2).getStudentUsername(), "chukavol3");
        Assertions.assertEquals(returnedStudent.get(2).getPassword(), "iLoveCoding3");
        Assertions.assertEquals(returnedStudent.get(2).getGrade(), 3);

        Mockito.verify(studentRepositoryMock, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void findAllByIds(){
        Student student1 = new Student("chukavol","iLoveCoding1",1,null);
        Student student2 = new Student("chukavol2","iLoveCoding2",2, null);
        Student student3 = new Student("chukavol3","iLoveCoding3",3, null);

        ReflectionTestUtils.setField(student1, "studentId", 31);
        ReflectionTestUtils.setField(student2, "studentId", 32);
        ReflectionTestUtils.setField(student3, "studentId", 33);

        List<Student> studentToReturn = Arrays.asList(student1,student2,student3);
        List<Integer> ids = Arrays.asList(31,32,33);

        BDDMockito.given(studentRepositoryMock.findAllById(ids)).willReturn(studentToReturn);
        List<Student> returnedStudent = studentService.findAllByIds(ids);

        Assertions.assertEquals(returnedStudent.get(0).getStudentId(), 31);
        Assertions.assertEquals(returnedStudent.get(0).getStudentUsername(), "chukavol");
        Assertions.assertEquals(returnedStudent.get(0).getPassword(), "iLoveCoding1");
        Assertions.assertEquals(returnedStudent.get(0).getGrade(), 1);
        Assertions.assertEquals(returnedStudent.get(0).getDeadlines(), null);
        Assertions.assertEquals(returnedStudent.get(1).getStudentId(), 32);
        Assertions.assertEquals(returnedStudent.get(1).getStudentUsername(), "chukavol2");
        Assertions.assertEquals(returnedStudent.get(1).getPassword(), "iLoveCoding2");
        Assertions.assertEquals(returnedStudent.get(1).getGrade(), 2);
        Assertions.assertEquals(returnedStudent.get(1).getDeadlines(), null);
        Assertions.assertEquals(returnedStudent.get(2).getStudentId(), 33);
        Assertions.assertEquals(returnedStudent.get(2).getStudentUsername(), "chukavol3");
        Assertions.assertEquals(returnedStudent.get(2).getPassword(), "iLoveCoding3");
        Assertions.assertEquals(returnedStudent.get(2).getGrade(), 3);
        Assertions.assertEquals(returnedStudent.get(2).getDeadlines(), null);
    }

    @Test
    void findById(){
        Student studentToReturn = new Student("chukavol","iLoveCoding1",1,null);
        ReflectionTestUtils.setField(studentToReturn, "studentId", 31);

        BDDMockito.given(studentRepositoryMock.findById(Mockito.any(Integer.class)))
                .willReturn(Optional.of(studentToReturn));

        Student returnedStudent = studentService.findById(31).get();

        Assertions.assertEquals(returnedStudent.getStudentId(), 31);
        Assertions.assertEquals(returnedStudent.getStudentUsername(), "chukavol");
        Assertions.assertEquals(returnedStudent.getPassword(), "iLoveCoding1");
        Assertions.assertEquals(returnedStudent.getGrade(), 1);
        Assertions.assertEquals(returnedStudent.getDeadlines(), null);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(studentRepositoryMock, Mockito.atLeastOnce()).findById(argumentCaptor.capture());
        Integer idProvided = argumentCaptor.getValue();
        Assertions.assertEquals(idProvided, 31);
    }

    @Test
    void findByIdAsDTO() {
        Student studentToReturn = new Student("chukavol", "iLoveCoding1", 1, null);
        ReflectionTestUtils.setField(studentToReturn, "studentId", 31);

        BDDMockito.given(studentRepositoryMock.findById(Mockito.any(Integer.class)))
                .willReturn(Optional.of(studentToReturn));

        StudentDTO returnedStudent = studentService.findByIdAsDTO(31).get();

        Assertions.assertEquals(returnedStudent.getStudentId(), 31);
        Assertions.assertEquals(returnedStudent.getStudentUsername(), "chukavol");
        Assertions.assertEquals(returnedStudent.getPassword(), "iLoveCoding1");
        Assertions.assertEquals(returnedStudent.getGrade(), 1);
        Assertions.assertEquals(returnedStudent.getDeadlinesId(), null);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(studentRepositoryMock, Mockito.atLeastOnce()).findById(argumentCaptor.capture());
        Integer idProvided = argumentCaptor.getValue();
        Assertions.assertEquals(idProvided, 31);
    }

    @Test
    void create(){
        Student studentToReturn = new Student("chukavol", "iLoveCoding1", 1, null);
        ReflectionTestUtils.setField(studentToReturn, "studentId", 31);
        StudentCreateDTO studentCreateDTO = new StudentCreateDTO("chukavol", "iLoveCoding1", 1, null);

        BDDMockito.given(studentRepositoryMock.save(any(Student.class))).willReturn(studentToReturn);

        StudentDTO returnedStudentDTO = studentService.create(studentCreateDTO);

        Assertions.assertEquals(returnedStudentDTO.getStudentId(), 31);
        Assertions.assertEquals(returnedStudentDTO.getStudentUsername(), "chukavol");
        Assertions.assertEquals(returnedStudentDTO.getPassword(), "iLoveCoding1");
        Assertions.assertEquals(returnedStudentDTO.getGrade(), 1);
        Assertions.assertEquals(returnedStudentDTO.getDeadlinesId(), null);

        ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);
        Mockito.verify(studentRepositoryMock, Mockito.atLeastOnce()).save(argumentCaptor.capture());
        Student subjectProvidedToSave = argumentCaptor.getValue();
        assertEquals("chukavol", subjectProvidedToSave.getStudentUsername());
        assertEquals("iLoveCoding1", subjectProvidedToSave.getPassword());
        assertEquals(1, subjectProvidedToSave.getGrade());
        assertEquals(null, subjectProvidedToSave.getDeadlines());
    }

    @Test
    void update(){
        Student studentToReturn = new Student("chukavol", "iLoveCoding1", 1, null);
        ReflectionTestUtils.setField(studentToReturn, "studentId", 31);
        StudentCreateDTO studentCreateDTO = new StudentCreateDTO("chukavol", "iLoveCoding1", 1, null);

        BDDMockito.given(studentRepositoryMock.save(any(Student.class))).willReturn(studentToReturn);
        BDDMockito.given(studentRepositoryMock.existsById(31)).willReturn(true);
        BDDMockito.given(studentRepositoryMock.findById(Mockito.any(Integer.class)))
                .willReturn(Optional.of(studentToReturn));

        StudentDTO returnedStudentDTO = studentService.create(studentCreateDTO);

        Assertions.assertEquals(returnedStudentDTO.getStudentId(), 31);
        Assertions.assertEquals(returnedStudentDTO.getStudentUsername(), "chukavol");
        Assertions.assertEquals(returnedStudentDTO.getPassword(), "iLoveCoding1");
        Assertions.assertEquals(returnedStudentDTO.getGrade(), 1);
        Assertions.assertEquals(returnedStudentDTO.getDeadlinesId(), null);
    }

    @Test
    void deleteById() throws Exception {

        Student studentToReturn = new Student("chukavol", "iLoveCoding1", 1, null);
        ReflectionTestUtils.setField(studentToReturn, "studentId", 31);

        BDDMockito.given(studentRepositoryMock.existsById(31)).willReturn(true);
        studentService.deleteById(31);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(studentRepositoryMock, Mockito.atLeastOnce()).deleteById(argumentCaptor.capture());
        Integer idProvided = argumentCaptor.getValue();
        Assertions.assertEquals(31, idProvided);
    }


}
