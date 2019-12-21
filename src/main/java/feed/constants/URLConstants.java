package feed.constants;

import feed.common.annotations.DefaultStringValue;
import feed.common.annotations.Key;
import feed.config.common.Constants;

public interface URLConstants extends Constants {
	
 	@DefaultStringValue("http")
	@Key("defaultProtocol")
    String protocol();

   @DefaultStringValue("localhost")
   @Key("ip")
   String ip();
   
   @DefaultStringValue("8400")
   @Key("port")
   String port();

   @DefaultStringValue("v1")
   @Key("version")
   String version();
   
   @DefaultStringValue("feedmgr/feeds")
   @Key("path")
   String path();
   
   @DefaultStringValue("proxy")
   @Key("proxy")
   String proxy();

}
