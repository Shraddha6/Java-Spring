package feed.common;

/**
 * <p>
 * This class acts as a wrapper for any exceptions that occur in the   component. It has "errorMessage" properties
 * that contains the relevant error code and error message respectively.
 * </p>
 */
public class FeedMetadatProcessingException extends RuntimeException {
	private static final long serialVersionUID = -4388365229901940468L;

	private String errorCode;
	private String errorMessage;

	/**
	 * Constructs a new <code>FeedException</code> with {@code null} as its detailed message.
	 */
	public FeedMetadatProcessingException() {
		super();
	}

	/**
	 * Constructs a new <code>FeedException</code> with the specified error code & detailed error message.
	 *
	 * @param errorCode
	 *            the error code for the exception.
	 *            
	 * @param errorMessage
	 *            the detailed error message. The detailed errorMessage is saved for later retrieval by the {@link #getCause()} method).
	 */
	public FeedMetadatProcessingException(String errorCode, String errorMessage,Throwable cause) {
		super(errorMessage,cause);
		this.setErrorCode(errorCode);
		this.setErrorMessage(errorMessage);
	}
	public FeedMetadatProcessingException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.setErrorCode(errorCode);
		this.setErrorMessage(errorMessage);
	}
	/**
	 * Constructs a new <code>FeedException</code> with the specified detailed error message.
	 *
	 * @param errorMessage
	 *            the detailed error message. The detailed errorMessage is saved for later retrieval by the {@link #getCause()} method).
	 */
	public FeedMetadatProcessingException(String errorMessage) {
		super(errorMessage);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
