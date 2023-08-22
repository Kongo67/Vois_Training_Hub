package com.vois.traininghub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vois.traininghub.Model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long>{
    
}
