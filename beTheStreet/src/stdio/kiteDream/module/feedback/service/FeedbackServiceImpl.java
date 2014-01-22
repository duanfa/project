package stdio.kiteDream.module.feedback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stdio.kiteDream.module.feedback.bean.Feedback;
import stdio.kiteDream.module.feedback.dao.FeedbackDao;
import stdio.kiteDream.module.helloMessage.bean.HelloMessage;

@Service
public class FeedbackServiceImpl implements FeedbackService {
	@Autowired
	FeedbackDao feedbackDao;

	@Override
	public List<Feedback> getFeedbacks(int pageNo, int pageSize) {
		return feedbackDao.getFeedbacks(pageNo, pageSize);
	}

	@Override
	public boolean saveFeedback(Feedback feedback) {
		return feedbackDao.saveFeedback(feedback);
	}

	@Override
	public boolean deleteFeedback(String id) {
		return feedbackDao.delFeedback(id);
	}

}
