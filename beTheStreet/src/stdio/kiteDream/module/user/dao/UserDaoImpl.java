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

import stdio.kiteDream.module.message.bean.Message;
import stdio.kiteDream.module.message.bean.MessageType;
import stdio.kiteDream.module.user.bean.User;
import stdio.kiteDream.module.vo.PageVO;

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
	public Integer getUserCount(int groupid) {
		Integer count;
		String sql = "select count(1) from user";
		if(groupid>0){
			sql = sql +" where groupid="+groupid;
		}
		try {
			BigInteger countRaw = (BigInteger) getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult();
			count = countRaw.intValue();
		} catch (Exception e) {
			Integer countRaw = (Integer) getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult();
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

	@Override
	public PageVO displayUserMessag(int userid, int page, int size) {
		PageVO pagevo = new PageVO();
		List<Message> list = null;
		try {
			String hql = "from Message message";
			if(userid>0){
				hql = hql+" where message.user.id="+userid + " order by message.create_time desc";
			}else{
				hql = hql+" order by message.create_time desc";
			}
			Query query = getSessionFactory().getCurrentSession().createQuery(hql);
			query.setFirstResult((page - 1) * size);
			query.setMaxResults(size);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		pagevo.setResult(list);
		Integer count;
		String sql = "select count(1) from message";
		if(userid>0){
			sql = sql +" where userid="+userid;
		}
		try {
			BigInteger countRaw = (BigInteger) getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult();
			count = countRaw.intValue();
		} catch (Exception e) {
			Integer countRaw = (Integer) getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult();
			count = countRaw.intValue();
		}
		pagevo.setCount(count);
		return pagevo;
	}

}
