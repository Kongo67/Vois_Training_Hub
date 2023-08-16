package com.vois.traininghub.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vois.traininghub.Model.training;;

public interface TrainingRepository extends JpaRepository<training, Long> {

    
    List<training> findByName(String name);

    // List<training> FindBylink(String link);

    // List<training> findbyduration(long duration);

    // List<training> findbytopic(String topic);

    // List<training> findbyentity(String entity);

    // List<training> findbyrate(long AVG_Rating);
}
