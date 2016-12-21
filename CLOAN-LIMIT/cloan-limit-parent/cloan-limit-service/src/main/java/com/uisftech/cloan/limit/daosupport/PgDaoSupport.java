package com.uisftech.cloan.limit.daosupport;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PgDaoSupport {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public <T> T persist(T entity) {
		em.persist(entity);
		return entity;
	}

	@Transactional
	public <T> T merge(T entity) {
		return em.merge(entity);
	}

	@Transactional
	public <T> T delete(T entity) {
		em.remove(entity);
		return entity;
	}

	@Transactional
	public <T> T findById(Class<T> entityClass, Object entityid) {
		return em.find(entityClass, entityid);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public <T> List<T> queryForList(String sql, Map<String, Object> map, Class clazz) {
		Query dataQuery = em.createNativeQuery(sql,clazz);
		setQueryParams(dataQuery, map);
//		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<T> data = dataQuery.getResultList();
		return data;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Map<String, Object>> queryForMapList(String sql, Map<String, Object> queryMap) {
		Query dataQuery = em.createNativeQuery(sql);
		setQueryParams(dataQuery, queryMap);
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> data = dataQuery.getResultList();
		return data;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public  List queryForDTO(String sql, Map<String, Object> map, Class clazz) throws Exception {
		List<Object> list=new ArrayList<Object>();
		Query dataQuery = em.createNativeQuery(sql);
		setQueryParams(dataQuery, map);
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> data = dataQuery.getResultList();
		if(data!=null&&data.size()!=0){
			for(Map<String,Object> dataMap:data){
				Object obj=clazz.newInstance();
				Method[] methods =clazz.getDeclaredMethods();
				for(Method method:methods){
					String methodName=method.getName();
					if(methodName.startsWith("set")){
						Class[] cla=method.getParameterTypes();
							 for(String key:dataMap.keySet()){
									if(key.replace("_", "").equalsIgnoreCase(methodName.substring(3))){
										if(dataMap.get(key)!=null){
											for(Class cl:cla){
												if(cl==String.class){
													method.invoke(obj, dataMap.get(key).toString());
												}
												else if(cl==Integer.class){
													method.invoke(obj, Integer.parseInt(dataMap.get(key).toString()));
												}else if(cl==Long.class){
													method.invoke(obj, Long.parseLong((dataMap.get(key).toString())));
												}else if(cl==BigDecimal.class){
													method.invoke(obj, BigDecimal.valueOf(Double.parseDouble(dataMap.get(key).toString())));
												}else if(cl==Date.class){
													method.invoke(obj, (Date)(dataMap.get(key)));
												}
											}
										}
									}
							 }

					}
				}
				list.add(obj);
			}

		}
		return  list;
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public Map<String, Object> queryForMap(String sql, Map<String, Object> queryMap) {
		Query dataQuery = em.createNativeQuery(sql);
		setQueryParams(dataQuery, queryMap);
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> data = dataQuery.getResultList();
		if(data==null||data.size()==0){
			return null;
		}
		return data.get(0);
	}
	@Transactional
	public Integer updateSql(String sql, Map<String, Object> queryMap) {
		Query dataQuery = em.createNativeQuery(sql);
		setQueryParams(dataQuery, queryMap);
		return dataQuery.executeUpdate();
	}

	@Transactional
	public <T> Object getSingleColumn(String sql, Map<String, Object> queryMap) {
		Query dataQuery = em.createNativeQuery(sql);
		setQueryParams(dataQuery, queryMap);
		return dataQuery.getSingleResult();
	}

	private void setQueryParams(Query query, Map<String, Object> queryMap) {
		if (null != queryMap&&0<=queryMap.keySet().size()) {
			Set<String> querySet = queryMap.keySet();
			Iterator<String> iterMap = querySet.iterator();
			while (iterMap.hasNext()) {
				String key = (String) iterMap.next();
				query.setParameter(key, queryMap.get(key));
			}
		}
	}
}
