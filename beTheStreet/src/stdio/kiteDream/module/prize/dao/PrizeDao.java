package stdio.kiteDream.module.prize.dao;

import java.util.List;

import stdio.kiteDream.module.prize.bean.Prize;

public interface PrizeDao {

	public List<Prize> getPrizes();
	
	public List<Prize> getPrizes(int pageNo,int pageSize);
	
	public Prize getPrize(String id);
	
	public boolean savePrize(Prize prize);

	public boolean delPrize(String id);

}
