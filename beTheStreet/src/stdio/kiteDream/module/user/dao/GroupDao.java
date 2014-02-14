package stdio.kiteDream.module.user.dao;

import java.util.List;

import stdio.kiteDream.module.user.bean.Group;
import stdio.kiteDream.module.user.bean.User;

public interface GroupDao {

	public Integer getCount();
	
	public Integer getGroupUserCount(int groupid);

	public List<User> getGroupUsers(int pageNo,int pageSize,int groupid);
	
	public List<Group> getGroups(int pageNo,int pageSize);
	
	public Group getGroup(int id);
	
	public boolean saveGroup(Group group);

	public boolean delGroup(String id);

	public List<Group> manageSearch(String keyword);

}
