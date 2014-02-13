package stdio.kiteDream.module.prize.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import stdio.kiteDream.module.user.bean.User;

@Entity
@Table(name = "prize_order")
public class Order implements Serializable {

	public enum OrderStatu {
		PURCHASE, SEND, DONE
	}

	private static final long serialVersionUID = 7545666740293797326L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private int num;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "prizeid")
	private Prize prize;

	private String description;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userid")
	private User user;

	private OrderStatu statu;

	private String addredss;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Prize getPrize() {
		return prize;
	}

	public void setPrize(Prize prize) {
		this.prize = prize;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OrderStatu getStatu() {
		return statu;
	}

	public void setStatu(OrderStatu statu) {
		this.statu = statu;
	}

	public String getAddredss() {
		return addredss;
	}

	public void setAddredss(String addredss) {
		this.addredss = addredss;
	}

}
