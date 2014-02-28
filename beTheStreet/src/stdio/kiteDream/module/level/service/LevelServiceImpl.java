package stdio.kiteDream.module.level.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.coins.bean.Coins;
import stdio.kiteDream.module.coins.dao.CoinsDao;
import stdio.kiteDream.module.level.bean.Level;
import stdio.kiteDream.module.level.bean.Level.LevelState;
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
	public boolean deleteLevel(String id,String realContextPath) {
		try {
			Level oldLevel = levelDao.getLevel(id);
			File oldHeadPhoto = new File(realContextPath+"/"+oldLevel.getPath());
			File oldThumbnail_path = new File(realContextPath+"/"+oldLevel.getThumbnail_path());
			if(oldHeadPhoto.isFile()){
				oldHeadPhoto.delete();
			}
			if(oldThumbnail_path.isFile()){
				oldThumbnail_path.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return levelDao.deleteLevel(id);
	}

	@Override
	public List<Level> getLevels(int userid) {
		List<Level> levels = levelDao.getLevel();
		if(userid>0){
			User user = userDao.getUser(userid+"");
			for(Level level:levels){
				if(level.getLevel()<user.getHigh_level()){
					level.setState(LevelState.REPLAY);
				}else if(level.getLevel()<user.getHigh_level()){
					level.setState(LevelState.LOCK);
				}else{
					level.setState(LevelState.PLAYING);
				}
			}
		}
		return levels;
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
					Coins coins = group.getCoins();
					if(coins!=null){
						coins.setGreenNum(coins.getGreenNum()+realCoins[0]);
						coins.setYellowNum(coins.getYellowNum()+realCoins[1]);
						coins.setRedNum(coins.getRedNum()+realCoins[2]);
					}else{
						coins = new Coins();
						coins.setGreenNum(realCoins[0]);
						coins.setYellowNum(realCoins[1]);
						coins.setRedNum(realCoins[2]);
						group.setCoins(coins);
					}
					coinsDao.saveCoins(coins);
					groupDao.saveGroup(group);
				}
				userDao.saveUser(user);
			}else{
				System.out.println("no such rule of level:"+level);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Coins getUserCoins(int userid) {
		return coinsDao.getUserCoins(userid+"");
	}

	@Override
	public int getCount() {
		return levelDao.getCount();
	}

	@Override
	public Level getLevelById(int id) {
		return levelDao.getLevel(id+"");
	}
	

}
