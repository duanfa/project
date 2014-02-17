package stdio.kiteDream.module.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.user.bean.Group;
import stdio.kiteDream.module.user.bean.GroupCategory;
import stdio.kiteDream.module.user.bean.GroupOrg;
import stdio.kiteDream.module.user.bean.User;
import stdio.kiteDream.module.user.dao.GroupDao;
import stdio.kiteDream.module.user.dao.UserDao;

@Service
public class GroupServiceImpl implements GroupService {
	@Autowired
	GroupDao groupDao;
	@Autowired
	UserDao userDao;

	@Override
	public List<GroupCategory> getGroupCategorys(int pageNo, int pageSize) {
		return groupDao.getGroupCategorys(pageNo, pageSize);
	}

	@Override
	public Integer getGroupCategoryCount() {
		return groupDao.getGroupCategoryCount();
	}

	@Override
	public boolean saveGroupCategory(GroupCategory groupCategory) {
		return groupDao.saveGroupCategory(groupCategory);
	}

	@Override
	public boolean delGroupCategory(int id) {
		return groupDao.delGroupCategory(id);
	}

	@Override
	public List<GroupOrg> getGroupOrgs(int categoryid, int pageNo, int pageSize) {
		return groupDao.getGroupOrgs(categoryid, pageNo, pageSize);
	}

	@Override
	public Integer getGroupOrgCount(int categoryid) {
		return groupDao.getGroupOrgCount(categoryid);
	}

	@Override
	public boolean saveGroupOrg(GroupOrg groupOrg) {
		return groupDao.saveGroupOrg(groupOrg);
	}

	@Override
	public boolean delGroupOrg(int id) {
		return groupDao.delGroupOrg(id);
	}

	@Override
	public List<Group> getGroups(int orgid, int pageNo, int pageSize) {
		return groupDao.getGroups(orgid, pageNo, pageSize);
	}

	@Override
	public Integer getGroupCount(int orgid) {
		return groupDao.getGroupCount(orgid);
	}

	@Override
	public boolean saveGroup(Group group) {
		group.setCreate_time(new Date());
		return groupDao.saveGroup(group);
	}

	@Override
	public boolean delGroup(int id) {
		return groupDao.delGroup(id);
	}

	@Override
	public List<User> getGroupUsers(int pageNo, int pageSize, int groupid) {
		return groupDao.getGroupUsers(pageNo, pageSize, groupid);
	}

	@Override
	public Integer getGroupUserCount(int groupid) {
		return groupDao.getGroupUserCount(groupid);
	}

	@Override
	public Group getGroup(int id) {
		return groupDao.getGroup(id);
	}

	@Override
	public List<Group> manageSearch(String keyword) {
		return groupDao.manageSearch(keyword);
	}

	@Override
	public boolean manageJoinGroup(int groupid, int userid) {
		try {
			Group group = groupDao.getGroup(groupid);
			User user = userDao.getUser(userid+"");
			List<User> users = group.getUsers();
			if(users!=null){
				users.add(user);
			}else{
				users = new ArrayList<User>();
				users.add(user);
			}
			user.setGroup(group);
			group.setUsers(users);
			groupDao.saveGroup(group);
			userDao.saveUser(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
