package com.vois.traininghub.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
@Table(name = "Feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_Id")
    private long feedback_Id;

    @Column(name = "review")
    @JsonProperty("review")
    private String review;

    @Column(name = "rating")
    @JsonProperty("rating")
    private int rating;

    @ManyToOne
    private Training FK;
    
    public Feedback() {
    }

    public Feedback(String review, int rating, Training FK) {
        this.review = review;
        this.rating = rating;
        this.FK = FK;
    }

    public long getFeedback_Id() {
        return feedback_Id;
    }

    public void setFeedback_Id(long feedback_Id) {
        this.feedback_Id = feedback_Id;
        System.out.println("feedback_Id set to " + this.feedback_Id);

    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Training getFK() {
        return FK;
    }

    public void setFK(Training fK) {
        FK = fK;
    }
    
}
