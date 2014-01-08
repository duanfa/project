package stdio.kiteDream.module.comic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.comic.bean.Comic;
import stdio.kiteDream.module.comic.dao.ComicDao;

@Service
public class ComicServiceImpl implements ComicService {
	@Autowired
	ComicDao comicDao;

	@Override
	public List<Comic> getComics(int level) {
		return comicDao.getComics(level);
	}

	@Override
	public Comic getComic(String id) {
		return comicDao.getComic(id);
	}

	@Override
	public boolean saveComic(Comic comic) {
		return comicDao.saveComic(comic);
	}

	@Override
	public boolean deleteComic(String comicId) {
		return comicDao.delComic(comicId);
	}

	@Override
	public List<Comic> getComics() {
		return comicDao.getComics();
	}

}
