package stdio.kiteDream.module.prize.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import stdio.kiteDream.module.prize.bean.Order;
import stdio.kiteDream.module.prize.bean.Prize;
import stdio.kiteDream.module.user.bean.User;

@Component
public class OrderDaoImpl implements OrderDao {
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
	public List<Order> getOrders(int pageNo, int pageSize) {
		List<Order> list = null;
		try {
			if (pageNo > 0 && pageSize > 0) {
				Query query = getSessionFactory().getCurrentSession().createQuery("from Order");
				query.setFirstResult((pageNo - 1) * pageSize);
				query.setMaxResults(pageSize);
				list = query.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
			list = new ArrayList<Order>();
		}
		return list;
	}

	@Override
	public List<Order> getUserOrders(int pageNo, int pageSize, int userid) {
		List<Order> list = null;
		try {
			String hql = "from Order order";
			if(userid>0){
				hql = hql + " where order.user.id="+userid;
			}
				Query query = getSessionFactory().getCurrentSession().createQuery(hql);
				if (pageNo > 0 && pageSize > 0) {
					query.setFirstResult((pageNo - 1) * pageSize);
					query.setMaxResults(pageSize);
				}
				list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			list = new ArrayList<Order>();
		}
		return list;
	}

	@Override
	public Order getOrder(String id) {
		return null;
	}

	@Override
	public boolean saveOrder(Order order) {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(order);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delOrder(String id) {
		try{
			Order order = (Order) getSessionFactory().getCurrentSession().get(Order.class, Integer.parseInt(id.trim()));
			getSessionFactory().getCurrentSession().delete(order);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Order getUserOrder(int userid, int prizeid) {
		List<Order> list = null;
		try {
				Query query = getSessionFactory().getCurrentSession().createQuery("from Order order where order.user.id="+userid+" and order.prize.id="+prizeid);
				list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public int getOrderCount(int userid) {
		Integer count;
		String sql = "select count(1) from prize_order";
		if(userid>0){
			sql = sql + " where userid="+userid;
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

}
