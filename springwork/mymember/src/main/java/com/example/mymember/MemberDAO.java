package com.example.mymember;

import java.util.List;

public interface MemberDAO {
	List<MemberDTO> list();
	
	void insert(MemberDTO dto);
	
	MemberDTO detail(String userid);
	
	void delete(String userid);
	
	void update(MemberDTO dto);
	
	boolean check_passwd(String userid, String passwd);
}
