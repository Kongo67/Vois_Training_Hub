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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vois.traininghub.Exceptions.NoTrainingFoundException;
import com.vois.traininghub.Model.training;
import com.vois.traininghub.Repository.TrainingRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")

public class TrainingController {
    @Autowired
    TrainingRepository trainingRepository;

    @GetMapping("/training")
    public ResponseEntity<?> getTraining(
            @RequestParam(value = "id", required = false) long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "entity", required = false) String entity,
            @RequestParam(value = "topic", required = false) String topic) {

        List<training> filteredTrainings = new ArrayList<>();

        if (id != 0) {
            Optional<training> trainingData = trainingRepository.findById(id);

            if (trainingData.isPresent()) {
                filteredTrainings.add(trainingData.get());
            }
        } else {
            filteredTrainings = trainingRepository.findAll();
        }

        if (name != null) {
            filteredTrainings = filteredTrainings.stream()
                    .filter(training -> training.getName().equals(name))
                    .collect(Collectors.toList());
        }

        if (entity != null) {
            filteredTrainings = filteredTrainings.stream()
                    .filter(training -> training.getEntity().equals(entity))
                    .collect(Collectors.toList());
        }

        if (topic != null) {
            filteredTrainings = filteredTrainings.stream()
                    .filter(training -> training.getTopic().equals(topic))
                    .collect(Collectors.toList());
        }

        if (filteredTrainings.isEmpty()) {
            throw new NoTrainingFoundException("No trainings were found with the given parameters. Please check your entries and try again.");
            //return new ResponseEntity<>("No trainings found.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(filteredTrainings, HttpStatus.OK);
    }

    // }

    @PostMapping("/training")
    public ResponseEntity<training> createTraining(@RequestBody training training) {
        try {
            training _training = trainingRepository
                    .save(new training(training.name, training.link, training.duration, training.topic, training.entity,
                            training.AVG_Rating));
            return new ResponseEntity<>(_training, HttpStatus.CREATED);
        } catch (Exception e) {
            throw e;

        }
    }

    @DeleteMapping("/training")
    public ResponseEntity deleteTraining(@RequestParam(value = "id", required = true) long id) {
        try {
            if (id != 0) {
                Optional<training> deleteOne = trainingRepository.findById(id);
                
                if (deleteOne.isPresent()) {
                    trainingRepository.deleteById(id);
                    
                    return new ResponseEntity<>(deleteOne, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Training with ID " + id + " not found.", HttpStatus.NOT_FOUND);
                }
        }else{
            
            throw new Exception("Please enter a valid ID");

            }
        } catch (Exception e) {
            throw new NoTrainingFoundException(e.getMessage(), e);
            //return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @GetMapping("/training/name")
    // public ResponseEntity<List<training>> getTrainingByName(@RequestParam("name")
    // String name) {
    // try {
    // List<training> trainings = trainingRepository.findByName(name);

    // if (trainings.isEmpty()) {
    // return new ResponseEntity("No training under this name are found. \nPlease
    // try again.",
    // HttpStatus.NOT_FOUND);
    // }
    // return new ResponseEntity<>(trainings, HttpStatus.OK);
    // } catch (Exception e) {
    // // return new ResponseEntity(e.getMessage(),
    // HttpStatus.INTERNAL_SERVER_ERROR);
    // throw e;
    // }
    // }

    // @GetMapping("/training/topic")
    // public ResponseEntity<List<training>>
    // getTrainingByTopic(@RequestParam("topic") String topic) {
    // try {
    // List<training> trainings = trainingRepository.findByTopic(topic);

    // if (trainings.isEmpty()) {
    // return new ResponseEntity("No training under this topic are found.",
    // HttpStatus.NOT_FOUND);
    // }
    // return new ResponseEntity<>(trainings, HttpStatus.OK);
    // } catch (Exception e) {
    // return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }

    // @GetMapping("/training/entity")
    // public ResponseEntity<List<training>>
    // getTrainingByEntity(@RequestParam("entity") String entity) {
    // try {
    // List<training> trainings = trainingRepository.findByEntity(entity);

    // if (trainings.isEmpty()) {
    // return new ResponseEntity("No training under this entity are found.",
    // HttpStatus.NOT_FOUND);
    // }
    // return new ResponseEntity<>(trainings, HttpStatus.OK);
    // } catch (Exception e) {
    // return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }

    @PatchMapping("/training")
    public ResponseEntity<training> updateTraining(@RequestParam("id") long id, @RequestBody training training) {
        Optional<training> trainingData = trainingRepository.findById(id);

        if (trainingData.isPresent()) {
            training _training = trainingData.get();
            if (training.name != null) {
                _training.name = training.name;
            }
            if (training.link != null) {
                _training.link = training.link;
            }
            if (training.duration != 0) {
                _training.duration = training.duration;
            }
            if (training.topic != null) {
                _training.topic = training.topic;
            }
            if (training.entity != null) {
                _training.entity = training.entity;
            }
            if (training.AVG_Rating != 0) {
                _training.AVG_Rating = training.AVG_Rating;
            }
            return new ResponseEntity<>(trainingRepository.save(_training), HttpStatus.OK);
        } else {
            throw new NoTrainingFoundException("No trainings were found with the given parameters. Please check your entries and try again.");
        }
        //hi0000
    }

}