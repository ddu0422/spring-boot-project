package per.study.jpa;

import per.study.jpa.entity.Board;
import per.study.jpa.entity.Member;
import per.study.jpa.entity.MemberId;
import per.study.jpa.entity.Post;
import per.study.jpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/**
 * ### 영속성 컨텍스트가 엔티티를 관리할 때 장점 ###
 * 1. 1차 캐시 (성능상 이점) => Application Level [REPEATABLE READ] 격리 수준 제공
 *    - 1차 캐시에 엔티티가 있는 경우 1차 캐시에서 데이터 반환
 *    - 1차 캐시에 엔티티가 없는 경우 DB 조회 후 1차 캐시 저장 후 데이터 반환
 * 2. 동일성 보장
 *    - 동일한 인스턴스
 * 3. 트랜잭션을 지원하는 쓰기 지연 (성능상 이점)
 *    - 모아둔 쿼리를 데이터베이스에 한번에 전달해서 성능 최적화
 * 4. 변경 감지
 *    - 스탭샷과 엔티티를 비교해서 변경된 엔티티를 찾은 후 수정 SQL을 쓰기 지연 SQL 저장소에 전달
 * 5. 지연 로딩
 */

/**
 * ### 플러시 ###
 * 영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화
 *
 * 1. em.flush() 호출
 *    - 테스트 or 다른 프레임워크와 JPA를 함께 사용할 때는 제외하고 거의 사용하지 않음.
 * 2. 트랜잭션 커밋 시 플러시 자동 호출
 *    - 트랜잭션을 커밋하기 전에 플러시를 호출해서 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영
 * 3. JPQL 쿼리 실행 시 플러시가 자동 호출
 *    - JPQL이나 Criteria같은 객체지향 쿼리를 호출할 때도 플러시가 실행
 *    - JPQL은 SQL로 변환되어 데이터베이스에서 엔티티를 조회
 *
 * ### 플러시 모드 옵션 ###
 * 1. FlushModeType.AUTO
 *    - 커밋이나 쿼리를 실행할 때 플러시 (기본값) <-- 대부분 그대로 사용
 * 2. FlushModeType.COMMIT
 *    - 커밋할 때만 플러시
 */

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
            // 엔티티 매니저는 데이터 변경 시 트랜잭션을 시작해야 함.
            tx.begin();
//            logic(em);
            testSave(em);
//            queryLogicJoin(em);
//            updateRelation(em);
//            deleteRelation(em);
            // 커밋하는 순간 데이터베이스에 SQL을 보냄
            tx.commit();
            // entity manager에는 team에 member 부분을 변경하지 않았기 때문에 DB에서 읽어서 쓰는 방식으로 예제를 해결함.
            em.clear();
            biDirection(em);
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }

    private static void logic(EntityManager em) {
        String id = "id1";

        // 객체를 생성한 상태 (비영속)
        Member member = new Member();
        member.setId(id);
        member.setUsername("두호");
        member.setAge(29);

        MemberId memberId = new MemberId();
        em.persist(memberId);
        System.out.println("memberId = " + memberId.getId());

        Board board = new Board();
        em.persist(board);
        System.out.println("board.id = " + board.getId());

        Post post = new Post();
        em.persist(post);
        System.out.println("post.id = " + post.getId());

        // 1차 캐시에 저장
        // 객체를 저장한 상태 (영속)
        // 엔티티 저장시 엔티티 매니저의 persist() 메서드에 저장할 엔티티를 넘겨줌.
        em.persist(member);
        // 엔티티의 값만 변경하면 UPDATE SQL을 생성해서 데이터베이스에 값을 변경함.
        member.setAge(30);

        // 1차 캐시에서 조회
        // 단건 조회
        // find 메서드는 조회활 엔티티 타입과 @Id로 데이터베이스 테이블의 기본 키와 매핑한 식별자 값으로 엔티티 하나를 조회하는 단순한 조회 메서드
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember = " + findMember.getUsername() + ", age = " + findMember.getAge());

        // 목록 조회
        // JPQL: 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색해야함.
        // SQL: 데이터베이스 테이블을 대상으로 쿼리함.
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size = " + members.size());

        // 회원 엔티티를 영속성 컨텍스느에서 분리, 준영속 상태
        // em.detach(member);

        // 객체를 삭제한 상태 (삭제)
        // 삭제
        // 엔티티를 삭제하려면 엔티티 매니저의 remove() 메서드에 삭제하려는 엔티티를 넘겨줌.
        em.remove(member);
    }

    public static void testSave(EntityManager em) {
        System.out.println("========== test save =========");

        // 팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        // 회원1 저장
        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1); // 회원 -> 팀 참조
        em.persist(member1);    // 저장

        // 회원2 저장
        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1);
        em.persist(member2);

        Member member = em.find(Member.class, "member1");
        Team team = member.getTeam();
        System.out.println("팀 이름 = " + team.getName());
    }

    private static void queryLogicJoin(EntityManager em) {
        System.out.println("========== query logic join =========");

        String jpql = "select m from Member m join m.team t where t.name=:teamName";

        List<Member> resultList = em.createQuery(jpql, Member.class)
            .setParameter("teamName", "팀1")
            .getResultList();

        for (Member member : resultList) {
            System.out.println("[query] member.username = " + member.getUsername());
        }
    }

    private static void updateRelation(EntityManager em) {
        System.out.println("========== update relation =========");

        // 새로운 팀2
        Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        // 회원1에 새로운 팀2 설정
        Member member = em.find(Member.class, "member1");
        member.setTeam(team2);
    }

    private static void deleteRelation(EntityManager em) {
        System.out.println("========== delete relation =========");

        Member member = em.find(Member.class, "member1");
        member.setTeam(null);
    }

    private static void biDirection(EntityManager em) {
        System.out.println("========== biDirection =========");

        Member member = em.find(Member.class, "member1");
        List<Member> members = member.getTeam().getMembers();
        System.out.println(members.size());

        for (Member a : members) {
            System.out.println("member.username = " + a.getUsername());
        }
    }
}
