package com.uisftech.cloan.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;


public class PredicateUtils {
	public static <T>  Specification<T>  buildSpecification(Map<String, Object> params){
		Specification<T> spec=new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<>();
				for(Entry<String, Object> entry:params.entrySet()){
					Object obj=entry.getValue();
					if(obj!=null){
						predicate.add(cb.like(root.get(entry.getKey()).as(String.class),
								"%" + entry.getValue() + "%"));
					}
				}
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		};
		return spec;
	}
}
