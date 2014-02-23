package stdio.kiteDream.module.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import stdio.kiteDream.module.message.bean.Message;
import stdio.kiteDream.module.message.service.MessageService;
import stdio.kiteDream.module.user.service.UserService;
import stdio.kiteDream.module.userEvent.service.UserEventService;
import stdio.kiteDream.module.vo.JsonVO;
import stdio.kiteDream.module.vo.PageVO;
import stdio.kiteDream.util.Constant;

@Controller
@RequestMapping("/api/message")
public class MessageController {
	@Autowired
	MessageService messageService;
	@Autowired
	UserEventService userEventService;

	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonVO listMessage(@RequestParam("userid") int userid,@RequestParam("page") int page,@RequestParam("size") int size) {
		JsonVO json = new JsonVO();
		try {
			json.setResult(messageService.manageGetUserMessage( userid, page, size));
			json.setErrorcode(Constant.OK);
			json.setUser_events(userEventService.checkEvent(userid));
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/pagelist", method = { RequestMethod.GET, RequestMethod.POST })
	public PageVO list(@RequestParam("userid") int userid,@RequestParam("page") int page,@RequestParam("size") int size) {
		PageVO json = new PageVO();
		try {
			json.setResult(messageService.manageGetUserMessage( userid, page, size));
			json.setErrorcode(Constant.OK);
			json.setCount(messageService.getUserMessageCount(userid));
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody()
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST },produces="text/plain;charset=UTF-8")
	public boolean addMessage(Message message,@RequestParam(value="bulkuserid",required=false) String bulkuserid) {
		try {
			messageService.saveMessage(message,bulkuserid);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@ResponseBody()
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public boolean deleteMessage(@PathVariable("id")int id) {
		try {
			messageService.deleteMessage(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@ResponseBody()
	@RequestMapping(value = "/resend/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public boolean resendMessage(@PathVariable("id")int id) {
		try {
			messageService.manageResendMessage(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


}