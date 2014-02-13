package stdio.kiteDream.module.user.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import stdio.kiteDream.module.user.bean.User;
import stdio.kiteDream.module.user.service.UserService;
import stdio.kiteDream.module.userEvent.service.UserEventService;
import stdio.kiteDream.module.vo.JsonVO;
import stdio.kiteDream.util.Constant;

@Controller
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	UserEventService userEventService;

	/*@ResponseBody
	@RequestMapping(value = "/register", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonVO register(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "name", required = false) String name, 
			@RequestParam(value = "nickname", required = true) String nickname, 
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "mac", required = false) String mac,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "birthday", required = false) String birthday,
			@RequestParam(value = "email", required = false) String email, 
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "cellphone", required = false) String cellphone) {*/
		@ResponseBody
		@RequestMapping(value = "/register", method = { RequestMethod.POST, RequestMethod.GET })
		public JsonVO register(HttpServletRequest request, HttpSession session,
				User user) {
		// 设置上下方文
		JsonVO json = new JsonVO();
		try {
			List<User> users = userService.getUserByParam("nickname", user.getNickname());
			if(users.size()>0){
				json.setErrorcode(Constant.EXIST);
				return json;
			}
			user.setActive(true);
			json.setErrorcode(userService.saveUser(user));
			json.setResult(userService.getUserByParam("nickname", user.getNickname()));
			json.setUser_events(userEventService.checkEvent(user.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	@ResponseBody
	@RequestMapping(value = "/addinfo/{userid}", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonVO addInfo(HttpServletRequest request, HttpSession session,
			@PathVariable("userid") String userid,
			@RequestParam(value = "name", required = false) String name, 
			@RequestParam(value = "nickname", required = false) String nickname, 
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "mac", required = false) String mac,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "birthday", required = false) String birthday,
			@RequestParam(value = "email", required = false) String email, 
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "cellphone", required = false) String cellphone) {
		// 设置上下方文
		JsonVO json = new JsonVO();
		try {
			User user = userService.getUser(userid);
			if(StringUtils.isNotBlank(name)){
				user.setName(name);
			}
			if(StringUtils.isNotBlank(nickname)){
				user.setNickname(nickname);
			}
			if(StringUtils.isNotBlank(password)){
				user.setPassword(password);
			}
			if(StringUtils.isNotBlank(mac)){
				user.setMac(mac);
			}
			if(StringUtils.isNotBlank(gender)){
				user.setGender(gender);
			}
			if(StringUtils.isNotBlank(email)){
				user.setEmail(email);
			}
			if(StringUtils.isNotBlank(address)){
				user.setAddress(address);
			}
			if(StringUtils.isNotBlank(cellphone)){
				user.setCellPhone(cellphone);
			}
			json.setErrorcode(userService.saveUser(user));
			json.setUser_events(userEventService.checkEvent(user.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/login", method = {RequestMethod.GET,RequestMethod.POST})
	public JsonVO login(HttpServletRequest request, @RequestParam(value = "name") String name, @RequestParam(value = "password") String password) {
		JsonVO json = new JsonVO();
		try {
			User user = userService.manageLogin(name, password);
			if (user != null) {
				json.setErrorcode(Constant.OK);
				List<User> users = new ArrayList<User>();
				users.add(user);
				json.setResult(users);
				json.setUser_events(userEventService.checkEvent(user.getId()));
			} else {
				json.setErrorcode(Constant.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/list/page", method = RequestMethod.GET)
	public JsonVO listAll(HttpServletRequest request, @RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
		JsonVO json = new JsonVO();
		try {
			List<User> users = userService.getUsers(page, size);
			json.setResult(users);
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/save/record", method = RequestMethod.GET)
	public JsonVO saveRecord(HttpServletRequest request) {
		JsonVO json = new JsonVO();
		try {
			json.setErrorcode(userService.saveUserEventRecord());
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public long getUserCount(HttpServletRequest request) {
		try {
			return userService.getUserCount();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public JsonVO search(HttpServletRequest request,@RequestParam(value = "keyword") String keyword) {

		JsonVO json = new JsonVO();
		try {
			json.setResult(userService.manageSearch(keyword));
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}

}
