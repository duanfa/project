package stdio.kiteDream.module.userEvent.service;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Date;
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
import stdio.kiteDream.module.userEvent.bean.UserLoginStatu;
import stdio.kiteDream.module.userEvent.dao.UserEventRecordDao;

@Service
public class UserEventServiceImpl implements UserEventService,
		ApplicationListener,Runnable {

	@Autowired
	UserDao userDao;
	@Autowired
	UserEventRecordDao userEventRecordDao;
	@Autowired
	UserService userService;
	
	static Thread loginTimeDemon;

	public static Map<Integer, Map<String, Object>> events = new ConcurrentHashMap<Integer, Map<String, Object>>();
	public static Map<Integer, UserLoginStatu> userLoginStatus = new ConcurrentHashMap<Integer, UserLoginStatu>();

	@Override
	public UserEvent checkEvent(int userId) {
		String key = "new_message_num";
		UserEvent userEvetn = new UserEvent();
		Map<String, Object> record = events.get(userId);
		if (record != null) {
			Integer new_message_num = (Integer) record.get(key);
			if (new_message_num != null) {
				userEvetn.setNew_message_num(new_message_num);
			}
		}else{
			addUserId(userId);
		}
		updateVisitStatu(userId);
		return userEvetn;
	}
	
	private void updateVisitStatu(int userId){
		UserLoginStatu statu = userLoginStatus.get(userId);
		Date now = new Date();
		if(statu==null){
			statu = new UserLoginStatu();
			statu.setUserid(userId);
			statu.setLoginDate(now);
			userLoginStatus.put(userId, statu);
		}
		statu.setVisitDate(new Date());
	}
	
	@Override
	public boolean clearEvent(int userId) {
		try {
			Map<String, Object> record = events.get(userId);
			if (record != null) {
				record.put("new_message_num", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
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
			if(record==null){
				addUserId(userId);
				record = events.get(userId);
			}
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
	public boolean setUserEvent(int userId, String key, Object value) {
		key = "new_message_num";
		Map<String, Object> record = events.get(userId);
		try {
				record.put(key, value);
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
		synchronized (userLoginStatus) {
			if (loginTimeDemon == null) {
				loginTimeDemon = new Thread(this);
				loginTimeDemon.start();
			}
		}
	}

	@Override
	public Map getEvents() {
		return events;
	}

	@Override
	public void run() {
		while(true){
			try {
				Date now = new Date();
				for(UserLoginStatu statu:userLoginStatus.values()){
					if(now.getTime()-statu.getVisitDate().getTime()>900000){
						System.out.println(statu.getUserid()+":"+statu.getLoginDate()+":"+statu.getVisitDate());
						User user = userService.getUser(statu.getUserid()+"");
						if(user==null){
							continue;
						}
						long past = statu.getVisitDate().getTime()-statu.getLoginDate().getTime();
						user.setTotaltime((int) (user.getTotaltime()+past/60000));
						userService.saveUser(user);
						userLoginStatus.remove(statu.getUserid());
					}
				}
				Thread.currentThread().sleep(60000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}
