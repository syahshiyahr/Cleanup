package com.example.cleanup.ui.profile;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cleanup.LoginActivity;
import com.example.cleanup.R;
import com.example.cleanup.adapter.UpcomingHomeAdapter;
import com.example.cleanup.ui.home.ReportFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    TextView name, reportHistory;
    CardView logout;
    DatabaseReference ref;
    String userId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.tv_name);
        reportHistory = view.findViewById(R.id.tv_history_reports);
        reportHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ReportFragment();
                Bundle bundle = new Bundle();
                bundle.putString("EXTRA_NAME", "profile");
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).commit();
            }
        });
        logout = view.findViewById(R.id.card_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent in = new Intent(getActivity(), LoginActivity.class);
                startActivity(in);
                requireActivity().finish();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("users");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ref.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nameUser = snapshot.child("name").getValue().toString();
                    name.setText(nameUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}