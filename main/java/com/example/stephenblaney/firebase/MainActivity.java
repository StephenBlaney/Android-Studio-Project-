package com.example.stephenblaney.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


    public class MainActivity extends AppCompatActivity implements View.OnClickListener{

        private Button buttonRegister;
        private EditText editTextEmail;
        private EditText editTextPassword;
        private TextView textViewSignin;
        private ProgressDialog progressDialog;
        private FirebaseAuth firebaseAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            firebaseAuth = FirebaseAuth.getInstance();

            if(firebaseAuth.getCurrentUser()!=null){
                //Profile activity

                finish();
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

            }

            progressDialog = new ProgressDialog(this);

            buttonRegister = (Button) findViewById(R.id.buttonRegister);

            editTextEmail = (EditText) findViewById(R.id.editTextEmail);
            editTextPassword = (EditText) findViewById(R.id.editTextPassword);


            textViewSignin = (TextView) findViewById(R.id.textViewSignin);


            buttonRegister.setOnClickListener(this);
            textViewSignin.setOnClickListener(this);

        }

        private void registerUser(){

            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();


            if (TextUtils.isEmpty(email)){
                //email is empty
                Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
                //stopping the function execution further
                return;

            }

            if (TextUtils.isEmpty(password)){
                //email is empty
                Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
                //stopping the function execution further
                return;

            }
            //If validations are ok
            //show progress bar
            progressDialog.setMessage("Registering User...");
            progressDialog.show();


            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                //User is successfully registered and logged in

                                    //Profile activity

                                    finish();
                                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class));






                            }

                            else{

                                Toast.makeText(MainActivity.this, "Failed to register" , Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();


                            }

                        }
                    });
        }

        @Override
        public void onClick(View v) {
            if(v == buttonRegister){

                registerUser();
            }

            if(v == textViewSignin){
                //will open login Activity here
                startActivity(new Intent(this,LoginActivity.class));

            }

        }
    }
