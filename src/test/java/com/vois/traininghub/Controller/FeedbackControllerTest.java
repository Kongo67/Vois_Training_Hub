package com.vois.traininghub.Controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vois.traininghub.Model.Feedback;
import com.vois.traininghub.Model.Training;
import com.vois.traininghub.Repository.FeedbackRepository;
import com.vois.traininghub.Repository.TrainingRepository;


@SpringBootTest
@AutoConfigureMockMvc
public class FeedbackControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackRepository feedbackRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private FeedbackController feedbackController;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

   @Test
    public void getFeedbackByIdTest() throws Exception {
        Long feedbackId = 1L;
        Feedback mockFeedback = new Feedback("Review", 5, new Training("TrainingName", "TrainingLink", 5, "TrainingEntity", "TrainingTopic", 5));
        mockFeedback.setFeedback_Id(feedbackId);

        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(mockFeedback));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/feedback")
                .param("id", String.valueOf(feedbackId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].feedback_Id", Matchers.equalTo(feedbackId.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].review", Matchers.equalTo("Review")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rating", Matchers.equalTo(5)))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        verify(feedbackRepository).findById(feedbackId);
    }

    @Test
    public void getFeedbackByReview() throws Exception {
        
        Feedback mockFeedback1 = new Feedback("Review1", 5, new Training("TrainingName", "TrainingLink", 5, "TrainingEntity", "TrainingTopic", 5));
        Feedback mockFeedback2 = new Feedback("Review1", 5, new Training("TrainingName", "TrainingLink", 5, "TrainingEntity", "TrainingTopic", 5));

        List<Feedback> mockFeedbackList = new ArrayList<>();
        mockFeedbackList.add(mockFeedback1);
        mockFeedbackList.add(mockFeedback2);

        when(feedbackRepository.findAll()).thenReturn(mockFeedbackList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/feedback")
                .param("review", "Review1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].review", Matchers.equalTo("Review1")))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
        verify(feedbackRepository).findAll();

    }

    @Test
    public void getFeedbackByRating() throws Exception{

        Feedback mockFeedback1 = new Feedback("Review1", 5, new Training("TrainingName", "TrainingLink", 5, "TrainingEntity", "TrainingTopic", 5));
        Feedback mockFeedback2 = new Feedback("Review2", 5, new Training("TrainingName", "TrainingLink", 5, "TrainingEntity", "TrainingTopic", 5));

        List<Feedback> mockFeedbackList = new ArrayList<>();
        mockFeedbackList.add(mockFeedback1);
        mockFeedbackList.add(mockFeedback2);

        when(feedbackRepository.findAll()).thenReturn(mockFeedbackList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/feedback")
                .param("rating", "5"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rating", Matchers.equalTo(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].rating", Matchers.equalTo(5)))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
        verify(feedbackRepository).findAll();
    }

    @Test
    public void getFeedbackByTrainingIdTest() throws Exception {

        Long trainingId = 1L;
        Training mockTraining = new Training("TrainingName", "TrainingLink", 5, "TrainingEntity", "TrainingTopic", 5);
        mockTraining.setId(trainingId);

        Training mockTraining2 = new Training("TrainingName", "TrainingLink", 5, "TrainingEntity", "TrainingTopic", 5);
        mockTraining2.setId(2L);

        Feedback mockFeedback1 = new Feedback("Review1", 5, mockTraining);
        Feedback mockFeedback2 = new Feedback("Review2", 5, mockTraining2);

        List<Feedback> mockFeedbackList = new ArrayList<>();
        mockFeedbackList.add(mockFeedback1);
        mockFeedbackList.add(mockFeedback2);

        when(feedbackRepository.findAll()).thenReturn(mockFeedbackList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/feedback")
                .param("trainingId", String.valueOf(trainingId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fk.id", Matchers.equalTo(trainingId.intValue())))
                //.andExpect(MockMvcResultMatchers.jsonPath("$[1].fk.id", Matchers.equalTo(trainingId.intValue())))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
        verify(feedbackRepository).findAll();
    }

    // @Test
    // public void createFeedbackTest() throws Exception {
    //     Training mockTraining = new Training("TrainingName", "TrainingLink", 5, "TrainingEntity", "TrainingTopic", 5);
    //     Long trainingId = 1L;
    //     mockTraining.setId(trainingId);

    //     Feedback mockFeedback = new Feedback("Review1", 5, mockTraining);

    //     when(trainingRepository.findById(any(Long.class))).thenReturn(Optional.of(mockTraining));
    //     when(feedbackRepository.save(any(Feedback.class))).thenReturn(mockFeedback);

    //     String requestBody = "{\"review\": \"Review1\", \"rating\": 5 }";

    //     mockMvc.perform(MockMvcRequestBuilders.post("/api/feedback")
    //             .param("FK", String.valueOf(trainingId)) // Provide the training ID as FK
    //             .content(requestBody) // Set the request body
    //             .contentType(MediaType.APPLICATION_JSON)) // Set the content type to JSON
    //             .andExpect(MockMvcResultMatchers.status().isCreated())
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.review", Matchers.equalTo("Review1")))
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.rating", Matchers.equalTo(5)));

    //     verify(trainingRepository).findById(trainingId);
    //     verify(feedbackRepository).save(any(Feedback.class));
    // }


    





        
    
}
