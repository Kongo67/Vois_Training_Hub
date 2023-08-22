package com.vois.traininghub.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vois.traininghub.Exceptions.NoTrainingFoundException;
import com.vois.traininghub.Model.Training;
import com.vois.traininghub.Model.Feedback;
import com.vois.traininghub.Repository.FeedbackRepository;
import com.vois.traininghub.Repository.TrainingRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")


public class FeedbackController {

    @Autowired
    FeedbackRepository feedbackRepository;
    
    @Autowired
    TrainingRepository trainingRepository;


    @GetMapping("/feedback")
    public ResponseEntity<?> getFeedback(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "review", required = false) String review,
            @RequestParam(value = "rating", required = false) Integer rating,
            @RequestParam(value = "trainingId", required = false) Long trainingId) {

            List<Feedback> filteredFeedback = new ArrayList<>();

            if(id != null) {
                Optional<Feedback> feedbackData = feedbackRepository.findById(id);

                if(feedbackData.isPresent()) {
                    filteredFeedback.add(feedbackData.get());
                }
            } else {
                filteredFeedback = feedbackRepository.findAll();
            }

            if(review != null) {
                filteredFeedback = filteredFeedback.stream()
                        .filter(feedback -> feedback.getReview().equals(review))
                        .collect(Collectors.toList());
            }

            if(rating != null){
                filteredFeedback = filteredFeedback.stream()
                        .filter(feedback -> {
                            Integer feedbackRating = feedback.getRating();
                            return feedbackRating != null && feedbackRating.equals(rating);
                        })
                        .collect(Collectors.toList());
            }
            

            if(filteredFeedback.isEmpty()) {
                throw new NoTrainingFoundException("No feedback found with the given parameters");
            }

        return new ResponseEntity<>(filteredFeedback, HttpStatus.OK);
    }


    @PostMapping(path = "/feedback", consumes = "application/json")
    public ResponseEntity<Feedback> createFeedback(
       @RequestParam(value = "FK", required= true) Long FK,
       @RequestBody Feedback requestfeedback) {
           
               Training currentTraining = trainingRepository.findById(FK).orElse(null);
               if(currentTraining == null){
                   throw new NoTrainingFoundException("No training found with the given id");
               }
               System.out.println("review: " + requestfeedback.getReview());
               System.out.println("rating: " + requestfeedback.getRating());
               //requestfeedback.setFK(currentTraining);

               //feedback newFeedBack = new feedback();
               //newFeedBack.setReview(requestfeedback.getReview());
               //newFeedBack.setRating(requestfeedback.getRating());
               //newFeedBack.setFK(currentTraining);
               Feedback savedFeedback = feedbackRepository.save(new Feedback(requestfeedback.getReview(), requestfeedback.getRating(), currentTraining));
               if(savedFeedback != null) {
                   return new ResponseEntity<>(savedFeedback, HttpStatus.CREATED);
               } else {
                   return ResponseEntity.badRequest().build();//Custom exception error;
               }
       }

    @DeleteMapping("/feedback")
    public ResponseEntity<?> deleteFeedback(@RequestParam(value = "id", required = true) Long id) {
        try{
        if(id != null) {
            Optional<Feedback> feedbackDeleted = feedbackRepository.findById(id);

            if(feedbackDeleted.isPresent()) {
                feedbackRepository.deleteById(id);
                return new ResponseEntity<>(feedbackDeleted,HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No feedback found with the given id",HttpStatus.NOT_FOUND);
            }
        } else {
            throw new Exception("Please enter a valid ID");
        }
        } catch (Exception e) {
            throw new NoTrainingFoundException("No feedback found with the given parameters");
        }  
    }
    
}
