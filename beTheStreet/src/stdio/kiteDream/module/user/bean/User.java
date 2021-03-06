package stdio.kiteDream.module.user.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import stdio.kiteDream.module.coins.bean.Coins;
import stdio.kiteDream.module.image.bean.Image;
import stdio.kiteDream.module.message.bean.Message;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({ "images","messages" }) 
public class User implements Serializable {

	private static final long serialVersionUID = -8619796610303376571L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

	private String password;
	
	private String mac;

	private String nickname;

	private String gender;

	private String birthday;
	
	private String latitude;
	
	private String  longitude;

	private String headPhoto;

	private String email;

	private String address;

	private Date create_time;
	
	private String cellPhone;

	private boolean active;
	
	private boolean ingroup;
	
	private boolean isadmin;
	
	private boolean readyChallenge;
	
	private int logins;
	
	//��ʱ��
	private int totaltime;
	
	private int high_level=1;
	
	private int high_level_stage=1;
	
	private int high_level_all=1;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Basic
 	private Map<Integer,Integer> bonusStatu = new TreeMap<Integer,Integer>();

	@OneToMany
	@JoinColumn
	private List<Image> images;
	
	@OneToMany
	@JoinColumn
	private List<Message> messages;
	
	@OneToOne
	@JoinColumn
	private Coins coins;
	
	@ManyToOne
	@JoinColumn(name = "groupid")
	private Group group;

	public int getId() {
		return id;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public int getHigh_level() {
		return high_level;
	}

	public void setHigh_level(int high_level) {
		this.high_level = high_level;
	}

	public int getHigh_level_stage() {
		return high_level_stage;
	}

	public void setHigh_level_stage(int high_level_stage) {
		this.high_level_stage = high_level_stage;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Coins getCoins() {
		return coins;
	}

	public void setCoins(Coins coins) {
		this.coins = coins;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHeadPhoto() {
		return headPhoto;
	}

	public void setHeadPhoto(String headPhoto) {
		this.headPhoto = headPhoto;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public boolean isIngroup() {
		return ingroup;
	}

	public void setIngroup(boolean ingroup) {
		this.ingroup = ingroup;
	}

	public Map<Integer, Integer> getBonusStatu() {
		return bonusStatu;
	}

	public void setBonusStatu(Map<Integer, Integer> bonusStatu) {
		this.bonusStatu = bonusStatu;
	}

	public boolean isReadyChallenge() {
		return readyChallenge;
	}

	public void setReadyChallenge(boolean readyChallenge) {
		this.readyChallenge = readyChallenge;
	}

	public int getHigh_level_all() {
		return high_level_all;
	}

	public void setHigh_level_all(int high_level_all) {
		this.high_level_all = high_level_all;
	}

	public boolean isIsadmin() {
		return isadmin;
	}

	public void setIsadmin(boolean isadmin) {
		this.isadmin = isadmin;
	}

	public int getLogins() {
		return logins;
	}

	public void setLogins(int logins) {
		this.logins = logins;
	}

	public int getTotaltime() {
		return totaltime;
	}

	public void setTotaltime(int totaltime) {
		this.totaltime = totaltime;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
