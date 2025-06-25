package com.example.friendbook;

import java.util.ArrayList;
import java.util.List;

//FriendsManager manages friend objects by allowing them to be added or removed
public class FriendsManager {
    private List<Friend> friends;

    //Constructor
    //requires: nothing
    //modifies: this
    //effects: initializes an empty friends list
    public FriendsManager() {
        friends = new ArrayList<>();
    }

    //requires: friend not null and not already in the list
    //modifies: this
    //effects: adds the new friend to the collection
    public void addFriend(Friend friend) {
        if (friend == null) {
            throw new IllegalArgumentException("Friend cannot be null");
        }
        if (friends.contains(friend)) {
            throw new IllegalArgumentException("Friend already exists");
        }
        friends.add(friend);
    }

    //requires: friend not null and exists in the list
    //modifies: this
    //effects: removes the friend from the collection
    public void removeFriend(Friend friend) {
        if (friend == null) {
            throw new IllegalArgumentException("Friend cannot be null");
        }
        if (!friends.contains(friend)) {
            throw new IllegalArgumentException("Friend does not exist");
        }
        friends.remove(friend);
    }


    //requires: nothing
    //modifies: nothing
    //effects: returns the list of all friends
    public List<Friend> getAllFriends() {
        return new ArrayList<>(friends);
    }
 }
