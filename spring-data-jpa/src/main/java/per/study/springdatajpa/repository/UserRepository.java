package per.study.springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import per.study.springdatajpa.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
