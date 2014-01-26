package stdio.kiteDream.module.vo;

import java.util.List;

import stdio.kiteDream.module.comic.bean.Comic;

public class ComicVO {

	public List<Comic> streetComic;
	public List<Comic> bonusComic;

	public List<Comic> getStreetComic() {
		return streetComic;
	}

	public void setStreetComic(List<Comic> streetComic) {
		this.streetComic = streetComic;
	}

	public List<Comic> getBonusComic() {
		return bonusComic;
	}

	public void setBonusComic(List<Comic> bonusComic) {
		this.bonusComic = bonusComic;
	}

}
