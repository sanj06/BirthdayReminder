package com.example.birthdayreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import java.util.Locale;
public class MainActivity extends AppCompatActivity {
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    EditText et;
    ListView lv;
    FloatingActionButton fb;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    static int stage_variable=0;
    static String values = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listview);
        fb = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,arrayList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);

                // Get the Layout Parameters for ListView Current Item View

                ViewGroup.LayoutParams params = view.getLayoutParams();

                // Set the height of the Item View
                params.height = 150;
                view.setLayoutParams(params);

                return view;
            }
        };;
        lv.setAdapter(adapter);
        onClick();
    }

    public  void onClick(){
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                values = "";
                startVoiceInput();

            }
        });
    }


    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi! Who's birthday is it?");
        stage_variable=1;
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }

    }


    private void secondVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Okay. When is their birthday?");
        stage_variable=2;
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {


                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(stage_variable == 1){
                        String name = result.get(0);
                        values = values + name +  "                          ";
                        secondVoiceInput();

                    }
                    else if(stage_variable == 2){
                        String dob = result.get(0);
                        values = values + dob ;
                        stage_variable = 3;
                    }

                    if(stage_variable == 3){
                        //check if values are okay
                    }

                }
                break;
            }

        }
    }



}
