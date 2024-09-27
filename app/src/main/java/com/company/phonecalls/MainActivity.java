package com.company.phonecalls;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtPhone;
    Button btnCall;
    Button btnSave ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtPhone = findViewById(R.id.edt);
        btnCall = findViewById(R.id.btn);
        btnSave = findViewById(R.id.btn1);


        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = edtPhone.getText().toString();
                if (!phoneNumber.isEmpty()) {
                    makePhoneCall(phoneNumber);
                } else {
                    Toast.makeText(MainActivity.this, "Enter a number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String savePhoneNumber = edtPhone.getText().toString().trim();
                if (!savePhoneNumber.isEmpty()) {
                    savePhoneNumber(savePhoneNumber);
                } else {
                    Toast.makeText(MainActivity.this, "Enter a number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void makePhoneCall(String phoneNumber) {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }

    private void savePhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber);
        startActivity(intent);
    }
}
