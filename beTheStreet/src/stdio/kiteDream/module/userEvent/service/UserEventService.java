package stdio.kiteDream.module.userEvent.service;

import stdio.kiteDream.module.userEvent.bean.UserEvent;

public interface UserEventService {

	public UserEvent checkEvent(int userId);
	
	public boolean updateAllUserEvent(String key,Object value);
	
	public boolean updateUserEvent(int userId,String key,Object value);
	
	public boolean addUserId(int userId);
	
	public String saveUserEventRecord();
	
}
