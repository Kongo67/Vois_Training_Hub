package com.vois.traininghub.Model;

import jakarta.persistence.*;

//import java.util.List;

@Entity
@Table(name = "Trainings")
public class training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Training_ID")
    private long id;

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

    // @OneToMany(cascade = CascadeType.ALL)
    // @JoinColumn(name = "FK_Training_ID", referencedColumnName = "Training_ID")
    // private List<feedback> Train_ID;

    public training() {
    }

    public training(String name, String link, long duration, String topic, String entity, long AVG_Rating) {

        this.name = name;
        this.link = link;
        this.duration = duration;
        this.topic = topic;
        this.entity = entity;
        this.AVG_Rating = AVG_Rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
        System.out.println("ID set to: " + this.id);
    }

}