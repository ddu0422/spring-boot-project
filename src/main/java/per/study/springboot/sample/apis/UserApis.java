package per.study.springboot.sample.apis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import per.study.springboot.sample.usecase.SearchUser;

@RestController
public class UserApis {

    private final SearchUser searchUser;

    public UserApis(SearchUser searchUser) {
        this.searchUser = searchUser;
    }

    @GetMapping("/users")
    public void allUsers() {
        searchUser.execute();
    }

}
