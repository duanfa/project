package stdio.kiteDream.module.comic.VO;

public enum MissionType {

	REGULAR("street—mode",0),
	BONUS("live-mode",1),
	CHALLENGE("challenge",2);
	
	private String accountName;
	private int intType;
	MissionType(String mAccountName,int mIntType){
		accountName = mAccountName;
		intType = mIntType ;  
	}
	public String getName(){
		return this.accountName;
	}
	public int getIntType(){
		return this.intType;
	}
}
