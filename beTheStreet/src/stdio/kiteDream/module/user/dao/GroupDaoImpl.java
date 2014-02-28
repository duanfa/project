package stdio.kiteDream.module.user.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import stdio.kiteDream.module.user.bean.Group;
import stdio.kiteDream.module.user.bean.GroupCategory;
import stdio.kiteDream.module.user.bean.GroupOrg;
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
	public Integer getGroupCount(int orgid) {
		Integer count;
		String sql = "select count(1) from user_group";
		if(orgid>0){
			sql = sql+" where orgid="+orgid;
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
		String hql = "from User user ";
		if(groupid>0){
			hql = hql + " where user.group.id="+groupid;
		}
		try {
			if (pageNo > 0 && pageSize > 0) {
				Query query = getSessionFactory().getCurrentSession().createQuery(hql);
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
	public List<Group> getGroups(int orgid,int pageNo, int pageSize) {
		List<Group> list = null;
		String hql = "from Group grou";
		if(orgid>0){
			hql = hql+" where grou.groupOrg.id="+orgid;
		}
		try {
			if (pageNo > 0 && pageSize > 0) {
				Query query = getSessionFactory().getCurrentSession().createQuery(hql);
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
	public boolean delGroup(int id) {
		try {
			Group group = (Group) getSessionFactory().getCurrentSession().get(Group.class, id);
			getSessionFactory().getCurrentSession().delete(group);
		} catch (Exception e) {
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

	@Override
	public List<GroupCategory> getGroupCategorys(int pageNo, int pageSize) {
		List<GroupCategory> list = null;
		try {
			if (pageNo > 0 && pageSize > 0) {
				Query query = getSessionFactory().getCurrentSession().createQuery("from GroupCategory");
				query.setFirstResult((pageNo - 1) * pageSize);
				query.setMaxResults(pageSize);
				list = query.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list==null){
			list = new ArrayList<GroupCategory>();
		}
		return list;
	}

	@Override
	public Integer getGroupCategoryCount() {
		Integer count;
		String sql = "select count(1) from group_category";
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
	public boolean saveGroupCategory(GroupCategory groupCategory) {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(groupCategory);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public boolean delGroupCategory(int id) {
		try {
			GroupCategory groupOrg = (GroupCategory) getSessionFactory().getCurrentSession().get(GroupCategory.class, id);
			getSessionFactory().getCurrentSession().delete(groupOrg);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<GroupOrg> getGroupOrgs(int categoryid, int pageNo, int pageSize) {
		List<GroupOrg> list = null;
		String hql = "from GroupOrg org ";
		if(categoryid>0){
			hql = hql + " where org.category.id="+categoryid;
		}
		try {
			if (pageNo > 0 && pageSize > 0) {
				Query query = getSessionFactory().getCurrentSession().createQuery(hql);
				query.setFirstResult((pageNo - 1) * pageSize);
				query.setMaxResults(pageSize);
				list = query.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list==null){
			list = new ArrayList<GroupOrg>();
		}
		return list;
	}

	@Override
	public Integer getGroupOrgCount(int categoryid) {
		Integer count;
		String sql = "select count(1) from group_org";
		if(categoryid>0){
			sql = sql +" where categoryid="+categoryid;
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
	public boolean saveGroupOrg(GroupOrg groupOrg) {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(groupOrg);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public boolean delGroupOrg(int id) {
		try {
			GroupOrg groupOrg = (GroupOrg) getSessionFactory().getCurrentSession().get(GroupOrg.class,id);
			getSessionFactory().getCurrentSession().delete(groupOrg);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Group getUserGroup(int userid) {
		List<Group> list = null;
		String hql = "select user.group from User user where user.id="+userid;
		try {
				Query query = getSessionFactory().getCurrentSession().createQuery(hql);
				list = query.list();
				if(list.size()>0){
					return list.get(0);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Group> getGroupByCategory(int categoryid) {
		List<Group> list = null;
		String hql = "from Group g where g.category.id="+categoryid +" order by (g.coins.greenNum+g.coins.greenNum+g.coins.greenNum) desc";
		try {
			list =  getSessionFactory().getCurrentSession().createQuery(hql).setMaxResults(10).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list==null){
			list = new ArrayList<Group>();
		}
		return list;
	}

}
