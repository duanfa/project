package stdio.kiteDream.module.userEvent.dao;

import stdio.kiteDream.module.userEvent.bean.UserEventRecord;

public interface UserEventRecordDao {

	public UserEventRecord getUserEventRecord();

	public boolean saveUserEventRecord(UserEventRecord record);

	public boolean delUserEventRecord(String id);

}
