package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class UsuarioModel {
    @Expose
    private String razaoSocial;
    @Expose
    private String cnpj;
    @Expose
    private String senha;
    @Expose
    private int role;
}
