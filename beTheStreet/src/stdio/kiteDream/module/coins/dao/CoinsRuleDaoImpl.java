package stdio.kiteDream.module.coins.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import stdio.kiteDream.module.coins.bean.CoinsRule;

@Component
public class CoinsRuleDaoImpl implements CoinsRuleDao {
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
	public CoinsRule getPrizeRule(String id) {
		return (CoinsRule) getSessionFactory().getCurrentSession().get(CoinsRule.class, Integer.parseInt(id.trim()));
	}

	@Override
	public CoinsRule getLevelRule(int level) {
		List<CoinsRule> rules = getSessionFactory().getCurrentSession().createQuery("from PrizeRule rule where rule.level="+level).list();
		if(rules.size()>0){
			return rules.get(0);
		}
		return null;
	}

	@Override
	public boolean savePrizeRule(CoinsRule rule) {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(rule);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delPrizeRule(String id) {
		try {
			CoinsRule rule = (CoinsRule) getSessionFactory().getCurrentSession().get(CoinsRule.class, Integer.parseInt(id.trim()));
			getSessionFactory().getCurrentSession().delete(rule);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<CoinsRule> getLevelRules() {
		 List<CoinsRule> list = getSessionFactory().getCurrentSession().createQuery("from PrizeRule").list();
		 if(list==null){
			 return new ArrayList<CoinsRule>();
		 }
		 return list;
		
	}

}
