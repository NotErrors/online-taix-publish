package com.yuren.listfiltertest.test;

import com.yuren.listfiltertest.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/8/26-15:57
 **/

public class ListFilterTest {

    public static void main(String[] args) {
        Person person = new Person();
        person.name = "1111";
        person.age = 1111;
        Person person1 = new Person();
        person1.name = "2222";
        person1.age = 2222;

        List<Person> list = new ArrayList<Person>();
        list.add(person);
        list.add(person1);

        List<Person> list2 = list.stream().filter(e -> e.name.equals("2222")).collect(Collectors.toList());

        Person person2 = list.get(1);
        Person person3 = list2.get(0);
//        System.out.println(person2 == person3);
        person2.name = "3333";
        System.out.println(person3.name);
    }
}
