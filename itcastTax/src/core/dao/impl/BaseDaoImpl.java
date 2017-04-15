package core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import core.dao.BaseDao;
import core.page.PageResult;
import core.util.QueryHelper;

public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	Class<T> clazz;
	public BaseDaoImpl(){
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();//获得当前运行类的参数化类型（如BaseDaoImp<user>）
		clazz = (Class<T>)pt.getActualTypeArguments()[0];//获得参数化类型实际的值
	}
	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}
	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(findObjectById(id));
	}
	@Override
	public T findObjectById(Serializable id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(clazz, id);
	}
	@Override
	public List<T> findObjects() {
		// TODO Auto-generated method stub
		Query query = getSession().createQuery("from "+clazz.getSimpleName());
		return query.list();
	}
	
	public List<T> findObjects(String hql, List<Object> parameters) {
		Query query = getSession().createQuery(hql);
		if(parameters != null){
			for(int i = 0; i < parameters.size(); i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
	
	@Override
	public List<T> findObjects(QueryHelper queryHelper) {
		//获取查询语句
		Query query = getSession().createQuery(queryHelper.getQueryListHql());
		//获取参数集合
		List<Object> parameters = queryHelper.getParameters();
		//设置参数
		if(parameters != null){
			for(int i = 0; i < parameters.size(); i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
	
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize){
		Query query = getSession().createQuery(queryHelper.getQueryListHql());
		List<Object> parameters = queryHelper.getParameters();
		if(parameters != null){
			for(int i=0;i<parameters.size();i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		//相当于初始化当前页
	    if(pageNo < 1) pageNo = 1;
	    //设置分页参数
	    query.setFirstResult((pageNo-1)*pageSize);
	    query.setMaxResults(pageSize);
	    //查询
	    List items = query.list();
		
	    //查询总记录数
	    Query query1 = getSession().createQuery(queryHelper.getQueryCountHql());
		if(parameters != null){
			for(int i=0;i<parameters.size();i++){
				query1.setParameter(i, parameters.get(i));
			}
		}
		long totalCount = (Long) query1.uniqueResult();
		return new PageResult(totalCount, pageNo, pageSize, items);
	}
}
