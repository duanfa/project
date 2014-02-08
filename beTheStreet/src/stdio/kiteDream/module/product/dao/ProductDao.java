package stdio.kiteDream.module.product.dao;

import java.util.List;

import stdio.kiteDream.module.product.bean.Product;

public interface ProductDao {

	public List<Product> getProducts();
	
	public Product getProduct(String id);

	public boolean saveProduct(Product product);

	public boolean delProduct(String productId);

}
