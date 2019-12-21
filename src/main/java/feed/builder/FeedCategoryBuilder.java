package feed.builder;


public class FeedCategoryBuilder {
	private String categoryName;
	private String categoryId;
	private String systemName;

	public FeedCategoryBuilder(String categoryName, String categoryId, String systemName) {
		super();
		this.categoryName = categoryName;
		this.categoryId = categoryId;
		this.systemName = systemName;
	}

	@Override
	public String toString() {
		return "FeedCategory [categoryName=" + categoryName + ", categoryId=" + categoryId + ", systemName="
				+ systemName + "]";
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public String getSystemName() {
		return systemName;
	}

	public FeedCategory getFeedCategory(FeedConfiguration feedConfig) {
		FeedCategory targetFeedCategory = new FeedCategory();
		FeedCategoryBuilder feedCatConfig = feed.constants.Constants.FeedCategory
				.getFeedCategory(feedConfig.getFeedCategory().toUpperCase());

		targetFeedCategory.setId(feedCatConfig.getCategoryId());
		targetFeedCategory.setSystemName(feedCatConfig.getSystemName());
		targetFeedCategory.setName(feedCatConfig.getCategoryName());

		System.out.println("targetFeedCategory details are " + targetFeedCategory.toString());
		return targetFeedCategory;

	}

	public FeedCategoryBuilder() {
		super();
		// TODO Auto-generated constructor stub
	}

}
