package com.example.board.model;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {
	String login(MemberDTO dto);
}