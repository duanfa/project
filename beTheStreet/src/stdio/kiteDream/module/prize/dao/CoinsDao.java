package stdio.kiteDream.module.prize.dao;

import stdio.kiteDream.module.prize.bean.Coins;

public interface CoinsDao {

	public Coins getCoins(String id);
	
	public Coins getUserCoins(String userid);

	public boolean saveCoins(Coins coins);

	public boolean delCoins(String id);

}
