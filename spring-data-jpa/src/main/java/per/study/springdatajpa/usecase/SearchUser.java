package per.study.springdatajpa.usecase;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import per.study.springdatajpa.entity.User;
import per.study.springdatajpa.repository.UserRepository;

@Service
public class SearchUser {

    private final UserRepository userRepository;

    public SearchUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute() {
        for (User user: userRepository.findAll(makeSpecification())) {
            System.out.println(user.getName());
        }
    }

    public Specification<User> makeSpecification() {
        return ((root, query, criteriaBuilder) ->
            criteriaBuilder.like(root.get("username").as(String.class), "%duho%")
        );
    }

}
