package com.example.qmember;

import org.springframework.data.domain.Page;

//페이징, 검색을 위한 확장기능
public interface MemberRepositoryCustom {
	Page<Member> search(MemberSearchDTO cond);
}