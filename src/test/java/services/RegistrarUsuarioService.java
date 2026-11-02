package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.UsuarioModel;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class RegistrarUsuarioService {

    UsuarioModel usuarioModel = new UsuarioModel();

    public Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public static Response response;

    public static Integer idEmpresa;

    String baseUrl = "http://localhost:8080";

    String schemasPath = "src/test/resources/schemas/";

    public static JSONObject jsonSchema;

    private final ObjectMapper mapper = new ObjectMapper();


    public void setFieldsUsuario(String field, String value) {
        switch (field) {
            case "razaoSocial" -> usuarioModel.setRazaoSocial(value);
            case "cnpj" -> usuarioModel.setCnpj(value);
            case "senha" -> usuarioModel.setSenha(value);
            case "role" -> usuarioModel.setRole(Integer.parseInt(value));
            default -> throw new IllegalStateException("Unexpected value: " + field);
        }

    }

    public void createUsuario(String endpoint) {
        String url = baseUrl + endpoint;
        String body = gson.toJson(usuarioModel);

        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(body)
                .when()
                .post(url)
                .then()
                .extract().response();

        if (response.statusCode() == 200 || response.statusCode() == 201) {
            try {
                idEmpresa = response.jsonPath().getInt("id");
            } catch (Exception e) {
                idEmpresa = null;
            }
        }
    }

    public void deleteEmpresa(String endpoint) {
        if (idEmpresa != null) {
            String url = baseUrl + endpoint.replace("{id}", String.valueOf(idEmpresa));

            var request = given().accept(ContentType.JSON);
            if (CriarProjetoService.token != null && !CriarProjetoService.token.isBlank()) {
                request.header("Authorization", "Bearer " + CriarProjetoService.token);
            }

            request.when()
                    .delete(url)
                    .then()
                    .extract().response();

            idEmpresa = null;
        }
    }

    private JSONObject loadJsonFromFile(String filePath) throws IOException {
        try (InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
            JSONTokener tokener = new JSONTokener(inputStream);
            return new JSONObject(tokener);
        }
    }

    public void setContrato(String nomeContrato) throws IOException {
        switch (nomeContrato) {
            case "Registro de usuário bem-sucedido" ->
                    jsonSchema = loadJsonFromFile(schemasPath + "registrar-usuario-bem-sucedido.json");
            case "Login bem-sucedido" ->
                jsonSchema = loadJsonFromFile(schemasPath + "login-bem-sucedido.json");
            case "Projeto criado com sucesso" ->
                jsonSchema = loadJsonFromFile(schemasPath + "projeto-criado-com-sucesso.json");
            default -> throw new IllegalStateException("Unexpected contract: " + nomeContrato);

        }
    }

    public Set<ValidationMessage> validateResponseAgainstSchema() throws IOException {

        // obter o corpo da resposta como String e converter para JSONObject
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());

        // configurar o JsonSchemaFactory e criar o JsonSchema
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema schema = schemaFactory.getSchema(jsonSchema.toString());

        // converter o JSON da resposta para JsonNode
        JsonNode jsonResponseNode = mapper.readTree(jsonResponse.toString());

        // validar o JSON da resposta contra o schema
        Set<ValidationMessage> schemaValidationErrors = schema.validate(jsonResponseNode);

        return schemaValidationErrors;

    }

}
