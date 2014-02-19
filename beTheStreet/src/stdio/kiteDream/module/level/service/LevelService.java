package stdio.kiteDream.module.level.service;

import java.util.List;

import stdio.kiteDream.module.coins.bean.Coins;
import stdio.kiteDream.module.level.bean.Level;

public interface LevelService {

public Level getPrizeRule(String id);
	
	public List<Level> getLevels();
	
	public Level getLevel(int level);
	
	public Coins getUserCoins(int userid);

	public boolean saveLevel(Level level);
	
	public boolean managePrize(int level,String userid);

	public boolean deleteLevel(String id);

	public int getCount();

}
