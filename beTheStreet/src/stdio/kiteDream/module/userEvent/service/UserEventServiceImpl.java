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
public class UserEventServiceImpl implements UserEventService,
		ApplicationListener {

	@Autowired
	UserDao userDao;
	@Autowired
	UserEventRecordDao userEventRecordDao;
	@Autowired
	UserService userService;

	public static Map<Integer, Map<String, Object>> events = new ConcurrentHashMap<Integer, Map<String, Object>>();

	@Override
	public UserEvent checkEvent(int userId) {
		String key = "new_message_num";
		UserEvent userEvetn = new UserEvent();
		Map<String, Object> record = events.get(userId);
		if (record != null) {
			Integer new_message_num = (Integer) record.get(key);
			if (new_message_num != null) {
				userEvetn.setNew_message_num(new_message_num);
				record.put("new_message_num", 0);
			}
		}else{
			addUserId(userId);
		}
		return userEvetn;
	}

	@Override
	public boolean updateAllUserEvent(String key, Object value) {
		key = "new_message_num";
		for (Map.Entry<Integer, Map<String, Object>> event : events.entrySet()) {
			try {
				Object v = event.getValue().get(key);
				if (v != null) {
					event.getValue().put(key, (Integer) v + (Integer) value);
				} else {
					event.getValue().put(key, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public boolean updateUserEvent(int userId, String key, Object value) {
		key = "new_message_num";
		Map<String, Object> record = events.get(userId);
		try {
			Object v = record.get(key);
			if (v != null) {
				record.put(key, (Integer) v + (Integer) value);
			} else {
				record.put(key, value);
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
		if (event instanceof ContextRefreshedEvent) {
			UserEventRecord record = userService.getUserEventRecord();
			if (record != null) {
				try {
					ObjectInputStream ois = new ObjectInputStream(
							new ByteArrayInputStream(record.getMem()));
					events = (Map<Integer, Map<String, Object>>) ois
							.readObject();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			List<User> users = userService.getUsers();
			for (User user : users) {
				if (events.get(user.getId()) == null) {
					Map<String, Object> newRecord = new HashMap<String, Object>();
					events.put(user.getId(), newRecord);
				}
			}
			System.out.println("init userEventService.events complet");
		}
		if (event instanceof ContextClosedEvent) {
			userService.saveUserEventRecord();
		}
	}

	@Override
	public Map getEvents() {
		return events;
	}

}
