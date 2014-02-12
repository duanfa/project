package stdio.kiteDream.module.prize.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.prize.bean.Coins;
import stdio.kiteDream.module.prize.bean.PrizeRule;
import stdio.kiteDream.module.prize.dao.CoinsDao;
import stdio.kiteDream.module.prize.dao.PrizeRuleDao;
import stdio.kiteDream.module.user.bean.User;
import stdio.kiteDream.module.user.dao.UserDao;
import stdio.kiteDream.module.userEvent.service.UserEventService;

@Service
public class PrizeRuleServiceImpl implements PrizeRuleService {
	
	@Autowired
	PrizeRuleDao prizeRuleDao;
	@Autowired
	UserDao userDao;
	@Autowired
	CoinsDao coinsDao;
	@Autowired
	UserEventService userEventService;

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
		return prizeRuleDao.savePrizeRule(rule);
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
			if(rule!=null){
				User user = userDao.getUser(userid);
				if(user.getCoins()!=null){
					user.getCoins().setGreenNum(user.getCoins().getGreenNum()+rule.getCoins().getGreenNum());
					user.getCoins().setRedNum(user.getCoins().getRedNum()+rule.getCoins().getRedNum());
					user.getCoins().setYellowNum(user.getCoins().getYellowNum()+rule.getCoins().getYellowNum());
				}else{
					Coins coins = new Coins();
					coins.setGreenNum(rule.getCoins().getGreenNum());
					coins.setRedNum(rule.getCoins().getRedNum());
					coins.setYellowNum(rule.getCoins().getYellowNum());
					coinsDao.saveCoins(coins);
					user.setCoins(coins);
				}
				userEventService.updateUserEvent(Integer.parseInt(userid), "new_reward_num", 1);
				return userDao.saveUser(user);
			}else{
				System.out.println("no such rule of level:"+level);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

}
