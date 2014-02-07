package stdio.kiteDream.module.user.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.user.bean.User;
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
	UserDao userDao;

	@Override
	public List<User> getUsers() {
		return userDao.getUsers();
	}

	@Override
	public List<User> getUsers(int pageNo, int pageSize) {
		return userDao.getUsers(pageNo, pageSize);
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
			if(userDao.saveUser(user)){
				userEventService.updateUserEvent(user.getId(), "new_level_comic", 31);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Constant.FAIL;
		}
		return Constant.OK;
	}

	@Override
	public boolean deleteUser(String userId) {
		return userDao.delUser(userId);
	}

	@Override
	public User manageLogin(String name, String password) {
		List<User> users = userDao.getUserByParam("name", name);
		if (users.size() > 0) {
			if (password.equals(users.get(0).getPassword())) {
				return users.get(0);
			}
		}
		return null;
	}

	@Override
	public boolean manageIsExist(String name) {
		List<User> users = userDao.getUserByParam("name", name);
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
			if(record==null){
				record = new UserEventRecord();
			}
			record.setCreateTime(new Date());
			ByteArrayOutputStream baos =new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(userEventService.getEvents());
			record.setMem(baos.toByteArray());
			userEventRecordDao.saveUserEventRecord(record);
		} catch (IOException e) {
			e.printStackTrace();
			return Constant.FAIL;
		}finally{
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Constant.OK;
	}

	@Override
	public long getUserCount() {
		return userDao.getUserCount();
	}

	@Override
	public List<User> manageSearch(String keyword) {
		return userDao.manageSearch(keyword);
	}
}
