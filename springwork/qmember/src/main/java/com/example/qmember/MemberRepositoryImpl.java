package com.example.qmember;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class MemberRepositoryImpl implements MemberRepositoryCustom {
	// Querydsl 쿼리 객체를 만드는 핵심 클래스
	@Autowired
	JPAQueryFactory qf;
	
	@Override
	public Page<Member> search(MemberSearchDTO cond) {
		// Q클래스 : entity를 기반으로 만든 메타 클래스
		QMember member = QMember.member;
		
		// 조건문을 동적으로 조합할 때 사용하는 객체
		BooleanBuilder where = new BooleanBuilder();
		
		if (hasText(cond.getKeyword())) {
			String kw = cond.getKeyword().toLowerCase();
			where.and(member.username.lower().like("%" + kw + "%").or(member.email.lower().like("%" + kw + "%")));
		}
		
		// null 공백문자열 체크
		if (hasText(cond.getRole())) {
			where.and(member.role.eq(cond.getRole().toUpperCase()));
		}
		
		if (cond.getEnabled() != null) {
			where.and(member.enabled.eq(cond.getEnabled()));
		}
		
		if (cond.getCreatedFrom() != null) {
			where.and(member.createdAt.goe(cond.getCreatedFrom()));
		}
		if (cond.getCreatedTo() != null) {
			where.and(member.createdAt.loe(cond.getCreatedTo()));
		}
		
		Order order = "asc".equalsIgnoreCase(cond.getDir()) ? Order.ASC : Order.DESC;
		OrderSpecifier<?> orderBy = switch (cond.getSort() == null ? "" : cond.getSort()) {
		case "id" -> new OrderSpecifier<>(order, member.id);
		case "username" -> new OrderSpecifier<>(order, member.username);
		default -> new OrderSpecifier<>(order, member.createdAt);
		};
		
		int page = cond.getPage() == null ? 0 : Math.max(0, cond.getPage());
		int size = cond.getSize() == null ? 10 : Math.max(1, cond.getSize());
		
		List<Member> content = qf.selectFrom(member).where(where).orderBy(orderBy, member.id.desc())
				.offset((long) page * size).limit(size).fetch();
		long total = qf.select(member.count()).from(member).where(where).fetchOne();
		
		return new PageImpl<>(content, PageRequest.of(page, size), total);
	}
	
	boolean hasText(String s) {
		return s != null && !s.isBlank();
	}
}