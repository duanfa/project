package stdio.kiteDream.module.user.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	@Column(columnDefinition="int default 0 ")
	private int createrid;

	@ManyToOne
	@JoinColumn(name = "categoryid")
	private GroupCategory category;
	
	@OneToMany
	private List<Group> groups;

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

	public GroupCategory getCategory() {
		return category;
	}

	public void setCategory(GroupCategory category) {
		this.category = category;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public int getCreaterid() {
		return createrid;
	}

	public void setCreaterid(int createrid) {
		this.createrid = createrid;
	}

}
