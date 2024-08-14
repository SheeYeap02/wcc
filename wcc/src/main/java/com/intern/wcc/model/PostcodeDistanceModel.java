package com.intern.wcc.model;

public class PostcodeDistanceModel {
    private Integer firstLocationId;
    private String postcode1;
    private double latitude1;
    private double longitude1;
    private Integer secondLocationId;
    private String postcode2;
    private double latitude2;
    private double longitude2;
    private String distance;
    private String userId;

    public PostcodeDistanceModel() {
    }

    public PostcodeDistanceModel(String postcode1, double latitude1, double longitude1, String postcode2, double latitude2, double longitude2, String distance) {
        this.postcode1 = postcode1;
        this.latitude1 = latitude1;
        this.longitude1 = longitude1;
        this.postcode2 = postcode2;
        this.latitude2 = latitude2;
        this.longitude2 = longitude2;
        this.distance = distance;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostcode1() {
        return postcode1;
    }

    public void setPostcode1(String postcode1) {
        this.postcode1 = postcode1;
    }

    public double getLatitude1() {
        return latitude1;
    }

    public void setLatitude1(double latitude1) {
        this.latitude1 = latitude1;
    }

    public double getLongitude1() {
        return longitude1;
    }

    public void setLongitude1(double longitude1) {
        this.longitude1 = longitude1;
    }

    public String getPostcode2() {
        return postcode2;
    }

    public void setPostcode2(String postcode2) {
        this.postcode2 = postcode2;
    }

    public double getLatitude2() {
        return latitude2;
    }

    public void setLatitude2(double latitude2) {
        this.latitude2 = latitude2;
    }

    public double getLongitude2() {
        return longitude2;
    }

    public void setLongitude2(double longitude2) {
        this.longitude2 = longitude2;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

}
