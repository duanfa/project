package stdio.kiteDream.module.user.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import stdio.kiteDream.module.comic.bean.Comic;
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
				int firstResult = (pageNo - 1) * pageSize;
				int maxResult = pageNo * pageSize;
				list = getSessionFactory().getCurrentSession().createCriteria(User.class).setFirstResult(firstResult).setMaxResults(maxResult).list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
