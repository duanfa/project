package stdio.kiteDream.module.vo;

import java.util.List;

import stdio.kiteDream.module.userEvent.bean.UserEvent;

public class PageVO {

	private List<?> result;

	private int count;

	private String errorcode;

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

}
