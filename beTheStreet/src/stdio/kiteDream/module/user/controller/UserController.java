package stdio.kiteDream.module.user.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

	@ResponseBody
	@RequestMapping(value = "/register", method = { RequestMethod.POST, RequestMethod.GET })
	public JsonVO register(HttpServletRequest request, HttpSession session, @RequestParam("name") String name, @RequestParam("password") String password,
			@RequestParam(value = "email", required = false) String email, @RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "cellphone", required = false) String cellphone) {
		// 设置上下方文
		JsonVO json = new JsonVO();
		try {
			User user = new User();
			user.setName(name);
			user.setPassword(password);
			user.setEmail(email);
			user.setAddress(address);
			user.setCellPhone(cellphone);
			user.setActive(true);
			json.setErrorcode(userService.saveUser(user));
			json.setResult(userService.getUserByParam("name", name));
			json.setUser_events(userEventService.checkEvent(user.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.GET)
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
			json.setErrorcode(userEventService.saveUserEventRecord());
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}

}