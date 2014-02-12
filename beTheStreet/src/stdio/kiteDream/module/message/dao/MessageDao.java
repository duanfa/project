package stdio.kiteDream.module.message.dao;

import java.util.List;

import stdio.kiteDream.module.message.bean.Message;

public interface MessageDao {

	public Message getMessage(String id);
	
	public List<Message> getUserMessage(String userid);

	public boolean saveMessage(Message message);

	public boolean delMessage(String id);

}
