package com.example.friendbook;

//Class represents a friend with personal details including name, age, email, phone, and interests
public class Friend {
    private String name;
    private int age;
    private String email;
    private String phoneNumber;
    private String interests;


    //requires: name not null or empty, age > 0, email including @, phone number not null
    //modifies: this
    //effects: Creates a new Friend object with the specified properties
    public Friend(String name, int age, String email, String phoneNumber, String interests) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.interests = interests;
    }

    //gets friend name
    //effects: returns the name of friend
    public String getName() {
        return name;
    }

    //requires: name not null or empty
    //modifies: this
    //effects: sets the name of the friend
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    //gets age of friend
    //effects: returns age of friend
    public int getAge() {
        return age;
    }

    //requires: age > 0
    //modifies: this
    //effects: sets the age of the friend
    public void setAge(int age) {
        if (age <= 0) {
            throw new IllegalArgumentException("Age must be positive");
        }
        this.age = age;
    }

    //gets email
    //effects: returns the friend's email
    public String getEmail() {
        return email;
    }

    //requires: email not null and contains '@'
    //modifies: this
    //effects: sets the email of the friend
    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    //gets phone number
    //effects: returns the friend's phone number
    public String getPhoneNumber() {
        return phoneNumber;
    }

    //requires: phoneNumber not null
    //modifies: this
    //effects: sets the phone number of the friend
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //gets interests
    //effects: returns interests
    public String getInterests() {
        return interests;
    }

    //requires: interests not null
    //modifies: this
    //effects: sets the interests of the friend
    public void setInterests(String interests) {
        this.interests = interests;
    }

    //string representing friend
    //effects: returns the friend's name for the listview display
    @Override
    public String toString() {
        return name;
    }
}