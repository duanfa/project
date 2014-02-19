package stdio.kiteDream.module.comic.service;

import java.util.List;

import stdio.kiteDream.module.coins.bean.Level;
import stdio.kiteDream.module.comic.bean.Comic;

public interface ComicService {

	public List<Comic> getComics(int level);
	
	public List<Comic> getComics(int level,Level.Type type);

	public List<Comic> getComics();

	public Comic getComic(String id);

	public boolean saveComic(Comic comic);

	public boolean deleteComic(String comicId);

}
