package com.course.tvapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{
    DBHelper DB, DB1;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View v = findViewById(R.id.reg_back);
        v.setOnClickListener(this);
        DB = new DBHelper(this);
        DB1 = new DBHelper(this);


    }


    @Override
    public void onClick(View arg0) {
        if (arg0.getId() == R.id.reg_back) {
            //define a new Intent for the second Activity
            Intent intent = new Intent(this, MainActivity.class);

            //start the second Activity
            this.startActivity(intent);
        }
    }

    public void onClickReg(View arg0) {
        EditText nick;
        EditText pass_1;
        EditText pass_2;
        EditText email;
        Button reg;

        nick = findViewById(R.id.nickname);
        pass_1 = findViewById(R.id.password);
        pass_2 = findViewById(R.id.conf_pass);
        email = findViewById(R.id.email);
        reg = findViewById(R.id.reg_in_log);

        String user = nick.getText().toString();
        String pass = pass_1.getText().toString();
        String repass = pass_2.getText().toString();
        String e_mail= email.getText().toString();

        if (isEmpty(nick)) {
            nick.setError("Nickname can`t be empty!");
        }

        if (isEmpty(pass_1)) {
            pass_1.setError("Password can`t be empty!");
        }
        if (!isSame(pass_1, pass_2)) {
            pass_2.setError("Passwords must match!");
        }
        if (!isEmail(email)) {
            email.setError("Please enter current email!");
        }

        else{
            if(pass.equals(repass)){
                Boolean checkuser = DB.checkusername(user);
                Boolean checkuser1 = DB1.checkusername(e_mail);
                if(checkuser==false && checkuser1 == false && !user.equals("admin")){
                    Boolean insert = DB.insertData(user, pass);
                    Boolean insert1 = DB1.insertData(e_mail, pass);

                    if(insert==true && insert1==true){
                        Toast.makeText(RegistrationActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    nick.setError("Provided nickname or e-mail already in use!!");
                }
            }
        }

    }


    boolean isEmail(EditText text) {
        CharSequence e_mail = text.getText().toString();
        return (!TextUtils.isEmpty(e_mail) && Patterns.EMAIL_ADDRESS.matcher(e_mail).matches());
    }

    boolean isSame(EditText text1, EditText text2) {
        CharSequence txt1 = text1.getText().toString();
        CharSequence txt2 = text2.getText().toString();
        if (txt1.equals(txt2)) {
            return true;
        }
        else {
            return false;
        }
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}


