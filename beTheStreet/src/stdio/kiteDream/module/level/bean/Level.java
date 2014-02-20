package stdio.kiteDream.module.level.bean;

import java.io.Serializable;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import stdio.kiteDream.module.comic.bean.BasePathJsonParser;

@Entity
@Table(name = "level")
public class Level implements Serializable {
	
	private static final long serialVersionUID = 5198787345729066551L;

	public enum Type{
		BONUS, STREET, CHALLENGE
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private int level;
	
	private Type type;
	
	private String path;
	
	private String title;
	
	private String desc;
	
	private int completeNum;
	
	private String thumbnail_path;
	
	private int greenRatio;
	private int yellowRatio;
	private int redRatio;
	
	private int sumcoins;

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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	@JsonSerialize(using = BasePathJsonParser.class)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getCompleteNum() {
		return completeNum;
	}

	public void setCompleteNum(int completeNum) {
		this.completeNum = completeNum;
	}
	@JsonSerialize(using = BasePathJsonParser.class)
	public String getThumbnail_path() {
		return thumbnail_path;
	}

	public void setThumbnail_path(String thumbnail_path) {
		this.thumbnail_path = thumbnail_path;
	}

	public int getGreenRatio() {
		return greenRatio;
	}

	public void setGreenRatio(int greenRatio) {
		this.greenRatio = greenRatio;
	}

	public int getYellowRatio() {
		return yellowRatio;
	}

	public void setYellowRatio(int yellowRatio) {
		this.yellowRatio = yellowRatio;
	}

	public int getRedRatio() {
		return redRatio;
	}

	public void setRedRatio(int redRatio) {
		this.redRatio = redRatio;
	}

	public int getSumcoins() {
		return sumcoins;
	}

	public void setSumcoins(int sumcoins) {
		this.sumcoins = sumcoins;
	}
	
	public int[] getRandomCoin() {
		int[] coins = new int[3];
		Random random = new Random();
		for(int i=0;i<sumcoins;i++){
			int r = random.nextInt(greenRatio+yellowRatio+redRatio);
			if(r<greenRatio){
				coins[0]=coins[0]+1;
			}else if(r>=greenRatio+yellowRatio){
				coins[2]=coins[2]+1;
			}else{
				coins[1]=coins[1]+1;
			}
		}
		return coins;
	}
	
	public int getYellowCoin() {
		return yellowRatio;
	}
	
	public int getRedCoin() {
		return redRatio;
	}
	public static void main(String[] args) {
		Level l = new Level();
		l.setGreenRatio(40);
		l.setYellowRatio(40);
		l.setRedRatio(20);
		l.setSumcoins(10);
		int[] coins = l.getRandomCoin();
		System.out.println(coins[0]+":"+coins[1]+":"+coins[2]);
	}
}
