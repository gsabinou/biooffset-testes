package hook;

import io.cucumber.java.After;
import services.RegistrarUsuarioService;

public class Hook {

    RegistrarUsuarioService usuarioService = new RegistrarUsuarioService();

    @After
    public void tearDownAfterClass() {
        usuarioService.deleteEmpresa("/empresas/{id}");
    }
}
