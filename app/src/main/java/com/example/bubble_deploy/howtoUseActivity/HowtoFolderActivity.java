package com.example.bubble_deploy.howtoUseActivity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import com.example.bubble_deploy.R;

@RequiresApi(api = Build.VERSION_CODES.M)

public class HowtoFolderActivity extends HowtoUseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howtofolder);  // layout xml 과 자바파일을 연결

        Button a = (Button) findViewById(R.id.how_close);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    } // end onCreate()

} // end MyTwo
