package com.vois.traininghub.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Feedback")
public class feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "feedback_Id")
    private long feedback_Id;

    @Column(name = "review")
    public String review;

    @Column(name = "rating")
    public int rating;
    
    public feedback() {
    }

    public feedback(String review, int rating) {
        this.review = review;
        this.rating = rating;
    }

    public long getFeedback_Id() {
        return feedback_Id;
    }

    public void setFeedback_Id(long feedback_Id) {
        this.feedback_Id = feedback_Id;
        System.out.println("feedback_Id set to " + this.feedback_Id);

    }
    
}
