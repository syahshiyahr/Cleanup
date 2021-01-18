package com.example.cleanup.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cleanup.LoginActivity;
import com.example.cleanup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private FirebaseAuth auth;
    private MaterialTextView btnLogin;
    private MaterialButton btnRegister;
    private TextInputLayout edtEmail, edtName, edtPass, edtConfirmPass;
    private ProgressBar progress;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogin = view.findViewById(R.id.tv_login_direction);
        btnRegister = view.findViewById(R.id.btn_register);
        edtName = view.findViewById(R.id.til_name);
        edtEmail = view.findViewById(R.id.til_email);
        edtPass = view.findViewById(R.id.til_pass);
        edtConfirmPass = view.findViewById(R.id.til_confirm_pass);
        progress = view.findViewById(R.id.progress);

        auth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_direction:
                LoginFragment mLoginFragment = new LoginFragment();
                FragmentManager mFragmentManager = getFragmentManager();
                if (mFragmentManager != null) {
                    mFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_container, mLoginFragment, LoginFragment.class.getSimpleName())
                            .addToBackStack(null)
                            .commit();
                }
                break;
            case R.id.btn_register:
                progress.setVisibility(View.VISIBLE);
                String name = edtName.getEditText().getText().toString().trim();
                String email = edtEmail.getEditText().getText().toString().trim();
                String password = edtPass.getEditText().getText().toString().trim();
                String confirm = edtConfirmPass.getEditText().getText().toString().trim();

                if(!isValidRegister(name, email, password, confirm))
                    progress.setVisibility(View.GONE);
                else
                    registerUser(name, email, password, confirm);
                break;
        }
    }

    private void registerUser(String name, String email, String password, String confirm) {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //jika gagal register do something
                        if (task.isSuccessful()){
                            LoginFragment mLoginFragment = new LoginFragment();
                            FragmentManager mFragmentManager = getFragmentManager();
                            if (mFragmentManager != null) {
                                mFragmentManager
                                        .beginTransaction()
                                        .replace(R.id.frame_container, mLoginFragment, LoginFragment.class.getSimpleName())
                                        .addToBackStack(null)
                                        .commit();
                            }

                            Toast.makeText(getContext(),
                                    "Login first",
                                    Toast.LENGTH_LONG).show();
                        }else {

                            Toast.makeText(getContext(),
                                    "Failed Registration: "+ task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        progress.setVisibility(View.GONE);
                    }
                }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progress.setVisibility(View.GONE);
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidRegister(String name, String email, String password, String confirm) {
        String empty = "Please fill this field";
        String invalid = "Invalid Email Address";
        String notMatch = "Password Not Match";
        String passCharacter = "Password must be at least 6 characters";

        if(name.isEmpty()){
            edtName.setError(empty);
            return false;
        }
        else if (email.isEmpty()){
            edtEmail.setError(empty);
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError(invalid);
            return false;
        }
        else if (password.isEmpty()){
            edtPass.setError(empty);
            return false;
        }
        else if (password.length() < 6){
            edtPass.setError(passCharacter);
            return false;
        }
        else if (confirm.isEmpty()){
            edtConfirmPass.setError(empty);
            return false;
        }
        else if (!confirm.equals(password)){
            edtConfirmPass.setError(notMatch);
            return false;
        }
        else{
            edtName.setError(null);
            edtEmail.setError(null);
            edtPass.setError(null);
            edtConfirmPass.setError(null);

            edtName.isErrorEnabled();
            edtEmail.isErrorEnabled();
            edtPass.isErrorEnabled();
            edtConfirmPass.isErrorEnabled();
            return true;
        }
    }
}