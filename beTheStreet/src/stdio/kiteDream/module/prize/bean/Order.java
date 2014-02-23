package stdio.kiteDream.module.prize.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import stdio.kiteDream.module.user.bean.User;

@Entity
@Table(name = "prize_order")
public class Order implements Serializable {

	public enum OrderStatu {
		PURCHASE, SEND, DONE, CANCEL, CLOSE
	}

	private static final long serialVersionUID = 7545666740293797326L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private int num;
	
	private int greenNum;
	private int yellowNum;
	private int redNum;
	
	private String name;
	
	private String email;
	
	private String phone;

	@ManyToOne
	@JoinColumn(name = "prizeid")
	private Prize prize;

	private String description;
	@OneToOne
	@JoinColumn(name = "userid")
	private User user;

	private OrderStatu statu;

	private String address;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getGreenNum() {
		return greenNum;
	}

	public void setGreenNum(int greenNum) {
		this.greenNum = greenNum;
	}

	public int getYellowNum() {
		return yellowNum;
	}

	public void setYellowNum(int yellowNum) {
		this.yellowNum = yellowNum;
	}

	public int getRedNum() {
		return redNum;
	}

	public void setRedNum(int redNum) {
		this.redNum = redNum;
	}

}
