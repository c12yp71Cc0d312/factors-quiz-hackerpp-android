package com.example.task1normal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class leader_board extends MainActivity {

    TextView leaderboardScores;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        int hs = shpr.getInt("Hi Score",0);
        int ls = shpr.getInt("Longest Streak",0);

        leaderboardScores = findViewById(R.id.leaderBoardScores);
        back = findViewById(R.id.back);
        leaderboardScores.setText("Hi Score: " + hs + "\n\nLongest Streak: " + ls);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goback = new Intent(leader_board.this, start_Screen.class);
                startActivity(goback);
            }
        });


    }
}
