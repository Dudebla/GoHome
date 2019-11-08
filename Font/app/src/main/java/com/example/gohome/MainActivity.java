package com.example.gohome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gohome.Member.Activity.MemberHomeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        init();
    }



    private void init(){
        Button btn_user = findViewById(R.id.btn_user);
        Button btn_organizer = findViewById(R.id.btn_organizer);
        Button btn_member = findViewById(R.id.btn_member);

        btn_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MemberHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
