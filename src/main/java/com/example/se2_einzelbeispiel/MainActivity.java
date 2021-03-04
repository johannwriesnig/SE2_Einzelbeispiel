package com.example.se2_einzelbeispiel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView view1 = findViewById(R.id.textView2);
        view1.setText("Gib hier deine Matrikelnummr ein");
        view1.setGravity(Gravity.CENTER);

        TextView view2 = findViewById(R.id.textView3);
        view2.setText("Antwort vom Server");
        view2.setGravity(Gravity.CENTER);

        TextView inputField = findViewById(R.id.editTextNumber);

        Button abschickenButton = findViewById(R.id.button);
        abschickenButton.setText("Abschicken");
        abschickenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputString = inputField.getText().toString();
                String outputString = "";
                Client client = new Client(inputString);
                Thread thread = new Thread(client);
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputString = client.getServerCalculation();
                view2.setText(outputString);
                view2.setTextSize(18);
                view2.setGravity(Gravity.CENTER);
            }
        });

    }
}