package com.example.texttospeechapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private EditText editTextInput;
    private Button buttonSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextInput = findViewById(R.id.editTextInput);
        buttonSpeak = findViewById(R.id.buttonSpeak);

        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    // Set the language for speech synthesis
                    int langResult = textToSpeech.setLanguage(Locale.US);
                    if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(MainActivity.this, "Language not supported", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Initialization failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Button click event to trigger text-to-speech
        buttonSpeak.setOnClickListener(view -> {
            String text = editTextInput.getText().toString();
            if (!text.isEmpty()) {
                speakText(text);
            } else {
                Toast.makeText(MainActivity.this, "Please enter text", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to convert text to speech
    private void speakText(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    protected void onDestroy() {
        // Shutdown TextToSpeech to free resources
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
