package com.vois.traininghub.Model;

import jakarta.persistence.*;

import java.util.List;

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

     @OneToMany(mappedBy = "FK")
     private List<feedback> feedbacks;

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

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;

    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;

    }

    public String getEntity() {
        return entity;

    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getTopic() {
        return topic;

    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public long getAVGRating() {
        return AVG_Rating;
    }

    public void setAVGRAting(long AVG_Rating) {
        this.AVG_Rating = AVG_Rating;

    }

  
     
    
        @Override
        public String toString() {
            return "{\n id =" + getId() + ",\n name =" + name + ",\n link=" + link + ",\n duration=" + duration + ",\n topic=" + topic + ",\n entity=" + entity + ",\n AVG_Rating=" + AVG_Rating + "\n}";
        }
    

}