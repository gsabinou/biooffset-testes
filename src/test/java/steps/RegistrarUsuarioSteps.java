package steps;

import com.networknt.schema.ValidationMessage;
import io.cucumber.java.PendingException;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import model.ErrorMessageModel;
import org.junit.Assert;
import services.RegistrarUsuarioService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RegistrarUsuarioSteps {

    RegistrarUsuarioService service = new RegistrarUsuarioService();

    @Dado("que eu insira os seguintes dados do usuário:")
    public void queEuInsiraOsSeguintesDadosDoUsuário(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            service.setFieldsUsuario(columns.get("campo"), columns.get("valor"));
        }
    }

    @Quando("eu enviar uma requisição para o endpoint {string}")
    public void euEnviarUmaRequisiçãoParaOEndpoint(String endpoint) {
        service.createUsuario(endpoint);
    }

    @Então("o status code da resposta deve ser {int}")
    public void oStatusCodeDaRespostaDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, service.response.statusCode());
    }

    @E("a resposta de mensagem de erro deve ser {string}")
    public void aRespostaDeMensagemDeErroDeveSer(String cnpj) {
        ErrorMessageModel errorMessage = service.gson.fromJson(
                service.response.jsonPath().prettify(), ErrorMessageModel.class);

        Assert.assertEquals(cnpj, errorMessage.getCnpj());

    }

    @E("que o arquivo de registro esperado seja o {string}")
    public void queOArquivoDeRegistroEsperadoSejaO(String contrato) throws IOException {
        service.setContrato(contrato);
    }

    @Então("a resposta da requisição deve estar em conformidade com o contrato selecionado")
    public void aRespostaDaRequisiçãoDeveEstarEmConformidadeComOContratoSelecionado() throws IOException {
        Set<ValidationMessage> validateResponse = service.validateResponseAgainstSchema();

        Assert.assertTrue("O contrato está errado. Erros encontrados: " + validateResponse,
                validateResponse.isEmpty());
    }
}
