package apple;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;

import javax.naming.directory.SearchResult;

//*** IDEALLY THIS TEST SHOULD BE AT LEAST THREE FILES.test,results,result***

public class itunesSearchAPITest {
	private static Logger logger = Logger.getGlobal();
	
	@Test
	public void testSearch() throws Exception {
		searchResults data = searchAPI("https://itunes.apple.com/search?term=mavisakal&country=TR");
        logger.info(data.toString());
        Assert.assertNotNull(data);
    }
		
    public searchResults searchAPI(String searchUrl) throws Exception {
		URL url;
		String charset = "UTF-8";
		
		url = new URL(searchUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    
		return parseResponseData(readResponse(connection));
    }
    
    public class searchResults {
    	private int resultCount;
        private List<SearchResult> results;

        public int getResultCount() {
            return resultCount;
        }
    
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            searchResults that = (searchResults) o;

            if (resultCount != that.resultCount) return false;
            if (results != null ? !results.equals(that.results) : that.results != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = resultCount;
            result = 31 * result + (results != null ? results.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "SearchResults{" +
                    "resultCount=" + resultCount +
                    ", results=" + results +
                    '}';
        }
    }
    
    public class searchResult {
        private String wrapperType;
    	private String trackName;
    	private String artistName;
    	
    	public String getWrapperType() {
    	    return wrapperType;
    	}
    	public String getTrackName() {
            return trackName;
    	}
    	public String getArtistName() {
            return artistName;
        }
    
    	@Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            searchResult that = (searchResult) o;
            
            if (wrapperType != null ? !wrapperType.equals(that.wrapperType) : that.wrapperType != null) return false;
            if (trackName != null ? !trackName.equals(that.trackName) : that.trackName != null) return false;
            if (artistName != null ? !artistName.equals(that.artistName) : that.artistName != null) return false;

            return true;
        }
    	
    	@Override
    	public int hashCode() {
            int result = wrapperType != null ? wrapperType.hashCode() : 0;
    	    result = 31 * result + (trackName != null ? trackName.hashCode() : 0);
    	    result = 31 * result + (artistName != null ? artistName.hashCode() : 0);
    	    return result;
    	}

    	@Override
        public String toString() {
            return "SearchResult{" +
                    "trackName='" + trackName + '\'' +
                    ", artistName='" + artistName + '\''+'}';
        }
    }
    
	private static searchResults parseResponseData(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, searchResults.class);
    }
	
	private static String readResponse(HttpURLConnection connection) {
        return readDataFromResponseStream(createResponseReader(connection));
    }
	
	private static String readDataFromResponseStream(BufferedReader responseReader) {
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            while ((line = responseReader.readLine()) != null) {
                builder.append(line);
            }
            responseReader.close();
        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage(), e);
        }
        return builder.toString();
    }
	
	private static BufferedReader createResponseReader(HttpURLConnection connection) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage(), e);
        }
        return in;
    }
}
	
	

