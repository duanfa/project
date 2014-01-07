package stdio.kiteDream.module.helloMessage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.helloMessage.bean.HelloMessage;
import stdio.kiteDream.module.helloMessage.dao.HelloMessageDao;

@Service
public class HelloMessageServiceImpl implements HelloMessageService {
	@Autowired
	HelloMessageDao helloMessageDao;
	@Override
	public List<HelloMessage> getMessages() {
		return helloMessageDao.getMessage();
	}

	@Override
	public boolean saveMessage(HelloMessage message) {
		return helloMessageDao.saveMessage(message);
	}

	@Override
	public boolean deleteMessage(String messageId) {
		return helloMessageDao.delMessage(messageId);
	}

	@Override
	public HelloMessage getNowMessage() {
		return helloMessageDao.getNowMessage();
	}

	@Override
	public HelloMessage getMessage(String id) {
		return helloMessageDao.getMessage(id);
	}

}
