package stdio.kiteDream.module.user.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
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

@Entity
@Table(name = "user_group")
@JsonIgnoreProperties({"users"}) 
public class Group implements Serializable {

	private static final long serialVersionUID = -990828283870178741L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int createrid;

	private String name;
	
	private String info;
	
	private Date create_time;
	
	private int menberNum;
	
	@ManyToOne
	@JoinColumn(name = "orgid")
	private GroupOrg groupOrg;

	@OneToOne
	@JoinColumn
	private Coins coins;
	
	@OneToMany
	private List<User> users;

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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Coins getCoins() {
		return coins;
	}

	public void setCoins(Coins coins) {
		this.coins = coins;
	}

	public List<User> getUsers() {
		return users;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public GroupOrg getGroupOrg() {
		return groupOrg;
	}

	public void setGroupOrg(GroupOrg groupOrg) {
		this.groupOrg = groupOrg;
	}

	public int getCreaterid() {
		return createrid;
	}

	public void setCreaterid(int createrid) {
		this.createrid = createrid;
	}

	public int getMenberNum() {
		return menberNum;
	}

	public void setMenberNum(int menberNum) {
		this.menberNum = menberNum;
	}
	
}
