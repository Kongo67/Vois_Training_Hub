package com.vois.traininghub.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vois.traininghub.Model.training;
import com.vois.traininghub.Repository.TrainingRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")

public class TrainingController {
    @Autowired
    TrainingRepository trainingRepository;

        @GetMapping("/training")
        public ResponseEntity<?> getTraining(@RequestParam(value = "id", required = false, defaultValue = "0") long id) {
            if (id == 0) {
                try {
                    List<training> trainings = trainingRepository.findAll();
                
                    if (trainings.isEmpty()) {
                        return new ResponseEntity<>("No trainings found.", HttpStatus.NOT_FOUND);
                    }
                
                    return new ResponseEntity<>(trainings, HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                Optional<training> trainingData = trainingRepository.findById(id);
            
                if (trainingData.isPresent()) {
                    return new ResponseEntity<>(trainingData.get(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Training not found.", HttpStatus.NOT_FOUND);
                }
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
    
    @GetMapping("/training/name")
    public ResponseEntity<List<training>> getTrainingByName(@RequestParam("name") String name) {
        try {
            List<training> trainings = trainingRepository.findByName(name);

            if (trainings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(trainings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/training/topic")
    public ResponseEntity<List<training>> getTrainingByTopic(@RequestParam("topic") String topic) {
        try {
            List<training> trainings = trainingRepository.findByTopic(topic);

            if (trainings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(trainings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/training/entity")
    public ResponseEntity<List<training>> getTrainingByEntity(@RequestParam("entity") String entity) {
        try {
            List<training> trainings = trainingRepository.findByEntity(entity);

            if (trainings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(trainings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/training")
    public ResponseEntity<training> updateTraining(@RequestParam("id") long id, @RequestBody training training) {
        Optional<training> trainingData = trainingRepository.findById(id);

        if (trainingData.isPresent()) {
            training _training = trainingData.get();
            if(training.name != null){
                _training.name = training.name;
            }
            if(training.link != null){
                _training.link = training.link;
            }
            if(training.duration != 0){
                _training.duration = training.duration;
            }
            if(training.topic != null){
                _training.topic = training.topic;
            }
            if(training.entity != null){
                _training.entity = training.entity;
            }
            if(training.AVG_Rating != 0){
                _training.AVG_Rating = training.AVG_Rating;
            }
            return new ResponseEntity<>(trainingRepository.save(_training), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //hi0000
    }
    
}