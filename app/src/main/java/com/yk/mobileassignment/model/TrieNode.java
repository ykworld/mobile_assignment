package com.yk.mobileassignment.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class TrieNode {
    char c;
    City city;
    HashMap<Character, TrieNode> children = new HashMap<>();
    boolean isLeaf;

    public TrieNode() {
    }

    public TrieNode(char c) {
        this.c = c;
    }

    public List<City> getCities() {
        List<City> cityList = new ArrayList<>();

        if (isLeaf) {
            cityList.add(city);
        }

        for (Map.Entry<Character, TrieNode> entry : children.entrySet()) {
            TrieNode t = entry.getValue();
            cityList.addAll(t.getCities());
        }

        return cityList;
    }
}
