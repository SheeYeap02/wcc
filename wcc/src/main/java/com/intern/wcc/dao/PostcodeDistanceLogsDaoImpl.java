package com.intern.wcc.dao;

import com.intern.wcc.entity.PostcodeDistanceLogs;
import com.intern.wcc.model.helper.PostcodeDistanceLogsSearchModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostcodeDistanceLogsDaoImpl implements PostcodeDistanceLogsDao {

    private final EntityManager em;

    @Override
    public void addPostcodeDistanceLog(PostcodeDistanceLogs postcodeDistanceLogs) {
        em.persist(postcodeDistanceLogs);
    }

    @Override
    public List<PostcodeDistanceLogs> findPostcodeDistanceLogsByUserId(PostcodeDistanceLogsSearchModel searchModel) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PostcodeDistanceLogs> criteriaQuery = cb.createQuery(PostcodeDistanceLogs.class);

        Root<PostcodeDistanceLogs> root = criteriaQuery.from(PostcodeDistanceLogs.class);
        root.fetch("firstLocation", JoinType.LEFT);
        root.fetch("secondLocation", JoinType.LEFT);
        root.fetch("customer", JoinType.LEFT);

        criteriaQuery.select(root).where(cb.equal(root.get("customer").get("userId"), searchModel.getUserId()));

        if (!ObjectUtils.isEmpty(searchModel.getOrder()) && !ObjectUtils.isEmpty(searchModel.getOrderFlow())) {
            criteriaQuery.orderBy(
                    searchModel.getOrderFlow().equals("ASC") ?
                            cb.asc(root.get(searchModel.getOrder()))
                            : cb.desc(root.get(searchModel.getOrder()))
            );
        } else {
            criteriaQuery.orderBy(cb.desc(root.get("updateDate")));
        }

        TypedQuery<PostcodeDistanceLogs> query = em.createQuery(criteriaQuery);

        if (!ObjectUtils.isEmpty(searchModel.getOffset())) {
            query.setFirstResult(searchModel.getOffset());
        }

        if (!ObjectUtils.isEmpty(searchModel.getMaxResult())) {
            query.setMaxResults(searchModel.getMaxResult());
        }

        return query.getResultList();
    }

    @Override
    public Integer getTotalCount(PostcodeDistanceLogsSearchModel searchModel) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<PostcodeDistanceLogs> root = query.from(PostcodeDistanceLogs.class);
        query.select(cb.count(root)).where(cb.equal(root.get("customer").get("userId"), searchModel.getUserId()));
        return em.createQuery(query).getSingleResult().intValue();
    }
}
