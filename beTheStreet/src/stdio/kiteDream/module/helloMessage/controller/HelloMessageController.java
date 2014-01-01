package stdio.kiteDream.module.helloMessage.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import stdio.kiteDream.module.helloMessage.bean.HelloMessage;
import stdio.kiteDream.module.helloMessage.service.HelloMessageService;
import stdio.kiteDream.util.Constant;

@Controller
@RequestMapping("/api")
public class HelloMessageController {

	@Autowired
	private HelloMessageService messageService;

	@ResponseBody
	@RequestMapping(value = "/helloMessage/get", method = RequestMethod.GET)
	public HelloMessage get(ModelMap model) {
		try {
			return messageService.getNowMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/helloMessage/list", method = RequestMethod.GET)
	public List<HelloMessage> list(ModelMap model) {
		try {
			return messageService.getMessages();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<HelloMessage>();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/helloMessage/add", method = RequestMethod.POST)
	public boolean add(ModelMap model, @RequestParam("msg") String msg,
			@RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime,
			@RequestParam("title") String title) {
		try {
			HelloMessage message = new HelloMessage();
			message.setTitle(title);
			message.setInfo(msg);
			message.setStartTime(Constant.DAY.parse(startTime));
			message.setEndTime(Constant.DAY.parse(endTime));
			return messageService.saveMessage(message);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/helloMessage/del/{msgId}", method = RequestMethod.GET)
	public boolean sendCmd(ModelMap model, @PathVariable("msgId") String msgId) {
		try {
			return messageService.deleteMessage(msgId);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
