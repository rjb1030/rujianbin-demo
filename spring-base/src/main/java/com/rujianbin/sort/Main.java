package com.rujianbin.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Main {

	public static void main(String[] args){
		Student a = new Student();
		a.setAddress("aaa");
		a.setAge(3);
		a.setName("rujianbin");
		Student b = new Student();
		b.setAddress("bbbb");
		b.setAge(5);
		b.setName("zhangsan");
		Student c = new Student();
		c.setAddress("ccc");
		c.setAge(6);
		c.setName("lisi");
		
		List<Student> list = new ArrayList<Student>();
		list.add(a);
		list.add(b);
		list.add(c);
		
		Collections.sort(list, new Comparator<Student>(){
			public int compare(Student a,Student b){
				return b.getName().compareTo(a.getName());
			}
		});
		
		for(Student s:list){
			System.out.println(s.getName());
		}
	}
}
