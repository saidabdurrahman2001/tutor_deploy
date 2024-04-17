// package asik.propensik.trainnas.service;

// import org.springframework.stereotype.Service;
// import asik.propensik.trainnas.dto.TrainerDto;
// import asik.propensik.trainnas.dto.TrainerMapper;
// import asik.propensik.trainnas.model.TrainerModel;
// import asik.propensik.trainnas.repository.TrainerDb;
// import lombok.AllArgsConstructor;

// @Service
// @AllArgsConstructor
// public class TrainerServiceImpl implements TrainerService{

//    private TrainerDb trainerDb;

//    @Override
//    public TrainerDto createTrainer(TrainerDto trainerDto) {
//       TrainerModel trainerModel = TrainerMapper.mapToTrainerModel(trainerDto);
//       TrainerModel savedTrainerModel = trainerDb.save(trainerModel);
//       return TrainerMapper.mapToTrainerDto(savedTrainerModel);
//    }
   
// }
