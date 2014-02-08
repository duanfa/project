package stdio.kiteDream.module.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.product.bean.Product;
import stdio.kiteDream.module.product.dao.ProductDao;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;

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

}
