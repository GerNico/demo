package com.example.kafka.demo;

import java.util.StringJoiner;

/**
 * Class DtoExample that responsible for
 *
 * @author Mykola.Matsishin <br> created on 29 November 2020
 * @since 5.0
 */
public class DtoExample {
	private String msg;
	private String name;

	public DtoExample(String msg, String name) {
		this.msg = msg;
		this.name = name;
	}

	public String getMsg() {
		return msg;
	}

	public String getName() {
		return name;
	}

	public DtoExample() {
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", DtoExample.class.getSimpleName() + "[", "]")
				.add("msg='" + msg + "'")
				.add("name='" + name + "'")
				.toString();
	}
}
