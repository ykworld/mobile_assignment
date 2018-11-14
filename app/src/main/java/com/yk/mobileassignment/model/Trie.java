package com.yk.mobileassignment.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
* I used tire data structure to optimize for fast search
*
* */
public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(City city) {
        HashMap<Character, TrieNode> children = root.children;

        String word = city.getDisplayName().toLowerCase();

        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);

            TrieNode t;
            if(children.containsKey(c)){
                t = children.get(c);
            }else{
                t = new TrieNode(c);
                children.put(c, t);
            }

            children = t.children;

            //set leaf node
            if(i==word.length()-1) {
                t.isLeaf = true;
                t.city = city;
            }
        }
    }

    public List<City> autocomplete(String prefix) {
        prefix = prefix.toLowerCase();
        HashMap<Character, TrieNode> children = root.children;
        TrieNode t = null;

        for (int i = 0; i< prefix.length(); i++) {
            char c = prefix.charAt(i);

            if(children.containsKey(c)){
                t = children.get(c);
                children = t.children;
            } else {
                return new ArrayList<>();
            }
        }

        return t.getCities();
    }

}
