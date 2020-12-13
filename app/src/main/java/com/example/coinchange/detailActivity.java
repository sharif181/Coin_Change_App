package com.example.coinchange;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class detailActivity extends AppCompatActivity {

    Fragment myFrag;
    Spinner mySpinner;
    TextView one,two,five,ten,twenty,fifty,hundred,fiveHundred,thousand,totalNote,totalTaka;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();
        one = findViewById(R.id.onetakaNoteTextId);
        two = findViewById(R.id.twoTakaNoteTextId);
        five = findViewById(R.id.fiveTakaNoteTextId);
        ten = findViewById(R.id.tenTakaNoteTextId);
        twenty = findViewById(R.id.twentyTakaNoteTextId);
        fifty = findViewById(R.id.fiftyTakaNoteTextId);
        hundred = findViewById(R.id.hundredTakaNoteTextId);
        fiveHundred = findViewById(R.id.fiveHundredTakaNoteTextId);
        thousand = findViewById(R.id.thousandTakaNoteTextId);
        totalNote = findViewById(R.id.totalNoteTextId);
        totalTaka = findViewById(R.id.finalTakaId);
        mySpinner = findViewById(R.id.spinnerID);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.options,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(mySpinner.getItemAtPosition(position).toString().equals("Total")){
                    myFrag = new totalFragment();
                }
                else{
                    myFrag = new developerInfoFragment();
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.myFragmentId,myFrag);
                ft.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        int coin[] = new int[9];
        Intent intent = getIntent();
        coin = intent.getIntArrayExtra("myArray");
        one.setText(""+coin[0]);
        two.setText(""+coin[1]);
        five.setText(""+coin[2]);
        ten.setText(""+coin[3]);
        twenty.setText(""+coin[4]);
        fifty.setText(""+coin[5]);
        hundred.setText(""+coin[6]);
        fiveHundred.setText(""+coin[7]);
        thousand.setText(""+coin[8]);
        int sum = 0;
        for(int i=0;i<9;i++)
            sum+=coin[i];
        totalNote.setText(""+sum);
        int coins[]={1,2,5,10,20,50,100,500,1000};
        int total=0;
        for(int i=0;i<9;i++)
            total+=coin[i]*coins[i];
        totalTaka.setText("Total Taka: "+total);
    }
}