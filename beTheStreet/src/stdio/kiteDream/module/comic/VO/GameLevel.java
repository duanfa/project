package stdio.kiteDream.module.comic.VO;

import java.io.Serializable;
import java.util.List;

import stdio.kiteDream.module.comic.bean.Comic;

public class GameLevel implements Serializable{

	private static final long serialVersionUID = -469478580270657003L;
	private int id;
	private int level;
	private String title;
	private List<Comic> regularLevel;
	private List<Comic> bonusLevel;
	private int regularLevelQuota;
	private int regularLevelFinishedNum;
	private int bonusLevelQuota;
	private int bonusLevelFinishedNum;
	private MissionType currentMissionType = MissionType.REGULAR;
	private int currentFinishedNum;
	private LevelState state;

	public GameLevel() {
	}
	
	public GameLevel(int id, int level, String title, LevelState state) {
		this.id = id;
		this.level = level;
		this.title = title;
		this.state = state;
	}

	public GameLevel(int level) {
		this.level = level;
	}

	public GameLevel(int id, int level, String title, LevelState state, int regularLevelQuota,
			int regularLevelFinishedNum, int bonusLevelQuota, int bonusLevelFinishedNum, MissionType currentMissionType, int currentFinishedNum) {
		this(id, level, title, state);
		this.regularLevelQuota = regularLevelQuota;
		this.regularLevelFinishedNum = regularLevelFinishedNum;
		this.bonusLevelQuota = bonusLevelQuota;
		this.bonusLevelFinishedNum = bonusLevelFinishedNum;
		this.currentMissionType = currentMissionType;
		this.currentFinishedNum = currentFinishedNum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LevelState getState() {
		return state;
	}

	public void setState(LevelState state) {
		this.state = state;
	}

	public List<Comic> getRegularLevel() {
		return regularLevel;
	}

	public void setRegularLevel(List<Comic> regularLevel) {
		this.regularLevel = regularLevel;
	}

	public List<Comic> getBonusLevel() {
		return bonusLevel;
	}

	public void setBonusLevel(List<Comic> bonusLevel) {
		this.bonusLevel = bonusLevel;
	}

	public int getRegularLevelQuota() {
		return regularLevelQuota;
	}

	public void setRegularLevelQuota(int regularLevelQuota) {
		this.regularLevelQuota = regularLevelQuota;
	}

	public int getRegularLevelFinishedNum() {
		return regularLevelFinishedNum;
	}

	public void setRegularLevelFinishedNum(int regularLevelFinishedNum) {
		this.regularLevelFinishedNum = regularLevelFinishedNum;
	}

	public int getBonusLevelQuota() {
		return bonusLevelQuota;
	}

	public void setBonusLevelQuota(int bonusLevelQuota) {
		this.bonusLevelQuota = bonusLevelQuota;
	}

	public int getBonusLevelFinishedNum() {
		return bonusLevelFinishedNum;
	}

	public void setBonusLevelFinishedNum(int bonusLevelFinishedNum) {
		this.bonusLevelFinishedNum = bonusLevelFinishedNum;
	}

	public MissionType getCurrentMissionType() {
		return currentMissionType;
	}

	public void setCurrentMissionType(MissionType currentMissionType) {
		this.currentMissionType = currentMissionType;
	}

	public int getCurrentFinishedNum() {
		return currentFinishedNum;
	}

	public void setCurrentFinishedNum(int currentFinishedNum) {
		this.currentFinishedNum = currentFinishedNum;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
