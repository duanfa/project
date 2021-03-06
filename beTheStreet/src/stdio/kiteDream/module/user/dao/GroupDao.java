package stdio.kiteDream.module.user.dao;

import java.util.List;

import stdio.kiteDream.module.user.bean.Group;
import stdio.kiteDream.module.user.bean.GroupCategory;
import stdio.kiteDream.module.user.bean.GroupOrg;
import stdio.kiteDream.module.user.bean.User;

public interface GroupDao {

	public List<GroupCategory> getGroupCategorys(int pageNo,int pageSize);
	public Integer getGroupCategoryCount();
	public boolean saveGroupCategory(GroupCategory groupCategory);
	public boolean delGroupCategory(int id);
	
	public List<GroupOrg> getGroupOrgs(int pageNo,int pageSize);
	public Integer getGroupOrgCount();
	public boolean saveGroupOrg(GroupOrg groupOrg);
	public boolean delGroupOrg(int id);
	
	public List<Group> getGroups(int categoryid,int orgid, int pageNo, int pageSize);
	public Integer getGroupCount(int category,int orgid);
	public boolean saveGroup(Group group);
	public boolean delGroup(int id);
	
	public List<User> getGroupUsers(int pageNo,int pageSize,int groupid);
	public Integer getGroupUserCount(int groupid);
	
	public GroupCategory getGroupCategory(int id);
	public GroupOrg getGroupOrg(int id);
	public Group getGroup(int id);
	
	public Group getUserGroup(int userid);
	
	public List<Group> manageSearch(String keyword);
	
	public List<Group> getGroupByCategory(int categoryid);

}
