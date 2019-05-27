package org.njy.book.chap11;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("memberDao")
public class MemberDaoImplUsingSpringJdbc implements MemberDao{
	
	static final String INSERT = "INSERT member(email, password, name) VALUES(?, sha2(?,256), ?)";
	
	static final String SELECT_ALL = "SELECT memberId, email, name, left(cdate,19) cdate FROM member ORDER BY memberId desc LIMIT ?,?";

	static final String COUNT_ALL = "SELECT count(memberId) count FROM member";
	
	static final String SELECT_BY_LOGIN = "SELECT memberId, email, password, name FROM member WHERE (email,password) = (?,sha2(?,256))";
	
	static final String CHANGE_PASSWORD = "UPDATE member SET password=sha2(?,256) WHERE (memberId, password)=(?, sha2(?,256))";
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	final RowMapper<Member> memberRowMapper = new BeanPropertyRowMapper<>(Member.class);

	@Override
	public Member selectByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Member member) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(INSERT, member.getEmail(), member.getPassword(), member.getName());
	}

	/*
	@Override
	public void update(Member member) {
		// TODO Auto-generated method stub
		
	}
	*/

	@Override
	public List<Member> selectAll(int offset, int count) {
		// TODO Auto-generated method stub
		return jdbcTemplate.query(SELECT_ALL, memberRowMapper, offset, count);
	}

	@Override
	public int countAll() {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject(COUNT_ALL, Integer.class);
	}

	@Override
	public Member selectByLogin(String email, String password) {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject(SELECT_BY_LOGIN, memberRowMapper,
				email, password);
	}
	
	@Override
	public int changePassword(String memberId, String currentPassword, String newPassword) {
		return jdbcTemplate.update(CHANGE_PASSWORD, newPassword, memberId, currentPassword);
	}
}