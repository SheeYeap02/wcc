package com.intern.wcc.service;

import com.intern.wcc.entity.Postcodelatlng;
import com.intern.wcc.model.PostcodeDistanceModel;
import com.intern.wcc.model.helper.PostcodelatlngSearchModel;

import java.util.List;

public interface PostcodelatlngService {
    List<Postcodelatlng> findPostcodelatlngs(PostcodelatlngSearchModel searchModel);

    Integer getTotalCount();

    Postcodelatlng findByPostcode(String postcode);

    PostcodeDistanceModel calculateDistance(Integer firstLocation, Integer secondLocation, String userId);

    void addPostcode(Postcodelatlng postcodelatlng);

    Postcodelatlng updatePostcode(Postcodelatlng postcodelatlng);


}
