package com.example.mongodb.member;

public interface MemberDAO {
	MemberDTO login(String userid, String passwd);
	void join(MemberDTO dto);
}