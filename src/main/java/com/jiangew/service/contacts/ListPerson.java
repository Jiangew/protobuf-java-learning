package com.jiangew.service.contacts;

import com.jiangew.protobuf.contacts.Contacts;
import com.jiangew.protobuf.contacts.Person;

import java.io.FileInputStream;

/**
 * 读取 Protobuf 序列化文件
 * Author: Jiangew
 * Date: 30/03/2017
 */
public class ListPerson {

    /**
     * 打印通讯录中联系人信息
     *
     * @param contacts
     */
    private static void print(Contacts contacts) {
        for (Person person : contacts.getPeopleList()) {
            System.out.println("Person Id: " + person.getId());
            System.out.println(" Name: " + person.getName());
            if (!person.getPhonesList().isEmpty()) {
                System.out.println(" Email address: " + person.getEmail());
            }

            for (Person.PhoneNumber phoneNumber : person.getPhonesList()) {
                switch (phoneNumber.getType()) {
                    case MOBILE:
                        System.out.print(" Mobile phone #: ");
                        break;
                    case HOME:
                        System.out.print(" Home phone #: ");
                        break;
                    case WORK:
                        System.out.print(" Work phone #: ");
                        break;
                }
                System.out.println(phoneNumber.getNumber());
            }
        }
    }

    /**
     * 加载制定的序列化文件，并输出所有联系人
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: ListPeople Contacts");
            System.exit(-1);
        }

        // read the exiting contacts
        Contacts contacts = Contacts.parseFrom(new FileInputStream(args[0]));
        print(contacts);
    }
}