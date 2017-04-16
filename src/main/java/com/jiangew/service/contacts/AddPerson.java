package com.jiangew.service.contacts;

import com.jiangew.protobuf.contacts.Contacts;
import com.jiangew.protobuf.contacts.Person;

import java.io.*;

/**
 * 写入 Protobuf 序列化文件
 * Author: Jiangew
 * Date: 30/03/2017
 */
public class AddPerson {

    /**
     * 通过用户输入构建一个Person对象
     *
     * @param stdin
     * @param stdout
     * @return
     * @throws IOException
     */
    private static Person promptForAddress(BufferedReader stdin, PrintStream stdout) throws IOException {
        Person.Builder person = Person.newBuilder();

        stdout.print("Enter person id: ");
        person.setId(Integer.valueOf(stdin.readLine()));

        stdout.print("Enter name: ");
        person.setName(stdin.readLine());

        stdout.print("Enter email address (blank for none): ");
        String email = stdin.readLine();
        if (email.length() > 0) {
            person.setEmail(email);
        }

        while (true) {
            stdout.print("Enter a phone number (or leave blank to finish): ");
            String number = stdin.readLine();
            if (number.length() == 0) {
                break;
            }

            Person.PhoneNumber.Builder phoneNumber = Person.PhoneNumber.newBuilder().setNumber(number);

            stdout.print("Is this a mobile, home, or work phone? ");
            String type = stdin.readLine();
            if (type.equals("mobile")) {
                phoneNumber.setType(Person.PhoneType.MOBILE);
            } else if (type.equals("home")) {
                phoneNumber.setType(Person.PhoneType.HOME);
            } else if (type.equals("work")) {
                phoneNumber.setType(Person.PhoneType.WORK);
            } else {
                stdout.println("Unknown phone type. Using default.");
            }

            person.addPhones(phoneNumber);
        }

        return person.build();
    }

    /**
     * 加载指定的序列化文件（如不存在则创建一个新的），再通过用户输入增加一个新的联系人到地址簿，最后序列化到文件中
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: AddPerson Contacts");
            System.exit(-1);
        }

        Contacts.Builder contacts = Contacts.newBuilder();

        // read the exiting contacts
        try {
            contacts.mergeFrom(new FileInputStream(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println(args[0] + ": File not found. Creating a new file.");
        }

        // add a person
        contacts.addPeople(promptForAddress(new BufferedReader(new InputStreamReader(System.in)), System.out));

        // write the new person back to disk
        FileOutputStream outputStream = new FileOutputStream(args[0]);
        contacts.build().writeTo(outputStream);
        outputStream.close();
    }
}