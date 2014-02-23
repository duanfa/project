package stdio.kiteDream.module.message.bean;

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

import stdio.kiteDream.module.user.bean.User;

@Entity
@JsonIgnoreProperties({"user"})
@Table(name = "message")
public class Message implements Serializable {

	private static final long serialVersionUID = 8037500748431050306L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private String description;
	private long receiveTime;
	/** 如果这个字段不为空，则用它，否则就根据messageType来确定使用什么logo **/
	private String headPhoto;
	private MessageType type;
	private Date create_time;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "userid")
	private User user;

	public Message() {
	}

	public Message(int id, String title, String headPhoto, MessageType type) {
		this.id = id;
		this.title = title;
		this.headPhoto = headPhoto;
		this.type = type;
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

	public String getHeadPhoto() {
		return headPhoto;
	}

	public void setHeadPhoto(String headPhoto) {
		this.headPhoto = headPhoto;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(long receiveTime) {
		this.receiveTime = receiveTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	public String getUserName(){
		if(user!=null){
			return user.getNickname();
		}else{
			return "";
		}
	}
}
