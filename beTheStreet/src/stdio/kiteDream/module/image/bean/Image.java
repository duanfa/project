package stdio.kiteDream.module.image.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import stdio.kiteDream.module.comic.bean.BasePathJsonParser;
import stdio.kiteDream.module.user.bean.User;

@Entity
@JsonIgnoreProperties({}) 
@Table(name = "image")
public class Image implements Serializable {
	
	public enum Type{
		BONUS, STREET, CHALLENGE
	}

	public enum Check {
		PASS, UNREAD, FAIL
	}

	private static final long serialVersionUID = 787921280747262572L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

	private String describ;
	
	private String latitude;
	
	private String  longitude;
	
	private String ip;
	
	private String address;

	private int level;
	
	private int level_stage;
	
	private Type levelType;

	private Date create_time = new Date();
	
	private Date update_time = new Date();

	private String path;
	
	private String thumbnail_path;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "userid")
	private User user;

	private Check statu;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getDescrib() {
		return describ;
	}

	public void setDescrib(String describe) {
		this.describ = describe;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public Check getStatu() {
		return statu;
	}

	public void setStatu(Check statu) {
		this.statu = statu;
	}

	@JsonSerialize(using = BasePathJsonParser.class)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@JsonSerialize(using = BasePathJsonParser.class)
	public String getThumbnail_path() {
		return thumbnail_path;
	}

	public void setThumbnail_path(String thumbnail_path) {
		this.thumbnail_path = thumbnail_path;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Type getLevelType() {
		return levelType;
	}

	public void setLevelType(Type levelType) {
		this.levelType = levelType;
	}

	public int getLevel_stage() {
		return level_stage;
	}

	public void setLevel_stage(int level_stage) {
		this.level_stage = level_stage;
	}

}
