package stdio.kiteDream.module.product.dao;

import java.util.List;

import stdio.kiteDream.module.product.bean.ProductCategory;

public interface ProductCategoryDao {

	public List<ProductCategory> getProductCategorys();
	
	public ProductCategory getProductCategory(String id);

	public boolean saveProductCategory(ProductCategory productCategory);

	public boolean delProductCategory(String categoryId);

}
