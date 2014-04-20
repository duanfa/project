package stdio.kiteDream.module.user.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
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
import stdio.kiteDream.module.vo.PageVO;
import stdio.kiteDream.util.Constant;
import stdio.kiteDream.util.CreateXL;

@Controller
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	UserEventService userEventService;

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
	public void login(HttpServletRequest request,HttpServletResponse response, @RequestParam(value = "nickname") String nickname, @RequestParam(value = "password") String password) {
		try {
			User user = userService.manageLogin(nickname, password);
			if (user != null) {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", nickname);
				response.sendRedirect(request.getContextPath() + "/index.html");
				return ;
			} 
			response.sendRedirect(request.getContextPath() + "/login.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@ResponseBody
	@RequestMapping(value = "/logout", method = {RequestMethod.GET,RequestMethod.POST})
	public void logout(HttpServletRequest request,HttpServletResponse response) {
		try {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", null);
				response.sendRedirect(request.getContextPath() + "/login.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/changepwd", method = {RequestMethod.GET,RequestMethod.POST})
	public void changepwd(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "nickname") String nickname, @RequestParam(value = "password")String password) {
		try {
			userService.manageChangepwd(nickname, password);
			HttpSession session = request.getSession(true);
			session.setAttribute("user", null);
			response.sendRedirect(request.getContextPath() + "/index.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/list/page", method = RequestMethod.GET)
	public PageVO listAll(HttpServletRequest request, @RequestParam(value = "page") int page, @RequestParam(value = "size") int size, @RequestParam(value = "groupid",required=false) int groupid) {
		PageVO json = new PageVO();
		try {
			List<User> users = userService.getUsers(page, size,groupid);
			json.setResult(users);
			json.setCount(userService.getUserCount(groupid));
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
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public JsonVO search(HttpServletRequest request,@RequestParam(value = "keyword") String keyword) {

		JsonVO json = new JsonVO();
		try {
			json.setResult(userService.manageSearchUser(keyword));
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/info/{userid}", method = RequestMethod.GET)
	public JsonVO info(@PathVariable("userid") int userid) {
		JsonVO json = new JsonVO();
		try {
			List<User> users = new ArrayList<User>();
			users.add(userService.getUser(userid+""));
			json.setResult(users);
			json.setUser_events(userEventService.checkEvent(userid));
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/xls", method = RequestMethod.GET)
	public void xls(HttpServletResponse res, @RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
		OutputStream os = null;
		File xls = null;
	    try {  
	    	os = res.getOutputStream();  
	        res.reset();  
	        res.setHeader("Content-Disposition", "attachment; filename=user"+"_"+page+"_"+size+".xls");  
	        res.setContentType("application/octet-stream; charset=utf-8");  
			List<User> users = userService.getUsers(page, size,-1);
			xls = CreateXL.createUserExcel(users);
	        os.write(FileUtils.readFileToByteArray(xls));  
	        os.flush();  
	    } catch (IOException e) {
			e.printStackTrace();
		} finally {  
	        if (os != null) {  
	            try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
	        }
	        if(xls.exists()){
	        	xls.delete();
	        }
	    }  
	}

}
