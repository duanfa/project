package stdio.kiteDream.module.image.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
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
	public List<Image> getImageByUserid(int userId,int page,int size) {
		List<Image> list = null;
		try {
			Query query = getSessionFactory().getCurrentSession().createQuery("from Image image where image.user.id="+userId+" order by image.create_time desc");
			query.setFirstResult((page - 1) * size);
			query.setMaxResults(size);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list == null) {
			return new ArrayList<Image>();
		}
		return list;
	}

	@Override
	public Image getImage(String id) {
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

	@Override
	public Integer getUserImageCount(int userId) {
		Integer count;
		try {
			BigInteger countRaw = (BigInteger) getSessionFactory().getCurrentSession().createSQLQuery("select count(1) from image where userid="+userId).uniqueResult();
			count = countRaw.intValue();
		} catch (Exception e) {
			Integer countRaw = (Integer) getSessionFactory().getCurrentSession().createSQLQuery("select count(1) from image where userid="+userId).uniqueResult();
			count = countRaw.intValue();
		}
		return count;
	}

	@Override
	public List<Image> getImages(int page, int size) {
		List<Image> list = null;
		try {
			Query query = getSessionFactory().getCurrentSession().createQuery("from Image image order by image.create_time desc");
			query.setFirstResult((page - 1) * size);
			query.setMaxResults(size);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list == null) {
			return new ArrayList<Image>();
		}
		return list;
	}

	@Override
	public Integer getImageCount() {
		Integer count;
		try {
			BigInteger countRaw = (BigInteger) getSessionFactory().getCurrentSession().createSQLQuery("select count(1) from image").uniqueResult();
			count = countRaw.intValue();
		} catch (Exception e) {
			Integer countRaw = (Integer) getSessionFactory().getCurrentSession().createSQLQuery("select count(1) from image").uniqueResult();
			count = countRaw.intValue();
		}
		return count;
	}

}
