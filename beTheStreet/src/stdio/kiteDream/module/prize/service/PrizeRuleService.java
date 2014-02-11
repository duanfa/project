package stdio.kiteDream.module.prize.service;

import java.util.List;

import stdio.kiteDream.module.prize.bean.PrizeRule;

public interface PrizeRuleService {

public PrizeRule getPrizeRule(String id);
	
	public List<PrizeRule> getLevelRules();
	
	public PrizeRule getLevelRule(int level);

	public boolean savePrizeRule(PrizeRule rule);
	
	public boolean managePrize(int level,String userid);

	public boolean delPrizeRule(String id);

}
