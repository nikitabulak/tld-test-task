package bul.nik.tldtesttask.region.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RegionDto {
    private long id;
    @NotBlank(message = "region name is mandatory and can not be empty")
    @Size(min = 3, max = 50, message = "name size should be between 3 an 50 characters")
    private String name;
    @NotBlank(message = "region short name is mandatory and can not be empty")
    @Size(min = 2, max = 10, message = "short name size should be between 3 an 50 characters")
    private String shortName;
}
