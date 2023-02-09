package per.study.springboot.jpa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import per.study.springboot.jpa.entity.Member;
import per.study.springboot.jpa.entity.Team;

/**
 * Application에서 SQL을 직접 다룰 때 문제점
 * - 진정한 의미의 계층 분할이 어렵다
 * - 엔티티를 신뢰할 수 없다
 * - SQL에 의존적인 개발을 피하기 어렵다.
 */
public class MemberDao {
    // Team 추가 시 team 관련 정보는 없어서 member.getTeam()을 하는 경우 null 값 반환
    // => findWithTeam 메서드 사용
    public Member find(String id) {
        // 1. 회원 조회용 SQL 작성
        String sql = "SELECT MEMBER_ID, NAME, TEL FROM WHERE M WHERE MEMBER_ID = ?"; // TEL 추가

        try (Connection con = DriverManager.getConnection("");
             PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setString(1, id);

            return mappingObject(pstmt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Member mappingObject(PreparedStatement stmt) {
        // 2. JDBC API를 사용해서 SQL을 실행
        try (ResultSet rs = stmt.getResultSet()) {
            String memberId = rs.getString("MEMBER_ID");
            String name = rs.getString("NAME");
            // TEL 필드 추가로 인한 조회 로직 변경
            String tel = rs.getString("TEL");

            // 3. 조회 결과를 Member 객체로 매핑
            Member member = new Member();
            member.setMemberId(memberId);
            member.setMemberId(name);
            // TEL 필드 추가로 인한 조회 로직 변경
            member.setTel(tel);

            return member;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public Member findWithTeam(String id) {
        String sql = "SELECT M.MEMBER_ID, M.NAME, M.TEL, T.TEAM_ID, T.TEAM_NAME " +
                "FROM MEMBER M " +
                "JOIN TEAM T ON M.TEAM_ID = T.TEAM_ID"; // TEAM 추가

        try (Connection con = DriverManager.getConnection("");
             PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setString(1, id);

            return mappingObjectWithTeam(pstmt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Member mappingObjectWithTeam(PreparedStatement stmt) {
        // 2. JDBC API를 사용해서 SQL을 실행
        try (ResultSet rs = stmt.getResultSet()) {
            String memberId = rs.getString("M.MEMBER_ID");
            String name = rs.getString("M.NAME");
            String tel = rs.getString("M.TEL");
            // TEAM 연관 관계 추가로 인한 조회 로직 변경
            String teamId = rs.getString("T.TEAM_ID");
            String teamName = rs.getString("T.TEAM_NAME");

            Team team = new Team();
            team.setTeamId(teamId);
            team.setTeamName(teamName);


            // 3. 조회 결과를 Member 객체로 매핑
            Member member = new Member();
            member.setMemberId(memberId);
            member.setMemberId(name);
            member.setTel(tel);
            // TEAM과 연관 관계 추가로 인한 조회 로직 변경
            member.setTeam(team);

            return member;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void save(Member member) {
        // 1. 회원 등록용 SQL 작성
        String sql = "INSERT INTO MEMBER(MEMBER_ID, NAME, TEL) VALUES(?, ?, ?)"; // TEL 추가

        try (Connection con = DriverManager.getConnection("");
             PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            // 2. 회원 객체의 값을 꺼내서 등록 SQL에 전달
            pstmt.setString(1, member.getMemberId());
            pstmt.setString(2, member.getName());
            // TEL 필드 추가로 인한 삽입 로직 변경
            pstmt.setString(3, member.getTel());

            // 3. JDBC API를 사용해서 SQL 실행
            pstmt.executeUpdate(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Member member) {
        // Member 필드에 tel이 추가되었지만 update logic 미변경으로 인한 장애 발생
    }
}
