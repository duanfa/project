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

import stdio.kiteDream.module.prize.bean.Prize;
import stdio.kiteDream.module.user.bean.User;

@Component
public class PrizeDaoImpl implements PrizeDao {
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
	public Prize getPrize(String id) {
		return (Prize) getSessionFactory().getCurrentSession().get(Prize.class, Integer.parseInt(id.trim()));
	}

	@Override
	public boolean savePrize(Prize prize) {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(prize);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delPrize(String id) {
		try {
			Prize prize = (Prize) getSessionFactory().getCurrentSession().get(Prize.class, Integer.parseInt(id.trim()));
			getSessionFactory().getCurrentSession().delete(prize);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Prize> getPrizes() {
		return getSessionFactory().getCurrentSession().createQuery("from Prize").list();
	}

	@Override
	public List<Prize> getPrizes(int pageNo, int pageSize) {
		List<Prize> list = null;
		try {
			if (pageNo > 0 && pageSize > 0) {
				Query query = getSessionFactory().getCurrentSession().createQuery("from Prize");
				query.setFirstResult((pageNo - 1) * pageSize);
				query.setMaxResults(pageSize);
				list = query.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
			list = new ArrayList<Prize>();
		}
		return list;
	}

	@Override
	public int getCount() {
		Integer count;
		String sql = "select count(1) from prize";
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
