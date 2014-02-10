package stdio.kiteDream.module.product.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import stdio.kiteDream.module.product.bean.ProductCategory;

@Component
public class ProductCategoryDaoImpl implements ProductCategoryDao {
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
	public List<ProductCategory> getProductCategorys() {
		@SuppressWarnings("unchecked")
		List<ProductCategory> list = getSessionFactory().getCurrentSession().createCriteria(ProductCategory.class).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
		if (list == null) {
			return new ArrayList<ProductCategory>();
		}
		return list;
	}

	@Override
	public ProductCategory getProductCategory(String id) {
		return (ProductCategory) getSessionFactory().getCurrentSession().get(ProductCategory.class, Integer.parseInt(id.trim()));
	}

	@Override
	public boolean saveProductCategory(ProductCategory productCategory) {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(productCategory);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delProductCategory(String productId) {
		try {
			ProductCategory category = (ProductCategory) getSessionFactory().getCurrentSession().get(ProductCategory.class, Integer.parseInt(productId.trim()));
			getSessionFactory().getCurrentSession().delete(category);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
