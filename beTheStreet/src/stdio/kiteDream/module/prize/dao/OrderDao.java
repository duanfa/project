package stdio.kiteDream.module.prize.dao;

import java.util.List;

import stdio.kiteDream.module.prize.bean.Order;

public interface OrderDao {

	public List<Order> getOrders(int pageNo, int pageSize);

	public List<Order> getUserOrders(int pageNo, int pageSize, int userid);

	public Order getOrder(String id);
	
	public Order getUserOrder(int userid,int prizeid);

	public boolean saveOrder(Order order);

	public boolean delOrder(String id);

}
