package com.intern.wcc.dao;

import com.intern.wcc.entity.Customer;
import com.intern.wcc.model.helper.SearchModel;
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
public class CustomerDaoImpl implements CustomerDao {

    private final EntityManager em;

    @Override
    public List<Customer> findAllCustomer(SearchModel searchModel) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = cb.createQuery(Customer.class);

        Root<Customer> root = criteriaQuery.from(Customer.class);
        criteriaQuery.orderBy(
                searchModel.getOrderFlow().equals("ASC") ?
                        cb.asc(root.get(searchModel.getOrder()))
                        : cb.desc(root.get(searchModel.getOrder()))
        );

        TypedQuery<Customer> query = em.createQuery(criteriaQuery);

        if (!ObjectUtils.isEmpty(searchModel.getOffset())) {
            query.setFirstResult(searchModel.getOffset());
        }

        if (!ObjectUtils.isEmpty(searchModel.getMaxResult())) {
            query.setMaxResults(searchModel.getMaxResult());
        }

        return query.getResultList();
    }

    @Override
    public Customer findByUserId(String userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = cb.createQuery(Customer.class);

        Root<Customer> root = criteriaQuery.from(Customer.class);
        criteriaQuery.select(root).where(cb.equal(root.get("userId"), userId));

        TypedQuery<Customer> query = em.createQuery(criteriaQuery);

        List<Customer> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public void addCustomer(Customer customer) {
        em.persist(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return em.merge(customer);
    }
}
