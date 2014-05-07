package stdio.kiteDream.module.level.service;

import java.util.List;

import stdio.kiteDream.module.coins.bean.Coins;
import stdio.kiteDream.module.level.bean.Level;

public interface LevelService {

public Level getPrizeRule(String id);
	
	public List<Level> getLevels(int userid);
	
	public Level getLevelById(int id);
	
	public Level getLevel(int level);
	
	public Coins getUserCoins(int userid);

	public boolean saveLevel(Level level);
	
	public boolean managePrize(String imageId,String userid);

	public boolean deleteLevel(String id,String realContextPath);

	public int getCount();

	public String manageAccessChallenge(int userid);

}
