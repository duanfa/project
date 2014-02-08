package stdio.kiteDream.module.product.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import stdio.kiteDream.module.product.bean.Product;

@Component
public class ProductDaoImpl implements ProductDao {
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
	public List<Product> getProducts() {
		@SuppressWarnings("unchecked")
		List<Product> list = getSessionFactory().getCurrentSession().createCriteria(Product.class).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
		if (list == null) {
			return new ArrayList<Product>();
		}
		return list;
	}

	@Override
	public Product getProduct(String id) {
		return (Product) getSessionFactory().getCurrentSession().get(Product.class, Integer.parseInt(id.trim()));

	}

	@Override
	public boolean saveProduct(Product product) {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(product);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delProduct(String productId) {
		try {
			Product product = (Product) getSessionFactory().getCurrentSession().get(Product.class, Integer.parseInt(productId.trim()));
			getSessionFactory().getCurrentSession().delete(product);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
