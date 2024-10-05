package com.company.phonecalls;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtPhone;
    EditText edtPhone1;

    Button btnCall;
    Button btnSave ;
    Button btnSend;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtPhone = findViewById(R.id.edt);
        edtPhone1 = findViewById(R.id.edt1);
        btnCall = findViewById(R.id.btn);
        btnSave = findViewById(R.id.btn1);
        btnSend = findViewById(R.id.btn2);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        }


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

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = edtPhone.getText().toString();
                String message = edtPhone1.getText().toString();
                if (!TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(message) ) {
                    sendsms(phoneNumber,message);
                }
                else {
                    Toast.makeText(MainActivity.this, "Enter a number or SMS ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendsms(String phoneNumber, String message) {
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "sms send", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(this, "sms failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
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
