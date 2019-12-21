package feed.constants;


import feed.common.annotations.DefaultStringValue;
import feed.common.annotations.Key;
import feed.config.common.Constants;
 public interface HttpConnectionConstants extends Constants {
	   
	   @DefaultStringValue("application/json")
	   @Key("contentType")
	   String contentType();
	   
	   @DefaultStringValue("UTF-8")
	   @Key("uniCode")
	   String uniCode();
	   
	   @DefaultStringValue("Authorization")
	   @Key("Authorization")
	   String authorization();
	   
	   
	   @DefaultStringValue("Basic ")
	   @Key("Basic")
	   String basic();
	   
	   
	   @DefaultStringValue("dladmin")
	   @Key("username")
	   String username();
	   
	   @DefaultStringValue("")
	   @Key("templatepath")
	   String templatePath();
	   
	   @DefaultStringValue("")
	   @Key("passFileLoc")
	   String passFileLoc();
	  
	   
}
 

