package stdio.kiteDream.module.coins.dao;

import java.util.List;

import stdio.kiteDream.module.coins.bean.CoinsRule;

public interface CoinsRuleDao {

	public CoinsRule getPrizeRule(String id);
	
	public CoinsRule getLevelRule(int level);

	public boolean savePrizeRule(CoinsRule rule);

	public boolean delPrizeRule(String id);

	public List<CoinsRule> getLevelRules();

}
