package stdio.kiteDream.module.user.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.level.dao.LevelDao;
import stdio.kiteDream.module.message.bean.Message;
import stdio.kiteDream.module.message.bean.MessageType;
import stdio.kiteDream.module.message.service.MessageService;
import stdio.kiteDream.module.user.bean.Group;
import stdio.kiteDream.module.user.bean.User;
import stdio.kiteDream.module.user.dao.GroupDao;
import stdio.kiteDream.module.user.dao.UserDao;
import stdio.kiteDream.module.userEvent.bean.UserEventRecord;
import stdio.kiteDream.module.userEvent.dao.UserEventRecordDao;
import stdio.kiteDream.module.userEvent.service.UserEventService;
import stdio.kiteDream.util.Constant;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserEventService userEventService;
	@Autowired
	UserEventRecordDao userEventRecordDao;
	@Autowired
	MessageService messageService;
	@Autowired
	UserDao userDao;
	@Autowired
	GroupDao groupDao;
	@Autowired
	LevelDao levelDao;

	@Override
	public List<User> getUsers() {
		return userDao.getUsers();
	}

	@Override
	public List<User> getUsers(int pageNo, int pageSize, int groupid) {
		return groupDao.getGroupUsers(pageNo, pageSize, groupid);
	}

	@Override
	public User getUser(String id) {
		return userDao.getUser(id);
	}

	@Override
	public List<User> getUserByParam(String param, String value) {
		return userDao.getUserByParam(param, value);
	}

	@Override
	public String saveUser(User user) {
		try {
			user.setCreate_time(new Date());
			user.setHigh_level_all(levelDao.getLevel(1).getRegular_stage());
			boolean isRegister = false;
			if(user.getId()<1){
				isRegister = true;
			}
			if (userDao.saveUser(user)&&isRegister) {
				manageOpenapp(user.getId());
				Message message = new Message();
				message.setCreate_time(new Date());
				message.setDescription("This inbox is where you'll hear from us, be notified about coins earned after your submitted challenge photos are verified, and be invited to take on new challenges. Good luck!");
				message.setTitle("Welcome to Be the Street!");
				message.setType(MessageType.NOTICE);
				messageService.saveMessage(message, user.getId() + "");
//				message = new Message();
//				message.setCreate_time(new Date());
//				message.setDescription("We need your help in creating the best game we can. Please use the \"Write to Us\" feature in Settings to send us comments, bugs, and suggestions - we need you!");
//				message.setTitle("Thanks for Beta Testing");
//				message.setType(MessageType.NOTICE);
//				messageService.saveMessage(message, user.getId() + "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Constant.FAIL;
		}
		return Constant.OK;
	}

	@Override
	public boolean deleteUser(String userId) {
		User user = userDao.getUser(userId);
		user.setActive(false);
		return userDao.saveUser(user);
	}

	@Override
	public User manageLogin(String nickname, String password) {
		List<User> users = userDao.getUserByParam("nickname", nickname);
		if (users.size() > 0) {
			if (users.get(0).isIsadmin()
					&& password.equals(users.get(0).getPassword())) {
				return users.get(0);
			}
		}
		return null;
	}

	@Override
	public boolean manageIsExist(String name) {
		List<User> users = userDao.getUserByParam("nickname", name);
		return users.size() > 0;
	}

	@Override
	public UserEventRecord getUserEventRecord() {
		return userEventRecordDao.getUserEventRecord();
	}

	@Override
	public String saveUserEventRecord() {
		ObjectOutputStream oos = null;
		try {
			UserEventRecord record = userEventRecordDao.getUserEventRecord();
			if (record == null) {
				record = new UserEventRecord();
			}
			record.setCreateTime(new Date());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(userEventService.getEvents());
			record.setMem(baos.toByteArray());
			userEventRecordDao.saveUserEventRecord(record);
		} catch (IOException e) {
			e.printStackTrace();
			return Constant.FAIL;
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Constant.OK;
	}

	@Override
	public int getUserCount(int groupid) {
		return userDao.getUserCount(groupid);
	}

	@Override
	public List<User> manageSearchUser(String keyword) {
		return userDao.manageSearch(keyword);
	}

	@Override
	public Integer getCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getGroupUserCount(int groupid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getGroupUsers(int pageNo, int pageSize, int groupid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> getGroups(int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Group getGroup(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveGroup(Group group) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delGroup(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Group> manageSearchGroup(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean manageChangepwd(String nickname, String password) {
		List<User> users = userDao.getUserByParam("nickname", nickname);
		if (users.size() > 0) {
			User user = users.get(0);
			user.setPassword(password);
			return userDao.saveUser(user);
		}
		return false;
	}

	@Override
	public void manageOpenapp(int userid) {
		User user = userDao.getUser(userid+"");
		user.setLogins(user.getLogins()+1);
		userDao.saveUser(user);
	}
}
