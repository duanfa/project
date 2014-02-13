package stdio.kiteDream.module.prize.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import stdio.kiteDream.module.coins.bean.CoinsRule;
import stdio.kiteDream.module.prize.service.PrizeService;
import stdio.kiteDream.module.vo.JsonVO;
import stdio.kiteDream.util.Constant;

@Controller
@RequestMapping("/api/coins")
public class PrizeController {
	@Autowired
	PrizeService coinsRuleService;

	@ResponseBody
	@RequestMapping(value = "/listrule", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonVO listrule() {
		JsonVO json = new JsonVO();
		try {
			json.setResult(coinsRuleService.getLevelRules());
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/addrule", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonVO check(CoinsRule rule) {
		JsonVO json = new JsonVO();
		try {
			if (coinsRuleService.savePrizeRule(rule)) {
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
			if (coinsRuleService.delPrizeRule(ruleid)) {
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

}