package com.amee.sample.roomDB.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.amee.sample.roomDB.DataBinding.Listener;
import com.amee.sample.roomDB.R;
import com.amee.sample.roomDB.RoomDatabase.LoginTable;
import com.amee.sample.roomDB.ViewModel.LoginViewModel;
import com.amee.sample.roomDB.databinding.ActivityMainBinding;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements Listener {

    private LoginViewModel loginViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        binding.setClickListener((MainActivity) this);

        loginViewModel = ViewModelProviders.of(MainActivity.this).get(LoginViewModel.class);

        loginViewModel.getGetAllData().observe(this, new Observer<List<LoginTable>>() {
            @Override
            public void onChanged(@Nullable List<LoginTable> data) {

                try {
                    binding.lblEmailAnswer.setText((Objects.requireNonNull(data).get(0).getEmail()));
                    binding.lblPasswordAnswer.setText((Objects.requireNonNull(data.get(0).getPassword())));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public void onClick(View view) {

        String strEmail = binding.txtEmailAddress.getText().toString().trim();
        String strPassword = binding.txtPassword.getText().toString().trim();

        LoginTable data = new LoginTable();

        if (TextUtils.isEmpty(strEmail)) {
            binding.txtEmailAddress.setError("Please Enter Your E-mail Address");
            Toast.makeText(this, "Please Enter Your E-mail Address", Toast.LENGTH_SHORT).show();
        }else if(!(Patterns.EMAIL_ADDRESS.matcher(strEmail).matches())){
            binding.txtEmailAddress.setError("Please Enter a Valid E-mail Address");
            Toast.makeText(this, "Please Enter a Valid E-mail Address", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(strPassword)) {
            binding.txtPassword.setError("Please Enter Your Password");
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
        }
        else {
            data.setEmail(strEmail);
            data.setPassword(strPassword);
            loginViewModel.insert(data);

        }

    }
}
