package stdio.kiteDream.module.feedback.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import stdio.kiteDream.module.user.bean.User;

@Entity
@Table(name = "feedback")
public class Feedback implements Serializable {

	private static final long serialVersionUID = -8598648884046539272L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String info;
	
	@OneToOne
	@JoinColumn(name="userid")
	private User user;
	
	private boolean read;
	
	private Date date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
