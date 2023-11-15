package com.apunao.elmirador;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private ProgressBar pb;
    private LinearLayout login;
    private Button btnLogin, btnNuevo;
    private EditText mail,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        user = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();
        pb = findViewById(R.id.progressBar2);
        login = findViewById(R.id.login);
        btnLogin = findViewById(R.id.btIngresar);
        btnNuevo = findViewById(R.id.btResgistrarse);
        mail = (EditText) findViewById(R.id.nuevoEmail);
        pass = (EditText) findViewById(R.id.nuevoPass);


        if (user != null) {
            pb.setVisibility(View.VISIBLE);
            login.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(Splash.this, MainActivity.class);
            startActivity(intent);
            finish();


        } else {
            pb.setVisibility(View.INVISIBLE);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pb.setVisibility(View.VISIBLE);
                    String txtMail = mail.getText().toString();
                    String txtPass = pass.getText().toString();

                    if (TextUtils.isEmpty(txtMail) || TextUtils.isEmpty(txtPass)) {
                        Toast.makeText(Splash.this, "COMPLETAR TODOS LOS DATOS", Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.INVISIBLE);
                    } else {
                        auth.signInWithEmailAndPassword(txtMail, txtPass)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(Splash.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else
                                            Toast.makeText(Splash.this, "OPSSS... ALGO PUSO MAL !!!", Toast.LENGTH_SHORT).show();
                                        pb.setVisibility(View.INVISIBLE);
                                    }
                                });
                    }
                }
            });
            btnNuevo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Splash.this, RegistrarseActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

}
