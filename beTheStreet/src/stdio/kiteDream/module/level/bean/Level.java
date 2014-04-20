package stdio.kiteDream.module.level.bean;

import java.io.Serializable;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import stdio.kiteDream.module.comic.bean.BasePathJsonParser;

@Entity
@JsonIgnoreProperties({ "randomCoin" }) 
@Table(name = "level")
public class Level implements Serializable {
	
	private static final long serialVersionUID = 5198787345729066551L;
	
	public enum LevelState {
		REPLAY("replay", 0),PLAYING("playing", 1),LOCK("lock", 2);
		String name;
		int value;
		private LevelState(String name, int value){
			this.name = name;
			this.value = value;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private int level;
	
	private boolean isChallenge;
	
	private String path;
	
	private String title;
	
	private String shortdesc;
	
	@Column(name="description")
	private String desc;
	
	
	private String title2;
	
	private String shortdesc2;
	
	private String desc2;
	
	private int completeNum;
	
	private String thumbnail_path;
	
	private int greenRatio;
	private int yellowRatio;
	private int redRatio;
	
	private int sumcoins;
	
	private int regular_stage;
	
	private int bonus_stage=1;
	
	@Transient
	private LevelState state;

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

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

	public String getDesc2() {
		return desc2;
	}

	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}

	public String getShortdesc() {
		return shortdesc;
	}

	public void setShortdesc(String shortdesc) {
		this.shortdesc = shortdesc;
	}

	public String getShortdesc2() {
		return shortdesc2;
	}

	public void setShortdesc2(String shortdesc2) {
		this.shortdesc2 = shortdesc2;
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

	public boolean isChallenge() {
		return isChallenge;
	}

	public void setChallenge(boolean isChallenge) {
		this.isChallenge = isChallenge;
	}

	public int getRegular_stage() {
		return regular_stage;
	}

	public void setRegular_stage(int regular_stage) {
		this.regular_stage = regular_stage;
	}

	public int getBonus_stage() {
		return bonus_stage;
	}

	public void setBonus_stage(int bonus_stage) {
		this.bonus_stage = bonus_stage;
	}

	public LevelState getState() {
		return state;
	}

	public void setState(LevelState state) {
		this.state = state;
	}
	
}
