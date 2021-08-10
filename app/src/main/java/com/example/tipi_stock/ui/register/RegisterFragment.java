package com.example.tipi_stock.ui.register;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tipi_stock.MainActivity;
import com.example.tipi_stock.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 *
 */
public class RegisterFragment extends Fragment {

    private FirebaseAuth authenticator;
    private Button registerButton;
    private TextInputLayout emailTextInput, passwordTextInput, confirmPasswordTextInput;
    private View rootView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.register_fragment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        registerButton = rootView.findViewById(R.id.register_button);
        emailTextInput = rootView.findViewById(R.id.email_text_field);
        passwordTextInput = rootView.findViewById(R.id.password_text_field);
        confirmPasswordTextInput = rootView.findViewById(R.id.check_password_text_field);

        authenticator = FirebaseAuth.getInstance();

        registerButton.setOnClickListener( registerButton -> {
            String emailText = emailTextInput.getEditText().getText().toString();
            String passwordText = passwordTextInput.getEditText().getText().toString();
            String checkPasswordText = confirmPasswordTextInput.getEditText().getText().toString();

            if (!checkFormInput(emailText, passwordText, checkPasswordText)) {
                Toast.makeText(getActivity(), "Register error, check fields.",
                        Toast.LENGTH_LONG).show();
            } else {
                createNewAccount(emailText, passwordText);
            }
        });

    }

    public boolean checkFormInput(String emailText, String passwordText, String checkPasswordText) {
        boolean isPopulated = true;
        if (emailText.isEmpty()) {
            emailTextInput.setError("Field required!");
            isPopulated = false;
        }

        if (passwordText.isEmpty()) {
            passwordTextInput.setError("Field required");
            isPopulated = false;
        }

        if (!passwordText.equals(checkPasswordText)) {
            passwordTextInput.setError("Passwords dont match");
            isPopulated = false;
        }

        return isPopulated;
    }

    /**
     * Method for attempting to sign up a new user with firebase cloud storaged
     *
     * @param newEmail user inputted email
     * @param newPassword user inputted password
     */
    private void createNewAccount(String newEmail, String newPassword) {
        authenticator.createUserWithEmailAndPassword(newEmail, newPassword)
                .addOnCompleteListener(requireActivity(), (OnCompleteListener<AuthResult>) signupTask -> {
                    // Authentication was successful
                    if (signupTask.isSuccessful()) {
                        FirebaseUser user = authenticator.getCurrentUser();
                        NavHostFragment.findNavController(this).navigate(
                                R.id.action_registerFragment_to_loginFragment);
                    } else {
                        // Authentication failed with the firebase DB
                        Toast.makeText(getActivity(), "Authentication failed!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}