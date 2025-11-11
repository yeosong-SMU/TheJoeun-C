package com.example.csrf;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
	@Id
	private String userid;
	
	//raw(32)와 byte[] 매핑(바이트배열은 기본적으로 blob 자료형)
	//자료형 지정. 지정안하며 blob으로 지정됨.
	@Column(name = "pwd", nullable = false, columnDefinition = "raw(32)")
	private byte[] pwd;
	
	private String name;
}