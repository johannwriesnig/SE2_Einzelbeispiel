package com.example.se2_einzelbeispiel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private int[] matrikelNummer;
    private TextView inputField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView view1 = findViewById(R.id.textView2);
        view1.setText("Gib hier deine Matrikelnummr ein");
        view1.setGravity(Gravity.CENTER);

        TextView serverMessageField = findViewById(R.id.serverMessage);
        serverMessageField.setText("Antwort vom Server");
        serverMessageField.setGravity(Gravity.CENTER);

        TextView sortedNumbersField = findViewById(R.id.sortedNumbers);
        sortedNumbersField.setText("Sortierte Matrikelnummer");
        sortedNumbersField.setGravity(Gravity.CENTER);

        inputField = findViewById(R.id.inputField);

        Button sortierenButton = findViewById(R.id.sortButton);
        sortierenButton.setText("Sortieren");
        sortierenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortedNumbersField.setText(String.valueOf(getSortedMatrikelNummer()));
                sortedNumbersField.setTextSize(18);
                sortedNumbersField.setGravity(Gravity.CENTER);
            }
        });

        Button abschickenButton = findViewById(R.id.sendButton);
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
                serverMessageField.setText(outputString);
                serverMessageField.setTextSize(18);
                serverMessageField.setGravity(Gravity.CENTER);
            }
        });

    }

    public int getSortedMatrikelNummer(){
        matrikelNummer = new int[inputField.getText().length()];

        for (int i = 0; i < matrikelNummer.length; i++) {
            matrikelNummer[i] = Integer.parseInt(String.valueOf(inputField.getText().charAt(i)));
        }

        groupByOddAndEvenNumbers(matrikelNummer);
        sortEvenNumbers(matrikelNummer);
        sortOddNumbers(matrikelNummer);

        return getNumberOutOfArray(matrikelNummer);

    }

    public int getNumberOutOfArray(int[] array){
        int number=0;
        for(int i=0; i<array.length;i++){
            number+=array[i];
            if(i==array.length-1)break;
            number*=10;
        }
        return number;
    }

    public void groupByOddAndEvenNumbers(int[] array){
        for (int i = 0; i < array.length; i++) {
        int currentDigit = array[i];
        if (currentDigit % 2 != 0) {
            int j = array.length - 1;
            while (j > i) {
                if (array[j] % 2 == 0) {
                    int aux = array[j];
                    array[j] = currentDigit;
                    array[i] = aux;
                    break;
                }
                j--;
            }
        }
    }}

    public void sortEvenNumbers(int [] array){
        for (int i = 0; i < array.length; i++) {
            int currentDigit = array[i];
            if(currentDigit%2!=0)break;
            int j = i;
            while (j > 0 && currentDigit <= array[j - 1]) {
                array[j] = array[j - 1];
                j--;
            }
            array[j] = currentDigit;
        }
    }
    public void sortOddNumbers(int [] array){
        for (int i = array.length-1; i > 0; i--) {
            int currentDigit = array[i];
            if(currentDigit%2==0)break;
            int j = i;
            while (j < array.length-1 && currentDigit >= array[j + 1]) {
                array[j] = array[j + 1];
                j++;
            }
            array[j] = currentDigit;
        }
    }

}