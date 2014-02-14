package stdio.kiteDream.module.user.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import stdio.kiteDream.module.user.bean.Group;
import stdio.kiteDream.module.user.bean.User;

@Component
public class GroupDaoImpl implements GroupDao {
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
	public Integer getCount() {
		Integer count;
		try {
			BigInteger countRaw = (BigInteger) getSessionFactory().getCurrentSession().createSQLQuery("select count(1) from Group").uniqueResult();
			count = countRaw.intValue();
		} catch (Exception e) {
			Integer countRaw = (Integer) getSessionFactory().getCurrentSession().createSQLQuery("select count(1) from Group").uniqueResult();
			count = countRaw.intValue();
		}
		return count;
	}

	@Override
	public Integer getGroupUserCount(int groupid) {
		Integer count;
		try {
			BigInteger countRaw = (BigInteger) getSessionFactory().getCurrentSession().createSQLQuery("select count(1) from user where groupid="+groupid).uniqueResult();
			count = countRaw.intValue();
		} catch (Exception e) {
			Integer countRaw = (Integer) getSessionFactory().getCurrentSession().createSQLQuery("select count(1) from user where groupid="+groupid).uniqueResult();
			count = countRaw.intValue();
		}
		return count;
	}

	@Override
	public List<User> getGroupUsers(int pageNo, int pageSize, int groupid) {
		List<User> list = null;
		try {
			if (pageNo > 0 && pageSize > 0) {
				Query query = getSessionFactory().getCurrentSession().createQuery("from User user where user.grop.id="+groupid);
				query.setFirstResult((pageNo - 1) * pageSize);
				query.setMaxResults(pageSize);
				list = query.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list==null){
			list = new ArrayList<User>();
		}
		return list;
	}

	@Override
	public List<Group> getGroups(int pageNo, int pageSize) {
		List<Group> list = null;
		try {
			if (pageNo > 0 && pageSize > 0) {
				Query query = getSessionFactory().getCurrentSession().createQuery("from Group");
				query.setFirstResult((pageNo - 1) * pageSize);
				query.setMaxResults(pageSize);
				list = query.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list==null){
			list = new ArrayList<Group>();
		}
		return list;
	}

	@Override
	public Group getGroup(int id) {
		return (Group) getSessionFactory().getCurrentSession().get(Group.class, id);
	}

	@Override
	public boolean saveGroup(Group group) {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(group);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public boolean delGroup(String id) {
		try {
			Group group = (Group) getSessionFactory().getCurrentSession().get(Group.class, id);
			getSessionFactory().getCurrentSession().delete(group);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Group> manageSearch(String keyword) {
		keyword = "%" + keyword + "%";
		@SuppressWarnings("unchecked")
		List<Group> list = getSessionFactory().getCurrentSession().createQuery("from Group group where group.name like '"+keyword+"' or group.info like '"+keyword+"'").list();
		if (list == null) {
			return new ArrayList<Group>();
		}
		return list;
	}

}
