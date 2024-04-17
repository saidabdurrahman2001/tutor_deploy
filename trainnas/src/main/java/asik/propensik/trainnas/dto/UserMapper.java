package asik.propensik.trainnas.dto;

import org.mapstruct.Mapper;

import asik.propensik.trainnas.dto.request.CreateUserRequestDTO;
import asik.propensik.trainnas.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
   User createUserRequestDTOToUser(CreateUserRequestDTO createUserRequestDTO);
}