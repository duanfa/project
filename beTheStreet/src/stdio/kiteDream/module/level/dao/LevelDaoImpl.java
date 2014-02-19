package stdio.kiteDream.module.level.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import stdio.kiteDream.module.level.bean.Level;

@Component
public class LevelDaoImpl implements LevelDao {
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
	public Level getLevel(String id) {
		return (Level) getSessionFactory().getCurrentSession().get(Level.class, Integer.parseInt(id.trim()));
	}

	@Override
	public Level getLevel(int level) {
		List<Level> rules = getSessionFactory().getCurrentSession().createQuery("from CoinsRule rule where rule.level="+level).list();
		if(rules.size()>0){
			return rules.get(0);
		}
		return null;
	}

	@Override
	public boolean saveLevel(Level rule) {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(rule);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteLevel(String id) {
		try {
			Level rule = (Level) getSessionFactory().getCurrentSession().get(Level.class, Integer.parseInt(id.trim()));
			getSessionFactory().getCurrentSession().delete(rule);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Level> getLevel() {
		 List<Level> list = getSessionFactory().getCurrentSession().createQuery("from CoinsRule").list();
		 if(list==null){
			 return new ArrayList<Level>();
		 }
		 return list;
		
	}

}
