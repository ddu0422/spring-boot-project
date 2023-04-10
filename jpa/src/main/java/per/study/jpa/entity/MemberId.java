package per.study.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * <직접할당>
 * 기본 키를 애플리케이션에서 직접 할당
 * <자동생성>
 * IDENTITY: 기본 키 생성을 데이터베이스에 위임
 * SEQUENCE: 데이터베이스 시퀀스를 사용해서 기본 키를 할당
 * TABLE: 키 생성 테이블을 사용
 */
@Entity
public class MemberId {

    @Id
    @Column(name = "ID")
    /**
     * IDENTITY: MySQL, PostgreSQL, SQL Server, DB2
     * - 데이터베이스에 값을 저장할 때 ID 컬럼을 비워두면 "데이터베이스가 순서대로 값을 채워줌"
     * - JPA는 기본 키 값을 얻어오기 위해 "데이터베이스를 추가 조회"
     * - JDBC3에 추가된 Statement.getGeneratedKeys()를 사용하면 데이터 저장과 동시에 기본 키 값을 얻어옴.
     *   - Hibernate는 이 메서드를 사용해서 데이터베이스와 한 번만 통신
     * - IDETITY 식별자 생성 전략은 데이터베이스에 저장해야 식별자를 구할 수 있으므로 em.persist()를 호출하는 즉시
     *   INSERT SQL이 데이터베이스에 전달 / 이 전략은 트랜잭션을 지원하는 쓰기 지연이 동작하지 않음
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }
}
