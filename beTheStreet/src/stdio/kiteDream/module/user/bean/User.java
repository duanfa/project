package stdio.kiteDream.module.user.bean;

import java.io.Serializable;
import java.util.List;

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

	private int blueCoinsNum;

	private int redCoinsNum;

	private int yellowCoinsNum;

	private String headPhoto;

	private String email;

	private String address;

	private String cellPhone;

	private boolean active;

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

	public int getBlueCoinsNum() {
		return blueCoinsNum;
	}

	public void setBlueCoinsNum(int blueCoinsNum) {
		this.blueCoinsNum = blueCoinsNum;
	}

	public int getRedCoinsNum() {
		return redCoinsNum;
	}

	public void setRedCoinsNum(int redCoinsNum) {
		this.redCoinsNum = redCoinsNum;
	}

	public int getYellowCoinsNum() {
		return yellowCoinsNum;
	}

	public void setYellowCoinsNum(int yellowCoinsNum) {
		this.yellowCoinsNum = yellowCoinsNum;
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

}
