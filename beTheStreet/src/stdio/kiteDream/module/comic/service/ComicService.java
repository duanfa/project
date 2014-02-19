package stdio.kiteDream.module.comic.service;

import java.util.List;

import stdio.kiteDream.module.comic.bean.Comic;
import stdio.kiteDream.module.level.bean.Level;

public interface ComicService {

	public List<Comic> getComics(int level);
	
	public List<Comic> getComics(int level,Level.Type type);

	public List<Comic> getComics();

	public Comic getComic(String id);

	public boolean saveComic(Comic comic);

	public boolean deleteComic(String comicId);

}
