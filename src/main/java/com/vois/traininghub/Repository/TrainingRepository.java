package com.vois.traininghub.Repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.vois.traininghub.Model.Training;;

public interface TrainingRepository extends JpaRepository<Training, Long> {


    List<Training> findByName(String name);

    List<Training> findByTopic(String topic);

    List<Training> findByEntity(String entity);

}
