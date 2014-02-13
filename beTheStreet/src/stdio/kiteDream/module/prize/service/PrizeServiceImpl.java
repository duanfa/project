package stdio.kiteDream.module.prize.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.prize.bean.Prize;
import stdio.kiteDream.module.prize.dao.PrizeDao;

@Service
public class PrizeServiceImpl implements PrizeService {
	@Autowired
	PrizeDao prizeDao;

	@Override
	public List<Prize> getPrizes() {
		return prizeDao.getPrizes();
	}

	@Override
	public List<Prize> getPrizes(int pageNo, int pageSize) {
		return prizeDao.getPrizes(pageNo, pageSize);
	}

	@Override
	public Prize getPrize(String id) {
		return prizeDao.getPrize(id);
	}

	@Override
	public boolean savePrize(Prize prize) {
		return prizeDao.savePrize(prize);
	}

	@Override
	public boolean delPrize(String id) {
		return prizeDao.delPrize(id);
	}
	

}
