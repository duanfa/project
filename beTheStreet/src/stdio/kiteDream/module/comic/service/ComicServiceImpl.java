package stdio.kiteDream.module.comic.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.comic.bean.Comic;
import stdio.kiteDream.module.comic.bean.ComicJsonPathParser;
import stdio.kiteDream.module.comic.dao.ComicDao;
import stdio.kiteDream.util.Constant;

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
		try {
			Comic comic = comicDao.getComic(comicId);
			if(comicDao.delComic(comicId)){
				String dir = this.getClass().getClassLoader().getResource("../../").getPath();
				File img = new File(dir+comic.getPath());
				if(img.exists()){
					img.delete();
				}
				img = new File(dir+comic.getThumbnail_path());
				if(img.exists()){
					img.delete();
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Comic> getComics() {
		return comicDao.getComics();
	}

}
