package com.example.tipi_stock.ui.login;

import android.os.Bundle;
import android.os.Handler;
import android.util.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.tipi_stock.MainActivity;
import com.example.tipi_stock.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    View rootView;
    private FirebaseAuth firebaseAuthenticator;
    TextInputLayout emailTextLayout, passwordTextLayout;
    private Button loginButton, registerButton;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.login_fragment, null);


        Handler handle = new Handler();
        handle.postDelayed(() -> {
            BottomNavigationView navigationView = container.getRootView().findViewById(R.id.nav_view);
            navigationView.setVisibility(View.GONE);
        }, 0);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        firebaseAuthenticator = FirebaseAuth.getInstance();
        loginButton = rootView.findViewById(R.id.login_button);
        registerButton = rootView.findViewById(R.id.register_button);
        emailTextLayout = rootView.findViewById(R.id.login_email_text);
        passwordTextLayout = rootView.findViewById(R.id.password_text_field);

        // On click listener for the register button
        registerButton.setOnClickListener(registerView -> {
            NavHostFragment.findNavController(this).navigate(
                    R.id.action_loginFragment_to_registerFragment);
        });

        // On click listener for the login button
        loginButton.setOnClickListener(loginView -> {
            String passwordText = passwordTextLayout.getEditText().getText().toString();
            String emailText = emailTextLayout.getEditText().getText().toString();
            boolean validInput = true;
            passwordTextLayout.setError("");
            emailTextLayout.setError("");

            // Check for input in the password field
            if (passwordText.isEmpty()) {
                passwordTextLayout.setError("Field requires input!");
                validInput = false;
            }

            // Check for input in the email field
            if (emailText.isEmpty()) {
                emailTextLayout.setError("Field requires input!");
                validInput = false;
            }

            // Check that the inputted email matches email format
            if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                emailTextLayout.setError("Not a valid email format!");
                validInput = false;
            }

            if (validInput == true) {
                userSignIn(emailText, passwordText);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.hideNavigation(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.hideNavigation(false);
        }
    }

    /**
     * Method that uses the firebase services to confirm user inputted credentials
     * @param userEmail
     * @param userPassword
     */
    private void userSignIn(String userEmail, String userPassword) {
        firebaseAuthenticator.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(requireActivity(), (OnCompleteListener<AuthResult>) task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuthenticator.getCurrentUser();
                        NavHostFragment.findNavController(this).navigate(
                                R.id.action_loginFragment_to_booking_nav);
                    } else {
                        Toast.makeText(getActivity(), "Credentials didn't match, try again or register!",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
