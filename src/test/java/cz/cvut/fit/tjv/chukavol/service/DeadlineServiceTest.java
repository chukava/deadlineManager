package cz.cvut.fit.tjv.chukavol.service;

import cz.cvut.fit.tjv.chukavol.dto.DeadlineCreateDTO;
import cz.cvut.fit.tjv.chukavol.dto.DeadlineDTO;
import cz.cvut.fit.tjv.chukavol.entity.Deadline;
import cz.cvut.fit.tjv.chukavol.entity.Student;
import cz.cvut.fit.tjv.chukavol.entity.Subject;
import cz.cvut.fit.tjv.chukavol.repository.DeadlineRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class DeadlineServiceTest {

    @Autowired
    private DeadlineService deadlineService;

    @MockBean
    private DeadlineRepository deadlineRepositoryMock;

    @MockBean
    private StudentService studentService;

    @MockBean
    private SubjectService subjectService;

    @Test
    void findAll() {
        Student student1 = new Student("chukavol", "iLoveCoding1", 1, null);
        Student student2 = new Student("chukavol2", "iLoveCoding2", 2, null);
        Student student3 = new Student("chukavol3", "iLoveCoding3", 3, null);

        ReflectionTestUtils.setField(student1, "studentId", 31);
        ReflectionTestUtils.setField(student2, "studentId", 32);
        ReflectionTestUtils.setField(student3, "studentId", 33);

        List<Student> studentToReturn1 = Arrays.asList(student1, student2);
        List<Student> studentToReturn2 = Arrays.asList(student1, student3);
        List<Student> studentToReturn3 = Arrays.asList(student2, student3);

        Subject subject1 = new Subject("BI-AG1", 6);
        Subject subject2 = new Subject("BI-PJV", 4);
        Subject subject3 = new Subject("BI-LIN", 7);

        ReflectionTestUtils.setField(subject1, "subjectId", 41);
        ReflectionTestUtils.setField(subject2, "subjectId", 42);
        ReflectionTestUtils.setField(subject3, "subjectId", 43);

        Deadline deadline1 = new Deadline("d.u.1.", "21.12.2020", 1, false, studentToReturn1, subject1);
        Deadline deadline2 = new Deadline("d.u.2.", "22.12.2020", 2, true, studentToReturn2, subject2);
        Deadline deadline3 = new Deadline("d.u.3.", "23.12.2020", 3, false, studentToReturn3, subject3);

        ReflectionTestUtils.setField(deadline1, "deadlineId", 51);
        ReflectionTestUtils.setField(deadline2, "deadlineId", 52);
        ReflectionTestUtils.setField(deadline3, "deadlineId", 53);

        List<Deadline> deadlineToReturn = Arrays.asList(deadline1, deadline2, deadline3);

        BDDMockito.given(deadlineRepositoryMock.findAll()).willReturn(deadlineToReturn);
        List<DeadlineDTO> returnedDeadline = deadlineService.findAll();

        List<Integer> stId1 = Arrays.asList(31, 32);
        List<Integer> stId2 = Arrays.asList(31, 33);
        List<Integer> stId3 = Arrays.asList(32, 33);

        Assertions.assertEquals(returnedDeadline.get(0).getDeadlineId(), 51);
        Assertions.assertEquals(returnedDeadline.get(0).getTaskDescription(), "d.u.1.");
        Assertions.assertEquals(returnedDeadline.get(0).getDeadlineDate(), "21.12.2020");
        Assertions.assertEquals(returnedDeadline.get(0).getMaxPoints(), 1);
        Assertions.assertEquals(returnedDeadline.get(0).getIsDone(), false);
        Assertions.assertEquals(returnedDeadline.get(0).getStudentsId(), stId1);
        Assertions.assertEquals(returnedDeadline.get(0).getSubjectId(), 41);

        Assertions.assertEquals(returnedDeadline.get(1).getDeadlineId(), 52);
        Assertions.assertEquals(returnedDeadline.get(1).getTaskDescription(), "d.u.2.");
        Assertions.assertEquals(returnedDeadline.get(1).getDeadlineDate(), "22.12.2020");
        Assertions.assertEquals(returnedDeadline.get(1).getMaxPoints(), 2);
        Assertions.assertEquals(returnedDeadline.get(1).getIsDone(), true);
        Assertions.assertEquals(returnedDeadline.get(1).getStudentsId(), stId2);
        Assertions.assertEquals(returnedDeadline.get(1).getSubjectId(), 42);

        Assertions.assertEquals(returnedDeadline.get(2).getDeadlineId(), 53);
        Assertions.assertEquals(returnedDeadline.get(2).getTaskDescription(), "d.u.3.");
        Assertions.assertEquals(returnedDeadline.get(2).getDeadlineDate(), "23.12.2020");
        Assertions.assertEquals(returnedDeadline.get(2).getMaxPoints(), 3);
        Assertions.assertEquals(returnedDeadline.get(2).getIsDone(), false);
        Assertions.assertEquals(returnedDeadline.get(2).getStudentsId(), stId3);
        Assertions.assertEquals(returnedDeadline.get(2).getSubjectId(), 43);

        Mockito.verify(deadlineRepositoryMock, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void findById() {
        Student student1 = new Student("chukavol", "iLoveCoding1", 1, null);
        Student student2 = new Student("chukavol2", "iLoveCoding2", 2, null);
        ReflectionTestUtils.setField(student1, "studentId", 31);
        ReflectionTestUtils.setField(student2, "studentId", 32);
        List<Student> studentToReturn1 = Arrays.asList(student1, student2);

        Subject subject1 = new Subject("BI-AG1", 6);
        ReflectionTestUtils.setField(subject1, "subjectId", 41);

        Deadline deadlineToReturn = new Deadline("d.u.1.", "21.12.2020", 1, false, studentToReturn1, subject1);
        ReflectionTestUtils.setField(deadlineToReturn, "deadlineId", 51);


        BDDMockito.given(deadlineRepositoryMock.findById(Mockito.any(Integer.class)))
                .willReturn(Optional.of(deadlineToReturn));

        Deadline returnedDeadline = deadlineService.findById(51).get();

        Assertions.assertEquals(returnedDeadline.getDeadlineId(), 51);
        Assertions.assertEquals(returnedDeadline.getTaskDescription(), "d.u.1.");
        Assertions.assertEquals(returnedDeadline.getDeadlineDate(), "21.12.2020");
        Assertions.assertEquals(returnedDeadline.getMaxPoints(), 1);
        Assertions.assertEquals(returnedDeadline.getIsDone(), false);
        Assertions.assertEquals(returnedDeadline.getStudents(), studentToReturn1);
        Assertions.assertEquals(returnedDeadline.getSubject(), subject1);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(deadlineRepositoryMock, Mockito.atLeastOnce()).findById(argumentCaptor.capture());
        Integer idProvided = argumentCaptor.getValue();
        Assertions.assertEquals(idProvided, 51);
    }

    @Test
    void findByIdAsDTO(){
        Student student1 = new Student("chukavol", "iLoveCoding1", 1, null);
        Student student2 = new Student("chukavol2", "iLoveCoding2", 2, null);
        ReflectionTestUtils.setField(student1, "studentId", 31);
        ReflectionTestUtils.setField(student2, "studentId", 32);
        List<Student> studentToReturn1 = Arrays.asList(student1, student2);

        Subject subject1 = new Subject("BI-AG1", 6);
        ReflectionTestUtils.setField(subject1, "subjectId", 41);

        Deadline deadlineToReturn = new Deadline("d.u.1.", "21.12.2020", 1, false, studentToReturn1, subject1);
        ReflectionTestUtils.setField(deadlineToReturn, "deadlineId", 51);


        BDDMockito.given(deadlineRepositoryMock.findById(Mockito.any(Integer.class)))
                .willReturn(Optional.of(deadlineToReturn));

        DeadlineDTO returnedDeadline = deadlineService.findByIdAsDTO(51).get();

        List<Integer> stId1 = Arrays.asList(31, 32);

        Assertions.assertEquals(returnedDeadline.getDeadlineId(), 51);
        Assertions.assertEquals(returnedDeadline.getTaskDescription(), "d.u.1.");
        Assertions.assertEquals(returnedDeadline.getDeadlineDate(), "21.12.2020");
        Assertions.assertEquals(returnedDeadline.getMaxPoints(), 1);
        Assertions.assertEquals(returnedDeadline.getIsDone(), false);
        Assertions.assertEquals(returnedDeadline.getStudentsId(), stId1);
        Assertions.assertEquals(returnedDeadline.getSubjectId(), 41);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(deadlineRepositoryMock, Mockito.atLeastOnce()).findById(argumentCaptor.capture());
        Integer idProvided = argumentCaptor.getValue();
        Assertions.assertEquals(idProvided, 51);
    }
//
//    @Test
//    void create() throws Exception {
//        //creating  deadlineToReturn that should be returned for ANY integer passed to save
//        Student student1 = new Student("chukavol", "iLoveCoding1", 1, null);
//        Student student2 = new Student("chukavol2", "iLoveCoding2", 2, null);
//        ReflectionTestUtils.setField(student1, "studentId", 31);
//        ReflectionTestUtils.setField(student2, "studentId", 32);
//        List<Student> studentToReturn1 = Arrays.asList(student1, student2);
//
//        BDDMockito.given(studentService.findAllByIds(Mockito.any(List.class))).willReturn(Arrays.asList(student1, student2));
//
//
//        Subject subject1 = new Subject("BI-AG1", 6);
//        ReflectionTestUtils.setField(subject1, "subjectId", 41);
//
//        BDDMockito.given(subjectService.findById(Mockito.any(Integer.class))).willReturn(Optional.of(subject1));
//
//
//        Deadline deadlineToReturn = new Deadline("d.u.1.", "21.12.2020", 1, false, studentToReturn1, subject1);
//        ReflectionTestUtils.setField(deadlineToReturn, "deadlineId", 51);
//
//
//        List<Integer> stId1 = Arrays.asList(31,32);
//        DeadlineCreateDTO deadlineCreateDTO = new DeadlineCreateDTO("d.u.1.", "21.12.2020", 1, false, stId1, 41);
//        BDDMockito.given(deadlineRepositoryMock.save(any(Deadline.class))).willReturn(deadlineToReturn);
//
//        DeadlineDTO returnedDeadline = deadlineService.create(deadlineCreateDTO);
//
//        Assertions.assertEquals(returnedDeadline.getDeadlineId(), 51);
//        Assertions.assertEquals(returnedDeadline.getTaskDescription(), "d.u.1.");
//        Assertions.assertEquals(returnedDeadline.getDeadlineDate(), "21.12.2020");
//        Assertions.assertEquals(returnedDeadline.getMaxPoints(), 1);
//        Assertions.assertEquals(returnedDeadline.getIsDone(), false);
//        Assertions.assertEquals(returnedDeadline.getStudentsId(), stId1);
//        Assertions.assertEquals(returnedDeadline.getSubjectId(), 41);
//    }
//
    @Test
    void update() throws Exception {
        //creating  deadlineToReturn that should be returned for ANY integer passed to save
        Student student1 = new Student("chukavol", "iLoveCoding1", 1, null);
        Student student2 = new Student("chukavol2", "iLoveCoding2", 2, null);
        ReflectionTestUtils.setField(student1, "studentId", 31);
        ReflectionTestUtils.setField(student2, "studentId", 32);
        List<Student> studentToReturn1 = Arrays.asList(student1, student2);

        BDDMockito.given(studentService.findAllByIds(Mockito.any(List.class))).willReturn(Arrays.asList(student1, student2));


        Subject subject1 = new Subject("BI-AG1", 6);
        ReflectionTestUtils.setField(subject1, "subjectId", 41);

        BDDMockito.given(subjectService.findById(Mockito.any(Integer.class))).willReturn(Optional.of(subject1));


        Deadline deadlineToReturn = new Deadline("d.u.1.", "21.12.2020", 1, false, studentToReturn1, subject1);
        ReflectionTestUtils.setField(deadlineToReturn, "deadlineId", 51);


        List<Integer> stId1 = Arrays.asList(31,32);
        DeadlineCreateDTO deadlineCreateDTO = new DeadlineCreateDTO("d.u.1.", "21.12.2020", 1, false, stId1, 41);

        BDDMockito.given(deadlineRepositoryMock.save(any(Deadline.class))).willReturn(deadlineToReturn);
        BDDMockito.given(deadlineRepositoryMock.existsById(51)).willReturn(true);
        BDDMockito.given(deadlineRepositoryMock.findById(Mockito.any(Integer.class))).willReturn(Optional.of(deadlineToReturn));

        DeadlineDTO returnedDeadline = deadlineService.update(51,deadlineCreateDTO);

        Assertions.assertEquals(returnedDeadline.getDeadlineId(), 51);
        Assertions.assertEquals(returnedDeadline.getTaskDescription(), "d.u.1.");
        Assertions.assertEquals(returnedDeadline.getDeadlineDate(), "21.12.2020");
        Assertions.assertEquals(returnedDeadline.getMaxPoints(), 1);
        Assertions.assertEquals(returnedDeadline.getIsDone(), false);
        Assertions.assertEquals(returnedDeadline.getStudentsId(), stId1);
        Assertions.assertEquals(returnedDeadline.getSubjectId(), 41);

    }

    @Test
    void deleteById() throws Exception {
        BDDMockito.given(deadlineRepositoryMock.existsById(51)).willReturn(true);
        deadlineService.deleteById(51);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(deadlineRepositoryMock, Mockito.atLeastOnce()).deleteById(argumentCaptor.capture());
        Integer idProvided = argumentCaptor.getValue();
        Assertions.assertEquals(51, idProvided);
    }
}
