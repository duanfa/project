package stdio.kiteDream.module.user.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "group_org")
@JsonIgnoreProperties({"groups"})
public class GroupOrg implements Serializable {

	private static final long serialVersionUID = -990828283870178741L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;
	
	private String info;
	
	private int createrid;

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

	public int getCreaterid() {
		return createrid;
	}

	public void setCreaterid(int createrid) {
		this.createrid = createrid;
	}

}
