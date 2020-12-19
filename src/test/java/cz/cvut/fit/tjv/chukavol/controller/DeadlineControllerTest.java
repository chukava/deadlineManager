package cz.cvut.fit.tjv.chukavol.controller;

import cz.cvut.fit.tjv.chukavol.dto.*;
import cz.cvut.fit.tjv.chukavol.entity.Deadline;
import cz.cvut.fit.tjv.chukavol.entity.Student;
import cz.cvut.fit.tjv.chukavol.entity.Subject;
import cz.cvut.fit.tjv.chukavol.service.DeadlineService;
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

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DeadlineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeadlineService deadlineService;

    @Test
    void byId() throws Exception {
        List<Integer> ids = new ArrayList<>();
        ids.addAll(Arrays.asList(31,32));

        DeadlineDTO deadline = new DeadlineDTO(21,"d.u.1.", "21.12.2020", 5, ids,41);

        BDDMockito.given(deadlineService.findByIdAsDTO(deadline.getDeadlineId())).willReturn(Optional.of(deadline));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/deadline/{deadlineId}", deadline.getDeadlineId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deadlineId", CoreMatchers.is(deadline.getDeadlineId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.taskDescription", CoreMatchers.is(deadline.getTaskDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deadlineDate", CoreMatchers.is(deadline.getDeadlineDate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxPoints", CoreMatchers.is(deadline.getMaxPoints())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.studentsId", CoreMatchers.is(deadline.getStudentsId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subjectId", CoreMatchers.is(deadline.getSubjectId())));
        Mockito.verify(deadlineService, Mockito.atLeastOnce()).findByIdAsDTO(deadline.getDeadlineId());
    }

    @Test
    void all() throws Exception {

        DeadlineDTO deadline1 = new DeadlineDTO(21,"d.u.1.", "21.12.2020", 5, Collections.singletonList(31),41);
        DeadlineDTO deadline2 = new DeadlineDTO(22,"d.u.2.", "22.12.2020", 6, Collections.singletonList(32),42);
        DeadlineDTO deadline3 = new DeadlineDTO(23,"d.u.3.", "23.12.2020", 7, Collections.singletonList(33),43);
        List<DeadlineDTO> deadlineToReturn = Arrays.asList(deadline1,deadline2,deadline3);

        BDDMockito.given(deadlineService.findAll(PageRequest.of(0, 3))).willReturn(deadlineToReturn);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/deadline/all?page=0&size=3"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(deadlineService, Mockito.atLeastOnce()).findAll(PageRequest.of(0, 3));
    }

    @Test
    void create() throws Exception {

        List<Integer> ids = new ArrayList<>();
        ids.addAll(Arrays.asList(31,32));

        DeadlineDTO deadlineToReturn = new DeadlineDTO(51,"d.u.1.", "21.12.2020", 5, ids,41);

        ResponseEntity<DeadlineDTO> response = ResponseEntity
                .created((Link.of("http://localhost:8080/deadline/" + deadlineToReturn.getDeadlineId())).toUri())
                .build();

        BDDMockito.given(deadlineService.create(Mockito.any(DeadlineCreateDTO.class))).willReturn(deadlineToReturn);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/deadline")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"taskDescription\":\"d.u.1.\"," +
                                  "\"deadlineDate\":\"21.12.2020\"," +
                                  "\"maxPoints\":\"5\"," +
                                  "\"studentsId\":[31,32]," +
                                  "\"subjectId\":\"41\"}")
                        .accept(MediaType.ALL)
        ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deadlineId", CoreMatchers.is(deadlineToReturn.getDeadlineId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.taskDescription", CoreMatchers.is(deadlineToReturn.getTaskDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deadlineDate", CoreMatchers.is(deadlineToReturn.getDeadlineDate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxPoints", CoreMatchers.is(deadlineToReturn.getMaxPoints())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.studentsId", CoreMatchers.is(deadlineToReturn.getStudentsId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subjectId", CoreMatchers.is(deadlineToReturn.getSubjectId())));
    }

    @Test
    void update() throws Exception {
        List<Integer> ids = new ArrayList<>();
        ids.addAll(Arrays.asList(31,32));

        DeadlineCreateDTO deadline = new DeadlineCreateDTO("d.u.1.", "21.12.2020", 10, ids,41);
        DeadlineDTO deadlineToReturn = new DeadlineDTO(51,"d.u.1.", "21.12.2020", 5, ids,41);

        BDDMockito.given(deadlineService.update(51,deadline)).willReturn(deadlineToReturn);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/deadline/" + deadlineToReturn.getDeadlineId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"taskDescription\":\"d.u.1.\"," +
                                "\"deadlineDate\":\"21.12.2020\"," +
                                "\"maxPoints\":\"10\"," +
                                "\"studentsId\":[31,32]," +
                                "\"subjectId\":\"41\"}")
                        .accept(MediaType.ALL)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        List<Integer> ids = new ArrayList<>();
        ids.addAll(Arrays.asList(31,32));

        DeadlineDTO deadline = new DeadlineDTO(51,"d.u.1.", "21.12.2020", 10, ids,41);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/deadline/"+ deadline.getDeadlineId())
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(deadlineService, Mockito.atLeastOnce()).deleteById(51);
    }

}
