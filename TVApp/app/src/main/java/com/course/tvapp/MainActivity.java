package com.course.tvapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DBHelper DB, DB1;
    CheckBox remember;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private EditText nick;
    private EditText pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        View v = findViewById(R.id.reg_in_log);
        v.setOnClickListener(this);
        DB = new DBHelper(this);
        DB1 = new DBHelper(this);

        remember = findViewById(R.id.remember);
        nick = findViewById(R.id.nickname_in_log);
        pass = findViewById(R.id.password_in_log);

        mPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        mPreferences = getSharedPreferences("com.course.tvapp", Context.MODE_PRIVATE);
        mEditor =mPreferences.edit();

        checkSharedPreferences();
    }

    @Override
    public void onClick(View arg0) {
        if (arg0.getId() == R.id.reg_in_log) {
            //define a new Intent for the second Activity
            Intent intent = new Intent(this, RegistrationActivity.class);

            //start the second Activity
            this.startActivity(intent);
        }
    }

    public void onClickLogin(View arg0) {

        String nickname = nick.getText().toString();
        String password = pass.getText().toString();

        if (remember.isChecked()) {
            mEditor.putString(getString(R.string.remember), "True");
            mEditor.commit();

            mEditor.putString(getString(R.string.name), nickname);
            mEditor.commit();

            mEditor.putString(getString(R.string.password), password);
            mEditor.commit();
        }
        else{
            mEditor.putString(getString(R.string.remember), "False");
            mEditor.commit();

            mEditor.putString(getString(R.string.name), "");
            mEditor.commit();

            mEditor.putString(getString(R.string.password), "");
            mEditor.commit();

        }
        if (nickname.equals("admin") && password.equals("admin")) {
            Intent intent = new Intent(this, AdminActivity.class);

            //start the second Activity
            this.startActivity(intent);
        } else {


            if (nickname.equals("") || password.equals("")) {
                Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            }
            else if(isEmail(nickname)) {
                Boolean checkemail = DB1.checkusernamepassword(nickname, password);

                if(checkemail==true){
                    Toast.makeText(MainActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(this, UserActivity.class);
                    this.startActivity(intent);
                }

                else{
                    Toast.makeText(MainActivity.this, "Invalid e-mail or password", Toast.LENGTH_SHORT).show();
                }
            }

            else {
                Boolean checkuserpass = DB.checkusernamepassword(nickname, password);


                if (checkuserpass == true) {
                    Toast.makeText(MainActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, UserActivity.class);
                    this.startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid nickname or password", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }


    private void checkSharedPreferences() {
        String checkbox = mPreferences.getString(getString(R.string.remember), "False");
        String name = mPreferences.getString(getString(R.string.name), "");
        String password = mPreferences.getString(getString(R.string.password), "");


        nick.setText(name);
        pass.setText(password);

        if (checkbox.equals("True")) {
            remember.setChecked(true);
        } else {
            remember.setChecked(false);
        }

    }
        boolean isEmail (String e_mail){
            return (!TextUtils.isEmpty(e_mail) && Patterns.EMAIL_ADDRESS.matcher(e_mail).matches());
        }

        public void hideKeyboard (View view){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

}
