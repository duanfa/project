package stdio.kiteDream.module.comic.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import stdio.kiteDream.module.comic.bean.Comic;

@Component
public class ComicDaoImpl implements ComicDao {
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
	public List<Comic> getComics(int level) {
		@SuppressWarnings("unchecked")
		List<Comic> list = getSessionFactory().getCurrentSession()
				.createCriteria(Comic.class)
				.add(Restrictions.eq("level", level))
				.addOrder(Order.asc("orderNum")).list();
		if(list==null){
			return new ArrayList<Comic>();
		}
		return list;
	}

	@Override
	public Comic getComic(String id) {
		return (Comic) getSessionFactory().getCurrentSession().get(Comic.class,
				Integer.parseInt(id.trim()));
	}

	@Override
	public boolean saveComic(Comic comic) {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(comic);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delComic(String comicId) {
		try {
			Comic comic = (Comic) getSessionFactory().getCurrentSession()
					.get(Comic.class, Integer.parseInt(comicId.trim()));
			getSessionFactory().getCurrentSession().delete(comic);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Comic> getComics() {
		@SuppressWarnings("unchecked")
		List<Comic> list = getSessionFactory().getCurrentSession()
				.createCriteria(Comic.class)
				.addOrder(Order.asc("level"))
				.addOrder(Order.asc("orderNum")).list();
		if(list==null){
			return new ArrayList<Comic>();
		}
		return list;
	}

}
