package feed.common;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import feed.constants.Constants.FEED_ERROR_CODE;

/**
 * </P> Common class for handling error conditions.</P>
 */

public class FeedErrorHandler{

	private FEED_ERROR_CODE feedErrorCode;
	private String errorMessage;
	@Bean
	public MessageSource messageSource() {
	     ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	     messageSource.setBasename("feederror");
	     return messageSource;
	}
    /**
     * Handles a specific Feed error code passed by the caller. This is to be called if specific error message is to be returned
     * that client can use to recover from the error. It will log and throw an exception after getting the error message from the
     * error resource bundle.
     * 
     * @param FeedErrCode
     *            ErrorCode from the resource bundle
     * @param params
     *            array of parameters that will populate the placeholder in the error message
     * @param ex
     *            Exception to be handled
     */
    public void handleError(FEED_ERROR_CODE feedErrCode, String[] params, Exception ex) {
    	this.errorMessage= messageSource().getMessage(feedErrCode.getValue(), params, Locale.getDefault());
        this.feedErrorCode=feedErrCode;
    	logAndThrowError(feedErrorCode,errorMessage, ex);

    }
    public void handleError(FEED_ERROR_CODE feedErrCode, String[] params) {
    	this.errorMessage= messageSource().getMessage(feedErrCode.getValue(), params, Locale.getDefault());
        this.feedErrorCode=feedErrCode;
    	logAndThrowError(feedErrorCode,errorMessage);

    }
    /**
     * Logs the given error and throws <code>FeedException</code> wrapping the exception
     * 
     * @param errMsg
     *            Error Message to log
     * @param ex
     *            Exception to be logged
     */
    private void logAndThrowError(FEED_ERROR_CODE feedErrCode,String errMsg, Exception ex) {
      //  LOG.error(errMsg, ex);
        throw new FeedMetadatProcessingException(feedErrCode.name(),errMsg, ex);
    }
    private void logAndThrowError(FEED_ERROR_CODE feedErrCode,String errMsg) {
        //  LOG.error(errMsg, ex);
          throw new FeedMetadatProcessingException(feedErrCode.name(),errMsg);
      }

    /**
     * Checks whether the passed arguments are null or empty and accordingly throws exception. This is to be called for required
     * fields that are to be checked for null and empty and exception thrown otherwise. It will log and throw an exception after
     * getting the error message from the error resource bundle.
     * 
     * @param params
     *            - Vargs list of parameters that are to be checked for null or empty.<br/>
     * 
     *            params are expected in sets where:<br/>
     *            -- 1st value is the param to be checked<br/>
     *            -- 2nd value is the string to be shown in the error message.<br/>
     *          
     */
    public void handleRequiredFields(Object... params) {
        List<Object> paramsList = new ArrayList<Object>(Arrays.asList(params));

        int paramsListSize = paramsList.size();
        for (int i = 0; i < paramsListSize;) {

            Object param = paramsList.get(i);

            if (param == null || (param instanceof String && StringUtils.isEmpty(param.toString()))) {
                // throw error picking up the name of parameter from next entry in the list
                logAndThrowError(FEED_ERROR_CODE.FEED_ERR_100, new String[] { paramsList.get(++i).toString() });
                // increment i to point to the next param set in the list
                i++;
            } else {
                // increment i by 2 to point to the next param set
                i += 2;
            }
        }
    }

    private void logAndThrowError(FEED_ERROR_CODE errCode, String[] params) {
        handleError(errCode, params, null);
    }

    /**
     * Handles an internal non recoverable error. This is to be called when system errors occur that are non-recoverable. It will
     * log and throw an exception after getting the error message from the error resource bundle.
     */
    public void handleInternalError(Exception ex) {
        handleError(FEED_ERROR_CODE.FEED_ERR_000, null, ex);
    }

    public void handleErrorCode(FEED_ERROR_CODE FeedErrCode, String[] params, Exception ex) {
        handleError(FeedErrCode, null, ex);
    }
	public FEED_ERROR_CODE getFeedErrorCode() {
		return feedErrorCode;
	}
	public void setFeedErrorCode(FEED_ERROR_CODE feedErrorCode) {
		this.feedErrorCode = feedErrorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
