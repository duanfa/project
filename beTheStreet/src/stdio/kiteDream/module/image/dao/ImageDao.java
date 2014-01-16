package stdio.kiteDream.module.image.dao;

import java.util.List;

import stdio.kiteDream.module.image.bean.Image;

public interface ImageDao {

	public List<Image> getImageByUserid(int userId);

	public Image getImage(String id);
	
	public boolean saveImage(Image image);

	public boolean delImage(String imageId);

}
