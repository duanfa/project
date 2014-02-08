package stdio.kiteDream.module.product.service;

import java.util.List;

import stdio.kiteDream.module.product.bean.Product;

public interface ProductService {

	public List<Product> getProducts();

	public Product getProduct(String id);

	public boolean saveProduct(Product product);

	public boolean delProduct(String productId);

}
