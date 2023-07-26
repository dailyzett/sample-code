package QRCODE.Capstone.repository;


import QRCODE.Capstone.domain.Member;
import QRCODE.Capstone.domain.OldPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SearchRepository {
    private final EntityManager em;

    public List<OldPlace> findAllByCriteria(MemberSearch memberSearch){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<OldPlace> cq = cb.createQuery(OldPlace.class);

        Root<OldPlace> o = cq.from(OldPlace.class);
        List<Predicate> criteria = new ArrayList<>();

        if (StringUtils.hasText(memberSearch.getMemberName())) {
            Predicate name = cb.like(o.<String>get("name"), "%" + memberSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<OldPlace> oldPlaceTypedQuery = em.createQuery(cq).setMaxResults(1000);
        return oldPlaceTypedQuery.getResultList();
    }
}
