package stdio.kiteDream.module.image.service;

import java.io.File;
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
		return imageDao.getImage(id);
	}

	@Override
	public boolean saveImage(Image image) {
		return imageDao.saveImage(image);
	}

	@Override
	public boolean deleteImage(String imageId) {
		Image image = imageDao.getImage(imageId);
		if(imageDao.delImage(imageId)){
			String dir = this.getClass().getClassLoader().getResource("../../").getPath();
			File img = new File(dir+image.getPath());
			if(img.exists()){
				img.delete();
			}
			img = new File(dir+image.getThumbnail_path());
			if(img.exists()){
				img.delete();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean updateImageStatu(String imageId, String statu) {
		Image image = imageDao.getImage(imageId);
		image.setStatu(statu);
		return imageDao.saveImage(image);
	}

}
