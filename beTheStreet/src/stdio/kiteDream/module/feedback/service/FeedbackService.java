package stdio.kiteDream.module.feedback.service;

import java.util.List;

import stdio.kiteDream.module.feedback.bean.Feedback;

public interface FeedbackService {

	public List<Feedback> getFeedbacks(int pageNo,int pageSize);
	
	public boolean saveFeedback(Feedback feedback);

	public boolean deleteFeedback(String id);

	public int getCount();

}
