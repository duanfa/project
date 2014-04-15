package stdio.kiteDream.module.user.service;

import java.util.List;

import stdio.kiteDream.module.user.bean.Group;
import stdio.kiteDream.module.user.bean.User;
import stdio.kiteDream.module.userEvent.bean.UserEventRecord;

public interface UserService {

	public List<User> getUsers();
	
	public List<User> getUsers(int pageNo,int pageSize,int groupid);

	public User getUser(String id);

	public List<User> getUserByParam(String param, String value);

	public String saveUser(User user);
	
	public User manageLogin(String name,String password);
	
	public boolean manageIsExist(String name);

	public boolean deleteUser(String userId);
	
	public UserEventRecord getUserEventRecord();
	
	public String saveUserEventRecord();

	public int getUserCount(int groupid);

	public List<User> manageSearchUser(String keyword);
	
	public Integer getCount();
	
	public Integer getGroupUserCount(int groupid);

	public List<User> getGroupUsers(int pageNo,int pageSize,int groupid);
	
	public List<Group> getGroups(int pageNo,int pageSize);
	
	public Group getGroup(int id);
	
	public boolean saveGroup(Group group);

	public boolean delGroup(String id);
	
	public List<Group> manageSearchGroup(String keyword);

	public boolean manageChangepwd(String nickname, String password);
}
