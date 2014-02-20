package stdio.kiteDream.module.level.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.coins.bean.Coins;
import stdio.kiteDream.module.coins.dao.CoinsDao;
import stdio.kiteDream.module.level.bean.Level;
import stdio.kiteDream.module.level.dao.LevelDao;
import stdio.kiteDream.module.user.bean.Group;
import stdio.kiteDream.module.user.bean.User;
import stdio.kiteDream.module.user.dao.GroupDao;
import stdio.kiteDream.module.user.dao.UserDao;

@Service
public class LevelServiceImpl implements LevelService {
	
	@Autowired
	LevelDao levelDao;
	@Autowired
	UserDao userDao;
	@Autowired
	CoinsDao coinsDao;
	@Autowired
	GroupDao groupDao;

	@Override
	public Level getPrizeRule(String id) {
		return levelDao.getLevel(id);
	}

	@Override
	public Level getLevel(int level) {
		return levelDao.getLevel(level);
	}

	@Override
	public boolean saveLevel(Level rule) {
		return levelDao.saveLevel(rule);
	}

	@Override
	public boolean deleteLevel(String id) {
		return levelDao.deleteLevel(id);
	}

	@Override
	public List<Level> getLevels() {
		return levelDao.getLevel();
	}

	@Override
	public boolean managePrize(int level, String userid) {
		try {
			Level rule = levelDao.getLevel(level);
			if(rule!=null){
				User user = userDao.getUser(userid);
				int[] realCoins = rule.getRandomCoin();
				if(user.getCoins()!=null){
					user.getCoins().setGreenNum(user.getCoins().getGreenNum()+realCoins[0]);
					user.getCoins().setYellowNum(user.getCoins().getYellowNum()+realCoins[1]);
					user.getCoins().setRedNum(user.getCoins().getRedNum()+realCoins[2]);
				}else{
					Coins coins = new Coins();
					coins.setGreenNum(realCoins[0]);
					coins.setYellowNum(realCoins[1]);
					coins.setRedNum(realCoins[2]);
					coinsDao.saveCoins(coins);
					user.setCoins(coins);
				}
				Group group = user.getGroup();
				if(group!=null){
					if(group.getCoins()!=null){
						user.getCoins().setGreenNum(user.getCoins().getGreenNum()+realCoins[0]);
						user.getCoins().setYellowNum(user.getCoins().getYellowNum()+realCoins[1]);
						user.getCoins().setRedNum(user.getCoins().getRedNum()+realCoins[2]);
					}else{
						Coins coins = new Coins();
						coins.setGreenNum(realCoins[0]);
						coins.setYellowNum(realCoins[1]);
						coins.setRedNum(realCoins[2]);
						coinsDao.saveCoins(coins);
						group.setCoins(coins);
					}
					groupDao.saveGroup(group);
				}
				userDao.saveUser(user);
			}else{
				System.out.println("no such rule of level:"+level);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Coins getUserCoins(int userid) {
		return coinsDao.getUserCoins(userid+"");
	}

	@Override
	public int getCount() {
		return levelDao.getCount();
	}
	

}
