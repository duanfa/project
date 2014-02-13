package stdio.kiteDream.module.prize.service;

import java.util.List;

import stdio.kiteDream.module.prize.bean.Order;
import stdio.kiteDream.module.prize.bean.Prize;

public interface PrizeService {

	public List<Prize> getPrizes();

	public List<Prize> getPrizes(int pageNo, int pageSize);
	
	public List<Order> getUserOrders(int userid,int pageNo, int pageSize);

	public Prize getPrize(String id);

	public boolean savePrize(Prize prize);

	public boolean deletePrize(String id);

	public int manageBuy(int userid, int prizeid, Order order);
}
