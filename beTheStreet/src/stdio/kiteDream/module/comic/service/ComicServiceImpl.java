package stdio.kiteDream.module.comic.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.comic.bean.Comic;
import stdio.kiteDream.module.comic.dao.ComicDao;
import stdio.kiteDream.module.userEvent.service.UserEventService;

@Service
public class ComicServiceImpl implements ComicService {
	@Autowired
	UserEventService userEventService;
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
		if(comicDao.saveComic(comic)){
			userEventService.updateAllUserEvent("new_level_comic", new Double(Math.pow(2, comic.getLevel()-1)).intValue());
		}
		return true;
	}

	@Override
	public boolean deleteComic(String comicId) {
		try {
			Comic comic = comicDao.getComic(comicId);
			if(comicDao.delComic(comicId)){
				try {
					String dir = this.getClass().getClassLoader().getResource("/").getPath()+"../../";
					File img = new File(dir+comic.getPath());
					if(img.exists()){
						img.delete();
					}
					img = new File(dir+comic.getThumbnail_path());
					if(img.exists()){
						img.delete();
					}
				} catch (Exception e) {
					e.printStackTrace();
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
