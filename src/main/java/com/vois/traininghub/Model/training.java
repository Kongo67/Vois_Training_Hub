package com.vois.traininghub.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Trainings")
public class training{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Training_ID")
    public long id;

    @Column(name = "name")
    public String name;

    @Column(name = "link")
    public String link;

    @Column(name = "duration")
    public long duration;

    @Column(name = "topic")
    public String topic;

    @Column(name = "entity")
    public String entity;

    @Column(name = "AVG_Rating")
    public long AVG_Rating;


}