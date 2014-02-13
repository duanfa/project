package stdio.kiteDream.module.prize.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.coins.bean.Coins;
import stdio.kiteDream.module.coins.bean.CoinsRule;
import stdio.kiteDream.module.coins.dao.CoinsRuleDao;
import stdio.kiteDream.module.prize.dao.PrizeDao;
import stdio.kiteDream.module.user.bean.User;
import stdio.kiteDream.module.user.dao.UserDao;

@Service
public class PrizeServiceImpl implements PrizeService {
	
	@Autowired
	CoinsRuleDao coinsRuleDao;
	@Autowired
	UserDao userDao;
	@Autowired
	PrizeDao coinsDao;

	@Override
	public CoinsRule getPrizeRule(String id) {
		return coinsRuleDao.getPrizeRule(id);
	}

	@Override
	public CoinsRule getLevelRule(int level) {
		return coinsRuleDao.getLevelRule(level);
	}

	@Override
	public boolean savePrizeRule(CoinsRule rule) {
		return coinsRuleDao.savePrizeRule(rule);
	}

	@Override
	public boolean delPrizeRule(String id) {
		return coinsRuleDao.delPrizeRule(id);
	}

	@Override
	public List<CoinsRule> getLevelRules() {
		return coinsRuleDao.getLevelRules();
	}

	@Override
	public boolean managePrize(int level, String userid) {
		try {
			CoinsRule rule = coinsRuleDao.getLevelRule(level);
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
