package com.intern.wcc.dao;

import com.intern.wcc.entity.Postcodelatlng;
import com.intern.wcc.model.helper.PostcodelatlngSearchModel;

import java.util.List;

public interface PostcodelatlngDao {
    List<Postcodelatlng> findPostcodelatlng(PostcodelatlngSearchModel searchModel);

    Integer getTotalCount();

    Postcodelatlng findByPostcode(String postcode);

    Postcodelatlng findById(Integer id);

    void addPostcode(Postcodelatlng postcodelatlng);

    Postcodelatlng updatePostcode(Postcodelatlng postcodelatlng);
}
