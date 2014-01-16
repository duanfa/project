package stdio.kiteDream.module.userEvent.service;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.user.bean.User;
import stdio.kiteDream.module.user.dao.UserDao;
import stdio.kiteDream.module.user.service.UserService;
import stdio.kiteDream.module.userEvent.bean.UserEvent;
import stdio.kiteDream.module.userEvent.bean.UserEventRecord;
import stdio.kiteDream.module.userEvent.dao.UserEventRecordDao;

@Service
public class UserEventServiceImpl implements UserEventService ,ApplicationListener{

	@Autowired
	UserDao userDao;
	@Autowired
	UserEventRecordDao userEventRecordDao;
	@Autowired
	UserService userService;

	public static Map<Integer, Map<String, Object>> events = new ConcurrentHashMap<Integer, Map<String, Object>>();

	@Override
	public UserEvent checkEvent(int userId) {
		UserEvent userEvetn = new UserEvent();
		Map<String, Object> record = events.get(userId);
		if(record!=null){
			//
			Integer new_level_comic = (Integer) record.get("new_level_comic");
			if (new_level_comic != null) {
				userEvetn.setNew_level_comic(new_level_comic);
				record.put("new_level_comic", 0);
			}
			Integer new_pass_image = (Integer) record.get("new_pass_image");
			if (new_pass_image != null) {
				userEvetn.setNew_pass_image_num(new_pass_image);
				record.put("new_pass_image", 0);
			}
			Integer new_deny_image = (Integer) record.get("new_deny_image");
			if (new_level_comic != null) {
				userEvetn.setNew_deny_image_num(new_deny_image);
				record.put("new_deny_image", 0);
			}
		}
		return userEvetn;
	}

	@Override
	public boolean updateAllUserEvent(String key, Object value) {
		for (Map.Entry<Integer, Map<String, Object>> event : events.entrySet()) {
			try {
				Object v = event.getValue().get(key);
				if ("new_level_comic".equals(key)) {
					if (v != null) {
						event.getValue().put(key, (Integer) v | (Integer) value);
					} else {
						event.getValue().put(key, value);
					}
				}else if("new_pass_image".equals(key)){
					if (v != null) {
						event.getValue().put(key, (Integer) v + (Integer) value);
					} else {
						event.getValue().put(key, value);
					}
				}else if("new_deny_image".equals(key)){
					if (v != null) {
						event.getValue().put(key, (Integer) v + (Integer) value);
					} else {
						event.getValue().put(key, value);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public boolean updateUserEvent(int userId, String key, Object value) {
		Map<String, Object> record = events.get(userId);
		try {
			Object v = record.get(key);
			if ("new_level_comic".equals(key)) {
				if (v != null) {
					record.put(key, (Integer) v | (Integer) value);
				} else {
					record.put(key, value);
				}
			}else if("new_pass_image".equals(key)){
				if (v != null) {
					record.put(key, (Integer) v + (Integer) value);
				} else {
					record.put(key, value);
				}
			}else if("new_deny_image".equals(key)){
				if (v != null) {
					record.put(key,(Integer) v + (Integer) value);
				} else {
					record.put(key, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean addUserId(int userId) {
		Map<String, Object> record = new HashMap<String, Object>();
		events.put(userId, record);
		return true;
	}

	

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof ContextRefreshedEvent ){ 
			UserEventRecord record = userService.getUserEventRecord();
			if(record!=null){
				try {
					ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(record.getMem()));
					events = (Map<Integer, Map<String, Object>>) ois.readObject();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (events.size() < 1) {
				List<User> users = userService.getUsers();
				for (User user : users) {
					Map<String, Object> newRecord = new HashMap<String, Object>();
					events.put(user.getId(), newRecord);
				}
				System.out.println("init userEventService.events complet");
			}
		}
		if(event instanceof ContextClosedEvent ){
			userService.saveUserEventRecord();
		}
	}


	@Override
	public Map getEvents() {
		return events;
	}

	

}
