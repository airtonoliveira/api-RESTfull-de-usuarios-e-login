import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.airton.model.Usuario;
import br.com.airton.repository.UsuarioRepository;
import br.com.airton.security.JWTUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=br.com.airton.Application.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationTests {
	
	@Autowired private WebApplicationContext webApplicationContext;
    @Autowired private UsuarioRepository repository;
	private MockMvc mockMvc;
	private final String TOKEN_REGEX = "^\\{\"token\":\"+[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*\"\\}$";
	
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void test00_EndpointSignup_DadosValidos() throws Exception {
		String postJson = "{\r\n" + 
				"        \"firstName\": \"José\",\r\n" + 
				"        \"lastName\": \"Silva\",\r\n" + 
				"        \"email\": \"teste@teste.com\",\r\n" + 
				"        \"password\": \"senha123\",\r\n" + 
				"        \"phones\": [\r\n" + 
				"            {\r\n" + 
				"                \"number\": 978782002,\r\n" + 
				"                \"area_code\": 81,\r\n" + 
				"                \"country_code\": \"+55\"\r\n" + 
				"            }\r\n" + 
				"        ]\r\n" + 
				"    }";
		
		
		mockMvc.perform(post("/signup")
				.content(postJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();
	}
	
	@Test
	public void test01_EndpointSignup_isTokenFornecido() throws Exception {
		String postJson = "{\r\n" + 
				"        \"firstName\": \"José\",\r\n" + 
				"        \"lastName\": \"Silva\",\r\n" + 
				"        \"email\": \"teste2@teste.com\",\r\n" + 
				"        \"password\": \"senha123\",\r\n" + 
				"        \"phones\": [\r\n" + 
				"            {\r\n" + 
				"                \"number\": 978782002,\r\n" + 
				"                \"area_code\": 81,\r\n" + 
				"                \"country_code\": \"+55\"\r\n" + 
				"            }\r\n" + 
				"        ]\r\n" + 
				"    }";
		
		
		MvcResult result = mockMvc.perform(post("/signup")
				.content(postJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		assertTrue(content.matches(TOKEN_REGEX));
	}
	
	@Test
	public void test03_EndpointMe_isTokenValido() throws Exception {
		String postJson = "{\r\n" + 
				"        \"firstName\": \"José\",\r\n" + 
				"        \"lastName\": \"Silva\",\r\n" + 
				"        \"email\": \"teste3@teste.com\",\r\n" + 
				"        \"password\": \"senha123\",\r\n" + 
				"        \"phones\": [\r\n" + 
				"            {\r\n" + 
				"                \"number\": 978782002,\r\n" + 
				"                \"area_code\": 81,\r\n" + 
				"                \"country_code\": \"+55\"\r\n" + 
				"            }\r\n" + 
				"        ]\r\n" + 
				"    }";
		
		
		MvcResult result = mockMvc.perform(post("/signup")
				.content(postJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();
		
		String tokenFornecido = result.getResponse().getHeader(JWTUtil.AUTHORIZATION);
		
		mockMvc.perform(get("/me")
				.header(JWTUtil.AUTHORIZATION, tokenFornecido)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
	
	@Test
	public void test04_EndpointSignup_CampoSenhaNaoInformado() throws Exception {
		String postJson = "{\r\n" + 
				"        \"firstName\": \"José\",\r\n" + 
				"        \"lastName\": \"Silva\",\r\n" + 
				"        \"email\": \"teste4@teste.com\",\r\n" + 
				"        \"phones\": [\r\n" + 
				"            {\r\n" + 
				"                \"number\": 978782002,\r\n" + 
				"                \"area_code\": 81,\r\n" + 
				"                \"country_code\": \"+55\"\r\n" + 
				"            }\r\n" + 
				"        ]\r\n" + 
				"    }";
		
		String expectedResult = "{\"message\":\"Missing Fields\",\"errorCode\":\"001\"}";
		
		mockMvc.perform(post("/signup")
				.content(postJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(expectedResult));

	}
	
	@Test
	public void test05_EndpointSignup_EmailJaExistente() throws Exception {
		String postJson = "{\r\n" + 
				"        \"firstName\": \"José\",\r\n" + 
				"        \"lastName\": \"Silva\",\r\n" + 
				"        \"email\": \"teste5@teste.com\",\r\n" + 
				"        \"password\": \"senha123\",\r\n" + 
				"        \"phones\": [\r\n" + 
				"            {\r\n" + 
				"                \"number\": 978782002,\r\n" + 
				"                \"area_code\": 81,\r\n" + 
				"                \"country_code\": \"+55\"\r\n" + 
				"            }\r\n" + 
				"        ]\r\n" + 
				"    }";
		
		mockMvc.perform(post("/signup")
				.content(postJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		
		String expectedResult = "{\"message\":\"E-mail already exists\",\"errorCode\":\"002\"}";
		
		mockMvc.perform(post("/signup")
				.content(postJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(expectedResult));

	}
	
    @Test
    public void test06_Repository_BuscarUsuarioTesteCadastradoNoBanco() {

        List<Usuario> users = repository.findAllByEmail("teste@teste.com");
        Assert.assertEquals("José", users.get(0).getFirstName());
    }
}