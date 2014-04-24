package stdio.kiteDream.module.image.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.coins.bean.Coins;
import stdio.kiteDream.module.coins.dao.CoinsDao;
import stdio.kiteDream.module.image.bean.Image;
import stdio.kiteDream.module.image.bean.Image.Check;
import stdio.kiteDream.module.image.bean.Image.Type;
import stdio.kiteDream.module.image.dao.ImageDao;
import stdio.kiteDream.module.level.bean.Level;
import stdio.kiteDream.module.level.dao.LevelDao;
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
	LevelDao levelDao;
	
	@Autowired
	CoinsDao coinsDao;

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
	public List<Coins> saveImage(Image image, int userid) {
		List<Coins> coinsList =  new ArrayList<Coins>();
		Level level = levelService.getLevel(image.getLevel());
		if(!Type.STREET.equals(image.getLevelType())){
			int[] realCoins = level.getRandomCoin();
			Coins coins = new Coins();
			coins.setGreenNum(realCoins[0]);
			coins.setYellowNum(realCoins[1]);
			coins.setRedNum(realCoins[2]);
			coinsDao.saveCoins(coins);
			image.setCoins(coins);
			coinsList.add(coins);
		}
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
			if(image.getLevel_stage()>=level.getRegular_stage()){//升级下一个level
				user.setHigh_level(level.getLevel()+1);
				user.setHigh_level_stage(1);
				Level level_next = levelService.getLevel((image.getLevel()+1));
				if(level_next!=null){
					user.setHigh_level_all(level_next.getRegular_stage());
				}
				//完成数加1
				level.setCompleteNum(level.getCompleteNum()+1);
				levelService.saveLevel(level);
			}else{//本level
				user.setHigh_level_stage(image.getLevel_stage()+1);
				user.setHigh_level_all(level.getRegular_stage());
			}
		}else if(Type.CHALLENGE.equals(image.getLevelType())){
			level.setCompleteNum(level.getCompleteNum()+1);
			levelService.saveLevel(level);
		}
		user.setImages(images);
		userDao.saveUser(user);
		imageDao.saveImage(image);
		return coinsList;
	}

	@Override
	public boolean updateImageStatu(String imageId, Check statu) {
		try {
			for (String id : imageId.split(",")) {
				if (StringUtils.isNotBlank(id.trim())) {
					Image image = imageDao.getImage(id);
					Level level = levelService.getLevel(image.getLevel());
					User user = image.getUser();
					image.setStatu(statu);
					image.setUpdate_time(new Date());
					if (imageDao.saveImage(image)) {
						if (Image.Check.PASS.equals(statu)) {
							levelService.managePrize(id, user.getId() + "");
							Message message = new Message();
							message.setDescription("Your image from "+level.getTitle2()+" has been verified. Coins earned have been added to your account. Don't spend them all in one place!");
							message.setCreate_time(new Date());
							message.setTitle("Image Verified");
							message.setType(MessageType.CHA_CHING);
							if(Image.Type.CHALLENGE.equals(image.getLevelType())){
								message.setDescription("Your image from "+level.getTitle()+" has been verified. Coins earned have been added to your account. Don't spend them all in one place!");
								Level nextChanllenge = levelDao.getLevel(image.getLevel()+1);
								Message messageChallenge = new Message();
								messageChallenge.setCreate_time(new Date());
								if(nextChanllenge!=null){
									messageChallenge.setDescription(nextChanllenge.getTitle()+":"+nextChanllenge.getShortdesc()+"   "+nextChanllenge.getDesc());
									messageChallenge.setTitle(nextChanllenge.getTitle()+" Unlocked!");
									messageChallenge.setType(MessageType.CHALLENGE);
									messageChallenge.setLevel(nextChanllenge.getLevel());
								}else{
									messageChallenge.setDescription("No more challenges, please wait, we will inform you;");
									messageChallenge.setTitle("the End");
									messageChallenge.setType(MessageType.CHALLENGE);
								}
								messageService.saveMessage(messageChallenge, user.getId() + "");
							}
							messageService.saveMessage(message, user.getId() + "");
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
