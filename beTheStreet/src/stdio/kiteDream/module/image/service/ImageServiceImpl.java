package stdio.kiteDream.module.image.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.coins.service.CoinsRuleService;
import stdio.kiteDream.module.image.bean.Image;
import stdio.kiteDream.module.image.dao.ImageDao;
import stdio.kiteDream.module.message.bean.Message;
import stdio.kiteDream.module.message.bean.MessageType;
import stdio.kiteDream.module.message.service.MessageService;
import stdio.kiteDream.module.user.bean.User;
import stdio.kiteDream.module.user.dao.UserDao;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageDao imageDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	CoinsRuleService coinsRuleService;

	@Autowired
	MessageService messageService;

	@Override
	public List<Image> getUserImages(int userId, int page, int size) {
		return imageDao.getImageByUserid(userId, page, size);
	}

	@Override
	public Image getImages(String id) {
		return imageDao.getImage(id);
	}

	@Override
	public boolean saveImage(Image image, int userid) {
		User user = userDao.getUser(userid + "");
		List<Image> images = user.getImages();
		if (images == null) {
			images = new ArrayList<Image>();
			images.add(image);
		} else {
			images.add(image);
		}
		image.setUser(user);
		user.setImages(images);
		userDao.saveUser(user);
		return imageDao.saveImage(image);
	}

	@Override
	public boolean deleteImage(String imageId) {
		Image image = imageDao.getImage(imageId);
		if (imageDao.delImage(imageId)) {
			try {
				String dir = this.getClass().getClassLoader().getResource("/").getPath() + "../../";
				File img = new File(dir + image.getPath());
				if (img.exists()) {
					img.delete();
					System.out.println("file:" + dir + image.getPath() + "   deleted!!!");
				}
				img = new File(dir + image.getThumbnail_path());
				if (img.exists()) {
					img.delete();
				}
				Message message = new Message();
				message.setDescription("image " + image.getId() + " been deleted");
				message.setTitle("new image delete");
				message.setType(MessageType.NOTICE);
				messageService.saveMessage(message, image.getUser().getId()+"");
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
			for (String id : imageId.split(",")) {
				if (StringUtils.isNotBlank(id.trim())) {
					Image image = imageDao.getImage(id);
					image.setStatu(statu);
					image.setUpdate_time(new Date());
					if (imageDao.saveImage(image)) {
						if (Image.Check.PASS.toString().equals(statu)) {
							coinsRuleService.managePrize(image.getLevel(), image.getUser().getId() + "");
							Message message = new Message();
							message.setDescription("new image " + image.getId() + " passed and coins is added ");
							message.setTitle("new image pass");
							message.setType(MessageType.CHA_CHING);
							messageService.saveMessage(message, image.getUser().getId()+"");
						} else if (Image.Check.FAIL.toString().equals(statu)) {
							Message message = new Message();
							message.setDescription("new image " + image.getId() + " been deny ");
							message.setTitle("new image pass");
							message.setType(MessageType.NOTICE);
							messageService.saveMessage(message, image.getUser().getId()+"");
						}
						return true;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Integer getUserImageCount(int userId) {
		return imageDao.getUserImageCount(userId);
	}

	@Override
	public List<Image> getImages(int page, int size) {
		return imageDao.getImages(page, size);
	}

	@Override
	public Integer getImageCount() {
		return imageDao.getImageCount();
	}

	public static void main(String[] args) {
		String ids = ",1,2,3,4,,";
		for (String id : ids.split(",")) {
			if (StringUtils.isNotBlank(id.trim())) {
				System.out.println(id.trim() + "aaa");
			}
		}
		ids = "123";
		for (String id : ids.split(",")) {
			if (StringUtils.isNotBlank(id.trim())) {
				System.out.println(id.trim() + "aaa");
			}
		}
	}
}
