package cz.cvut.fit.tjv.chukavol;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class DeadlineTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getDeadline() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/deadline")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        "{\"taskDescription\":\"Prvni kontrolni bod semestralky\",\"deadlineDate\":\"06.10.2020\",\"maxPoints\":100,\"isDone\":true}")
                );
    }

}
