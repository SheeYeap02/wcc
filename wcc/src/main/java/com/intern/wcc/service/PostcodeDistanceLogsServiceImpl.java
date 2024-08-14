package com.intern.wcc.service;

import com.intern.wcc.dao.PostcodeDistanceLogsDao;
import com.intern.wcc.entity.PostcodeDistanceLogs;
import com.intern.wcc.model.helper.PostcodeDistanceLogsSearchModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PostcodeDistanceLogsServiceImpl implements PostcodeDistanceLogsService {

    private final PostcodeDistanceLogsDao postcodeDistanceLogsDao;

    @Autowired
    public PostcodeDistanceLogsServiceImpl(PostcodeDistanceLogsDao postcodeDistanceLogsDao) {
        this.postcodeDistanceLogsDao = postcodeDistanceLogsDao;
    }

    @Override
    public void addPostcodeDistanceLog(PostcodeDistanceLogs postcodeDistanceLogs) {
        postcodeDistanceLogsDao.addPostcodeDistanceLog(postcodeDistanceLogs);
    }

    @Override
    public List<PostcodeDistanceLogs> findPostcodeDistanceLogsByUserId(PostcodeDistanceLogsSearchModel searchModel) {
        return postcodeDistanceLogsDao.findPostcodeDistanceLogsByUserId(searchModel);
    }

    @Override
    public Integer getTotalCount(PostcodeDistanceLogsSearchModel searchModel) {
        return postcodeDistanceLogsDao.getTotalCount(searchModel);
    }
}
