package com.vois.traininghub.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vois.traininghub.Model.training;
import com.vois.traininghub.Repository.TrainingRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")

public class TrainingController {
    @Autowired
    TrainingRepository trainingRepository;

    @PostMapping("/training")
    public ResponseEntity<training> createTraining(@RequestBody training training) {
        try {
            training _training = trainingRepository
                    .save(new training(training.name, training.link, training.duration, training.topic, training.entity,
                            training.AVG_Rating));
            return new ResponseEntity<>(_training, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}