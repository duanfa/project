package stdio.kiteDream.module.message.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import stdio.kiteDream.module.message.bean.Message;
import stdio.kiteDream.module.message.bean.MessageType;

@Component
public class MessageDaoImpl implements MessageDao {
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
	public Message getMessage(String id) {
		return (Message) getSessionFactory().getCurrentSession().get(Message.class, Integer.parseInt(id.trim()));
	}

	@Override
	public List<Message> getUserMessage(String userid) {
		List<Message> list = getSessionFactory().getCurrentSession().createQuery("from Message message where message.type=? or message.user.id=?").setParameter(0, MessageType.BROADCAST).setParameter(1, Integer.parseInt(userid.trim())).list();
		 if(list==null){
			 return new ArrayList<Message>();
		 }
		 return list;
	}

	@Override
	public boolean saveMessage(Message message) {

		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(message);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	
	}

	@Override
	public boolean delMessage(String id) {
		try {
			Message message = (Message) getSessionFactory().getCurrentSession().get(Message.class, Integer.parseInt(id.trim()));
			getSessionFactory().getCurrentSession().delete(message);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
