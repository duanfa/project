package stdio.kiteDream.module.user.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import stdio.kiteDream.module.user.bean.User;

@Component
public class UserDaoImpl implements UserDao {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<User> getUsers() {
		@SuppressWarnings("unchecked")
		List<User> list = getSessionFactory().getCurrentSession().createCriteria(User.class).list();
		if (list == null) {
			return new ArrayList<User>();
		}
		return list;
	}

	@Override
	public User getUser(String id) {
		return (User) getSessionFactory().getCurrentSession().get(User.class, Integer.parseInt(id.trim()));
	}

	@Override
	public List<User> getUserByParam(String param, String value) {
		try {
			@SuppressWarnings("unchecked")
			List<User> list = getSessionFactory().getCurrentSession().createCriteria(User.class).add(Restrictions.eq(param.trim(), value.trim())).list();
			if (list == null) {
				return new ArrayList<User>();
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<User>();
		}
	}

	@Override
	public boolean saveUser(User user) {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(user);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public boolean delUser(String userId) {
		try {
			User user = (User) getSessionFactory().getCurrentSession().get(User.class, Integer.parseInt(userId.trim()));
			getSessionFactory().getCurrentSession().delete(user);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<User> getUsers(int pageNo, int pageSize) {
		List<User> list = new ArrayList<User>();
		try {
			if (pageNo > 0 && pageSize > 0) {
				Query query = getSessionFactory().getCurrentSession().createQuery("from User");
				query.setFirstResult((pageNo - 1) * pageSize);
				query.setMaxResults(pageSize);
				list = query.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Integer getUserCount() {
		Integer count;
		try {
			BigInteger countRaw = (BigInteger) getSessionFactory().getCurrentSession().createSQLQuery("select count(1) from user").uniqueResult();
			count = countRaw.intValue();
		} catch (Exception e) {
			Integer countRaw = (Integer) getSessionFactory().getCurrentSession().createSQLQuery("select count(1) from user").uniqueResult();
			count = countRaw.intValue();
		}
		return count;
	}

	@Override
	public List<User> manageSearch(String keyword) {
		keyword = "%" + keyword + "%";
		@SuppressWarnings("unchecked")
		List<User> list = getSessionFactory().getCurrentSession().createQuery("from User user where user.name like '"+keyword+"' or user.nickname like '"+keyword+"'").list();
		if (list == null) {
			return new ArrayList<User>();
		}
		return list;
	}

}
