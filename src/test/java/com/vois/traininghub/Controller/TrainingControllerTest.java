package com.vois.traininghub.Controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.vois.traininghub.Model.Training;
import com.vois.traininghub.Repository.TrainingRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driver-class-name=org.h2.Driver",
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
                .content("{ \"name\": \"Kongo\", \"link\": \"link\", \"duration\": 5, \"topic\": \"entity\", \"entity\": \"topic\", \"AVG_Rating\": 5 }"))
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

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
    }

    
    @Test
    public void getTrainingByIdTest() throws Exception {
        Training mockTraining = new Training("name","link",5,"entity","topic",5);
        mockTraining.setId(1L);

        when(trainingRepository.findById(1L)).thenReturn(Optional.of(mockTraining));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/training")
                .param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        assertTrue(responseContent.contains("\"id\":1"));
    
    }


    @Test
    public void getTrainingByNameTest() throws Exception {
        Training mockTraining1 = new Training("name", "link", 5, "entity", "topic", 5);
        Training mockTraining2 = new Training("name", "link2", 8, "entity2", "topic2", 8);

        List<Training> mockTrainingList = new ArrayList<>();
        mockTrainingList.add(mockTraining1);
        mockTrainingList.add(mockTraining2);

        when(trainingRepository.findAll()).thenReturn(mockTrainingList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/training")
                .param("name", "name"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        assertTrue(responseContent.contains("\"name\":\"name\""));

        verify(trainingRepository).findAll();
    }

    @Test
    public void getTrainingByEntityTest() throws Exception {
        Training mockTraining1 = new Training("name", "link", 5, "entity", "topic", 5);
        Training mockTraining2 = new Training("name", "link2", 8, "entity2", "topic2", 8);
        Training mockTraining3 = new Training("name", "link", 5, "entity", "topic", 5);

        List<Training> mockTrainingList = new ArrayList<>();
        mockTrainingList.add(mockTraining1);
        mockTrainingList.add(mockTraining2);
        mockTrainingList.add(mockTraining3);

        when(trainingRepository.findAll()).thenReturn(mockTrainingList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/training")
                .param("entity", "topic"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        assertTrue(responseContent.contains("\"entity\":\"topic\""));

        verify(trainingRepository).findAll();
    }

    @Test
    public void getTrainingByTopicTest() throws Exception {
        Training mockTraining1 = new Training("name", "link", 5, "entity", "topic", 5);
        Training mockTraining2 = new Training("name", "link2", 8, "entity2", "topic2", 8);
        Training mockTraining3 = new Training("name", "link", 5, "entity", "topic", 5);

        List<Training> mockTrainingList = new ArrayList<>();
        mockTrainingList.add(mockTraining1);
        mockTrainingList.add(mockTraining2);
        mockTrainingList.add(mockTraining3);

        when(trainingRepository.findAll()).thenReturn(mockTrainingList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/training")
                .param("topic", "entity"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        assertTrue(responseContent.contains("\"entity\":\"topic\""));

        verify(trainingRepository).findAll();
    }

    @Test
    public void deleteTrainingTest() throws Exception {
        long trainingId = 1L;
        Training mockTraining = new Training("name", "link", 5, "entity", "topic", 5);
        mockTraining.setId(trainingId);

        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(mockTraining));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/training")
                .param("id", String.valueOf(trainingId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        assertTrue(responseContent.contains("\"id\":" + trainingId));
        verify(trainingRepository).deleteById(trainingId);
    }

        @Test
        public void deleteTrainingNotFoundTest() throws Exception {
            long trainingId = 1L;
        
            when(trainingRepository.findById(trainingId)).thenReturn(Optional.empty());
        
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/training")
                    .param("id", String.valueOf(trainingId)))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andReturn();
        
            String responseContent = result.getResponse().getContentAsString();
            System.out.println("Response Content: " + responseContent);
        
            assertTrue(responseContent.contains("Training with ID " + trainingId + " not found."));
            verify(trainingRepository, never()).deleteById(anyLong());
    }

    @Test
    public void deleteTrainingInvalidIdTest() throws Exception {
        long invalidTrainingId = 0L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/training")
                .param("id", String.valueOf(invalidTrainingId)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        assertTrue(responseContent.contains("Please enter a valid ID"));
        verify(trainingRepository, never()).deleteById(anyLong());
    }
    
}
      
