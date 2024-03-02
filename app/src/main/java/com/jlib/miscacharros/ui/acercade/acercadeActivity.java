package com.jlib.miscacharros.ui.acercade;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.jlib.miscacharros.R;

public class acercadeActivity extends AppCompatActivity {

    public TextView tAcerca;
    public Button volver;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acercade);

        tAcerca = findViewById(R.id.tAcercaDe);
        tAcerca.setText(getResources().getText(R.string.acercade)); // asi ponemos el texto guardado en el XML

        volver = findViewById(R.id.buttonVolver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
