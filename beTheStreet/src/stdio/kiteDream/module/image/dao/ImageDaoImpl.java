package stdio.kiteDream.module.image.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import stdio.kiteDream.module.image.bean.Image;

@Component
public class ImageDaoImpl implements ImageDao {
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
	public List<Image> getImages(int userId) {
		@SuppressWarnings("unchecked")
		List<Image> list = getSessionFactory().getCurrentSession().createCriteria(Image.class).createCriteria("user", "user").add(Restrictions.eq("user.id", userId)).addOrder(Order.asc("create")).list();
		if (list == null) {
			return new ArrayList<Image>();
		}
		return list;
	}

	@Override
	public Image getImages(String id) {
		return (Image) getSessionFactory().getCurrentSession().get(Image.class, Integer.parseInt(id.trim()));
	}

	@Override
	public boolean saveImage(Image image) {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(image);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delImage(String imageId) {
		try {
			Image image = (Image) getSessionFactory().getCurrentSession().get(Image.class, Integer.parseInt(imageId.trim()));
			getSessionFactory().getCurrentSession().delete(image);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
