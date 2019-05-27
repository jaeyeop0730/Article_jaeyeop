package org.njy.book.chap11;

import java.util.List;

public interface MemberDao {

	
	Member selectByEmail(String email);

	
	void insert(Member member);

	
	//void update(Member member);


	List<Member> selectAll(int offset, int count);
	
	
	int countAll();
	
	Member selectByLogin(String email, String password);
	
	
	int changePassword(String memberId, String currentPassword,
			String newPassword);
}