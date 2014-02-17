package stdio.kiteDream.module.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import stdio.kiteDream.module.message.bean.Message;
import stdio.kiteDream.module.message.service.MessageService;
import stdio.kiteDream.module.user.service.UserService;
import stdio.kiteDream.module.userEvent.service.UserEventService;
import stdio.kiteDream.module.vo.JsonVO;
import stdio.kiteDream.util.Constant;

@Controller
@RequestMapping("/api/message")
public class MessageController {
	@Autowired
	MessageService messageService;
	@Autowired
	UserEventService userEventService;

	@ResponseBody
	@RequestMapping(value = "/list/{userid}", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonVO listMessage(@PathVariable("userid") int userid) {
		JsonVO json = new JsonVO();
		try {
			json.setResult(messageService.manageGetUserMessage(userid+""));
			json.setErrorcode(Constant.OK);
			json.setUser_events(userEventService.checkEvent(userid));
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public boolean addMessage(Message message) {
		try {
			messageService.saveMessage(message,message.getUser().getId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


}