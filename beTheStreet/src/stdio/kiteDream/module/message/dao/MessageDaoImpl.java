package stdio.kiteDream.module.message.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
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
	public Message getMessage(int id) {
		return (Message) getSessionFactory().getCurrentSession().get(Message.class, id);
	}

	@Override
	public List<Message> getUserMessage(int unRead,int userid,int page,int size) {
		
		List<Message> list = null;
		try {
			String hql = "from Message message";
			if(userid>0){
				if(unRead<1){
					return new ArrayList<Message>();
				}
				hql = hql+" where message.type=? or message.user.id="+userid + " order by message.create_time desc";
				Query query = getSessionFactory().getCurrentSession().createQuery(hql);
				query.setParameter(0, MessageType.BROADCAST);
				query.setFirstResult((page - 1) * size);
				query.setMaxResults(unRead);
				list = query.list();
				while((unRead--)>size){
					list.remove(0);
				}
			}else{
				hql = hql + " order by create_time desc";
				Query query = getSessionFactory().getCurrentSession().createQuery(hql);
				query.setFirstResult((page - 1) * size);
				query.setMaxResults(size);
				list = query.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public boolean delMessage(int id) {
		try {
			Message message = (Message) getSessionFactory().getCurrentSession().get(Message.class, id);
			getSessionFactory().getCurrentSession().delete(message);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public int getUserMessageCount(int userid) {
		Integer count;
		String sql = "select count(1) from message ";
		if(userid>0){
			sql = sql+" where userid="+userid;
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
