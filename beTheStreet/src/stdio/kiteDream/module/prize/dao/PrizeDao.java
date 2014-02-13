package stdio.kiteDream.module.prize.dao;

import stdio.kiteDream.module.coins.bean.Coins;

public interface PrizeDao {

	public Coins getCoins(String id);
	
	public Coins getUserCoins(String userid);

	public boolean saveCoins(Coins coins);

	public boolean delCoins(String id);

}
