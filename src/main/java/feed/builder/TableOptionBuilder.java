package feed.builder;


public class TableOptionBuilder extends FeedBuilder {

	TableOptions tableOption = new TableOptions();

	public TableOptionBuilder() {
	}

	public TableOptionBuilder(TableOptions tableOption) {
		this.tableOption = tableOption;

	}

	public TableOptionBuilder compress(boolean compress) {
		tableOption.setCompress(compress);
		return this;

	}

	public TableOptionBuilder auditLogging(boolean compress) {
		tableOption.setCompress(compress);
		return this;

	}

	public TableOptionBuilder compressFormat(String compressionFormat) {
		tableOption.setCompressionFormat(compressionFormat);
		return this;

	}

	public TableOptionBuilder encrypt(boolean encrypt) {
		tableOption.setCompress(encrypt);
		return this;

	}

	public TableOptionBuilder trackHistory(boolean trackHistory) {
		tableOption.setCompress(trackHistory);
		return this;

	}

	public TableOptions build() {
		return tableOption;
	}

}
