package kr.co.controller;

//import org.junit.Before;
import org.junit.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import com.google.gson.Gson;
import kr.co.vo.SampleVO;

public class VoTest {
	@Autowired
	//private WebApplicationContext ctx;
	
	@Test
	public void testVO() throws Exception {
		//VOTest
		SampleVO sv = new SampleVO();
		sv.setNum(7);
		sv.setFirstName("기태");
		sv.setLastName("김");
		System.out.println(sv.toString());
	}
}