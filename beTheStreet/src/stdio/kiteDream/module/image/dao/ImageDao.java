package stdio.kiteDream.module.image.dao;

import java.util.List;

import stdio.kiteDream.module.image.bean.Image;

public interface ImageDao {

	public List<Image> getImageByUserid(int userId,int page,int size);
	
	public Integer getUserImageCount(int userId);
	
	public List<Image> getImages(int page,int size);
	
	public Integer getImageCount();

	public Image getImage(String id);
	
	public boolean saveImage(Image image);

	public boolean delImage(String imageId);

}
