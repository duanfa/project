package stdio.kiteDream.module.helloMessage.service;

import java.util.List;

import stdio.kiteDream.module.helloMessage.bean.HelloMessage;

public interface HelloMessageService {

	public List<HelloMessage> getMessages();
	
	public HelloMessage getNowMessage();
	
	public HelloMessage getMessage(String id);

	public boolean saveMessage(HelloMessage message);

	public boolean deleteMessage(String messageId);

}
