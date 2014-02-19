package stdio.kiteDream.module.level.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import stdio.kiteDream.module.coins.bean.Coins;
import stdio.kiteDream.module.level.bean.Level;
import stdio.kiteDream.module.level.service.LevelService;
import stdio.kiteDream.module.userEvent.service.UserEventService;
import stdio.kiteDream.module.vo.JsonVO;
import stdio.kiteDream.util.Constant;

@Controller
@RequestMapping("/api/level")
public class CoinsController {
	@Autowired
	LevelService levelService;
	@Autowired
	UserEventService userEventService;
	@ResponseBody
	@RequestMapping(value = "/listrule", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonVO listrule() {
		JsonVO json = new JsonVO();
		try {
			json.setResult(levelService.getLevel());
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/addrule", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonVO check(Level rule) {
		JsonVO json = new JsonVO();
		try {
			if (levelService.saveLevel(rule)) {
				json.setErrorcode(Constant.OK);
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
	@RequestMapping(value = "/deleterule/{ruleid}", method = RequestMethod.GET)
	public JsonVO del(HttpServletRequest request, @PathVariable("ruleid") String ruleid) {
		JsonVO json = new JsonVO();
		try {
			if (levelService.deleteLevel(ruleid)) {
				json.setErrorcode(Constant.OK);
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
	@RequestMapping(value = "/view/{userid}", method = RequestMethod.GET)
	public JsonVO view(HttpServletRequest request, @PathVariable("userid") int userid) {
		JsonVO json = new JsonVO();
		try {
			List<Coins> coines = new ArrayList<Coins>();
			coines.add(levelService.getUserCoins(userid));
			json.setResult(coines);
			json.setErrorcode(Constant.OK);
			json.setUser_events(userEventService.checkEvent(userid));
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}

}