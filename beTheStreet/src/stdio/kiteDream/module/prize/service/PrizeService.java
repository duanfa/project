package stdio.kiteDream.module.prize.service;

import java.util.List;

import stdio.kiteDream.module.coins.bean.CoinsRule;

public interface PrizeService {

public CoinsRule getPrizeRule(String id);
	
	public List<CoinsRule> getLevelRules();
	
	public CoinsRule getLevelRule(int level);

	public boolean savePrizeRule(CoinsRule rule);
	
	public boolean managePrize(int level,String userid);

	public boolean delPrizeRule(String id);

}
