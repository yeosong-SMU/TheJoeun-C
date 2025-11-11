package com.example.memo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemoRepository extends JpaRepository<Memo, Long> {
	// 제목 포함 검색 (기존)
	@Query("select m from Memo m where m.title like %:keyword% order by m.createdAt desc")
	List<Memo> findByTitleContaining(@Param("keyword") String keyword);
	
	// 내용 포함 검색 (기존)
	@Query("select m from Memo m where m.content like %:keyword%")
	List<Memo> findByContentContaining(@Param("keyword") String keyword);
	
	// 제목 또는 내용에 키워듣 포함 검색
	@Query("select m from Memo m where m.title like %:keyword% or m.content like %:keyword% order by m.createdAt desc")
	List<Memo> findByTitleOrContent(@Param("keyword") String keyword);
	
	// 특정 날짜 이후 작성된 메모 조회
	@Query("select m from Memo m where m.createdAt >= :fromDate order by m.createdAt desc")
	List<Memo> findByCreatedAtAfter(@Param("fromDate") java.time.LocalDateTime fromDate);

	// 제목이 정확히 일치하는 메모 조회
	@Query("select m from Memo m where m.title = :title")
	List<Memo> findByExactTitle(@Param("title") String title);
	
	// 제목이 특정 단어로 시작하는 메모
	@Query("select m from Memo m where m.title like :prefix% order by m.createdAt desc")
	List<Memo> findByTitleStartingWith(@Param("prefix") String prefix);
}