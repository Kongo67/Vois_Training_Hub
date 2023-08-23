package com.vois.traininghub.Controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
//write a class called TrainingCreateTest and write a unit test for post api in training controller
//use the following imports
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

//import com.vois.traininghub.Controller.TrainingController;
import com.vois.traininghub.Model.Training;
import com.vois.traininghub.Repository.TrainingRepository;

//@WebMvcTest(value = TrainingController.class)
//@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driver-class-name=org.h2.Driver",
    // other H2 properties if needed
})

public class TrainingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainingController trainingController;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createTrainingTest() throws Exception{

        Training mockTraining = new Training("Kongo","link",5,"entity","topic",5);

        when(trainingRepository.save(any(Training.class))).thenReturn(mockTraining);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/training")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"Simon\", \"link\": \"link\", \"duration\": 5, \"topic\": \"entity\", \"entity\": \"topic\", \"AVG_Rating\": 5 }"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("Kongo")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.link", Matchers.equalTo("link")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.duration", Matchers.equalTo(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.topic", Matchers.equalTo("entity")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.entity", Matchers.equalTo("topic")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.AVG_Rating", Matchers.equalTo(5)))
                .andExpect(MockMvcResultMatchers.status().isCreated()) // Verify HTTP status is CREATED (201)
                .andReturn();


        int statusCode = result.getResponse().getStatus();
        assertEquals(HttpStatus.CREATED.value(), statusCode);
    }

    
    @Test
    public void getTrainingByIdTest() throws Exception {
        Training mockTraining = new Training("name","link",5,"entity","topic",5);
        mockTraining.setId(1L);

        when(trainingRepository.findById(1L)).thenReturn(Optional.of(mockTraining));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/training?id=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.equalTo("name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].link", Matchers.equalTo("link")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].duration", Matchers.equalTo(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].entity", Matchers.equalTo("topic")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].topic", Matchers.equalTo("entity")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].AVG_Rating", Matchers.equalTo(5)));
    }
}
      
