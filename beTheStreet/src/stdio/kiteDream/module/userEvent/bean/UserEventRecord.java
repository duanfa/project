package stdio.kiteDream.module.userEvent.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_event_record")
public class UserEventRecord implements Serializable{
	
	private static final long serialVersionUID = 4130756521644707165L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private Date createTime;
	
	@Column(columnDefinition = "BLOB") 
	private byte[] mem;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public byte[] getMem() {
		return mem;
	}

	public void setMem(byte[] mem) {
		this.mem = mem;
	}

}
