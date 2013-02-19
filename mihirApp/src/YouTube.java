/*Could not find all the external dependencies. 
There are following possibilities:
1. Either a wrong directory for external dependencies was provided
2. Or there was an error downloading the files
3. Or some files are missing from the directory.

You may not be able to execute this template (or your future application) without adding the required dependencies.*/

/* INSTRUCTION: This is a command line application. So please execute this template with the following arguments:

		arg[0] = YouTube account username whose videos' list you want to see
*/

/**
 * @author (Your Name Here)
 *
 */
 
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.CommentEntry;
import com.google.gdata.data.youtube.CommentFeed;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * This is a test template
 */

  public class YouTube {
    
    public static void main(String[] args) {
      
      try {
    	  System.getProperties().put("http.proxyHost", "proxy.iiit.ac.in");
    	  System.getProperties().put("http.proxyPort", "8080");
    	  
        // Create a new YouTube service
        YouTubeService myService = new YouTubeService("AI39si5PR1nW5HIjgyEnpdubMdMsBUqhMx3BVoL6eH-ZnvIB3ktSxn-c8FKhn8bpuHvs3FxPm0VF7b-co3dDukH45jPajP6LCQ");
       // myService.setUserCredentials("mihirshekharcse2010@gmail.com", "5o1i6x~!@");
        // Get a list of all entries
        URL metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/"+args[0]+"/favorites");
        System.out.println("Getting favorite video entries...\n");
        
        VideoFeed resultFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
        System.out.println("i love you");
        List<VideoEntry> entries = resultFeed.getEntries();
        for(int i=0; i<entries.size(); i++) {
          VideoEntry entry = entries.get(i);
          String commentUrl = entry.getComments().getFeedLink().getHref();
          
          CommentFeed commentFeed = myService.getFeed(new URL(commentUrl), CommentFeed.class);
          for(CommentEntry comment : commentFeed.getEntries()) {
            System.out.println(comment.getPlainTextContent());
          }
          System.out.println("\t" + entry.getTitle().getPlainText());
        }
        System.out.println("\nTotal Entries: "+entries.size());
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
  }
