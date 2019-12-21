package feed.processors;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import feed.handlers.FeedExecutor;

public class TestFeed {

		 public static void main(String[] args) {	 
		 ApplicationContext context = new ClassPathXmlApplicationContext("auto-feed-core-application-context.xml");
		// FeedConfigurationImpl feedConfigurator = (FeedConfigurationImpl)context.getBean("feedConfigurator"); 
		 FeedExecutor feedExecutor = (FeedExecutor) context.getBean("feedExecutor");
		 
		 
		 
		
		    System.out.println("=======================================");
		    System.out.println("|             Test Client             |");
		    System.out.println("=======================================");
		    
		    System.out.println("| Options:                            |");
		    System.out.println("|                                     |");
		    System.out.println("|      1.Configure feed metadata      |");
		    System.out.println("|      2.Exit                         |");
		    System.out.println("=======================================");
		    System.out.println("Please enter a choice :");
		    Scanner inScanner = new Scanner(System.in);
		    int choice = inScanner.nextInt();
		    switch (choice)
		    {
		    case 1:    	
		      feedExecutor.execute();
		      break;
		    case 2: 
		      System.exit(0);
		    default: 
		      System.out.println("Invalid Choice");
		    }
		    inScanner.close();
		  }
}
