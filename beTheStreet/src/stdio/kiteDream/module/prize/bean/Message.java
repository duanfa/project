package stdio.kiteDream.module.prize.bean;

import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = 4678778767056399374L;
	private int id;
	private String title;
	private String description;
	private long receiveTime;
	/** 如果这个字段不为空，则用它，否则就根据messageType来确定使用什么logo **/
	private String headPhoto;
	private MessageType type;
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
}
