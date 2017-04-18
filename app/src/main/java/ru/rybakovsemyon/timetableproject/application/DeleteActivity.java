package ru.rybakovsemyon.timetableproject.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.rybakovsemyon.timetableproject.R;

public class DeleteActivity extends AppCompatActivity {

    private String choose = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);
        Intent intentGet = getIntent();
        choose = intentGet.getStringExtra("_id");
        Button button = (Button) findViewById(R.id.btn_ok);
        button.setVisibility(View.GONE);
        TextView textView = (TextView) findViewById(R.id.delete_tittle);
        textView.setText(getString(R.string.delete_not));
        long _id = Long.parseLong(choose);
        if (_id != -1){
            textView.setText(getString(R.string.delete_tittle));
            button.setVisibility(View.VISIBLE);
        }
    }

    public void btn_cancel(View view) {
        finish();
    }

    public void btn_ok(View view) {
        Intent intent = new Intent();
        intent.putExtra("db_id", choose);
        setResult(RESULT_OK, intent);
        finish();
    }
}
