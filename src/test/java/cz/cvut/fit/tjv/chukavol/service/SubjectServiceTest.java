package cz.cvut.fit.tjv.chukavol.service;

import cz.cvut.fit.tjv.chukavol.dto.SubjectCreateDTO;
import cz.cvut.fit.tjv.chukavol.dto.SubjectDTO;
import cz.cvut.fit.tjv.chukavol.entity.Subject;
import cz.cvut.fit.tjv.chukavol.repository.SubjectRepository;
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
public class SubjectServiceTest {

    @Autowired
    private SubjectService subjectService;

    @MockBean
    private SubjectRepository  subjectRepositoryMock;

    @Test
    void findAll() {
        Subject subject1 = new Subject("BI-AG1",6);
        Subject subject2 = new Subject("BI-PJV",4);
        Subject subject3 = new Subject("BI-LIN",7);

        ReflectionTestUtils.setField(subject1, "subjectId", 31);
        ReflectionTestUtils.setField(subject2, "subjectId", 32);
        ReflectionTestUtils.setField(subject3, "subjectId", 33);

        List<Subject> subjectToReturn = Arrays.asList(subject1,subject2,subject3);
        BDDMockito.given(subjectRepositoryMock.findAll()).willReturn(subjectToReturn);
        List<SubjectDTO> returnedSubjects = subjectService.findAll();

        Assertions.assertEquals(returnedSubjects.get(0).getSubjectId(), 31);
        Assertions.assertEquals(returnedSubjects.get(0).getSubjectCode(), "BI-AG1");
        Assertions.assertEquals(returnedSubjects.get(0).getNumberOfCredits(), 6);
        Assertions.assertEquals(returnedSubjects.get(1).getSubjectId(), 32);
        Assertions.assertEquals(returnedSubjects.get(1).getSubjectCode(), "BI-PJV");
        Assertions.assertEquals(returnedSubjects.get(1).getNumberOfCredits(), 4);
        Assertions.assertEquals(returnedSubjects.get(2).getSubjectId(), 33);
        Assertions.assertEquals(returnedSubjects.get(2).getSubjectCode(), "BI-LIN");
        Assertions.assertEquals(returnedSubjects.get(2).getNumberOfCredits(), 7);

        Mockito.verify(subjectRepositoryMock, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void findById(){
        Subject subjectToReturn = new Subject("BI-AG1",6);
        ReflectionTestUtils.setField(subjectToReturn,"subjectId", 30);

        BDDMockito.given(subjectRepositoryMock.findById(Mockito.any(Integer.class)))
                .willReturn(Optional.of(subjectToReturn));
        Subject returnedSubject = subjectService.findById(30).get();

        Assertions.assertEquals(returnedSubject.getSubjectId(), 30);
        Assertions.assertEquals(returnedSubject.getSubjectCode(), "BI-AG1");
        Assertions.assertEquals(returnedSubject.getNumberOfCredits(), 6);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(subjectRepositoryMock, Mockito.atLeastOnce()).findById(argumentCaptor.capture());
        Integer idProvided = argumentCaptor.getValue();
        Assertions.assertEquals(idProvided, 30);
    }

    @Test
    void findByIdAsDTO(){
        Subject subjectToReturn = new Subject("BI-AG1",6);

        ReflectionTestUtils.setField(subjectToReturn,"subjectId", 30);

        BDDMockito.given(subjectRepositoryMock.findById(Mockito.any(Integer.class)))
                .willReturn(Optional.of(subjectToReturn));

        SubjectDTO returnedSubject = subjectService.findByIdAsDTO(30).get();

        Assertions.assertEquals(returnedSubject.getSubjectId(), 30);
        Assertions.assertEquals(returnedSubject.getSubjectCode(), "BI-AG1");
        Assertions.assertEquals(returnedSubject.getNumberOfCredits(), 6);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(subjectRepositoryMock, Mockito.atLeastOnce()).findById(argumentCaptor.capture());
        Integer idProvided = argumentCaptor.getValue();
        Assertions.assertEquals(idProvided, 30);
    }


    @Test
    void create(){
        Subject subjectToReturn = new Subject("BI-AG1",6);

        // Setting id even without setter or constructor
        ReflectionTestUtils.setField(subjectToReturn, "subjectId",30);
        SubjectCreateDTO subjectCreateDTO = new SubjectCreateDTO("BI-AG1", 6);

        // subjectToReturn should be returned for ANY subject passed to save
        BDDMockito.given(subjectRepositoryMock.save(any(Subject.class))).willReturn(subjectToReturn);
        SubjectDTO returnedSubjectDTO = subjectService.create(subjectCreateDTO);

        // End of option with equals() in SubjectDTO
        // Option without equals() in SubjectDTO
        assertEquals(returnedSubjectDTO.getSubjectId(), 30);
        assertEquals(returnedSubjectDTO.getSubjectCode(), "BI-AG1");
        assertEquals(returnedSubjectDTO.getNumberOfCredits(), 6);
        // End of option without equals() in SubjectDTO

        // Checking that subject passed to .save() had correct attributes
        ArgumentCaptor<Subject> argumentCaptor = ArgumentCaptor.forClass(Subject.class);
        Mockito.verify(subjectRepositoryMock, Mockito.atLeastOnce()).save(argumentCaptor.capture());
        Subject subjectProvidedToSave = argumentCaptor.getValue();
        assertEquals("BI-AG1", subjectProvidedToSave.getSubjectCode());
        assertEquals(6, subjectProvidedToSave.getNumberOfCredits());
    }


    @Test
    void update() throws Exception {
        Subject subjectToReturn = new Subject("BI-AG1",6);

        ReflectionTestUtils.setField(subjectToReturn, "subjectId",30);
        SubjectCreateDTO subjectCreateDTO = new SubjectCreateDTO("BI-AG1", 6);

        BDDMockito.given(subjectRepositoryMock.save(any(Subject.class))).willReturn(subjectToReturn);
        BDDMockito.given(subjectRepositoryMock.existsById(30)).willReturn(true);
        BDDMockito.given(subjectRepositoryMock.findById(Mockito.any(Integer.class)))
                .willReturn(Optional.of(subjectToReturn));

        SubjectDTO returnedSubjectDTO = subjectService.update(30,subjectCreateDTO);

        // End of option with equals() in SubjectDTO
        // Option without equals() in SubjectDTO
        assertEquals(returnedSubjectDTO.getSubjectId(), 30);
        assertEquals(returnedSubjectDTO.getSubjectCode(), "BI-AG1");
        assertEquals(returnedSubjectDTO.getNumberOfCredits(), 6);
        // End of option without equals() in SubjectDTO
    }

    @Test
    void deleteById() throws Exception {

        Subject subjectToReturn = new Subject("BI-AG1",6);
        ReflectionTestUtils.setField(subjectToReturn, "subjectId",30);

        BDDMockito.given(subjectRepositoryMock.existsById(30)).willReturn(true);
        subjectService.deleteById(30);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(subjectRepositoryMock, Mockito.atLeastOnce()).deleteById(argumentCaptor.capture());
        Integer idProvided = argumentCaptor.getValue();
        Assertions.assertEquals(30, idProvided);
    }
}
