package stdio.kiteDream.module.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import stdio.kiteDream.module.user.bean.Group;
import stdio.kiteDream.module.user.bean.GroupCategory;
import stdio.kiteDream.module.user.bean.GroupOrg;
import stdio.kiteDream.module.user.bean.User;
import stdio.kiteDream.module.user.service.GroupService;
import stdio.kiteDream.module.user.service.UserService;
import stdio.kiteDream.module.userEvent.service.UserEventService;
import stdio.kiteDream.module.vo.JsonVO;
import stdio.kiteDream.module.vo.PageVO;
import stdio.kiteDream.util.Constant;

@Controller
@RequestMapping("/api/group")
public class GroupController {
	@Autowired
	GroupService groupService;
	@Autowired
	UserEventService userEventService;
	@Autowired
	UserService userService;

	@ResponseBody
	@RequestMapping(value = "/category/add", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonVO addCategory(GroupCategory groupCategory) {
	// 设置上下方文
		JsonVO json = new JsonVO();
		try {
			groupService.saveGroupCategory(groupCategory);
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	@ResponseBody
	@RequestMapping(value = "/category/delete/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonVO deletCategory(@PathVariable("id")int id) {
		// 设置上下方文
		JsonVO json = new JsonVO();
		try {
			if(groupService.deleteGroupCategory(id)){
				json.setErrorcode(Constant.OK);
			}else{
				json.setErrorcode(Constant.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/org/add", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonVO addGroupOrg(GroupOrg groupOrg) {
		// 设置上下方文
		JsonVO json = new JsonVO();
		try {
			groupService.saveGroupOrg(groupOrg);
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/org/delete/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonVO deleteGroupOrg(@PathVariable("id")int id) {
		// 设置上下方文
		JsonVO json = new JsonVO();
		try {
			if(groupService.deleteGroupOrg(id)){
				json.setErrorcode(Constant.OK);
			}else{
				json.setErrorcode(Constant.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/add", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonVO register(Group group,@RequestParam("userid")int userid) {
		// 设置上下方文
		JsonVO json = new JsonVO();
		boolean isCreate = false;
		try {
			if(group.getId()<1&&userid>0){
				isCreate = true;
				group.setCreaterid(userid);
			}
			groupService.saveGroup(group);
			if(isCreate){
				User user = userService.getUser(userid+"");
				List<User> users = new ArrayList<User>();
				users.add(user);
				json.setResult(users);
			}
			json.setErrorcode(Constant.OK);
			json.setUser_events(userEventService.checkEvent(userid));
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonVO deleteGroup(@PathVariable("id")int id) {
		// 设置上下方文
		JsonVO json = new JsonVO();
		try {
			if(groupService.deleteGroup(id)){
				json.setErrorcode(Constant.OK);
			}else{
				json.setErrorcode(Constant.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/listorder", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonVO listorder(@RequestParam("userid")int userid) {
		// 设置上下方文
		JsonVO json = new JsonVO();
		try {
			json.setResult(groupService.getorder(userid));
			json.setErrorcode(Constant.OK);
			json.setUser_events(userEventService.checkEvent(userid));
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/join/{groupid}", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonVO join(@PathVariable("groupid")int groupid,@RequestParam("userid")int userid) {
		// 设置上下方文
		JsonVO json = new JsonVO();
		try {
			User user = groupService.manageJoinGroup(groupid,userid);
			List<User> users = new ArrayList<User>();
			users.add(user);
			json.setResult(users);
			json.setErrorcode(Constant.OK);
			json.setUser_events(userEventService.checkEvent(userid));
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/leave", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonVO leave(@RequestParam("userid")int userid) {
		// 设置上下方文
		JsonVO json = new JsonVO();
		try {
			if(groupService.manageLeaveGroup(userid)){
				json.setErrorcode(Constant.OK);
			}else{
				json.setErrorcode(Constant.FAIL);
			}
			json.setUser_events(userEventService.checkEvent(userid));
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/category/list", method = { RequestMethod.POST, RequestMethod.GET })
	public PageVO categoryList(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
		// 设置上下方文
		PageVO json = new PageVO();
		try {
			json.setResult(groupService.getGroupCategorys(page, size));
			json.setCount(groupService.getGroupCategoryCount());
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	@ResponseBody
	@RequestMapping(value = "/org/list", method = { RequestMethod.POST, RequestMethod.GET })
	public PageVO groupOrgList(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
		// 设置上下方文
				PageVO json = new PageVO();
				try {
					json.setResult(groupService.getGroupOrgs(page, size));
					json.setCount(groupService.getGroupOrgCount());
					json.setErrorcode(Constant.OK);
				} catch (Exception e) {
					e.printStackTrace();
					json.setErrorcode(Constant.FAIL);
				}
				return json;
	}
	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.POST, RequestMethod.GET })
	public PageVO groupList(@RequestParam(value = "categoryid") int categoryid,@RequestParam(value = "orgid") int orgid,@RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
		// 设置上下方文
				PageVO json = new PageVO();
				try {
					json.setResult(groupService.getGroups(categoryid,orgid, page, size));
					json.setCount(groupService.getGroupCount(categoryid,orgid));
					json.setErrorcode(Constant.OK);
				} catch (Exception e) {
					e.printStackTrace();
					json.setErrorcode(Constant.FAIL);
				}
				return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/listcategory", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonVO listcategory(@RequestParam(value = "userid") int userid ) {
		// 设置上下方文
		JsonVO json = new JsonVO();
		try {
			json.setResult(groupService.getGroupCategorys(-1, -1));
			json.setUser_events(userEventService.checkEvent(userid));
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	@ResponseBody
	@RequestMapping(value = "/listorg", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonVO listorg(@RequestParam(value = "userid") int userid) {
		// 设置上下方文
		JsonVO json = new JsonVO();
		try {
			json.setResult(groupService.getGroupOrgs(-1, -1));
			json.setUser_events(userEventService.checkEvent(userid));
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	@ResponseBody
	@RequestMapping(value = "/listgroup", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonVO listgroup(@RequestParam(value = "categoryid") int categoryid,@RequestParam(value = "orgid") int orgid,@RequestParam(value = "userid") int userid) {
		// 设置上下方文
		JsonVO json = new JsonVO();
		try {
			json.setResult(groupService.getGroups(categoryid, orgid, -1, -1));
			json.setUser_events(userEventService.checkEvent(userid));
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}

}
