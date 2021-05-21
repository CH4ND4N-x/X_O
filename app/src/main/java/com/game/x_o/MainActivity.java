package com.game.x_o;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Dialog mydialog;
    private final Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView img = findViewById(R.id.Source_code);
        for (int i =0; i<3; i++){
            for (int j=0; j<3; j++){
                String buttonID = "button_"+i+j;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button button_reset = findViewById(R.id.reset);
        button_reset.setOnClickListener(v -> resetBoard());

        //pop window
        mydialog = new Dialog(this);
        //Source code button
        img.setOnClickListener(v -> {
            Intent intent = new Intent();
            img.setClickable(true);
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            Intent intent1 = intent.setData(Uri.parse("https://github.com/CH4ND4N-x/X_O/tree/master"));
            startActivity(intent1);
        });
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")){
            return;
        }
        if (player1Turn){
            ((Button) v).setText("X");
            v.setBackgroundColor(getColor(R.color.X_color));
        } else {
            ((Button) v).setText("O");
            v.setBackgroundColor(getColor(R.color.Y_color));
        }
        roundCount++;
        if (checkForWin()){
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if(roundCount == 9){
            draw();
        } else{
            player1Turn = !player1Turn;
        }
    }
    //Check for win method
    private boolean  checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true;
        }
        return false;

    }
    private void player1Wins(){
        ShowPopup_X();
        resetBoard();
    }
    private void player2Wins(){
        ShowPopup_O();
        resetBoard();
    }
    private void draw(){
        ShowPopup_Draw();
        resetBoard();
    }
    //popup when X wins
    public void ShowPopup_X(){
        TextView txtclose;
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.setContentView(R.layout.popup_x);
        txtclose= mydialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(v -> {
            resetBoard();
            mydialog.dismiss();
        });
        mydialog.show();
    }
    //popup when O wins
    public void ShowPopup_O(){
        TextView txtclose;
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.setContentView(R.layout.popup_o);
        txtclose= mydialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(v -> {
            resetBoard();
            mydialog.dismiss();
        });
        mydialog.show();
    }
    //Draw popup
    public void ShowPopup_Draw(){
        TextView txtclose;
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.setContentView(R.layout.popup_draw);
        txtclose= mydialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(v -> {
            resetBoard();
            mydialog.dismiss();
        });
        mydialog.show();
    }

    @SuppressLint("ResourceAsColor")
    private void resetBoard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackgroundColor(getColor(R.color.purple_200));
            }
        }
        roundCount = 0;
        player1Turn = true;
    }
}