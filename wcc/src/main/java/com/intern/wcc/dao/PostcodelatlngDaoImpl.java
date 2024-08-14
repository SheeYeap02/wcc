package com.intern.wcc.dao;

import com.intern.wcc.entity.Postcodelatlng;
import com.intern.wcc.model.helper.PostcodelatlngSearchModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostcodelatlngDaoImpl implements PostcodelatlngDao {

    private final EntityManager em;

    public List<Postcodelatlng> findPostcodelatlng(PostcodelatlngSearchModel searchModel) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Postcodelatlng> criteriaQuery = cb.createQuery(Postcodelatlng.class);

        Root<Postcodelatlng> root = criteriaQuery.from(Postcodelatlng.class);
        if (!ObjectUtils.isEmpty(searchModel.getOrder()) && !ObjectUtils.isEmpty(searchModel.getOrderFlow())) {
            criteriaQuery.orderBy(
                    searchModel.getOrderFlow().equals("ASC") ?
                            cb.asc(root.get(searchModel.getOrder()))
                            : cb.desc(root.get(searchModel.getOrder()))
            );
        } else {
            criteriaQuery.orderBy(cb.asc(root.get("id")));
        }

        TypedQuery<Postcodelatlng> query = em.createQuery(criteriaQuery);

        if (!ObjectUtils.isEmpty(searchModel.getOffset())) {
            query.setFirstResult(searchModel.getOffset());
        }

        if (!ObjectUtils.isEmpty(searchModel.getMaxResult())) {
            query.setMaxResults(searchModel.getMaxResult());
        }

        return query.getResultList();
    }

    @Override
    public Integer getTotalCount() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Postcodelatlng> root = query.from(Postcodelatlng.class);
        query.select(cb.count(root));
        return em.createQuery(query).getSingleResult().intValue();
    }

    @Override
    public Postcodelatlng findByPostcode(String postcode) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Postcodelatlng> criteriaQuery = cb.createQuery(Postcodelatlng.class);

        Root<Postcodelatlng> root = criteriaQuery.from(Postcodelatlng.class);
        criteriaQuery.select(root).where(cb.equal(root.get("postcode"), postcode));

        TypedQuery<Postcodelatlng> query = em.createQuery(criteriaQuery);

        List<Postcodelatlng> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public Postcodelatlng findById(Integer id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Postcodelatlng> criteriaQuery = cb.createQuery(Postcodelatlng.class);

        Root<Postcodelatlng> root = criteriaQuery.from(Postcodelatlng.class);
        criteriaQuery.select(root).where(cb.equal(root.get("id"), id));

        TypedQuery<Postcodelatlng> query = em.createQuery(criteriaQuery);

        List<Postcodelatlng> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public void addPostcode(Postcodelatlng postcodelatlng) {
        em.persist(postcodelatlng);
    }

    @Override
    public Postcodelatlng updatePostcode(Postcodelatlng postcodelatlng) {
        return em.merge(postcodelatlng);
    }

}
