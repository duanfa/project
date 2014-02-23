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

	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public PageVO list(ModelMap model, @RequestParam("pageNo")int pageNo,  @RequestParam("pageSize")int pageSize) {
		PageVO json = new PageVO();
		try {
			json.setResult(feedbackService.getFeedbacks(pageNo, pageSize));
			json.setCount(feedbackService.getCount());
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/add",  method = { RequestMethod.GET, RequestMethod.POST })
	public JsonVO add(ModelMap model, @RequestParam(value="title") String title, @RequestParam(value="email",required=false) String email,
			@RequestParam(value="info",required=false) String info,@RequestParam(value="userid",required=false) int userid) {
		JsonVO json = new JsonVO();
		try {
			Feedback feedback = new Feedback();
			feedback.setInfo(info);
			feedback.setEmail(email);
			feedback.setTitle(title);
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
	@RequestMapping(value = "/count", method = { RequestMethod.GET, RequestMethod.POST })
	public long count() {
		try {
			return feedbackService.getCount();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
