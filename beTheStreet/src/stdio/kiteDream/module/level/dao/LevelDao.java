package stdio.kiteDream.module.level.dao;

import java.util.List;

import stdio.kiteDream.module.level.bean.Level;

public interface LevelDao {

	public Level getLevel(String id);
	
	public Level getLevel(int level);

	public boolean saveLevel(Level level);

	public boolean deleteLevel(String id);

	public List<Level> getLevel();

}
