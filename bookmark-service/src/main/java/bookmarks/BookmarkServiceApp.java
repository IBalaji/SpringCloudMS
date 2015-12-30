package bookmarks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
public class BookmarkServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(BookmarkServiceApp.class, args);
	}
}

@RestController
@RequestMapping("/bookmarks")
class BookmarkRestController {

	static Map<String, List<String>> userBookmarks = new HashMap<>();

	static {
		userBookmarks.put("jamie", new ArrayList<String>(){{
			add("Book_1");
			add("Book_2");
		}});

		userBookmarks.put("dimon", new ArrayList<String>(){{
			add("Book_3");
			add("Book_4");
		}});
	}

	@RequestMapping(method = RequestMethod.GET)
	String getBookmarks() {
		System.err.println("Inside Get Bookmarks at "+new Date());
		return userBookmarks.toString();
	}
	
	@RequestMapping(value = "/{userName}",method = RequestMethod.GET)
	Collection<String> getBookmarksByUsername(@PathVariable String userName) {
		System.err.println("Inside Get Bookmarks");
		userBookmarks.get(userName);
		return userBookmarks.get(userName);
	}

}