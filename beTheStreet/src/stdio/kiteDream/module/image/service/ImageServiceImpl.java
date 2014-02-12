package stdio.kiteDream.module.image.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.image.bean.Image;
import stdio.kiteDream.module.image.dao.ImageDao;
import stdio.kiteDream.module.message.bean.Message;
import stdio.kiteDream.module.message.bean.MessageType;
import stdio.kiteDream.module.message.service.MessageService;
import stdio.kiteDream.module.prize.service.PrizeRuleService;
import stdio.kiteDream.module.user.dao.UserDao;
import stdio.kiteDream.module.userEvent.service.UserEventService;

@Service
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	UserEventService userEventService;
	
	@Autowired
	PrizeRuleService prizeRuleService;
	
	@Autowired
	MessageService messageService;
	
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
				Message message = new Message();
				message.setDescription("image "+image.getId()+" been deleted");
				message.setTitle("new image delete");
				message.setType(MessageType.NOTICE);
				messageService.saveMessage(message, image.getUser().getId());
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
					prizeRuleService.managePrize(image.getLevel(), image.getUser().getId()+"");
					Message message = new Message();
					message.setDescription("new image "+image.getId()+" passed and coins is added ");
					message.setTitle("new image pass");
					message.setType(MessageType.CHA_CHING);
					messageService.saveMessage(message, image.getUser().getId());
				}else if(Image.Check.FAIL.toString().equals(statu)){
					Message message = new Message();
					message.setDescription("new image "+image.getId()+" been deny ");
					message.setTitle("new image pass");
					message.setType(MessageType.NOTICE);
					messageService.saveMessage(message, image.getUser().getId());
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
