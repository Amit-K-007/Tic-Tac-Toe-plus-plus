package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private TextView textView,textView2;
    private ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9;
    private String currentPlayer,human = "O",ai = "X";
    private String board[][] = {{"","",""},{"","",""},{"","",""}};
    private ImageView imageViews[][];
    private String winner=null;
    private boolean flag2 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        MainActivity.flag1 = false;
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        imageViews = new ImageView[][]{{imageView1,imageView2,imageView3},{imageView4,imageView5,imageView6},{imageView7,imageView8,imageView9}};
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                humanMove(0,0);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                humanMove(0,1);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                humanMove(0,2);
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                humanMove(1,0);
            }
        });
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                humanMove(1,1);
            }
        });
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                humanMove(1,2);
            }
        });
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                humanMove(2,0);
            }
        });
        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                humanMove(2,1);
            }
        });
        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                humanMove(2,2);
            }
        });
        Random random = new Random();
        int choice = random.nextInt(2);
        if(choice == 0) {
            currentPlayer = ai;
            textView.setText("AI's move");
            delay(1100);
        }
        else{
            currentPlayer = human;
            textView.setText("Player's move");
        }
    }
    public int scoreValue(String str){
        if(str == "X"){
            return 1;
        }
        else if(str == "O"){
            return -1;
        }
        else{
            return 0;
        }
    }
    public void bestMove(){
        int bestScore = -999999999;
        int move[] = {0,0};
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++) {
                if(board[i][j] == "") {
                    board[i][j] = ai;
                    int score = minimax(board,0,false);
                    board[i][j] = "";
                    if(score > bestScore){
                        bestScore = score;
                        move[0] = i;
                        move[1] = j;
                    }
                }
            }
        }
        board[move[0]][move[1]] = ai;
        imageViews[move[0]][move[1]].setImageResource(R.drawable.x);
        currentPlayer = human;
        String result = checkWinner();
        if(result!=null){
            displayWinner(result);
        }
        else{
            textView.setText("Player's move");
        }
    }
    public int minimax(String board[][],int depth,boolean isMaximizing){
        String result = checkWinner();
        if(result!=null){
            return scoreValue(result);
        }
        if(isMaximizing){
            int bestScore = -99999999;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++) {
                    if(board[i][j] == ""){
                        board[i][j] = ai;
                        int score = minimax(board,depth+1,false);
                        board[i][j] = "";
                        bestScore = Math.max(score,bestScore);
                    }
                }
            }
            return bestScore;
        }
        else{
            int bestScore = 999999999;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++) {
                    if(board[i][j] == ""){
                        board[i][j] = human;
                        int score = minimax(board,depth+1,true);
                        board[i][j] = "";
                        bestScore = Math.min(score,bestScore);
                    }
                }
            }
            return bestScore;
        }
    }
    public String checkWinner(){
        winner=null;
        //horizontal
        for(int i=0;i<3;i++){
            if(board[i][0] != "" && (board[i][0] == board[i][1]) && (board[i][0] == board[i][2])){
                winner = board[i][0];
            }
        }
        //Vertical
        for(int i=0;i<3;i++){
            if(board[0][i] != "" && (board[0][i] == board[1][i]) && (board[0][i] == board[2][i])){
                winner = board[0][i];
            }
        }
        //diagonal
        if(board[0][0] != "" && (board[0][0] == board[1][1]) && (board[0][0] == board[2][2])){
            winner = board[0][0];
        }
        if(board[0][2] != "" && (board[0][2] == board[1][1]) && (board[0][2] == board[2][0])){
            winner = board[0][2];
        }
        int openspots=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j] == ""){
                    openspots++;
                }
            }
        }
        if(winner == null && openspots==0){
            return "tie";
        }
        else {
            return winner;
        }
    }
    public void humanMove(int i,int j) {
        if (winner == null) {
            if (currentPlayer.equals(human)) {
                if (board[i][j] == "") {
                    board[i][j] = human;
                    imageViews[i][j].setImageResource(R.drawable.o);
                    currentPlayer = ai;
                    String result = checkWinner();
                    if (result != null) {
                        displayWinner(result);
                    } else {
                        textView.setText("AI's move");
                        delay(1100);
                    }
                }
            }
        }
    }
    public void displayWinner(String winner){
        if(winner == ai){
            textView2.setText("AI won!!!");
        }
        else if(winner == human){
            textView2.setText("Player won!!!");
        }
        else{
            textView2.setText("Nobody won!!!");
        }
    }
    public void delay(int mili){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                bestMove();
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,mili);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(flag2 == false){
            MainActivity.mediaPlayer.pause();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(flag2 == false){
            MainActivity.mediaPlayer.start();
        }
    }
}