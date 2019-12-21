package feed.builder;

public class FeedScheduler extends FeedBuilder{
	
	 boolean schedulingStrategyTouched;
	 
	public FeedSchedule populateFeedSchedule(FeedConfiguration feedConfig) {
		FeedSchedule feedSchedule=new FeedSchedule();
		
		//"schedulingStrategy": "TIMER_DRIVEN" Actual result must be
		if(null!=feedConfig.getScheduleFeed().getSchedulingType())
		feedSchedule.setSchedulingStrategy(feedConfig.getScheduleFeed().getSchedulingType());
		//"schedulingPeriod": "5 sec",
		if(null!=feedConfig.getScheduleFeed().getTimer())
		feedSchedule.setSchedulingPeriod(feedConfig.getScheduleFeed().getTimer().getRunScheduleWithUnit());
	
		//"concurrentTasks": 1,
		if (null != feedConfig.getScheduleFeed().getCron()) {
			feedSchedule.setSchedulingPeriod(feedConfig.getScheduleFeed().getCron().getCronExpression());
			}

			// TODO
			feedSchedule.setConcurrentTasks(1);
			feedSchedule.setPreconditions(null);
			feedSchedule.setExecutionNode(null);
			System.out.println("feedSchedule details are " + feedSchedule.toString());
			return feedSchedule;
		
	}

}
