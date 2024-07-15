package br.com.brunodias.gestao_vagas.modules.company.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobDTO {

   // @Schema(example = "Vaga para pessoa desenvolvedora júnior", requiredMode = RequiredMode.REQUIRED)
    private String description;

   // @Schema(example = "GymPass, Plano de saúde", requiredMode = RequiredMode.REQUIRED)
    private String benefits;

   // @Schema(example = "JUNIOR", requiredMode = RequiredMode.REQUIRED)
    private String level;
}