package stdio.kiteDream.module.vo;

import java.util.List;

public class JsonVO {

	private List<?> result;

	private UserEvent user_events;

	private String errorcode;

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}

	public UserEvent getUser_events() {
		return user_events;
	}

	public void setUser_events(UserEvent user_events) {
		this.user_events = user_events;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

}
