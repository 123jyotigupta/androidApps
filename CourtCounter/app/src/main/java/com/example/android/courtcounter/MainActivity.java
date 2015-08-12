package com.example.android.courtcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int teamAScore,teamBScore = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //displayTeamAScore(8);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayTeamAScore(int score){
        TextView teamaScore  =(TextView)findViewById(R.id.team_a_score);
        teamaScore.setText(String.valueOf(score));
    }

    private void displayTeamBScore(int score){
        TextView teambScore  =(TextView)findViewById(R.id.team_b_score);
        teambScore.setText(String.valueOf(score));
    }

    public void plus3PointsTeamA(View view){
        teamAScore = teamAScore + 3;
        displayTeamAScore(teamAScore);
    }

    public void plus2PointsTeamA(View view){
        teamAScore = teamAScore + 2;
        displayTeamAScore(teamAScore);
    }

    public void freeThrowPointsTeamA(View view){
        teamAScore = teamAScore + 1 ;
        displayTeamAScore(teamAScore);
    }

    public void plus3PointsTeamB(View view){
        teamBScore = teamBScore + 3;
        displayTeamBScore(teamBScore);
    }

    public void plus2PointsTeamB(View view){
        teamBScore = teamBScore + 2;
        displayTeamBScore(teamBScore);
    }

    public void freeThrowPointsTeamB(View view){
        teamBScore = teamBScore + 1 ;
        displayTeamBScore(teamBScore);
    }

    public void resetScores(View view){
        teamAScore = 0;
        displayTeamAScore(teamAScore);
        teamBScore = 0;
        displayTeamBScore(teamBScore);
    }
}
