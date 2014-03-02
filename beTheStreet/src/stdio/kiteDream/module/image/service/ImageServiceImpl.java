package stdio.kiteDream.module.image.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.image.bean.Image;
import stdio.kiteDream.module.image.bean.Image.Check;
import stdio.kiteDream.module.image.bean.Image.Type;
import stdio.kiteDream.module.image.dao.ImageDao;
import stdio.kiteDream.module.level.bean.Level;
import stdio.kiteDream.module.level.service.LevelService;
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
	LevelService levelService;

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
		if(Type.STREET.equals(image.getLevelType())){
			Level level = levelService.getLevelById(image.getLevel());
			if(image.getLevel_stage()>=level.getRegular_stage()){
				user.setHigh_level(level.getLevel()+1);
				user.setHigh_level_stage(1);
			}else{
				user.setHigh_level_stage(image.getLevel_stage()+1);
			}
		}else{
			if(user.getHigh_level()<image.getLevel()){
				user.setHigh_level(image.getLevel());
			}
		}
		user.setImages(images);
		userDao.saveUser(user);
		return imageDao.saveImage(image);
	}

	@Override
	public boolean deleteImage(String bulkImageId) {
		try {
			for (String imageId : bulkImageId.split(",")) {
				if (StringUtils.isNotBlank(imageId.trim())) {
					Image image = imageDao.getImage(imageId);
					if (imageDao.delImage(imageId)) {
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
							messageService.saveMessage(message, image.getUser().getId() + "");
					}
	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Override
	public boolean updateImageStatu(String imageId, Check statu) {
		try {
			for (String id : imageId.split(",")) {
				if (StringUtils.isNotBlank(id.trim())) {
					Image image = imageDao.getImage(id);
					User user = image.getUser();
					image.setStatu(statu);
					image.setUpdate_time(new Date());
					if (imageDao.saveImage(image)) {
						if (Image.Check.PASS.equals(statu)) {
							levelService.managePrize(image.getLevel(), user.getId() + "");
							Message message = new Message();
							message.setDescription("new image " + image.getId() + " passed and coins is added ");
							message.setTitle("new image pass");
							message.setType(MessageType.CHA_CHING);
							messageService.saveMessage(message, user.getId() + "");
							updateBounsStatu(user,image.getLevel(),image.getLevel_stage());
							userDao.saveUser(user);
							levelService.getChallenge(user.getId());
						} else if (Image.Check.FAIL.equals(statu)) {
							Message message = new Message();
							message.setDescription("new image " + image.getId() + " been deny ");
							message.setTitle("new image pass");
							message.setType(MessageType.NOTICE);
							messageService.saveMessage(message, user.getId() + "");
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean updateBounsStatu(User user,int level,int level_stage){
		try {
			Map<Integer,Integer> bonusStatu = user.getBonusStatu();
			Integer levelStatu = bonusStatu.get(level);
			if(levelStatu!=null){
				String s = (int)Math.pow(10,level_stage-1)+"";
			 	int display = Integer.valueOf(s,2);
				levelStatu = levelStatu|display;
				bonusStatu.put(level, levelStatu);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static void main(String[] args) {
	 	System.out.println((2|4));
	 	
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

	
}
