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

@Entity
@Table(name = "prize_rule")
public class PrizeRule implements Serializable {

	private static final long serialVersionUID = 1495038434873416129L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private int level;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "coinid")
	private Coins coins;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Coins getCoins() {
		return coins;
	}

	public void setCoins(Coins coins) {
		this.coins = coins;
	}

}
