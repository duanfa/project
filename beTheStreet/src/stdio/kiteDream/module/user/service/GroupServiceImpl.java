package stdio.kiteDream.module.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.coins.bean.Coins;
import stdio.kiteDream.module.coins.dao.CoinsDao;
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
	@Autowired
	CoinsDao coinsDao;

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
		groupCategory.setAlias(groupCategory.getName());
		return groupDao.saveGroupCategory(groupCategory);
	}

	@Override
	public boolean deleteGroupCategory(int id) {
		return groupDao.delGroupCategory(id);
	}

	@Override
	public List<GroupOrg> getGroupOrgs( int pageNo, int pageSize) {
		return groupDao.getGroupOrgs( pageNo, pageSize);
	}

	@Override
	public Integer getGroupOrgCount() {
		return groupDao.getGroupOrgCount();
	}

	@Override
	public boolean saveGroupOrg(GroupOrg groupOrg) {
		groupOrg.setAlias(groupOrg.getName());
		return groupDao.saveGroupOrg(groupOrg);
	}

	@Override
	public boolean deleteGroupOrg(int id) {
		return groupDao.delGroupOrg(id);
	}

	@Override
	public List<Group> getGroups(int categoryid,int orgid, int pageNo, int pageSize) {
		return groupDao.getGroups(categoryid,orgid, pageNo, pageSize);
	}

	@Override
	public Integer getGroupCount(int category,int orgid) {
		return groupDao.getGroupCount(category,orgid);
	}

	@Override
	public boolean saveGroup(Group group) {
		try {
			if(group.getGroupOrg()!=null&&group.getGroupOrg().getId()<0){
				group.setGroupOrg(null);
			}
			boolean isupdate = group.getId()>0;
			group.setCreate_time(new Date());
			if(group.getCreaterid()>0){
				User user = userDao.getUser(group.getCreaterid()+"");
				if(user!=null){
					group.setCreatername(user.getNickname());
				}
			}
			Coins coins = new Coins();
			coinsDao.saveCoins(coins);
			group.setCoins(coins);
			groupDao.saveGroup(group);
			if(group.getCreaterid()>0&&(!isupdate)){
				manageJoinGroup(group.getId(), group.getCreaterid());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteGroup(int id) {
		Group group = groupDao.getGroup(id);
		group.setUsers(null);
		groupDao.saveGroup(group);
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
	public User manageJoinGroup(int groupid, int userid) {
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
			group.setMenberNum(users.size());
			user.setIngroup(true);
			groupDao.saveGroup(group);
			userDao.saveUser(user);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Group> getorder(int userid) {
		try {
			Group group = groupDao.getUserGroup(userid);
			return groupDao.getGroupByCategory(group.getCategory().getId());
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Group>();
		}
	}

	@Override
	public boolean manageLeaveGroup( int userid) {
		try {
			Group group = groupDao.getUserGroup(userid);
			User user = userDao.getUser(userid+"");
			if(group!=null){
				List<User> users = group.getUsers();
				if(users!=null){
					users.remove(user);
				}else{
					group.setUsers(new ArrayList<User>());
				}
				group.setUsers(users);
				group.setMenberNum(users.size());
				groupDao.saveGroup(group);
			}
			user.setGroup(null);
			user.setIngroup(false);
			userDao.saveUser(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
