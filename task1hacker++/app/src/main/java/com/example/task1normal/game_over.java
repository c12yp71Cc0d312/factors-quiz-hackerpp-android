package com.example.task1normal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class game_over extends MainActivity {

    private TextView scoreCard;
    private Button menu;
    private TextView rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);

        scoreCard = findViewById(R.id.finalScore);
        menu = findViewById(R.id.menu);

        int sc = getIntent().getIntExtra("Score", 0);

        scoreCard.setText("Your Score: " + sc);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMenu = new Intent(game_over.this, start_Screen.class);
                startActivity(toMenu);
            }
        });

    }
}
