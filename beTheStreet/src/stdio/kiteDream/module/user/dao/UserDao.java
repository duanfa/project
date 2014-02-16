package stdio.kiteDream.module.user.dao;

import java.util.List;

import stdio.kiteDream.module.user.bean.User;

public interface UserDao {

	public List<User> getUsers();
	
	public Integer getUserCount();

	public User getUser(String id);
	
	public List<User> getUserByParam(String param,String value);

	public boolean saveUser(User user);

	public boolean delUser(String userId);

	public List<User> manageSearch(String keyword);

}
