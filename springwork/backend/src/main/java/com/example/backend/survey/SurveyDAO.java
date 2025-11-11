package com.example.backend.survey;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SurveyDAO {
	Map<String, Object> view(@Param("survey_idx") int survey_idx);
	void insert(Map<String, Object> map);
	List<Map<String, Object>> summary(@Param("survey_idx") int survey_idx);
}