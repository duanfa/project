package stdio.kiteDream.module.image.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.image.bean.Image;
import stdio.kiteDream.module.image.dao.ImageDao;

@Service
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private ImageDao imageDao;
	
	@Override
	public List<Image> getImages(int userId) {
		return imageDao.getImages(userId);
	}

	@Override
	public Image getImages(String id) {
		return imageDao.getImages(id);
	}

	@Override
	public boolean saveImage(Image image) {
		return imageDao.saveImage(image);
	}

	@Override
	public boolean delImage(String imageId) {
		return imageDao.delImage(imageId);
	}

}
