package stdio.kiteDream.module.helloMessage.dao;

import java.util.List;

import stdio.kiteDream.module.helloMessage.bean.HelloMessage;

public interface HelloMessageDao {

	public List<HelloMessage> getMessage();

	public HelloMessage getNowMessage();

	public boolean saveMessage(HelloMessage message);

	public boolean delMessage(String msgId);

}
