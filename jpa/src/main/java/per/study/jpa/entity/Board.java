package per.study.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
/**
 * allocationSize값이 50이면 한 번에 시퀀스 값을 증가시키고 메모리에 시퀀스 값을 할당
 * 51이 되면 시퀀스 값을 100으로 증가시킨 다음 51~100까지 메모리에서 식별자를 할당
 * 여러 JVM이 동시에 동작해도 기본 키 값이 충동하지 않는 장점
 * 데이터베이스에 직접 접근해서 데이터를 등록할 때 시퀀스 값이 한번에 많이 증가 한다는 점
 */
@SequenceGenerator(
    name = "BOARD_SEQ_GENERATOR",
    sequenceName = "BOARD_SEQ", // 매핑할 데이터베이스 시퀀스 이름
    initialValue = 1,
    allocationSize = 1
)
public class Board {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "BOARD_SEQ_GENERATOR"
    )
    private Long id;

    public Long getId() {
        return id;
    }
}
