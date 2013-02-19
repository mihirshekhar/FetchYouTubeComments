package cde.youtube;

import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.Content;
import com.google.gdata.data.Person;
import com.google.gdata.data.extensions.Rating;
import com.google.gdata.data.geo.impl.GeoRssWhere;
import com.google.gdata.data.media.mediarss.MediaKeywords;
import com.google.gdata.data.media.mediarss.MediaPlayer;
import com.google.gdata.data.youtube.CommentEntry;
import com.google.gdata.data.youtube.CommentFeed;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.data.youtube.YouTubeMediaContent;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.data.youtube.YouTubeMediaRating;
import com.google.gdata.data.youtube.YtPublicationState;
import com.google.gdata.data.youtube.YtStatistics;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CollectComment {

	/**
	 * It generates you tube comment for specified user
	 */
	  static YouTubeService myService;
	  static int i=0;
	  static FileWriter fstreamtxt ;
	  static BufferedWriter outtxt;
	  static FileWriter fstreamcsv ;
	  static BufferedWriter outcsv ;
	  static FileWriter fstreamcsvtotal ;
	  static BufferedWriter outcsvtotal ;
	  static FileWriter fstreamtxttotal ;
	  static BufferedWriter outtxttotal;
	  final static String user="Channel8Beyond";
	  static int count;
	 public static void main(String[] args) {
	      
	      try {
	    	  //proxy for iiit,use your own proxy
	    	  System.getProperties().put("http.proxyHost", "proxy.iiit.ac.in");
	    	  System.getProperties().put("http.proxyPort", "8080");
	    	  String [] channel={"Channel8Beyond"};
	    	  fstreamtxttotal=new FileWriter("/home/mihir/workspace/mihirApp/youtube/"+"total"+".txt",true);
	    	  outtxttotal=new BufferedWriter(fstreamtxt);
	    	  fstreamcsvtotal=new FileWriter("/home/mihir/workspace/mihirApp/youtube/"+"total"+".csv",true);
	    	  outcsvtotal=new BufferedWriter(fstreamcsv);
	    	  for(int i=0;i<channel.length;i++)
	    	  {
	    	  count=0;
	    	  fstreamtxt=new FileWriter("/home/mihir/workspace/mihirApp/youtube/"+channel[i]+".txt",true);
	    	  outtxt=new BufferedWriter(fstreamtxt);
	    	  fstreamcsv=new FileWriter("/home/mihir/workspace/mihirApp/youtube/"+channel[i]+".csv",true);
	    	  outcsv=new BufferedWriter(fstreamcsv);
	        // Create a new YouTube service
	          myService = new YouTubeService("AI39si5PR1nW5HIjgyEnpdubMdMsBUqhMx3BVoL6eH-ZnvIB3ktSxn-c8FKhn8bpuHvs3FxPm0VF7b-co3dDukH45jPajP6LCQ");
	       // myService.setUserCredentials("mihirshekharcse2010@gmail.com", "5o1i6x~!@");
	        // Get a list of all entries
	          URL metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/"+user+"/uploads");//specific url to retrieve video feed for all uploads
	      //  System.out.println("Getting favorite video entries...\n");
	        
	        VideoFeed resultFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
	        printEntireVideoFeed(myService, resultFeed, true);//Prints all video feed not current
	        //printVideoFeed(resultFeed, true);
	       // System.out.println("i love you");
	         outtxt.close();
	         fstreamtxt.close();
	         outcsv.close();
	         fstreamcsv.close();
	    	  }
	    	     outtxttotal.close();
		         fstreamtxttotal.close();
		         outcsvtotal.close();
		         fstreamcsvtotal.close();
	      }
	      catch(AuthenticationException e) {
	        e.printStackTrace();
	      }
	      catch(MalformedURLException e) {
	        e.printStackTrace();
	      }
	      catch(ServiceException e) {
	        e.printStackTrace();
	      }
	      catch(IOException e) {
	        e.printStackTrace();
	      }
	    }

	 public static void printVideoFeed(VideoFeed videoFeed, boolean detailed) throws MalformedURLException, IOException, ServiceException {
		  for(VideoEntry videoEntry : videoFeed.getEntries() ) {
		    printVideoEntry(videoEntry, detailed);
		    
		  }
		  }
		  
		  public static void printVideoEntry(VideoEntry videoEntry, boolean detailed) throws MalformedURLException, IOException, ServiceException {
			  
			//  System.out.println("Title: " + videoEntry.getTitle().getPlainText());
			  String commentUrl = videoEntry.getComments().getFeedLink().getHref();
		       
			  CommentFeed commentFeed = myService.getFeed(new URL(commentUrl), CommentFeed.class);
			    for(CommentEntry comment : commentFeed.getEntries()) {
				  List<Person> per ;
				  
				  per=comment.getAuthors();
				  String author = per.get(0).getName();
				  String Date = comment.getPublished().toString();
				  String content=comment.getPlainTextContent();
				  content=content.replaceAll(","," ");
				  author=author.replaceAll(","," ");
				  //prints individual channels comments
				  outcsv.write(author+","+Date+","+content+"\n");
				  outtxt.write(author+"#$%^%$#"+Date+"#$%^%$#"+content+"&&**&&");
				  outtxt.newLine();
				  //prints total 
				  outcsvtotal.write(author+","+Date+","+content+"\n");
				  outtxttotal.write(author+"#$%^%$#"+Date+"#$%^%$#"+content+"&&**&&");
				  outtxttotal.newLine();
				  System.out.println(++count+","+author+","+Date+","+content+"\n");
			  }

			 /* if(videoEntry.isDraft()) {
			    System.out.println("Video is not live");
			    YtPublicationState pubState = videoEntry.getPublicationState();
			    if(pubState.getState() == YtPublicationState.State.PROCESSING) {
			      System.out.println("Video is still being processed.");
			    }
			    else if(pubState.getState() == YtPublicationState.State.REJECTED) {
			      System.out.print("Video has been rejected because: ");
			      System.out.println(pubState.getDescription());
			      System.out.print("For help visit: ");
			      System.out.println(pubState.getHelpUrl());
			    }
			    else if(pubState.getState() == YtPublicationState.State.FAILED) {
			      System.out.print("Video failed uploading because: ");
			      System.out.println(pubState.getDescription());
			      System.out.print("For help visit: ");
			      System.out.println(pubState.getHelpUrl());
			    }
			  }

			  if(videoEntry.getEditLink() != null) {
			    System.out.println("Video is editable by current user.");
			  }

			  if(detailed) {

			    YouTubeMediaGroup mediaGroup = videoEntry.getMediaGroup();

			    System.out.println("Uploaded by: " + mediaGroup.getUploader());

			    System.out.println("Video ID: " + mediaGroup.getVideoId());
			    System.out.println("Description: " + 
			      mediaGroup.getDescription().getPlainTextContent());

			    MediaPlayer mediaPlayer = mediaGroup.getPlayer();
			    System.out.println("Web Player URL: " + mediaPlayer.getUrl());
			    MediaKeywords keywords = mediaGroup.getKeywords();
			    System.out.print("Keywords: ");
			    for(String keyword : keywords.getKeywords()) {
			      System.out.print(keyword + ",");
			    }

			    GeoRssWhere location = videoEntry.getGeoCoordinates();
			    if(location != null) {
			      System.out.println("Latitude: " + location.getLatitude());
			      System.out.println("Longitude: " + location.getLongitude());
			    }

			    Rating rating = videoEntry.getRating();
			    if(rating != null) {
			      System.out.println("Average rating: " + rating.getAverage());
			    }

			    YtStatistics stats = videoEntry.getStatistics();
			    if(stats != null ) {
			      System.out.println("View count: " + stats.getViewCount());
			    }
			    System.out.println();


			    System.out.println("\tMedia:");
			    for(YouTubeMediaContent mediaContent : mediaGroup.getYouTubeContents()) {
			      System.out.println("\t\tMedia Location: "+ mediaContent.getUrl());
			      System.out.println("\t\tMedia Type: "+ mediaContent.getType());
			      System.out.println("\t\tDuration: " + mediaContent.getDuration());
			      System.out.println();
			    }

			   /for(YouTubeMediaRating mediaRating : mediaGroup.getYouTubeRatings()) {
			      System.out.println("Video restricted in the following countries: " +
			        mediaRating.getCountries().toString());
			    }
			  }*/
			}	  
	
		// Recursive function to print an entire feed.
		  public static void printEntireVideoFeed(YouTubeService service, 
		    VideoFeed videoFeed, boolean detailed) throws MalformedURLException, 
		    IOException, ServiceException {
		   do {
		     printVideoFeed(videoFeed, detailed);
		     if(videoFeed.getNextLink() != null) {
		       videoFeed = service.getFeed(new URL(videoFeed.getNextLink().getHref()), 
		         VideoFeed.class);
		     }
		     else {
		       videoFeed = null;
		     }
		   }
		   while(videoFeed != null);
		  }
			
}
