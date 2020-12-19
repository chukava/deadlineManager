package cz.cvut.fit.tjv.chukavol.controller;

import cz.cvut.fit.tjv.chukavol.dto.StudentDTO;
import cz.cvut.fit.tjv.chukavol.dto.SubjectCreateDTO;
import cz.cvut.fit.tjv.chukavol.dto.SubjectDTO;
import cz.cvut.fit.tjv.chukavol.entity.Subject;
import cz.cvut.fit.tjv.chukavol.service.SubjectService;
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
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class SubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectService subjectService;

    @Test
    void byId() throws Exception {
        SubjectDTO subject = new SubjectDTO(6,"BI-AG1",6);
        BDDMockito.given(subjectService.findByIdAsDTO(subject.getSubjectId())).willReturn(Optional.of(subject));

        mockMvc.perform(
                MockMvcRequestBuilders
                .get("/subject/{subjectId}", subject.getSubjectId()))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andExpect(MockMvcResultMatchers.jsonPath("$.subjectId", CoreMatchers.is(subject.getSubjectId())))
         .andExpect(MockMvcResultMatchers.jsonPath("$.subjectCode", CoreMatchers.is(subject.getSubjectCode())))
         .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfCredits", CoreMatchers.is(subject.getNumberOfCredits())));

        Mockito.verify(subjectService, Mockito.atLeastOnce()).findByIdAsDTO(subject.getSubjectId());
    }

    @Test
    void all() throws Exception {
        SubjectDTO subject1 = new SubjectDTO(1,"BI-AG1",6);
        SubjectDTO subject2 = new SubjectDTO(2,"BI-PJV",4);
        SubjectDTO subject3 = new SubjectDTO(3,"BI-LIN",7);

        List<SubjectDTO> subjectToReturn = Arrays.asList(subject1,subject2,subject3);

        BDDMockito.given(subjectService.findAll(PageRequest.of(0, 3))).willReturn(subjectToReturn);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/subject/all?page=0&size=3"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(subjectService, Mockito.atLeastOnce()).findAll(PageRequest.of(0, 3));
    }

    @Test
    void create() throws Exception {
        SubjectDTO subjectToReturn = new SubjectDTO(30,"BI-AG1",6);

        ResponseEntity<StudentDTO> response = ResponseEntity
                .created((Link.of("http://localhost:8080/subject/" + subjectToReturn.getSubjectId())).toUri())
                .build();

        BDDMockito.given(subjectService.create(Mockito.any(SubjectCreateDTO.class))).willReturn(subjectToReturn);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/subject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"subjectCode\":\"BI-AG1\"," +
                                  "\"numberOfCredits\":\"6\"}")
                        .accept(MediaType.ALL)
        ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.subjectId", CoreMatchers.is(subjectToReturn.getSubjectId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subjectCode", CoreMatchers.is(subjectToReturn.getSubjectCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfCredits", CoreMatchers.is(subjectToReturn.getNumberOfCredits())));
    }

    @Test
    void update() throws Exception {
        SubjectCreateDTO subject = new SubjectCreateDTO("BI-AG1",6);
        SubjectDTO subjectToReturn = new SubjectDTO(30,"BI-AG1",5);

        BDDMockito.given(subjectService.update(30,subject)).willReturn(subjectToReturn);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/subject/" + subjectToReturn.getSubjectId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"subjectCode\":\"BI-AG1\",\"numberOfCredits\":\"5\"}")
                        .accept(MediaType.ALL)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        Subject subject = new Subject("BI-AG1",6);
        ReflectionTestUtils.setField(subject,"subjectId", 31);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/subject/"+ subject.getSubjectId())
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(subjectService, Mockito.atLeastOnce()).deleteById(31);
    }
}
