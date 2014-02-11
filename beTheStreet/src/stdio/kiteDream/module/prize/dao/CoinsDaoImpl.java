package stdio.kiteDream.module.prize.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import stdio.kiteDream.module.prize.bean.Coins;
import stdio.kiteDream.module.prize.bean.PrizeRule;

@Component
public class CoinsDaoImpl implements CoinsDao {
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
	public Coins getUserCoins(String userid) {
		List<Coins> list = getSessionFactory().getCurrentSession().createQuery("select user.coins from User user where user.id="+userid).list();
		 if(list.size()>0){
			 return list.get(0);
		 }
		 return null;
	}

	@Override
	public boolean saveCoins(Coins coins) {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(coins);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delCoins(String id) {
		try {
			Coins coins = (Coins) getSessionFactory().getCurrentSession().get(Coins.class, Integer.parseInt(id.trim()));
			getSessionFactory().getCurrentSession().delete(coins);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Coins getCoins(String id) {
		return (Coins) getSessionFactory().getCurrentSession().get(Coins.class, Integer.parseInt(id.trim()));
	}

}
