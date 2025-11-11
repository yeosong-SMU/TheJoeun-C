package com.example.memo;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MemoDAO {
	@Select("select * from memo order by idx desc")
	List<MemoDTO> list();
	
	@Insert("insert into memo (idx, writer, memo) values ((select nvl(max(idx)+1, 1) from memo), #{writer}, #{memo})")
	void insert(@Param("writer") String writer, @Param("memo") String memo);
	
	@Select("select * from memo where idx=#{idx}")
	MemoDTO view(@Param("idx") int idx);
	
	@Update("update memo set writer=#{writer}, memo=#{memo} where idx=#{idx}")
	void update(MemoDTO dto);
	
	@Delete("delete from memo where idx=#{idx}")
	void delete(@Param("idx") int idx);
}
