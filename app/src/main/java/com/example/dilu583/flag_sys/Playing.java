package com.example.dilu583.flag_sys;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dilu583.flag_sys.DbHelper.DbHelper;
import com.example.dilu583.flag_sys.Model.Question;

import java.util.ArrayList;
import java.util.List;

public class Playing extends AppCompatActivity implements View.OnClickListener {

    final static long INTERVAL = 1000;
    final static long TIMEOUT = 7000;
    int progressValue = 0;
    Question playQuetion;
    CountDownTimer mCountDown;
    List<Question> questionPlay = new ArrayList<>();
    DbHelper db;
    int index =0, score =0, thisQuestion =0, totalQuestion, correctAnswer;
    String mode = "";
    String name = "";
    ProgressBar progressBar;
    ImageView imageView;
    Button btnA, btnB, btnC, btnD;
    TextView txtScore, txtQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        //Get Data from main activity

        Bundle extra = getIntent().getExtras();
        if(extra!=null) {
            mode = extra.getString("MODE");
            name = extra.getString("NAME");
        }
        db = new DbHelper(this);

        txtScore=(TextView)findViewById(R.id.txtScore);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imageView = (ImageView) findViewById(R.id.question_flag);
        btnA = (Button) findViewById(R.id.btnAnswerA);
        btnB = (Button) findViewById(R.id.btnAnswerB);
        btnC = (Button) findViewById(R.id.btnAnswerC);
        btnD = (Button) findViewById(R.id.btnAnswerD);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);



    }

    @Override
    protected void onResume() {
        super.onResume();

        //questionPlay = db.getQuestionMode(mode);
        playQuetion = db.getQuestion("china");
        //totalQuestion = questionPlay.size();
        totalQuestion = 10;

        mCountDown = new CountDownTimer(TIMEOUT,INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                showQuestion();

            }
        };

                showQuestion();

    }

    private void showQuestion() {

            thisQuestion++;
            //txtQuestion.setText(String.format("%d/%d", thisQuestion,totalQuestion));
            progressBar.setProgress(0);
            progressValue=0;

            int ImageId = this.getResources().getIdentifier(playQuetion.getImage().toLowerCase(),"drawable",getPackageName());
            imageView.setBackgroundResource(ImageId);
            btnA.setText(playQuetion.getAnswerA());
            btnB.setText(playQuetion.getAnswerB());
            btnC.setText(playQuetion.getAnswerC());
            btnD.setText(playQuetion.getAnswerD());

            mCountDown.start();




    }

    @Override
    public void onClick(View v) {

        mCountDown.cancel();

            Button clickedButton =(Button) v;
            if(clickedButton.getText().equals(playQuetion.getCorrectAnswer())) // if the answer is correct
            {
                score+=10;
                correctAnswer++;
                showQuestion();
            }
            else showQuestion();  // if the answer is wrong.

            txtScore.setText(String.format("%d",score));


    }
}
