package stdio.kiteDream.module.feedback.dao;

import java.util.List;

import stdio.kiteDream.module.feedback.bean.Feedback;

public interface FeedbackDao {

	public List<Feedback> getFeedbacks(int pageNo,int pageSize);
	
	public boolean saveFeedback(Feedback feedback);

	public boolean delFeedback(String id);

}
