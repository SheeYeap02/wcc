package com.intern.wcc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "postcode_distance_logs")
@NoArgsConstructor
@AllArgsConstructor
public class PostcodeDistanceLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "first_location_id", nullable = false)
    private Integer firstLocationId;

    @Column(name = "second_location_id", nullable = false)
    private Integer secondLocationId;

    @Column(name = "distance", nullable = false)
    private String distance;

    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "first_location_id", insertable = false, updatable = false)
    private Postcodelatlng firstLocation;

    @ManyToOne
    @JoinColumn(name = "second_location_id", insertable = false, updatable = false)
    private Postcodelatlng secondLocation;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Customer customer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getFirstLocationId() {
        return firstLocationId;
    }

    public void setFirstLocationId(Integer firstLocationId) {
        this.firstLocationId = firstLocationId;
    }

    public Integer getSecondLocationId() {
        return secondLocationId;
    }

    public void setSecondLocationId(Integer secondLocationId) {
        this.secondLocationId = secondLocationId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public Postcodelatlng getFirstLocation() {
        return firstLocation;
    }

    public void setFirstLocation(Postcodelatlng firstLocation) {
        this.firstLocation = firstLocation;
    }

    public Postcodelatlng getSecondLocation() {
        return secondLocation;
    }

    public void setSecondLocation(Postcodelatlng secondLocation) {
        this.secondLocation = secondLocation;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
