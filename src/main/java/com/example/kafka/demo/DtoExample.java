package com.example.kafka.demo;

import java.util.StringJoiner;
import java.util.UUID;

/**
 * Class DtoExample that responsible for
 *
 * @author Mykola.Matsishin <br> created on 29 November 2020
 * @since 5.0
 */
public class DtoExample {
	private String msg;
	private String name;
	private Integer num;
	private UUID id;

	public DtoExample() {
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", DtoExample.class.getSimpleName() + "[", "]")
				.add("msg='" + msg + "'")
				.add("name='" + name + "'")
				.add("num=" + num)
				.add("id=" + id)
				.toString();
	}
}
