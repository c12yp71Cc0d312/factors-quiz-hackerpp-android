package com.example.task1normal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class start_Screen extends MainActivity {

    Button start;
    Button leaderBoardbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);

        start = findViewById(R.id.startButton);
        leaderBoardbtn = findViewById(R.id.btnleaderBoard);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchToQuiz = new Intent(start_Screen.this, MainActivity.class);
                startActivity(switchToQuiz);
            }
        });

        leaderBoardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchToLB = new Intent(start_Screen.this, leader_board.class);
                startActivity(switchToLB);
            }
        });
    }


}
