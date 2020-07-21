package com.mperozo.joingroup.api.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterRequest {
	
	@NotBlank
    @Size(min = 3, max = 100)
    private String email;
    
	@NotBlank
    @Size(max = 150)
    private String nome;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String senha;
}
