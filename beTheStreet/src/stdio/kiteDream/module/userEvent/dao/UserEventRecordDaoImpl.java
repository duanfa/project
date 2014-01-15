package stdio.kiteDream.module.userEvent.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import stdio.kiteDream.module.userEvent.bean.UserEventRecord;

@Component
public class UserEventRecordDaoImpl implements UserEventRecordDao {
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
	public UserEventRecord getUserEventRecord() {
		 List<UserEventRecord> records = getSessionFactory().getCurrentSession().createCriteria(UserEventRecord.class).list();
		 if(records.size()>0){
			 return records.get(0);
		 }
		 return null;
	}

	@Override
	public boolean saveUserEventRecord(UserEventRecord record) {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(record);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delUserEventRecord(String id) {
		try {
			UserEventRecord record = (UserEventRecord) getSessionFactory().getCurrentSession().get(UserEventRecord.class, Integer.parseInt(id.trim()));
			getSessionFactory().getCurrentSession().delete(record);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
