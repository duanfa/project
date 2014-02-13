package stdio.kiteDream.module.userEvent.service;

import java.util.Map;

import stdio.kiteDream.module.userEvent.bean.UserEvent;

public interface UserEventService {

	public UserEvent checkEvent(int userid);
	
	public boolean clearEvent(int userId);
	
	public boolean updateAllUserEvent(String key,Object value);
	
	public boolean updateUserEvent(int userId,String key,Object value);
	
	public boolean addUserId(int userId);
	
	public Map getEvents();
	
}
