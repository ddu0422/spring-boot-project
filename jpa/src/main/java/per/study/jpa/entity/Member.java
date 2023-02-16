package per.study.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // 이 클래스를 테이블과 매핑한다고 JPA에게 알려줌
@Table(name = "JPA_MEMBER") // 엔티티 클래스에 매핑할 테이블 정보를 알려줌, 생략하면 엔티티 이름을 테이블 이름으로 매핑
public class Member {

    @Id // 테이블의 기본 키에 매핑 (식별자 필드)
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME") // name 속성을 사용해서 필드를 name 속성 값에 해당하는 컬럼에 매핑
    private String username;

    // 매핑 정보가 없는 필드 -> 매핑 어노테이션을 생략하면 필드명을 사용해서 컬럼명으로 매핑
    private Integer age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
