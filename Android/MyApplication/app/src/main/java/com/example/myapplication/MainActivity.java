package com.example.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout relativeLayout =(RelativeLayout)View.inflate(MainActivity.this, R.layout.newmsg,null);
                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.liner);

                if(linearLayout.getChildCount() % 2  == 0){
                    relativeLayout.setBackgroundColor(Color.GRAY);
                }
                else{
                    relativeLayout.setBackgroundColor(Color.RED);
                }


                linearLayout.addView(relativeLayout);

                Button show = (Button)relativeLayout.findViewById(R.id.mButton1);
                Button edit = (Button)relativeLayout.findViewById(R.id.mButton2);

                final TextView textView = (TextView)relativeLayout.findViewById(R.id.mText2);

                show.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),textView.getText().toString() , Toast.LENGTH_LONG).show();
                    }
                });

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText =(EditText)findViewById(R.id.editText);
                        String temp = editText.getText().toString();
                        textView.setText(temp);
                    }
                });
            }
        });



    }
}
