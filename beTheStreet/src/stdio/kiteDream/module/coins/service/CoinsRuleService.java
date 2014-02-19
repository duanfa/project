package stdio.kiteDream.module.coins.service;

import java.util.List;

import stdio.kiteDream.module.coins.bean.Coins;
import stdio.kiteDream.module.coins.bean.Level;

public interface CoinsRuleService {

public Level getPrizeRule(String id);
	
	public List<Level> getLevelRules();
	
	public Level getLevelRule(int level);
	
	public Coins getUserCoins(int userid);

	public boolean savePrizeRule(Level rule);
	
	public boolean managePrize(int level,String userid);

	public boolean delPrizeRule(String id);

}
