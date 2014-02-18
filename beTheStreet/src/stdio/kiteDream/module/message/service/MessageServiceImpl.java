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
import stdio.kiteDream.module.userEvent.service.UserEventService;

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
		List<Message> messages = messageDao.getUserMessage(userid, page, size);
		userEventService.clearEvent(userid);
		return messages;
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

}
