package stdio.kiteDream.module.product.service;

import java.util.List;

import stdio.kiteDream.module.product.bean.Product;
import stdio.kiteDream.module.product.bean.ProductCategory;

public interface ProductService {

	public List<Product> getProducts();
	
	public List<Product> getProductsByCategory(String categoryId);
	
	public List<ProductCategory> getProductCategorys();

	public Product getProduct(String id);
	
	public ProductCategory getProductCategory(String id);

	public boolean saveProduct(Product product);
	
	public boolean saveProductCategory(ProductCategory category);

	public boolean delProduct(String productId);
	
	public boolean delProductCategory(String categoryId);

}
