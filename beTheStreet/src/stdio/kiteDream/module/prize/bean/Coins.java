package stdio.kiteDream.module.prize.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "coins")
public class Coins implements Serializable{

	private static final long serialVersionUID = 6338505113857035134L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int greenNum;
	private int yellowNum;
	private int redNum;
	
	public Coins(){}
	
	public Coins(int greenNum, int yellowNum, int redNum){
		this.greenNum = greenNum;
		this.yellowNum = yellowNum;
		this.redNum = redNum;
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
