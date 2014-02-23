package stdio.kiteDream.module.prize.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import stdio.kiteDream.module.coins.bean.Coins;
import stdio.kiteDream.module.comic.bean.BasePathJsonParser;

@Entity
@JsonIgnoreProperties({ "orders" }) 
@Table(name = "prize")
public class Prize implements Serializable {

	private static final long serialVersionUID = -1443130064688988876L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String headPhoto;
	private String thumbnail_path;
	private String title;
	private int num;
	private String description;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "coinid")
	private Coins coins;
	private int sellState;

	@OneToMany
	@JoinColumn(name = "prizeid")
	private List<Order> orders;
	
	public Prize(){
		
	}
	
	public Prize(int id, String headPhoto, String title, String description,
			Coins coins, int sellState) {
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

	@JsonSerialize(using = BasePathJsonParser.class)
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

	@JsonSerialize(using = BasePathJsonParser.class)
	public String getThumbnail_path() {
		return thumbnail_path;
	}

	public void setThumbnail_path(String thumbnail_path) {
		this.thumbnail_path = thumbnail_path;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}
