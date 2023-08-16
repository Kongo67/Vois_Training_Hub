package com.vois.traininghub.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/trainings")
    public ResponseEntity<List<training>> getAllTrainings() {
        try {
            List<training> trainings = new ArrayList<training>();

            trainingRepository.findAll().forEach(trainings::add);

            if (trainings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(trainings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    @DeleteMapping("/training/{id}")
    public ResponseEntity<HttpStatus> deleteTraining(@PathVariable("id") long id) {
        try {
            trainingRepository.deleteById(id);
            System.out.print("Training deleted");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/training/{id}")
    public ResponseEntity<training> getTrainingById(@PathVariable("id") long id) {
        Optional<training> trainingData = trainingRepository.findById(id);

        if (trainingData.isPresent()) {
            return new ResponseEntity<>(trainingData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}