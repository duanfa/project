package stdio.kiteDream.module.prize.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import stdio.kiteDream.module.prize.bean.PrizeRule;

@Component
public class PrizeRuleDaoImpl implements PrizeRuleDao {
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
	public PrizeRule getPrizeRule(String id) {
		return (PrizeRule) getSessionFactory().getCurrentSession().get(PrizeRule.class, Integer.parseInt(id.trim()));
	}

	@Override
	public PrizeRule getLevelRule(int level) {
		List<PrizeRule> rules = getSessionFactory().getCurrentSession().createQuery("from PrizeRule rule where rule.level="+level).list();
		if(rules.size()>0){
			return rules.get(0);
		}
		return null;
	}

	@Override
	public boolean savePrizeRule(PrizeRule rule) {
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
			PrizeRule rule = (PrizeRule) getSessionFactory().getCurrentSession().get(PrizeRule.class, Integer.parseInt(id.trim()));
			getSessionFactory().getCurrentSession().delete(rule);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<PrizeRule> getLevelRules() {
		 List<PrizeRule> list = getSessionFactory().getCurrentSession().createQuery("from PrizeRule").list();
		 if(list==null){
			 return new ArrayList<PrizeRule>();
		 }
		 return list;
		
	}

}
