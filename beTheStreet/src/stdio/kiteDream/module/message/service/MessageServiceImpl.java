package stdio.kiteDream.module.message.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.message.bean.Message;
import stdio.kiteDream.module.message.bean.MessageType;
import stdio.kiteDream.module.message.dao.MessageDao;
import stdio.kiteDream.module.user.dao.UserDao;
import stdio.kiteDream.module.userEvent.bean.UserEvent;
import stdio.kiteDream.module.userEvent.service.UserEventService;
import stdio.kiteDream.module.vo.PageVO;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	MessageDao messageDao;
	@Autowired
	UserDao userDao;
	@Autowired
	UserEventService userEventService;

	@Override
	public List<Message> manageGetUserMessage(int userid,int page,int size) {
		UserEvent event = userEventService.checkEvent(userid);
		int unRead = -1;
		if(event!=null){
			unRead = event.getNew_message_num();
		}
		List<Message> messages = messageDao.getUserMessage(unRead,userid, page, size);
		if(unRead>0){
			if(unRead<=size){
				userEventService.clearEvent(userid);
			}else{
				userEventService.setUserEvent(userid, "new_message_num", unRead-size);
			}
		}
		return messages;
	}
	
	@Override
	public PageVO manageDisplayUserMessage(int userid,int page,int size) {
		return userDao.displayUserMessag( userid, page, size);
	}

	@Override
	public boolean saveMessage(Message message,String bulkuserid) {
		try {
			for(String id :bulkuserid.split(",")){
				if(StringUtils.isNotBlank(id.trim())){
					Message newMessage = new Message();
					newMessage.setTitle(message.getTitle());
					newMessage.setDescription(message.getDescription());
					newMessage.setUser(userDao.getUser(id));
					newMessage.setCreate_time(new Date());
					newMessage.setType(message.getType());
					if(MessageType.BROADCAST.equals(message.getType())){
						userEventService.updateAllUserEvent("new_message_num", 1);
					}else{
						userEventService.updateUserEvent(Integer.parseInt(id), "new_message_num", 1);
					}
					messageDao.saveMessage(newMessage);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public int getUserMessageCount(int userid) {
		return messageDao.getUserMessageCount(userid);
	}

	@Override
	public boolean deleteMessage(int id) {
		return messageDao.delMessage(id);
	}

	@Override
	public boolean manageResendMessage(int id) {
		Message message = messageDao.getMessage(id);
		message.setCreate_time(new Date());
		if(MessageType.BROADCAST.equals(message.getType())){
			userEventService.updateAllUserEvent("new_message_num", 1);
		}else{
			userEventService.updateUserEvent(message.getUser().getId(), "new_message_num", 1);
		}
		messageDao.saveMessage(message);
		
		return true;
	}

}
