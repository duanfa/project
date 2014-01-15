package stdio.kiteDream.module.user.service;

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
			List<User> users = userDao.getUserByParam("name", user.getName());
			if(users.size()>0){
				return Constant.EXIST;
			}
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
}
