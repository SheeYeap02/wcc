package com.intern.wcc.controller;

import com.intern.wcc.common.Result;
import com.intern.wcc.entity.PostcodeDistanceLogs;
import com.intern.wcc.entity.Postcodelatlng;
import com.intern.wcc.exception.PostcodeNotFoundException;
import com.intern.wcc.model.PostcodeDistanceModel;
import com.intern.wcc.model.helper.PostcodeDistanceLogsSearchModel;
import com.intern.wcc.model.helper.PostcodelatlngSearchModel;
import com.intern.wcc.service.PostcodeDistanceLogsService;
import com.intern.wcc.service.PostcodelatlngService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("wcc/postcodelatlng")
@Slf4j
public class PostcodelatlngController {

    private final PostcodelatlngService postcodelatlngService;
    private final PostcodeDistanceLogsService postcodeDistanceLogsService;

    @Autowired
    public PostcodelatlngController(PostcodelatlngService postcodelatlngService,
                                    PostcodeDistanceLogsService postcodeDistanceLogsService
    ) {
        this.postcodelatlngService = postcodelatlngService;
        this.postcodeDistanceLogsService = postcodeDistanceLogsService;
    }

    @PostMapping("/find")
    public ResponseEntity<Result<List<Postcodelatlng>>> findPostcodelatlngs(@RequestBody PostcodelatlngSearchModel searchModel) {
        try {
            Result<List<Postcodelatlng>> result = Result.success(postcodelatlngService.findPostcodelatlngs(searchModel));
            result.setTotal(postcodelatlngService.getTotalCount());
            return ResponseEntity.ok(result);
        } catch (PostcodeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/findByPostcode")
    public ResponseEntity<Postcodelatlng> findByPostcode(@RequestParam String postcode) {
        try {
            return ResponseEntity.ok(postcodelatlngService.findByPostcode(postcode));
        } catch (PostcodeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/calculate-distance")
    public ResponseEntity<PostcodeDistanceModel> getDistance(@RequestParam Integer firstLocation, @RequestParam Integer secondLocation, @RequestParam String userId) {
        try {
            PostcodeDistanceModel response = postcodelatlngService.calculateDistance(firstLocation, secondLocation, userId);
            return ResponseEntity.ok(response);
        } catch (PostcodeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addPostcodelatlng(@RequestBody Postcodelatlng postcodelatlng) {
        postcodelatlngService.addPostcode(postcodelatlng);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Postcodelatlng> updatePostcodelatlng(@RequestBody Postcodelatlng postcodelatlng) {
        Postcodelatlng updatePostcodelatlng = postcodelatlngService.updatePostcode(postcodelatlng);
        return new ResponseEntity<>(updatePostcodelatlng, HttpStatus.OK);
    }

    @PostMapping("/find-logs")
    public ResponseEntity<Result<List<PostcodeDistanceLogs>>> findPostcodeDistanceLogs(@RequestBody PostcodeDistanceLogsSearchModel searchModel) {
        try {
            Result<List<PostcodeDistanceLogs>> result = Result.success(postcodeDistanceLogsService.findPostcodeDistanceLogsByUserId(searchModel));
            result.setTotal(postcodeDistanceLogsService.getTotalCount(searchModel));
            return ResponseEntity.ok(result);
        } catch (PostcodeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
