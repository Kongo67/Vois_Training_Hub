package com.vois.traininghub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vois.traininghub.Model.feedback;

public interface FeedbackRepository extends JpaRepository<feedback, Long>{
    
}
