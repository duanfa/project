package stdio.kiteDream.module.feedback.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import stdio.kiteDream.module.feedback.bean.Feedback;

@Component
public class FeedbackDaoImpl implements FeedbackDao {
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
	public List<Feedback> getFeedbacks(int pageNo, int pageSize) {
		List<Feedback> list = new ArrayList<Feedback>();
		try {
			if (pageNo > 0 && pageSize > 0) {
				int firstResult = (pageNo - 1) * pageSize;
				int maxResult = pageNo * pageSize;
				list = getSessionFactory().getCurrentSession().createCriteria(Feedback.class).setFirstResult(firstResult).setMaxResults(maxResult).list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean delFeedback(String id) {
		try {
			Feedback feedback = (Feedback) getSessionFactory().getCurrentSession().get(Feedback.class, Integer.parseInt(id.trim()));
			getSessionFactory().getCurrentSession().delete(feedback);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean saveFeedback(Feedback feedback) {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(feedback);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}



}
