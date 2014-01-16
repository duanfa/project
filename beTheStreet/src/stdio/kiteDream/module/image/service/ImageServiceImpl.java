package stdio.kiteDream.module.image.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.image.bean.Image;
import stdio.kiteDream.module.image.dao.ImageDao;
import stdio.kiteDream.module.userEvent.service.UserEventService;

@Service
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	UserEventService userEventService;
	
	@Override
	public List<Image> getImages(int userId) {
		return imageDao.getImageByUserid(userId);
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
			try {
				String dir = this.getClass().getClassLoader().getResource("/").getPath()+"../../";
				File img = new File(dir+image.getPath());
				if(img.exists()){
					img.delete();
					System.out.println("file:"+dir +image.getPath()+"   deleted!!!");
				}
				img = new File(dir+image.getThumbnail_path());
				if(img.exists()){
					img.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean updateImageStatu(String imageId, String statu) {
		try {
			Image image = imageDao.getImage(imageId);
			image.setStatu(statu);
			if(imageDao.saveImage(image)){
				if(Image.Check.PASS.toString().equals(statu)){
					userEventService.updateUserEvent(image.getUser().getId(), "new_pass_image", 1);
				}else if(Image.Check.FAIL.toString().equals(statu)){
					userEventService.updateUserEvent(image.getUser().getId(), "new_deny_image", 1);
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
