package sri.sample.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author Srinivas
 * @since 15 March 2017
 */

@SpringBootApplication(scanBasePackages={"sri.sample.main"})// same as @Configuration @EnableAutoConfiguration @ComponentScan
public class RestServiceMain extends SpringBootServletInitializer
{
	public static void main(String[] args) 
	{
        SpringApplication.run(RestServiceMain.class, args);
    }
	
	@Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) 
	{
	      return builder.sources(RestServiceMain.class);
	  }
}
