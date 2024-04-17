package asik.propensik.trainnas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import asik.propensik.trainnas.dto.UserMapper;
import asik.propensik.trainnas.dto.request.CreateUserRequestDTO;
import asik.propensik.trainnas.model.User;
import asik.propensik.trainnas.service.UserService;

import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class UserController {
    @Autowired
    UserMapper userMapper;
    
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("tipeRole", "All");
        return "user-list"; // Nama file HTML untuk menampilkan daftar user
    }

    @GetMapping("/users/add-trainee")
    public String addTraineeFormPage(Model model) {
        var userDTO = new CreateUserRequestDTO();
        model.addAttribute("userDTO", userDTO);
        return "trainee/form-registrasi-trainee";
    }

    @PostMapping("/users/add-trainee")
    public String addTraineeSubmit(@ModelAttribute CreateUserRequestDTO userDTO, Model model) {
        userDTO.setRole("Trainee");
        var user = userMapper.createUserRequestDTOToUser(userDTO);
        userService.addUser(user);
        return "trainee/success-create-trainee";
    }

    @GetMapping("/users/add-trainer")
    public String addTrainerFormPage(Model model) {
        var userDTO = new CreateUserRequestDTO();
        model.addAttribute("userDTO", userDTO);
        return "trainer/form-registrasi-trainer";
    }

    @PostMapping("/users/add-trainer")
    public String addTrainerSubmit(@ModelAttribute CreateUserRequestDTO userDTO, Model model) {
        userDTO.setRole("Trainer");
        var user = userMapper.createUserRequestDTOToUser(userDTO);
        userService.addUser(user);
        return "trainer/success-create-trainer";
    }

    @GetMapping("/users/add-community")
    public String addCommunityFormPage(Model model) {
        var userDTO = new CreateUserRequestDTO();
        model.addAttribute("userDTO", userDTO);
        return "community/form-registrasi-community";    
    }

    @PostMapping("/users/add-community")
    public String addCommunitySubmit(@ModelAttribute CreateUserRequestDTO userDTO, Model model) {
        userDTO.setRole("Community");
        var user = userMapper.createUserRequestDTOToUser(userDTO);
        userService.addUser(user);
        return "community/success-create-community";
    }
    
    @GetMapping("/users/profile-update")
    public String formUpdateProfile(Model model, @RequestParam("id") Long idUser) {
        var user = userService.getUserById(idUser);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/users/profile-update")
    public String formUpdateProfile(Model model,@RequestParam("id") Long id, @RequestParam("school") String school, @RequestParam("phoneNumber") String phoneNumber) {
        userService.setPhoneNumber(id, phoneNumber);
        userService.setSchool(id, school);        
        return "home";
    }
    
    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-detail"; // Nama file HTML untuk menampilkan detail user
    }

    @GetMapping("/users/detail")
    public String detailUser(@RequestParam("id") Long idUser, Model model){
        var user = userService.getUserById(idUser);
        model.addAttribute("user", user);
        return "user-detail";
    }
    
    @GetMapping("/users/user-update")
    public String formUpdateUser(Model model, @RequestParam("id") Long idUser) {
        var user = userService.getUserById(idUser);
        model.addAttribute("user", user);
        return "user-update";
    }

    @PostMapping("/users/user-update")
    public String formUpdateUser(@RequestParam("id") Long id, Model model, @RequestParam("role") String role, @RequestParam("isActive") Boolean isActive){
        userService.updateUser(id,role);
        userService.activatedUser(id, isActive);
        return "redirect:/users";

    }

    @GetMapping("users/search-user")
    public String searchUser(@RequestParam("searchQuery") String searchQuery, Model model) {
        List<User> listUser;
        listUser = userService.searchUserByNama(searchQuery);
        model.addAttribute("users", listUser);
        return "user-list";
    }

    @GetMapping("users/filter-user")
    public String filterUser(@RequestParam("sortType") String sortType, Model model) {
        System.out.println("masuk filter");
        if ("All".equals(sortType)) {
            List<User> listUser = userService.getAllUsers();
            model.addAttribute("users", listUser);
            model.addAttribute("tipeRole", "All");
        } else if ("Trainer".equals(sortType)) {
            List<User> listUser = userService.getUserTrainer();
            model.addAttribute("users", listUser);
            model.addAttribute("tipeRole", "Trainer");
        } else if ("Trainee".equals(sortType)) {
            List<User> listUser = userService.getUserTrainee();
            model.addAttribute("users", listUser);
            model.addAttribute("tipeRole", "Trainee");
        } else if ("Community".equals(sortType)) {
            List<User> listUser = userService.getUserCommunity();
            model.addAttribute("users", listUser);
            model.addAttribute("tipeRole", "Community");
    }
        return "user-list";
    }
}
    
