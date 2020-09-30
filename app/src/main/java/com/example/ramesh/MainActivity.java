package com.example.ramesh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import com.example.ramesh.madel.users;
import com.example.ramesh.prevalent.prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button joinnowButton;
    private Button loginnowButton;
    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinnowButton=(Button) findViewById(R.id.main_join_now_btn);
        loginnowButton=(Button) findViewById(R.id.main_login_btn);
        LoadingBar=new ProgressDialog(this);

        Paper.init(this);

        loginnowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)


            {
                Intent intent=new Intent(MainActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        joinnowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        String UserPhoneKey=Paper.book().read(prevalent.UserPhoneKey);
        String UserPasswordKey=Paper.book().read(prevalent.UserPasswordKey);
        if(UserPhoneKey != "" && UserPasswordKey != "")
        {
            if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey))
            {
                AllowAccess(UserPhoneKey, UserPasswordKey);

                LoadingBar.setTitle("Already Logged In ");
                LoadingBar.setMessage("Please wait....");
                LoadingBar.setCanceledOnTouchOutside(false);
                LoadingBar.show();

            }
        }

    }
    private void AllowAccess(final String phone, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.child("Users").child(phone).exists())
                {
                    users userdata = snapshot.child("Users").child(phone).getValue(users.class);

                    if (userdata.getPhone().equals(phone))
                    {
                        if (userdata.getPassword().equals(password))
                        {
                            Toast.makeText(MainActivity.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                            LoadingBar.dismiss();
                            Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            LoadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}

