package com.intern.wcc.model.helper;

import java.math.BigDecimal;

public class PostcodelatlngHelper {
    private final static double EARTH_RADIUS = 6371; // radius in kilometers

    public double calculateDistance(BigDecimal latitude, BigDecimal longitude, BigDecimal latitude2, BigDecimal longitude2) {
        // Using Haversine formula! See Wikipedia;
        double lon1Radians = Math.toRadians(longitude.doubleValue());
        double lon2Radians = Math.toRadians(longitude2.doubleValue());
        double lat1Radians = Math.toRadians(latitude.doubleValue());
        double lat2Radians = Math.toRadians(latitude2.doubleValue());

        double a = haversine(lat1Radians, lat2Radians) + Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (EARTH_RADIUS * c);
    }
    private double haversine(double deg1, double deg2) {
        return square(Math.sin((deg1 - deg2) / 2.0));
    }

    private double square(double x) {
        return x * x;
    }

}
