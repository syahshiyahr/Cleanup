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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cleanup.MainActivity;
import com.example.cleanup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment implements View.OnClickListener  {
    private FirebaseAuth auth;
    private MaterialTextView btnRegister;
    private MaterialButton btnLogin;
    private TextInputLayout edtEmail, edtPass;
    private ProgressBar progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnRegister = view.findViewById(R.id.tv_sign_up_direction);
        btnLogin = view.findViewById(R.id.btn_login);
        edtEmail = view.findViewById(R.id.til_email);
        edtPass = view.findViewById(R.id.til_pass);
        progress = view.findViewById(R.id.progress);


        auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_sign_up_direction:
                RegisterFragment mRegisterFragment = new RegisterFragment();
                FragmentManager mFragmentManager = getFragmentManager();
                if (mFragmentManager != null) {
                    mFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_container, mRegisterFragment, RegisterFragment.class.getSimpleName())
                            .addToBackStack(null)
                            .commit();
                }
                break;
            case R.id.btn_login:
                progress.setVisibility(View.VISIBLE);
                String email = edtEmail.getEditText().getText().toString().trim();
                String password = edtPass.getEditText().getText().toString().trim();

                if(!isValidLogin(email, password))
                    progress.setVisibility(View.GONE);
                else
                    loginUser(email, password);
                break;



        }
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    requireActivity().finish();
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

    private boolean isValidLogin(String email, String password) {
        String empty = "Please fill this field";
        String invalid = "Invalid Email Address";

        if(email.isEmpty()){
            edtEmail.setError(empty);
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError(invalid);
            return false;
        }else if(password.isEmpty()){
            edtPass.setError(empty);
            return false;
        }
        else {
            edtEmail.setError(null);
            edtPass.setError(null);

            edtEmail.isErrorEnabled();
            edtPass.isErrorEnabled();
            return true;
        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        if(user != null){
//            Intent intent = new Intent(getContext(), MainActivity.class);
//            startActivity(intent);
//            requireActivity().finish();
//        }
//    }
}