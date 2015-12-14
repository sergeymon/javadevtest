package ru.myrest.rate;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Perminov Sergey
 *
 */
@EnableAutoConfiguration
@ComponentScan
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Controller
public class TestController implements CommandLineRunner 
{
	private final static Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	TestDao testDao;
	
	@Override
	public void run(String... arg0) throws Exception {
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = {"/addperson/{name}/{age}"}, 
			method = RequestMethod.GET, produces="application/json")
    @ResponseBody 
    ResponseEntity<String> getRate(@PathVariable Map<String, String> pathVarsMap) {
		try {
			String name = pathVarsMap.get("name");
			String age = pathVarsMap.get("age");
			testDao.createUser(name, Integer.parseInt(age));
			logger.trace("Получен запрос с параметрами name: {}, age: {}.", name, age);
			ObjectMapper objMapper = new ObjectMapper();
	        return new ResponseEntity<String>(objMapper.writeValueAsString("Пользователь добавлен"), HttpStatus.OK);
		} catch (Throwable e) {
			logger.error("Ошибка при оработке запроса.", e);
			return new ResponseEntity<String>("На сервере произошла ошибка.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	public static void main( String[] args )
    {
		SpringApplication.run(TestController.class, args);
		logger.info(String.format("user.home = %s", System.getProperty("user.home")));
		logger.info("Server started!");
    }
}
