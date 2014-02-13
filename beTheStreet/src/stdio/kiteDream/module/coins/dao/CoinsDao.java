package stdio.kiteDream.module.coins.dao;

import stdio.kiteDream.module.coins.bean.Coins;

public interface CoinsDao {

	public Coins getCoins(String id);
	
	public Coins getUserCoins(String userid);

	public boolean saveCoins(Coins coins);

	public boolean delCoins(String id);

}
