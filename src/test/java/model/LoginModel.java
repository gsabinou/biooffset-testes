package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class LoginModel {
    @Expose
    private String cnpj;
    @Expose
    private String senha;
}
