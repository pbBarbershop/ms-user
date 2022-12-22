package br.com.pb.barbershop.msuser.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageableDTO {

    private Integer numberOfElements;

    private Long totalElements;

    private Integer totalPages;

    private List<UserResponseGetAll> usersResponse;
}
