package stdio.kiteDream.module.image.service;

import java.util.List;

import stdio.kiteDream.module.image.bean.Image;

public interface ImageService {

	public List<Image> getImages(int userId);

	public Image getImages(String id);
	
	public boolean saveImage(Image image);

	public boolean delImage(String imageId);

}
