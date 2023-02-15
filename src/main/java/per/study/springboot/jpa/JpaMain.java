package per.study.springboot.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import per.study.springboot.jpa.entity.JpaMember;

public class JpaMain {
    public static void main(String[] args) {
        // persistence.xml 설정 정보를 읽어서 JPA를 동작시키기 위한 기반 객체를 만들고 JPA 구현체에 따라서 커넥션 풀 생성
        // 엔티티 매니터 팩토리를 생성하는 비용은 크므로 애플리케이션 전체에서 딱 한 번만 생성하고 공유해서 사용해야 함.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

        // 엔티티 매니저를 사용해서 엔티티를 데이터베이스에 등록/수정/삭제/조회할 수 있음
        EntityManager em = emf.createEntityManager();

        // JPA를 사용하면 항상 트랜잭션 안에서 데이터를 변경해야 함.
        // 트랜잭션 없이 데이터를 변경하면 예외가 발생
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            logic(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }

    private static void logic(EntityManager em) {
        String id = "id1";
        JpaMember member = new JpaMember();

        member.setId(id);
        member.setUsername("두호");
        member.setAge(29);

        // 엔티티 저장시 엔티티 매니저의 persist() 메서드에 저장할 엔티티를 넘겨줌.
        em.persist(member);
        // 엔티티의 값만 변경하면 UPDATE SQL을 생성해서 데이터베이스에 값을 변경함.
        member.setAge(30);

        // 단건 조회
        // find 메서드는 조회활 엔티티 타입과 @Id로 데이터베이스 테이블의 기본 키와 매핑한 식별자 값으로 엔티티 하나를 조회하는 단순한 조회 메서드
        JpaMember findMember = em.find(JpaMember.class, id);
        System.out.println("findMember = " + findMember.getUsername() + ", age = " + findMember.getAge());

        // 목록 조회
        // JPQL: 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색해야함.
        // SQL: 데이터베이스 테이블을 대상으로 쿼리함.
        List<JpaMember> members = em.createQuery("select m from JpaMember m", JpaMember.class).getResultList();
        System.out.println("members.size = " + members.size());

        // 삭제
        // 엔티티를 삭제하려면 엔티티 매니저의 remove() 메서드에 삭제하려는 엔티티를 넘겨줌.
        em.remove(member);
    }
}
