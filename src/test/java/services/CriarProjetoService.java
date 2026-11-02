package services;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.LoginModel;
import model.ProjetoModel;

import static io.restassured.RestAssured.given;

public class CriarProjetoService {

    ProjetoModel projetoModel = new ProjetoModel();

    LoginModel loginModel = new LoginModel();

    public Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public Response response;

    public static String token;

    String baseUrl = "http://localhost:8080";

    public void setFieldsLogin(String field, String value) {
        switch (field) {
            case "cnpj" -> loginModel.setCnpj(value);
            case "senha" -> loginModel.setSenha(value);
            default -> throw new IllegalArgumentException("Unexpected field: " + field);
        }
    }

    public void setFieldsProjeto(String field, String value) {
        switch (field) {
            case "empresaDesenvolvedora" -> projetoModel.setEmpresaDesenvolvedora(Long.valueOf(value));
            case "nome" -> projetoModel.setNome(value);
            case "descricao" -> projetoModel.setDescricao(value);
            case "bioma" -> projetoModel.setBioma(value);
            case "latitude" -> projetoModel.setLatitude(Double.valueOf(value));
            case "longitude" -> projetoModel.setLongitude(Double.valueOf(value));
            case "status" -> projetoModel.setStatus(value);
            default -> throw new IllegalStateException("Unexpected field: " + field);
        }
    }

    public void createProjeto(String endpoint) {
        String url = baseUrl + endpoint;
        String body = gson.toJson(projetoModel);

        response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(body)
                .when()
                .post(url)
                .then()
                .extract().response();

        RegistrarUsuarioService.response = response;
    }

    public void createLogin(String endpoint) {
        String url = baseUrl + endpoint;
        String body = gson.toJson(loginModel);

        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(body)
                .when()
                .post(url)
                .then()
                .extract().response();

        if (response.statusCode() == 200) {
            token = response.jsonPath().getString("token");
        }

        RegistrarUsuarioService.response = response;
    }

}
