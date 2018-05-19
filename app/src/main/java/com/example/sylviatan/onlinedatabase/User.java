package com.example.sylviatan.onlinedatabase;

public class User {

    private String id;
    private String name;
    private String age;
    private String married;


    //Constructor
    public User(String id, String name, String age, String married) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.married = married;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMarried() {
        return married;
    }

    public void setMarried(String married) {
        this.married = married;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", married=" + married +
                '}';
    }
}
