package per.study.springboot.jpa;

import java.util.ArrayList;
import java.util.List;
import per.study.springboot.jpa.dao.MemberDao;
import per.study.springboot.jpa.entity.Member;

public class Main {
    private static final MemberDao memberDao = new MemberDao();

    public static void main(String[] args) {
        String memberId = "100";
        Member member1 = memberDao.find(memberId);
        Member member2 = memberDao.find(memberId);

        // 같은 데이스베이스 로우에서 조회했지만, 객체 측면에서 볼 때 둘은 다른 인스턴스입니다.
        System.out.println(member1 == member2); // false

        List<Member> members = new ArrayList<>();
        members.add(new Member());

        Member memberCollection1 = members.get(0);
        Member memberCollection2 = members.get(0);

        // 객체를 컬렉션에 보관했다면 동일성 비교에 성공합니다.
        // JPA는 같은 트랜잭션일 때 같은 객체가 조회되는 것을 보장합니다.
        System.out.println(memberCollection1 == memberCollection2);
    }
}
