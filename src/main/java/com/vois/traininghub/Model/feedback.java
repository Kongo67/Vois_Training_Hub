package com.vois.traininghub.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Feedback")
public class feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "feedback_Id")
    public long feedback_Id;

    @Column(name = "review")
    public String review;

    @Column(name = "rating")
    public int rating;

    @Column(name = "Training_ID")
    public long Training_ID;

    
}
