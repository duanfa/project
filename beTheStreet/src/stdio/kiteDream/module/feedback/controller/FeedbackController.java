package stdio.kiteDream.module.feedback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import stdio.kiteDream.module.feedback.bean.Feedback;
import stdio.kiteDream.module.feedback.service.FeedbackService;
import stdio.kiteDream.module.user.bean.User;
import stdio.kiteDream.module.user.service.UserService;
import stdio.kiteDream.module.userEvent.service.UserEventService;
import stdio.kiteDream.module.vo.JsonVO;
import stdio.kiteDream.module.vo.PageVO;
import stdio.kiteDream.util.Constant;

@Controller
@RequestMapping("/api/feedback")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	UserEventService userEventService;
	@Autowired
	UserService userService;

	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public PageVO list(ModelMap model, @RequestParam("pageNo")int pageNo,  @RequestParam("pageSize")int pageSize,  @RequestParam(value="userid" ,required=false)int userid) {
		PageVO json = new PageVO();
		try {
			json.setResult(feedbackService.getFeedbacks(userid,pageNo, pageSize));
			json.setCount(feedbackService.getCount(userid));
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/add",  method = { RequestMethod.GET, RequestMethod.POST },produces="text/plain;charset=UTF-8")
	public JsonVO add(ModelMap model, @RequestParam(value="info",required=false) String info,@RequestParam(value="userid",required=false) int userid) {
		JsonVO json = new JsonVO();
		try {
			User user = userService.getUser(userid+"");
			Feedback feedback = new Feedback();
			feedback.setInfo(info);
			feedback.setUser(user);
			if(feedbackService.saveFeedback(feedback)){
				json.setErrorcode(Constant.OK);
			}
			json.setUser_events(userEventService.checkEvent(userid));
		} catch (Exception e) {
			json.setErrorcode(Constant.FAIL);
			e.printStackTrace();
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/del/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonVO del(ModelMap model, @PathVariable("id") String id) {
		JsonVO json = new JsonVO();
		try {
			if(feedbackService.deleteFeedback(id)){
				json.setErrorcode(Constant.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/read/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonVO read(ModelMap model, @PathVariable("id") int id) {
		JsonVO json = new JsonVO();
		try {
			Feedback feedback = feedbackService.getFeedback(id);
			if(feedback==null){
				return json;
			}
			feedback.setRead(true);
			feedbackService.saveFeedback(feedback);
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/count", method = { RequestMethod.GET, RequestMethod.POST })
	public long count() {
		try {
			return feedbackService.getCount(-1);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
