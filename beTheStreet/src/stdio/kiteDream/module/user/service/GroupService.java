package stdio.kiteDream.module.user.service;

import java.util.List;

import stdio.kiteDream.module.user.bean.Group;
import stdio.kiteDream.module.user.bean.GroupCategory;
import stdio.kiteDream.module.user.bean.GroupOrg;
import stdio.kiteDream.module.user.bean.User;

public interface GroupService {
	public List<GroupCategory> getGroupCategorys(int pageNo, int pageSize);

	public Integer getGroupCategoryCount();

	public boolean saveGroupCategory(GroupCategory groupCategory);

	public boolean deleteGroupCategory(int id);

	public List<GroupOrg> getGroupOrgs( int pageNo, int pageSize);

	public Integer getGroupOrgCount();

	public boolean saveGroupOrg(GroupOrg groupOrg);

	public boolean deleteGroupOrg(int id);

	public List<Group> getGroups(int categoryid,int orgid, int pageNo, int pageSize);

	public Integer getGroupCount(int categoryid,int orgid);

	public boolean saveGroup(Group group);

	public boolean deleteGroup(int id);

	public List<User> getGroupUsers(int pageNo, int pageSize, int groupid);

	public Integer getGroupUserCount(int groupid);

	public Group getGroup(int id);

	public List<Group> manageSearch(String keyword);

	public User manageJoinGroup(int groupid, int userid);

	public List<Group> getorder(int userid);

	public boolean manageLeaveGroup(int userid);
}
