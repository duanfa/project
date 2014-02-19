package stdio.kiteDream.module.coins.dao;

import java.util.List;

import stdio.kiteDream.module.coins.bean.Level;

public interface CoinsRuleDao {

	public Level getPrizeRule(String id);
	
	public Level getLevelRule(int level);

	public boolean savePrizeRule(Level rule);

	public boolean delPrizeRule(String id);

	public List<Level> getLevelRules();

}
