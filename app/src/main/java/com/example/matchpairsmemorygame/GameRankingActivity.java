package com.example.matchpairsmemorygame;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GameRankingActivity extends Activity {
    ListView lvGameRanking;
    ArrayAdapter<String> adapter;
    List<String> data = new ArrayList<>();

    String apiUrl = "https://ranking-mobileasignment-wlicpnigvf.cn-hongkong.fcapp.run";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ranking);

        // Initialize the ListView and ArrayAdapter
        lvGameRanking = findViewById(R.id.lvGameRanking);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, data);
        lvGameRanking.setAdapter(adapter);

        // Fetch ranking data from the API
        fetchData();
    }

    /**
     * Fetches ranking data from the API using Volley.
     * Parses the JSON response, sorts the players by moves,
     * and updates the UI with the ranking data.
     */
    private void fetchData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Create a JSON array request to retrieve the ranking data
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
                response -> {
                    try {
                        String jsonData = response.toString();

                        // Check if the JSON data is empty
                        if (jsonData.isEmpty()) {
                            Toast.makeText(GameRankingActivity.this, "No ranking found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Parse the JSON data using Gson library
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Player>>() {
                        }.getType();
                        List<Player> players = gson.fromJson(jsonData, listType);

                        // Sort the players list based on moves in ascending order
                        Collections.sort(players, Comparator.comparingInt(Player::getMoves));

                        // Update the UI with the ranking data
                        runOnUiThread(() -> {
                            data.addAll(convertToRankingStrings(players));
                            adapter.notifyDataSetChanged();
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(GameRankingActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(GameRankingActivity.this, "Error retrieving JSON", Toast.LENGTH_SHORT).show();
                });

        // Add the JSON array request to the request queue
        requestQueue.add(jsonArrayRequest);
    }

    /**
     * Converts a list of players to ranking strings.
     *
     * @param players The list of players to convert.
     * @return The ranking strings.
     */
    private List<String> convertToRankingStrings(List<Player> players) {
        List<String> rankingStrings = new ArrayList<>();
        for (Player player : players) {
            rankingStrings.add(player.getName() + " - " + player.getMoves() + " moves");
        }
        return rankingStrings;
    }

    static class Player {
        private String Name;
        private int Moves;

        public String getName() {
            return Name;
        }

        public int getMoves() {
            return Moves;
        }
    }
}