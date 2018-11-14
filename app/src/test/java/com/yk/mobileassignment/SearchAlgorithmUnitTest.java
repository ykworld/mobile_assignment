package com.yk.mobileassignment;

import com.yk.mobileassignment.model.City;
import com.yk.mobileassignment.model.Trie;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class SearchAlgorithmUnitTest {
    private List<City> cities = new ArrayList<>();
    private Trie trie = new Trie();

    @Before
    public void prepareCityList() {
        cities.add(new City("Alabama", "US"));
        cities.add(new City("Albuquerque", "US"));
        cities.add(new City("Anaheim", "US"));
        cities.add(new City("Arizona", "US"));
        cities.add(new City("Sydney", "AU"));

        for (City city : cities) {
            trie.insert(city);
        }
    }

    @Test
    public void If_the_given_prefix_is_A_all_cities_but_Sydney_should_appear() {

        List<City> actual = trie.autocomplete("A");

        assertEquals(4, actual.size());
        assertThat(actual, CoreMatchers.hasItems(
                new City("Alabama", "US"),
                new City("Albuquerque", "US"),
                new City("Anaheim", "US"),
                new City("Arizona", "US")
        ));
        assertThat(actual, not(CoreMatchers.hasItem(new City("Sydney", "AU"))));
    }

    @Test
    public void If_the_given_prefix_is_s_the_only_result_should_be_Sydney_AU() {

        List<City> actual = trie.autocomplete("S");

        assertEquals(1, actual.size());
        assertThat(actual, CoreMatchers.hasItem(new City("Sydney", "AU")));
    }

    @Test
    public void If_the_given_prefix_is_Al_Alabama_US_and_Albuquerque_US_are_the_only_results() {
        List<City> actual = trie.autocomplete("Al");

        assertEquals(2, actual.size());
        assertThat(actual, CoreMatchers.hasItems(
                new City("Alabama", "US"),
                new City("Albuquerque", "US")
        ));
    }

    @Test
    public void If_the_given_prefix_is_Alb_then_the_only_result_is_Albuquerque_US() {
        List<City> actual = trie.autocomplete("Alb");

        assertEquals(1, actual.size());
        assertThat(actual, CoreMatchers.hasItems(
                new City("Albuquerque", "US")
        ));
    }


}