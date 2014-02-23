package stdio.kiteDream.module.message.service;

import java.util.List;

import stdio.kiteDream.module.message.bean.Message;

public interface MessageService {

	public List<Message> manageGetUserMessage(int userid,int page,int size);
	
	public int getUserMessageCount(int userid);
	
	public boolean deleteMessage(int id);
	
	public boolean manageResendMessage(int id);

	public boolean saveMessage(Message message,String bulkuserid);

}
