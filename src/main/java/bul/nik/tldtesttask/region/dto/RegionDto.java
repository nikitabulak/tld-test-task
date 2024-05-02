package bul.nik.tldtesttask.region.dto;

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
    private String name;
    private String shortName;
}
