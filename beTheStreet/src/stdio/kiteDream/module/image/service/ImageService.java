package stdio.kiteDream.module.image.service;

import java.util.List;

import stdio.kiteDream.module.image.bean.Image;

public interface ImageService {

	public List<Image> getUserImages(int userId,int page,int size);
	
	public Integer getUserImageCount(int userId);
	
	public List<Image> getImages(int page,int size);
	
	public Integer getImageCount();

	public Image getImages(String id);

	public boolean saveImage(Image image,int userid);

	public boolean deleteImage(String imageId);

	public boolean updateImageStatu(String imageId, String statu);

}
