package per.study.springboot.sample.usecase;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import per.study.springboot.sample.domain.User;
import per.study.springboot.sample.repository.UserRepository;

@Service
public class SearchUser {

    private final UserRepository userRepository;

    public SearchUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute() {
        for (User user: userRepository.findAll(makeSpecification())) {
            System.out.println(user.getUsername());
        }
    }

    public Specification<User> makeSpecification() {
        return ((root, query, criteriaBuilder) ->
            criteriaBuilder.like(root.get("username").as(String.class), "%duho%")
        );
    }

}
