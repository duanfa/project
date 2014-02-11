package stdio.kiteDream.module.prize.dao;

import java.util.List;

import stdio.kiteDream.module.prize.bean.PrizeRule;

public interface PrizeRuleDao {

	public PrizeRule getPrizeRule(String id);
	
	public PrizeRule getLevelRule(int level);

	public boolean savePrizeRule(PrizeRule rule);

	public boolean delPrizeRule(String id);

	public List<PrizeRule> getLevelRules();

}
