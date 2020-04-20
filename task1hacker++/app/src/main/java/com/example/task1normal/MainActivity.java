package com.example.task1normal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static com.example.task1normal.R.string.null_option;
import static com.example.task1normal.R.string.response_correct;
import static com.example.task1normal.R.string.submit_newgame;
import static com.example.task1normal.R.string.submit_next;
import static com.example.task1normal.R.string.submit_submit;
import static com.example.task1normal.R.color.white;
import static com.example.task1normal.R.color.red;
import static com.example.task1normal.R.color.green;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RadioGroup radioGroup;
    private RadioButton num1, num2, num3;
    private Button go;
    private int chosen;
    private int numEntered;
    private EditText editText;
    private int randomCorrectButton;
    private int correctAns;
    private Button submit;
    private TextView response;
    private boolean goClicked = false;
    protected SharedPreferences shpr;
    private SharedPreferences.Editor shprEditor;
    private int score = 0;
    private int hiScore = 0;
    private TextView scoreText;
    private TextView hiScoreText;
    private View layout;
    private Vibrator vibrator;
    private CountDownTimer timer;
    private TextView timerText;
    private int longestStreak = 0;
    private int streak = 0;
    private  Button quit;


    ArrayList<Integer> factorsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Started app");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);
        go = findViewById(R.id.go);
        editText = findViewById(R.id.editText);
        submit = findViewById(R.id.submit);
        response = findViewById(R.id.response);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        shpr = PreferenceManager.getDefaultSharedPreferences(this);
        scoreText = findViewById(R.id.score);
        hiScoreText = findViewById(R.id.hiScore);
        shprEditor = shpr.edit();
        hiScore = shpr.getInt("Hi Score", 0);
        longestStreak = shpr.getInt("Longest Streak", 0);
        hiScoreText.setText("Hi Score: " + hiScore + "\nLongest Streak: " + longestStreak);
        layout = findViewById(R.id.layout);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        timerText = findViewById(R.id.timer);
        quit = findViewById(R.id.quit);


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Number entered and clicked 'go'");
                boolean correctFormat = true;
                try {
                    numEntered = Integer.parseInt(String.valueOf(editText.getText()));
                } catch(Exception e) {
                    correctFormat = false;
                }
                if("Next".equals(submit.getText())) {
                    Toast.makeText(MainActivity.this, "Go to next question", Toast.LENGTH_SHORT).show();
                }
                else if (correctFormat && numEntered > 0) {
                    if(goClicked == true)
                        Toast.makeText(MainActivity.this, "Select Option", Toast.LENGTH_SHORT).show();
                    else {
                        startTimer();
                        goClicked = true;
                        factorsList = factors(numEntered);
                        Random random = new Random();
                        int randomIndex = random.nextInt(factorsList.size());
                        correctAns = factorsList.get(randomIndex);
                        randomCorrectButton = random.nextInt(3) + 1;
                        assignValues(randomCorrectButton, correctAns);
                    }

                }
                else
                    Toast.makeText(MainActivity.this, "Enter a natural number!", Toast.LENGTH_SHORT).show();
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(submit.getText().equals("Submit")) {
                    Log.d(TAG, "onClick: Submitted");
                    if(!(num1.isChecked() || num2.isChecked() || num3.isChecked())) {
                        if(goClicked)
                            Toast.makeText(MainActivity.this, "Select option!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Enter natural number and click Go!", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        if(goClicked) {
                            RadioButton checkedButton = findViewById(radioGroup.getCheckedRadioButtonId());
                            chosen = Integer.parseInt((String) checkedButton.getText());
                            checkResponse(chosen);
                            timer.cancel();
                        }
                        else
                            Toast.makeText(MainActivity.this, "Enter natural number and click Go!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Log.d(TAG, "onClick: Went to next question");
                    setToInitialValues();
                }
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                if(hiScore < score) {
                    hiScore = score;
                    hiScoreText.setText("Hi Score: " + hiScore + "\nLongest Streak: " + longestStreak);
                    shprEditor.putInt("Hi Score", hiScore);
                    shprEditor.commit();
                }
                Intent switchtogameover = new Intent(MainActivity.this, game_over.class);
                switchtogameover.putExtra("Score",score);
                switchtogameover.putExtra("Streak", streak);
                startActivity(switchtogameover);
            }
        });


    }



    public void assignValues(int correctButton, int correct) {
        Random random = new Random();
        int nonFactor;
        int nonFactor2;
        int multiplier;
        if(numEntered > 4)
            multiplier = 2;
        else
            multiplier = 8;
        if(correctButton == 1) {
            num1.setText(String.valueOf(correct));
            do {
                nonFactor = random.nextInt(numEntered * multiplier) + 1;
            }while(factorsList.contains(nonFactor));
            num2.setText(String.valueOf(nonFactor));
            do {
                nonFactor2 = random.nextInt(numEntered * multiplier) + 1;
            }while(factorsList.contains(nonFactor2) || nonFactor2 == nonFactor);
            num3.setText(String.valueOf(nonFactor2));
        }
        else if(correctButton == 2) {
            num2.setText(String.valueOf(correct));
            do {
                nonFactor = random.nextInt(numEntered * multiplier)+ 1;
            }while(factorsList.contains(nonFactor));
            num1.setText(String.valueOf(nonFactor));
            do {
                nonFactor2 = random.nextInt(numEntered * multiplier) + 1;
            }while(factorsList.contains(nonFactor2) || nonFactor2 == nonFactor);
            num3.setText(String.valueOf(nonFactor2));
        }
        else {
            num3.setText(String.valueOf(correct));
            do {
                nonFactor = random.nextInt(numEntered * multiplier) + 1;
            }while(factorsList.contains(nonFactor));
            num2.setText(String.valueOf(nonFactor));
            do {
                nonFactor2 = random.nextInt(numEntered * multiplier) + 1;
            }while(factorsList.contains(nonFactor2) || nonFactor2 == nonFactor);
            num1.setText(String.valueOf(nonFactor2));
        }
    }



    public ArrayList<Integer> factors(int numEntered) {
        for (int i = 1; i <= numEntered/2; i++) {
            if (numEntered % i == 0)
                factorsList.add(i);
        }
        factorsList.add(numEntered);
        return factorsList;
    }



    public void checkResponse(int chose) {
        if(chose == correctAns) {
            response.setText(response_correct);
            score += 5;
            streak ++;
            scoreText.setText("Score: " + score + "\nStreak: " + streak);
            submit.setText(submit_next);

            if(streak > longestStreak) {
                longestStreak = streak;
                shprEditor.putInt("Longest Streak", longestStreak);
                shprEditor.commit();
            }
            layout.setBackgroundResource(green);
        }
        else {
            response.setText("Wrong! Answer is " + correctAns);
            score -= 2;
            streak = 0;
            scoreText.setText("Score: " + score + "\nStreak: " + streak);
            layout.setBackgroundResource(red);
            submit.setText(submit_next);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            else
                vibrator.vibrate(500);
        }
    }




    public void setToInitialValues() {
        editText.setText("");
        submit.setText(submit_submit);
        response.setText("");
        num1.setText(null_option);
        num2.setText(null_option);
        num3.setText(null_option);
        radioGroup.clearCheck();
        factorsList.clear();
        randomCorrectButton = 0;
        correctAns = 0;
        goClicked = false;
        layout.setBackgroundResource(white);
        timerText.setText("-");
    }


    public void startTimer() {
        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText(String.valueOf(millisUntilFinished/1000 + 1));
            }

            @Override
            public void onFinish() {
                timerText.setText("0");
                Toast.makeText(MainActivity.this,"Timeout!", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                else
                    vibrator.vibrate(500);
                if(hiScore < score) {
                    hiScore = score;
                    shprEditor.putInt("Hi Score", hiScore);
                    shprEditor.commit();
                }
                Intent switchtogameover = new Intent(MainActivity.this, game_over.class);
                switchtogameover.putExtra("Score",score);
                switchtogameover.putExtra("Streak", streak);
                startActivity(switchtogameover);

            }
        }.start();
    }

    protected int getScore() {
        return score;
    }

    protected int getStreak() {
        return streak;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer != null)
            timer.cancel();
    }
}
