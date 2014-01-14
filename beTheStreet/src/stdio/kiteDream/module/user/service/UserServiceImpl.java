package stdio.kiteDream.module.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.user.bean.User;
import stdio.kiteDream.module.user.dao.UserDao;

@Service
public class UserServiceImpl implements UserService {
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
	public boolean saveUser(User user) {
		return userDao.saveUser(user);
	}

	@Override
	public boolean deleteUser(String userId) {
		return userDao.delUser(userId);
	}

	@Override
	public User manageLogin(String name, String password) {
		List<User> users = userDao.getUserByParam("name", name);
		if (users.size() > 0) {
			if( password.equals(users.get(0).getPassword())){
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

}
