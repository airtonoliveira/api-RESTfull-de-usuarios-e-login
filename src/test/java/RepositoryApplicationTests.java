import br.com.airton.model.Phone;
import br.com.airton.model.Usuario;
import br.com.airton.repository.UsuarioRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=br.com.airton.Application.class)
public class RepositoryApplicationTests {

    @Autowired
    UsuarioRepository repository;

    @Test
    public void testRepository() {
        repository.save(new Usuario("Airton", "Oliveira", "airtonluis.oliveira@gmail.com", "123456", new HashSet<>(Arrays.asList(new Phone(998097240L, 81L, "+55"))), new Date()));
        repository.save(new Usuario("Teste", "Da Silva", "testea@gmail.com", "123456", new HashSet<>(Arrays.asList(new Phone(987874520L, 81L, "+55"))), new Date()));

        final List<Usuario> todos = repository.findAll();
        System.out.println("Lista:" + todos.toString());
        Assert.assertEquals(2, todos.size());

        List<Usuario> users = repository.findAllByEmail("airtonluis.oliveira@gmail.com");
        Assert.assertEquals("Airton", users.get(0).getFirstName());
    }
}
