package stdio.kiteDream.module.prize.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.prize.bean.PrizeRule;
import stdio.kiteDream.module.prize.dao.PrizeRuleDao;
import stdio.kiteDream.module.user.bean.User;
import stdio.kiteDream.module.user.dao.UserDao;

@Service
public class PrizeRuleServiceImpl implements PrizeRuleService {
	
	@Autowired
	PrizeRuleDao prizeRuleDao;
	@Autowired
	UserDao userDao;

	@Override
	public PrizeRule getPrizeRule(String id) {
		return prizeRuleDao.getPrizeRule(id);
	}

	@Override
	public PrizeRule getLevelRule(int level) {
		return prizeRuleDao.getLevelRule(level);
	}

	@Override
	public boolean savePrizeRule(PrizeRule rule) {
		if(prizeRuleDao.getLevelRule(rule.getLevel())!=null){
			return prizeRuleDao.savePrizeRule(rule);
		}
		return false;
	}

	@Override
	public boolean delPrizeRule(String id) {
		return prizeRuleDao.delPrizeRule(id);
	}

	@Override
	public List<PrizeRule> getLevelRules() {
		return prizeRuleDao.getLevelRules();
	}

	@Override
	public boolean managePrize(int level, String userid) {
		try {
			PrizeRule rule = prizeRuleDao.getLevelRule(level);
			User user = userDao.getUser(userid);
			user.getCoins().setGreenNum(user.getCoins().getGreenNum()+rule.getCoins().getGreenNum());
			user.getCoins().setRedNum(user.getCoins().getRedNum()+rule.getCoins().getRedNum());
			user.getCoins().setYellowNum(user.getCoins().getYellowNum()+rule.getCoins().getYellowNum());
			return userDao.saveUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

}
