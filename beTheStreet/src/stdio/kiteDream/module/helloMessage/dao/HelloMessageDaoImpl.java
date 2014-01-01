package stdio.kiteDream.module.helloMessage.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import stdio.kiteDream.module.helloMessage.bean.HelloMessage;

@Component
public class HelloMessageDaoImpl implements HelloMessageDao {
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
	public List<HelloMessage> getMessage() {
		@SuppressWarnings("unchecked")
		List<HelloMessage> list = getSessionFactory().getCurrentSession()
				.createCriteria(HelloMessage.class).list();
		return list;
	}

	@Override
	public boolean saveMessage(HelloMessage message) {
		try {
			getSessionFactory().getCurrentSession().save(message);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delMessage(String msgId) {
		try {
			HelloMessage message = (HelloMessage) getSessionFactory()
					.getCurrentSession().get(HelloMessage.class,
							Integer.parseInt(msgId.trim()));
			getSessionFactory().getCurrentSession().delete(message);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public HelloMessage getNowMessage() {
		Date now = new Date();
		@SuppressWarnings("unchecked")
		List<HelloMessage> list = getSessionFactory().getCurrentSession()
				.createCriteria(HelloMessage.class).add(Restrictions.le("startTime", now)).add(Restrictions.ge("endTime", now)).list();
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
