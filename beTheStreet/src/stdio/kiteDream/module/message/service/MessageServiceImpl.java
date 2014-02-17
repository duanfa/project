package stdio.kiteDream.module.message.service;

import java.util.List;

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
	public List<Message> manageGetUserMessage(String userid) {
		List<Message> messages = messageDao.getUserMessage(userid);
		userEventService.clearEvent(Integer.parseInt(userid));
		return messages;
	}

	@Override
	public boolean saveMessage(Message message,int userid) {
		message.setUser(userDao.getUser(userid+""));
		if(MessageType.BROADCAST.equals(message.getType())){
			userEventService.updateAllUserEvent("new_message_num", 1);
		}else{
			userEventService.updateUserEvent(userid, "new_message_num", 1);
		}
		return messageDao.saveMessage(message);
	}

}
