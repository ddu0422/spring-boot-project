package per.study.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
/**
 * TABLE 전략은 값을 조회하면서 SELECT 쿼리를 사용하고 다음 값으로 증가시키기 위해 UPDATE 쿼리를 사용
 * SEQUENCE 전략과 비교해서 데이터베이스와 한 번 더 통신하는 단점
 * 최적화하려면 @TableGenerator.allocationSize를 사용
 */
@TableGenerator(
    name = "POST_SEQ_GENERATOR",
    table = "MY_SEQUENCES",
    pkColumnValue = "POST_SEQ",
    allocationSize = 1
)
public class Post {

    @Id
    @GeneratedValue(
        strategy = GenerationType.TABLE,
        generator = "POST_SEQ_GENERATOR"
    )
    private Long id;

    public Long getId() {
        return id;
    }
}
