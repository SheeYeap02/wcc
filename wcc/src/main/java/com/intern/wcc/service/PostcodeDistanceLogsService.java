package com.intern.wcc.service;

import com.intern.wcc.entity.PostcodeDistanceLogs;
import com.intern.wcc.model.helper.PostcodeDistanceLogsSearchModel;

import java.util.List;

public interface PostcodeDistanceLogsService {
    void addPostcodeDistanceLog(PostcodeDistanceLogs postcodeDistanceLogs);

    List<PostcodeDistanceLogs> findPostcodeDistanceLogsByUserId(PostcodeDistanceLogsSearchModel searchModel);

    Integer getTotalCount(PostcodeDistanceLogsSearchModel searchModel);
}
