package nsfw.user.dao.impl;

import java.io.Serializable;
import java.util.List;

import nsfw.user.dao.UserDao;
import nsfw.user.entity.User;
import nsfw.user.entity.UserRole;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;

import core.dao.impl.BaseDaoImpl;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	public List<User> findUserByAccountAndId(String id, String account) {
		String hql = "FROM User WHERE account = ?";
		if(StringUtils.isNotBlank(id)){
			hql += " AND id!=?";
		}
		Query query = getSession().createQuery(hql);
		query.setParameter(0, account);
		if(StringUtils.isNotBlank(id)){
			query.setParameter(1, id);
		}
		
		return query.list();
	}

	public void saveUserRole(UserRole userRole) {
		getHibernateTemplate().save(userRole);
	}

	@Override
	public void deleteUserRoleByUserId(Serializable id) {
		Query query = getSession().createQuery("DELETE FROM UserRole WHERE id.userId=?");
		query.setParameter(0, id);
		query.executeUpdate();
	}

	public List<UserRole> getUserRolesByUserId(String id) {
		Query query = getSession().createQuery("FROM UserRole WHERE id.userId=?");
		query.setParameter(0, id);
		return query.list();
	}

	public List<User> findUsersByAcccountAndPass(String account, String password) {
		Query query = getSession().createQuery("FROM User WHERE account=? AND password=?");
		query.setParameter(0, account);
		query.setParameter(1, password);
		return query.list();
	}

	public List<User> findObjectsByAccountAndId(String account, String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
