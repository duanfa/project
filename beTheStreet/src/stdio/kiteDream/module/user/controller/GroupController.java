package stdio.kiteDream.module.user.controller;

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
import stdio.kiteDream.module.user.service.GroupService;
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
	@RequestMapping(value = "/add", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonVO register(Group group) {
		// 设置上下方文
		JsonVO json = new JsonVO();
		try {
			groupService.saveGroup(group);
			json.setErrorcode(Constant.OK);
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
			if(groupService.manageJoinGroup(groupid,userid)){
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
	public PageVO groupOrgList(@RequestParam(value = "categoryid") int categoryid,@RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
		// 设置上下方文
				PageVO json = new PageVO();
				try {
					json.setResult(groupService.getGroupOrgs(categoryid, page, size));
					json.setCount(groupService.getGroupOrgCount(categoryid));
					json.setErrorcode(Constant.OK);
				} catch (Exception e) {
					e.printStackTrace();
					json.setErrorcode(Constant.FAIL);
				}
				return json;
	}
	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.POST, RequestMethod.GET })
	public PageVO groupList(@RequestParam(value = "orgid") int orgid,@RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
		// 设置上下方文
				PageVO json = new PageVO();
				try {
					json.setResult(groupService.getGroups(orgid, page, size));
					json.setCount(groupService.getGroupCount(orgid));
					json.setErrorcode(Constant.OK);
				} catch (Exception e) {
					e.printStackTrace();
					json.setErrorcode(Constant.FAIL);
				}
				return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/listcategory", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonVO listcategory(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size,@RequestParam(value = "userid") int userid ) {
		// 设置上下方文
		JsonVO json = new JsonVO();
		try {
			json.setResult(groupService.getGroupCategorys(page, size));
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
	public JsonVO listorg(@RequestParam(value = "categoryid") int categoryid,@RequestParam(value = "page") int page, @RequestParam(value = "size") int size,@RequestParam(value = "userid") int userid) {
		// 设置上下方文
		JsonVO json = new JsonVO();
		try {
			json.setResult(groupService.getGroupOrgs(categoryid, page, size));
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
	public JsonVO listgroup(@RequestParam(value = "orgid") int orgid,@RequestParam(value = "page") int page, @RequestParam(value = "size") int size,@RequestParam(value = "userid") int userid) {
		// 设置上下方文
		JsonVO json = new JsonVO();
		try {
			json.setResult(groupService.getGroups(orgid, page, size));
			json.setUser_events(userEventService.checkEvent(userid));
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}

}
