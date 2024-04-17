package asik.propensik.trainnas.dto;

import org.hibernate.sql.Update;
import org.mapstruct.Mapper;

import asik.propensik.trainnas.dto.request.CreatePelatihanRequestDTO;
import asik.propensik.trainnas.dto.request.UpdatePelatihanRequestDTO;
import asik.propensik.trainnas.model.Pelatihan;

@Mapper(componentModel = "spring")
public interface PelatihanMapper {
    Pelatihan createPelatihanRequestDTOToPelatihan(CreatePelatihanRequestDTO createPelatihanRequestDTO);

    Pelatihan updatePelatihanRequestDTOToPelatihan(UpdatePelatihanRequestDTO updatePelatihanRequestDTO);
}
