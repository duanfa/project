package stdio.kiteDream.module.message.dao;

import java.util.List;

import stdio.kiteDream.module.message.bean.Message;

public interface MessageDao {

	public Message getMessage(int id);
	
	public boolean saveMessage(Message message);

	public boolean delMessage(int id);
	
	public List<Message> getUserMessage(int unRead,int userid,int page,int size);
	
	public int getUserMessageCount(int userid);
	

}
