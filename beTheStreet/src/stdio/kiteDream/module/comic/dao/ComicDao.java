package stdio.kiteDream.module.comic.dao;

import java.util.List;

import stdio.kiteDream.module.coins.bean.Level.Type;
import stdio.kiteDream.module.comic.bean.Comic;

public interface ComicDao {

	public List<Comic> getComics(int level);
	
	public List<Comic> getComics();

	public Comic getComic(String id);

	public boolean saveComic(Comic comic);

	public boolean delComic(String comicId);

	public List<Comic> getComics(int level, Type type);

}
