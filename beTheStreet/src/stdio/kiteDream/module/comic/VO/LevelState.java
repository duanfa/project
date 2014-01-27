package stdio.kiteDream.module.comic.VO;

public enum LevelState {

	REPLAY("replay", 0),PLAYING("playing", 0),LOCK("lock", 0);
	String name;
	int value;
	private LevelState(String name, int value){
		this.name = name;
		this.value = value;
	}

}
