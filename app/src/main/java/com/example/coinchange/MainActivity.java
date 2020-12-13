package com.example.coinchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText amountEdit,costEdit,oneTakaEdit,twoTakaEdit,fiveTakaEdit,tenTakaEdit,twentyTakaEdit,fiftyTakaEdit,hundredTakaEdit,fivehundredTakaEdit,thousandTakaEdit;
    TextView outputView,totalNeed;
    int coins[]={1000,500,100,50,20,10,5,2,1};
    int coinNumber[]=new int[9];
    int requied[]= new int[9];
    int coin[] = new int [9];
    DatabaseHelperClass db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        amountEdit = findViewById(R.id.totalEditTextId);
        costEdit = findViewById(R.id.costEditTextId);
        totalNeed = findViewById(R.id.totalNeededTextViewId);
        outputView = findViewById(R.id.outPutTextViewId);
        oneTakaEdit = findViewById(R.id.oneTakaEditId);
        twoTakaEdit = findViewById(R.id.twoTakaEditId);
        fiveTakaEdit = findViewById(R.id.fiveTakaEditId);
        tenTakaEdit = findViewById(R.id.tenTakaEditId);
        twentyTakaEdit = findViewById(R.id.twentyTakaEditId);
        fiftyTakaEdit = findViewById(R.id.fiftyTakaEditId);
        hundredTakaEdit = findViewById(R.id.hundredTakaEditId);
        fivehundredTakaEdit = findViewById(R.id.fiveHundredEditId);
        thousandTakaEdit = findViewById(R.id.thousandTakaEditId);


        db = new DatabaseHelperClass(this);
        //load coinNumber from database onCreating time
        db.addCoin(1,"0");
        db.addCoin(2,"0");
        db.addCoin(3,"0");
        db.addCoin(4,"0");
        db.addCoin(5,"0");
        db.addCoin(6,"0");
        db.addCoin(7,"0");
        db.addCoin(8,"0");
        db.addCoin(9,"0");

        for (int i=0;i<9;i++)
            coinNumber[i] = db.getCoinInfo(9-i);

    }

    public void changeCoin(View view) {
        int i;
        int totalAmount = 0, costAmount = 0;
        for ( i=0;i<9;i++)
            coinNumber[i] = db.getCoinInfo(9-i);

        if(amountEdit.getText().toString().equals("")) amountEdit.setText(""+0);
        if(costEdit.getText().toString().equals("")) costEdit.setText(""+0);

        totalAmount = Integer.parseInt(amountEdit.getText().toString());
        costAmount = Integer.parseInt(costEdit.getText().toString());

        int targetAmount = totalAmount - costAmount;
        int finalArray[] = combine(coins,coinNumber);
        int result = dp_coin_change(targetAmount,finalArray);

        outputView.setText("");
        if(result != -1){
            totalNeed.setText("Total Notes: "+result);
            for(i=0;i<9;i++)
                if(requied[i]>0)
                {
                    outputView.setText(outputView.getText().toString()+"\n"+requied[i]+ " Notes of "+coins[8-i]+" Taka");
                    coinNumber[8-i] = coinNumber[8-i] - requied[i];
                }
        }
        else{
            totalNeed.setText("");
            outputView.setText("Notes not available");
        }
        amountEdit.setText("");
        costEdit.setText("");
        for(int k=0;k<9;k++)
            requied[k]=0;

        for(int j = 1; j<=9;j++)
            db.updateCoin(j,String.valueOf(coinNumber[9-j]));

        

    }

    private int dp_coin_change(int targetAmount, int[] finalArray) {
        int sum=0,i,count=0;
        for(i=0;i<finalArray.length;i++){
            if(sum == targetAmount){
                return count;
            }
            if(sum+finalArray[i]<=targetAmount){
                sum+=finalArray[i];
                count++;
                if(finalArray[i]==1) requied[0]+=1;
                else if (finalArray[i]==2) requied[1]+=1;
                else if (finalArray[i]==5) requied[2]+=1;
                else if (finalArray[i]==10) requied[3]+=1;
                else if (finalArray[i]==20) requied[4]+=1;
                else if (finalArray[i]==50) requied[5]+=1;
                else if (finalArray[i]==100) requied[6]+=1;
                else if (finalArray[i]==500) requied[7]+=1;
                else if (finalArray[i]==1000) requied[8]+=1;
            }

        }

        if(i==finalArray.length && sum<targetAmount) return -1;
        else return count;
    }

    private int[] combine(int[] coins, int[] coinNumber) {
        int sum=0,i;
        for (int coin: coinNumber){
            sum += coin;
        }
        int finalReturnArray[] = new int [sum];

        int index = 0;
        for(i=0;i<coins.length;i++){
            int count = coinNumber[i];
            while(count != 0){
                finalReturnArray[index++] = coins[i];
                count--;
            }
        }
        return finalReturnArray;
    }

    public void resetEverything(View view){
        amountEdit.setHint("Enter total amount");
        amountEdit.setText("");
        costEdit.setHint("Enter total cost:");
        costEdit.setText("");
        outputView.setText("");
        totalNeed.setText("");
    }

    public void onetakaIncreceBtn(View view){
        int res = db.getCoinInfo(1);
        int now = Integer.parseInt(oneTakaEdit.getText().toString());
        if (now == 0){
            res+=1;
        }
        else{
            res += now;
        }
        String Res = ""+res;
        db.updateCoin(1,Res);
        oneTakaEdit.setText("0");
        Toast.makeText(this,"Total One taka's notes: "+res,Toast.LENGTH_SHORT).show();
    }

    public void onetakaDecrimentBtn(View view) {
       int res = db.getCoinInfo(1);
       int now = Integer.parseInt(oneTakaEdit.getText().toString());
       if(res>0){
           if(now==0) res = res -1;
           else{
               res -= now;
           }
           String Res = ""+res;
           db.updateCoin(1,Res);
       }
       oneTakaEdit.setText("0");
        Toast.makeText(this,"Total One taka's notes: "+res,Toast.LENGTH_SHORT).show();
    }

    public void twoTakaDecreBtn(View view) {
        int res = db.getCoinInfo(2);
        int now = Integer.parseInt(twoTakaEdit.getText().toString());
        if(res>0){
            if(now==0) res = res -1;
            else{
                res -= now;
            }
            String Res = ""+res;
            db.updateCoin(2,Res);
        }
        twoTakaEdit.setText("0");
        Toast.makeText(this,"Total Two taka's notes: "+res,Toast.LENGTH_SHORT).show();
    }

    public void twoIncreBtn(View view) {
        int res = db.getCoinInfo(2);
        int now = Integer.parseInt(twoTakaEdit.getText().toString());
        if (now == 0){
            res+=1;
        }
        else{
            res += now;
        }
        String Res = ""+res;
        db.updateCoin(2,Res);
        twoTakaEdit.setText("0");
        Toast.makeText(this,"Total Two taka's notes: "+res,Toast.LENGTH_SHORT).show();
    }

    public void fiveDecribtn(View view) {
        int res = db.getCoinInfo(3);
        int now = Integer.parseInt(fiveTakaEdit.getText().toString());
        if(res>0){
            if(now==0) res = res -1;
            else{
                res -= now;
            }
            String Res = ""+res;
            db.updateCoin(3,Res);
        }
        fiveTakaEdit.setText("0");
        Toast.makeText(this,"Total Five taka's notes: "+res,Toast.LENGTH_SHORT).show();
    }

    public void fiveIncreBtn(View view) {
        int res = db.getCoinInfo(3);
        int now = Integer.parseInt(fiveTakaEdit.getText().toString());
        if (now == 0){
            res+=1;
        }
        else{
            res += now;
        }
        String Res = ""+res;
        db.updateCoin(3,Res);
        fiveTakaEdit.setText("0");
        Toast.makeText(this,"Total Five taka's notes: "+res,Toast.LENGTH_SHORT).show();
    }

    public void tenTakaDecrbtn(View view) {
        int res = db.getCoinInfo(4);
        int now = Integer.parseInt(tenTakaEdit.getText().toString());
        if(res>0){
            if(now==0) res = res -1;
            else{
                res -= now;
            }
            String Res = ""+res;
            db.updateCoin(4,Res);
        }
        tenTakaEdit.setText("0");
        Toast.makeText(this,"Total Ten taka's notes: "+res,Toast.LENGTH_SHORT).show();
    }

    public void tenTakaIncreBtn(View view) {
        int res = db.getCoinInfo(4);
        int now = Integer.parseInt(tenTakaEdit.getText().toString());
        if (now == 0){
            res+=1;
        }
        else{
            res += now;
        }
        String Res = ""+res;
        db.updateCoin(4,Res);
        tenTakaEdit.setText("0");
        Toast.makeText(this,"Total Ten taka's notes: "+res,Toast.LENGTH_SHORT).show();
    }

    public void twentyTakaDecreBtn(View view) {
        int res = db.getCoinInfo(5);
        int now = Integer.parseInt(twentyTakaEdit.getText().toString());
        if(res>0){
            if(now==0) res = res -1;
            else{
                res -= now;
            }
            String Res = ""+res;
            db.updateCoin(5,Res);
        }
        twentyTakaEdit.setText("0");
        Toast.makeText(this,"Total Twenty taka's notes: "+res,Toast.LENGTH_SHORT).show();
    }

    public void twentyTakaIncreBtn(View view) {
        int res = db.getCoinInfo(5);
        int now = Integer.parseInt(twentyTakaEdit.getText().toString());
        if (now == 0){
            res+=1;
        }
        else{
            res += now;
        }
        String Res = ""+res;
        db.updateCoin(5,Res);
        twentyTakaEdit.setText("0");
        Toast.makeText(this,"Total Twenty taka's notes: "+res,Toast.LENGTH_SHORT).show();
    }

    public void fiftyTakaDecrBtn(View view) {
        int res = db.getCoinInfo(6);
        int now = Integer.parseInt(fiftyTakaEdit.getText().toString());
        if(res>0){
            if(now==0) res = res -1;
            else{
                res -= now;
            }
            String Res = ""+res;
            db.updateCoin(6,Res);
        }
        fiftyTakaEdit.setText("0");
        Toast.makeText(this,"Total Fifty taka's notes: "+res,Toast.LENGTH_SHORT).show();
    }

    public void fiftyTakaIncreBtn(View view) {
        int res = db.getCoinInfo(6);
        int now = Integer.parseInt(fiftyTakaEdit.getText().toString());
        if (now == 0){
            res+=1;
        }
        else{
            res += now;
        }
        String Res = ""+res;
        db.updateCoin(6,Res);
        fiftyTakaEdit.setText("0");
        Toast.makeText(this,"Total Fifty taka's notes: "+res,Toast.LENGTH_SHORT).show();
    }

    public void hundredTakaDecreBtn(View view) {
        int res = db.getCoinInfo(7);
        int now = Integer.parseInt(hundredTakaEdit.getText().toString());
        if(res>0){
            if(now==0) res = res -1;
            else{
                res -= now;
            }
            String Res = ""+res;
            db.updateCoin(7,Res);
        }
        hundredTakaEdit.setText("0");
        Toast.makeText(this,"Total Hundred taka's notes: "+res,Toast.LENGTH_SHORT).show();
    }

    public void hundredTakaIncreBtn(View view) {
        int res = db.getCoinInfo(7);
        int now = Integer.parseInt(hundredTakaEdit.getText().toString());
        if (now == 0){
            res+=1;
        }
        else{
            res += now;
        }
        String Res = ""+res;
        db.updateCoin(7,Res);
        hundredTakaEdit.setText("0");
        Toast.makeText(this,"Total Hundred taka's notes: "+res,Toast.LENGTH_SHORT).show();
    }

    public void fiveHundredTakaDrecBtn(View view) {
        int res = db.getCoinInfo(8);
        int now = Integer.parseInt(fivehundredTakaEdit.getText().toString());
        if(res>0){
            if(now==0) res = res -1;
            else{
                res -= now;
            }
            String Res = ""+res;
            db.updateCoin(8,Res);
        }
        fivehundredTakaEdit.setText("0");
        Toast.makeText(this,"Total Five Hundred taka's notes: "+res,Toast.LENGTH_SHORT).show();
    }

    public void fiveHundredTakaIncreBtn(View view) {
        int res = db.getCoinInfo(8);
        int now = Integer.parseInt(fivehundredTakaEdit.getText().toString());
        if (now == 0){
            res+=1;
        }
        else{
            res += now;
        }
        String Res = ""+res;
        db.updateCoin(8,Res);
        fivehundredTakaEdit.setText("0");
        Toast.makeText(this,"Total Five Hundred taka's notes: "+res,Toast.LENGTH_SHORT).show();
    }

    public void thousandTakaDecreBtn(View view) {
        int res = db.getCoinInfo(9);
        int now = Integer.parseInt(thousandTakaEdit.getText().toString());
        if(res>0){
            if(now==0) res = res -1;
            else{
                res -= now;
            }
            String Res = ""+res;
            db.updateCoin(9,Res);
        }
        thousandTakaEdit.setText("0");
        Toast.makeText(this,"Total Thousand taka's notes: "+res,Toast.LENGTH_SHORT).show();
    }

    public void thousandTakaIncreBtn(View view) {
        int res = db.getCoinInfo(9);
        int now = Integer.parseInt(thousandTakaEdit.getText().toString());
        if (now == 0){
            res+=1;
        }
        else{
            res += now;
        }
        String Res = ""+res;
        db.updateCoin(9,Res);
        thousandTakaEdit.setText("0");
        Toast.makeText(this,"Total Thousand taka's notes: "+res,Toast.LENGTH_SHORT).show();
    }

    public void detailShowBtn(View view) {

        for(int i=0;i<9;i++)
            coin[i] = db.getCoinInfo(i+1);

        Intent intent = new Intent(this,detailActivity.class);
        intent.putExtra("myArray",coin);
        startActivity(intent);
    }
}