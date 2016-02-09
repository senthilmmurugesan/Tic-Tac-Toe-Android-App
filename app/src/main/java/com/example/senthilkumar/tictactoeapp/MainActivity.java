package com.example.senthilkumar.tictactoeapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static boolean STATUS = true;
    private TextView messageText;
    private String[][] positions = new String[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        messageText = (TextView) findViewById(R.id.moveTextView);
    }

    public void MakeMove(View view) {
        String text;
        text = STATUS ? "X" : "O";

        Button selectedButton = (Button) view;
        selectedButton.setText(text);
        selectedButton.setTextSize(50);
        selectedButton.setEnabled(false);
        STATUS = !STATUS;
        CreatePositions();
        ValidatePositions();
    }

    public void CreatePositions() {

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout1);
        for(int i = 0; i <tableLayout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            for(int j = 0; j < tableRow.getChildCount(); j++) {
                Button button = (Button) tableRow.getChildAt(j);
                if (!button.isEnabled())
                    positions[i][j] = button.getText().toString();
            }
        }
    }

    public void ValidatePositions() {
        String count = "";
        for(int i = 0; i < positions.length; i++) {
            for(int j = 0; j < positions.length; j++) {
                if(positions[i][j] != null)
                    count = count + positions[i][j];
            }
            switch(count) {
                case "XXX":
                    ShowWinner("X", "Row", i);
                    break;
                case "OOO":
                    ShowWinner("O", "Row", i);
                    break;
                default:
                    count = "";
            }
        }

        count = "";
        for(int i = 0; i < positions.length; i++) {
            for(int j = 0; j < positions.length; j++) {
                if(positions[j][i] != null)
                    count = count + positions[j][i];
            }
            switch(count) {
                case "XXX":
                    ShowWinner("X", "Column", i);
                    break;
                case "OOO":
                    ShowWinner("O", "Column", i);
                    break;
                default:
                    count = "";
            }
        }

        count = "";
        for(int i = 0, j = 0; i < positions.length && j < positions.length; i++, j++) {
            if(positions[i][j] != null)
                count = count + positions[i][j];
        }
        switch(count) {
            case "XXX":
                ShowWinner("X", "D1", 1);
                break;
            case "OOO":
                ShowWinner("O", "D1", 1);
                break;
            default:
                count = "";
        }

        count = "";
        for(int i = 0, j = 2; i < positions.length && j < positions.length; i++, j--) {
                if (positions[i][j] != null)
                    count = count + positions[i][j];
        }
        switch(count) {
            case "XXX":
                ShowWinner("X", "D2", 1);
                break;
            case "OOO":
                ShowWinner("O", "D2", 1);
                break;
            default:
                count = "";
        }

    }

    public void ShowWinner(String player, String align, int num) {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout1);
        if("Row".equals(align)) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(num);
            messageText.setText(getString(R.string.winner) + player);
            for (int i = 0; i < tableRow.getChildCount(); i++) {
                tableRow.getChildAt(i).setBackgroundColor(Color.YELLOW);
            }
        }
        else if("Column".equals(align)){
            messageText.setText(getString(R.string.winner) + player);

            for(int i = 0; i < tableLayout.getChildCount(); i++) {
                TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
                tableRow.getChildAt(num).setBackgroundColor(Color.YELLOW);
            }
        }
        else if("D1".equals(align)) {
            messageText.setText(getString(R.string.winner) + player);
            for(int i = 0; i < tableLayout.getChildCount(); i++) {
                TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
                tableRow.getChildAt(i).setBackgroundColor(Color.YELLOW);
            }
        }
        else if("D2".equals(align)) {
            messageText.setText(getString(R.string.winner) + player);

            for(int i = 0; i < tableLayout.getChildCount(); i++) {
                TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
                tableRow.getChildAt(Math.abs(2-i)).setBackgroundColor(Color.YELLOW);
            }
        }

        DisableAllButtons();
    }

    public void DisableAllButtons() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout1);
        for(int i = 0; i <tableLayout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            for(int j = 0; j < tableRow.getChildCount(); j++) {
                Button button = (Button) tableRow.getChildAt(j);
                button.setEnabled(false);
            }
        }
    }

    public void ResetGame(View view) {
        STATUS = true;
        messageText.setText("");
        for(String[] temp : positions)
            Arrays.fill(temp, "");
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout1);
        for(int i = 0; i <tableLayout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            for(int j = 0; j < tableRow.getChildCount(); j++) {
                Button button = (Button) tableRow.getChildAt(j);
                button.setText("");
                button.setEnabled(true);
                button.setBackgroundResource(android.R.drawable.btn_default);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        STATUS = true;
    }
}
