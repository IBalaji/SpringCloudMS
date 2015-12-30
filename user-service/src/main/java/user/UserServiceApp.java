package user;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class UserServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp.class, args);
    }
}

@RestController
@RequestMapping("/bookmarks")
class UserDataController {
 
	@Autowired
    private RestTemplate restTemplate;

	@RequestMapping(method = RequestMethod.GET)
	public String loadBookmarks()
	{
		
        ResponseEntity<String> exchange =
                this.restTemplate.exchange(
                        "http://bookmark-service/bookmarks",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<String>() {
                        },
                        new HashMap<>());

        System.err.println(exchange.getBody());
        
        return exchange.getBody().toString();
    }
	
	@RequestMapping(value = "/{userName}",method = RequestMethod.GET)
	public String loadUserBookamrks(@PathVariable String userName)
	{
		
        ResponseEntity<List<String>> exchange =
                this.restTemplate.exchange(
                        "http://bookmark-service/bookmarks/{userName}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<String>>() {
                        },
                        (Object) userName);

        exchange.getBody().forEach(System.err::println);
        
        return exchange.getBody().toString();
    }
	
}


/*@Component
class DiscoveryClientExample implements CommandLineRunner {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public void run(String... strings) throws Exception {
        discoveryClient.getInstances("user-service").forEach((ServiceInstance s) -> {
            System.out.println(ToStringBuilder.reflectionToString(s));
        });
        discoveryClient.getInstances("bookmark-service").forEach((ServiceInstance s) -> {
            System.out.println(ToStringBuilder.reflectionToString(s));
        });
    }
}*/

/*@Component
class RestTemplateExample implements CommandLineRunner {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(String... strings) throws Exception {
        ResponseEntity<String> exchange =
                this.restTemplate.exchange(
                        "http://bookmark-service/bookmarks",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<String>() {
                        },
                        (Object) "dimon");

        System.err.println(exchange.getBody());
    }

}*/

/*@Component
class FeignExample implements CommandLineRunner {

    @Autowired
    private BookmarkClient bookmarkClient;

    @Override
    public void run(String... strings) throws Exception {
        this.bookmarkClient.getBookmarksByUsername("jamie").forEach(System.out::println);
    }
}

@Component
@FeignClient("bookmark-service")
interface BookmarkClient {

    @RequestMapping(method = RequestMethod.GET, value = "/bookmarks/{userName}")
    List<String> getBookmarksByUsername(@PathVariable("userName") String userName);
    
    @RequestMapping(method = RequestMethod.GET, value = "/bookmarks")
    String getBookmarks();
}*/