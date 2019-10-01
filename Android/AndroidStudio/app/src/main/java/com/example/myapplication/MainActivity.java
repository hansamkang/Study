package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button firstButton, secondButton, thirdButton;
    LinearLayout firstPage, secondPage;
    TableLayout thirdPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstButton = (Button)findViewById(R.id.FirstButton);
        secondButton = (Button)findViewById(R.id.SecondButton);
        thirdButton = (Button)findViewById(R.id.ThirdButton);

        firstPage = (LinearLayout)findViewById(R.id.FisrtPage);
        secondPage = (LinearLayout)findViewById(R.id.SecondPage);
        thirdPage = (TableLayout)findViewById(R.id.ThirdPage);

        firstButton.setOnClickListener(this);
        secondButton.setOnClickListener(this);
        thirdButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        // FirstButton Event
        if(v == firstButton){
            firstPage.setVisibility(View.VISIBLE);
            secondPage.setVisibility(View.INVISIBLE);
            thirdPage.setVisibility(View.INVISIBLE);
        }

        // SecondButton Event
        if(v == secondButton){
            firstPage.setVisibility(View.INVISIBLE);
            secondPage.setVisibility(View.VISIBLE);
            thirdPage.setVisibility(View.INVISIBLE);
        }

        // ThirdButton Event
        if(v == thirdButton){
            firstPage.setVisibility(View.INVISIBLE);
            secondPage.setVisibility(View.INVISIBLE);
            thirdPage.setVisibility(View.VISIBLE);
        }

    }
}
