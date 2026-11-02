package steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import services.CriarProjetoService;
import services.RegistrarUsuarioService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CriarProjetoSteps {

    CriarProjetoService projetoService = new CriarProjetoService();
    RegistrarUsuarioService usuarioService = new RegistrarUsuarioService();

    @Dado("que eu efetue o login com o seguinte usuário:")
    public void queEuEfetueOLoginComOSeguinteUsuário(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            projetoService.setFieldsLogin(columns.get("campo"), columns.get("valor"));
        }
    }

    @E("que o arquivo esperado seja o {string}")
    public void queOArquivoEsperadoSejaO(String contrato) throws IOException {
        usuarioService.setContrato(contrato);
    }

    @Dado("que eu insira os seguintes dados de um projeto:")
    public void queEuInsiraOsSeguintesDadosDeUmProjeto(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            projetoService.setFieldsProjeto(columns.get("campo"), columns.get("valor"));
        }
    }

    @Então("o status code da resposta da requisição deve ser {int}")
    public void oStatusCodeDaRespostaDaRequisiçãoDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, projetoService.response.statusCode());
    }

    @Quando("eu enviar uma requisição ao endpoint {string}")
    public void euEnviarUmaRequisiçãoAoEndpoint(String endpoint) {
        projetoService.createLogin(endpoint);
    }

    @Quando("eu enviar uma requisição direcionada ao endpoint {string}")
    public void euEnviarUmaRequisiçãoDirecionadaAoEndpoint(String endpoint) {
        projetoService.createProjeto(endpoint);
    }
}
