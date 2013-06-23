package com.tvkkpt.cinemapicks;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.tvkkpt.cinemapicks.utils.RobotoFontActivity;

/**
 * Copyright © Smart Cows Inc. All rights reserved.
 * Author: tvkkpt
 * Date: 12/31/12
 * Time: 2:10 PM
 */
public class AboutActivity extends RobotoFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button fb = (Button)findViewById(R.id.feedbackButton);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                String emailList[] = { "tvkkpt@gmail.com" };
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailList);
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "[MegaPicks] Phản hồi từ người dùng");
                emailIntent.setType("plain/text");
                startActivity(emailIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
