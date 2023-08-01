package per.study.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.util.Date;

/**
 * DDL
 * create: 기존 테이블을 삭제하고 새로 생성             (DROP + CREATE)
 * create-drop: 애플리케이션을 종료할 때 생성한 DDL 제거 (DROP + CREATE + DROP)
 * update: 데이터베이스 테이블과 엔티티 매핑정보 비교해서 변경 사항만 수정
 * validate: 데이터베이스 테이블과 엔티티 매핑정보 비교해서 차이가 있는 경우 경고를 남기고 애플리케이션을 실행하지 않음
 * none: 자동 생성 기능 사용하지 않음
 */
@Entity // 이 클래스를 테이블과 매핑한다고 JPA에게 알려줌
@Table(name = "JPA_MEMBER", // 엔티티 클래스에 매핑할 테이블 정보를 알려줌, 생략하면 엔티티 이름을 테이블 이름으로 매핑
    uniqueConstraints = {
        // 직접 DDL을 만든다면 사용할 이유가 없지만, 애플리케이션 개발자가 엔티티만 보고도 손쉽게 다양한 제약 조건을 파악할 수 있는 장점
        @UniqueConstraint(name = "NAME_AGE_UNIQUE", columnNames = {"NAME", "AGE"})})
public class Member {

    @Id // 테이블의 기본 키에 매핑 (식별자 필드)
    @Column(name = "ID") // ImprovedNamingStrategy인 경우 테이블 명이나 컬럼 명이 생략되면 "자바의 카멜 표기법을 테이블의 언더스코어 표기법"으로 매핑
    private String id;

    @Column(name = "NAME", nullable = false, length = 10) // name 속성을 사용해서 필드를 name 속성 값에 해당하는 컬럼에 매핑
    private String username;

    // 매핑 정보가 없는 필드 -> 매핑 어노테이션을 생략하면 필드명을 사용해서 컬럼명으로 매핑
    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // ZondDateTime은 Temporal 사용 불가
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Lob
    private String description;

    @ManyToOne // 다대일 관계라는 매핑 정보
    @JoinColumn(name = "TEAM_ID") // 외래 키를 매핑할 때 사용 (생략 가능)
    // referencedColumnName: 외래 키가 참조하는 대상 테이블의 컬럼명
    // foreignKey(DDL):      외래 키 제약 조건
    private Team team;

    // 엔티티 객체를 생성할 때 기본 생성자를 사용하므로 필수 (public / protected)
    public Member() {
    }

    public Member(String id, String username) {
        this.id = id;
        this.username = username;
    }

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
