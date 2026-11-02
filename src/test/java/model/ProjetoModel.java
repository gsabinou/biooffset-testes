package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class ProjetoModel {
    @Expose
    private Long empresaDesenvolvedora;
    @Expose
    private String nome;
    @Expose
    private String descricao;
    @Expose
    private String bioma;
    @Expose
    private Double latitude;
    @Expose
    private Double longitude;
    @Expose
    private String status;

}
