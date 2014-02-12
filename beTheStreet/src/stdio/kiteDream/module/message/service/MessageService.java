package stdio.kiteDream.module.message.service;

import java.util.List;

import stdio.kiteDream.module.message.bean.Message;

public interface MessageService {

	public List<Message> manageGetUserMessage(String userid);

	public boolean saveMessage(Message message,int userid);

}
