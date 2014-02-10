package stdio.kiteDream.module.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.product.bean.Product;
import stdio.kiteDream.module.product.bean.ProductCategory;
import stdio.kiteDream.module.product.dao.ProductCategoryDao;
import stdio.kiteDream.module.product.dao.ProductDao;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Override
	public List<Product> getProducts() {
		return productDao.getProducts();
	}

	@Override
	public Product getProduct(String id) {
		return productDao.getProduct(id);
	}

	@Override
	public boolean saveProduct(Product product) {
		return productDao.saveProduct(product);
	}

	@Override
	public boolean delProduct(String productId) {
		return productDao.delProduct(productId);
	}

	@Override
	public List<ProductCategory> getProductCategorys() {
		return productCategoryDao.getProductCategorys();
	}

	@Override
	public ProductCategory getProductCategory(String id) {
		return productCategoryDao.getProductCategory(id);
	}

	@Override
	public boolean saveProductCategory(ProductCategory category) {
		return productCategoryDao.saveProductCategory(category);
	}

	@Override
	public boolean delProductCategory(String categoryId) {
		return productCategoryDao.delProductCategory(categoryId);
	}

}
