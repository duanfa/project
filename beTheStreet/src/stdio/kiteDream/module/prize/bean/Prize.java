package stdio.kiteDream.module.prize.bean;

import java.io.Serializable;

public class Prize implements Serializable{

	private static final long serialVersionUID = -1443130064688988876L;

	private int id;
	private String headPhoto;
	private String title;
	private String description;
	/** 三种金币数 **/
	private Coins coins;
	/** 销售状态-0是售罄1是销售中 **/
	private int sellState;
	
	public Prize(){}
	
	public Prize(int id, String headPhoto, String title, String description, Coins coins, int sellState){
		this.id = id;
		this.title = title;
		this.headPhoto = headPhoto;
		this.description = description;
		this.coins = coins;
		this.sellState = sellState;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHeadPhoto() {
		return headPhoto;
	}

	public void setHeadPhoto(String headPhoto) {
		this.headPhoto = headPhoto;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Coins getCoins() {
		return coins;
	}

	public void setCoins(Coins coins) {
		this.coins = coins;
	}

	public int getSellState() {
		return sellState;
	}

	public void setSellState(int sellState) {
		this.sellState = sellState;
	}
}
