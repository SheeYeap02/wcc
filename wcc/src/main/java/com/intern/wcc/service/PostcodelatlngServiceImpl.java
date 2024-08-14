package com.intern.wcc.service;

import com.intern.wcc.dao.CustomerDao;
import com.intern.wcc.dao.PostcodeDistanceLogsDao;
import com.intern.wcc.dao.PostcodelatlngDao;
import com.intern.wcc.entity.PostcodeDistanceLogs;
import com.intern.wcc.entity.Postcodelatlng;
import com.intern.wcc.exception.PostcodeNotFoundException;
import com.intern.wcc.model.PostcodeDistanceModel;
import com.intern.wcc.model.helper.PostcodelatlngHelper;
import com.intern.wcc.model.helper.PostcodelatlngSearchModel;
import com.intern.wcc.utils.BeanCompare;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PostcodelatlngServiceImpl implements PostcodelatlngService {

    private final PostcodelatlngDao postcodelatlngDao;
    private final PostcodeDistanceLogsDao postcodeDistanceLogsDao;
    private final CustomerDao customerDao;

    @Autowired
    public PostcodelatlngServiceImpl(PostcodelatlngDao postcodelatlngDao,
                                     PostcodeDistanceLogsDao postcodeDistanceLogsDao,
                                     CustomerDao customerDao
    ) {
        this.postcodelatlngDao = postcodelatlngDao;
        this.postcodeDistanceLogsDao = postcodeDistanceLogsDao;
        this.customerDao = customerDao;
    }

    @Override
    public List<Postcodelatlng> findPostcodelatlngs(PostcodelatlngSearchModel searchModel) {
        List<Postcodelatlng> postcodelatlngs = postcodelatlngDao.findPostcodelatlng(searchModel);
        return postcodelatlngDao.findPostcodelatlng(searchModel);
    }

    @Override
    public Integer getTotalCount() {
        return postcodelatlngDao.getTotalCount();
    }

    @Override
    public Postcodelatlng findByPostcode(String postcode) {
        return postcodelatlngDao.findByPostcode(postcode);
    }

    @Override
    public PostcodeDistanceModel calculateDistance(Integer firstLocation, Integer secondLocation, String userId) {
        Postcodelatlng location1 = postcodelatlngDao.findById(firstLocation);
        if (location1 == null) {
            throw new PostcodeNotFoundException("Location Id not found: " + firstLocation);
        }

        Postcodelatlng location2 = postcodelatlngDao.findById(secondLocation);
        if (location2 == null) {
            throw new PostcodeNotFoundException("Location Id not found: " + firstLocation);
        }

        PostcodelatlngHelper postcodelatlngHelper = new PostcodelatlngHelper();
        double distance = postcodelatlngHelper.calculateDistance(
                location1.getLatitude(), location1.getLongitude(),
                location2.getLatitude(), location2.getLongitude()
        );

        PostcodeDistanceModel postcodeDistanceModel = new PostcodeDistanceModel(location1.getPostcode(), location1.getLatitude().doubleValue(), location1.getLongitude().doubleValue(),
                location2.getPostcode(), location2.getLatitude().doubleValue(), location2.getLongitude().doubleValue(),
                distance + " km");

        PostcodeDistanceLogs postcodeDistanceLogs = new PostcodeDistanceLogs();
        postcodeDistanceLogs.setFirstLocationId(firstLocation);
        postcodeDistanceLogs.setSecondLocationId(secondLocation);
        postcodeDistanceLogs.setUserId(userId);
        postcodeDistanceLogs.setDistance(distance + " km");
        postcodeDistanceLogs.setUpdateDate(LocalDateTime.now());
        postcodeDistanceLogsDao.addPostcodeDistanceLog(postcodeDistanceLogs);

        return postcodeDistanceModel;
    }

    @Override
    public void addPostcode(Postcodelatlng postcodelatlng) {
        postcodelatlngDao.addPostcode(postcodelatlng);
    }

    @Override
    public Postcodelatlng updatePostcode(Postcodelatlng postcodelatlng) {
        Postcodelatlng existingPostcode = postcodelatlngDao.findById(postcodelatlng.getId());
        if (existingPostcode == null) {
            throw new PostcodeNotFoundException("Postcode not found: " + postcodelatlng.getPostcode());
        }

        if (BeanCompare.hasDifferences(postcodelatlng, existingPostcode)) {
            BeanUtils.copyProperties(postcodelatlng, existingPostcode);
            return postcodelatlngDao.updatePostcode(existingPostcode);
        } else {
            return existingPostcode;
        }
    }

}
