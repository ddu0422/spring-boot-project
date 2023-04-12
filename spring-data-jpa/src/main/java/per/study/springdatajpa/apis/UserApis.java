package per.study.springdatajpa.apis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import per.study.springdatajpa.entity.User;
import per.study.springdatajpa.repository.UserRepository;

@RestController
public class UserApis {

    private final UserRepository userRepository;

    public UserApis(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/")
    public String index() {
        User user = new User();

        userRepository.save(user);

        return String.valueOf(user.getId());
    }
}
