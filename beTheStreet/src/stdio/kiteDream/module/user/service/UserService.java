package stdio.kiteDream.module.user.service;

import java.util.List;

import stdio.kiteDream.module.user.bean.User;
import stdio.kiteDream.module.userEvent.bean.UserEventRecord;

public interface UserService {

	public List<User> getUsers();
	
	public List<User> getUsers(int pageNo,int pageSize);

	public User getUser(String id);

	public List<User> getUserByParam(String param, String value);

	public String saveUser(User user);
	
	public User manageLogin(String name,String password);
	
	public boolean manageIsExist(String name);

	public boolean deleteUser(String userId);
	
	public UserEventRecord getUserEventRecord();

}
