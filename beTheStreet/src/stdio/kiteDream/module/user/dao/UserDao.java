package stdio.kiteDream.module.user.dao;

import java.util.List;

import stdio.kiteDream.module.user.bean.User;
import stdio.kiteDream.module.vo.PageVO;

public interface UserDao {

	public List<User> getUsers();
	
	public Integer getUserCount(int groupid);

	public User getUser(String id);
	
	public List<User> getUserByParam(String param,String value);

	public boolean saveUser(User user);

	public boolean delUser(String userId);

	public List<User> manageSearch(String keyword);

	public PageVO displayUserMessag(int userid, int page, int size);
	
	public boolean login(String username,String password);

}
