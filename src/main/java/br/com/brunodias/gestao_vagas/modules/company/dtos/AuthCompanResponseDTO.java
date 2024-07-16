package br.com.brunodias.gestao_vagas.modules.company.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthCompanResponseDTO {
    private String access_token;
    private Long expires_in;
    private List<String> roles;

}


