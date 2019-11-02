package com.example.kdejava;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
<<<<<<< HEAD

=======
//import androidx.annotation.NonNull;
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

<<<<<<< HEAD
=======

>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {
    private static final String TAG = "EmailPassword";
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private EditText mEmailField;
    private EditText mPasswordField;
<<<<<<< HEAD

    private FirebaseAuth mAuth;

=======
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD


        Intent intent = new Intent(MainActivity.this,NotificationService.class);
        startService(intent);


=======
        // Views
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
        mStatusTextView = findViewById(R.id.status);
        mDetailTextView = findViewById(R.id.detail);
        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);
<<<<<<< HEAD

=======
        // Buttons
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
        findViewById(R.id.emailSignInButton).setOnClickListener(this);
        findViewById(R.id.emailCreateAccountButton).setOnClickListener(this);
        findViewById(R.id.signOutButton).setOnClickListener(this);
        findViewById(R.id.verifyEmailButton).setOnClickListener(this);
<<<<<<< HEAD

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

=======
        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }
    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
<<<<<<< HEAD

=======
        //showProgressDialog();
        // [START create_user_with_email]
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
<<<<<<< HEAD

=======
                            // Sign in success, update UI with the signed-in user's information
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            finish();
                        } else {
<<<<<<< HEAD

=======
                            // If sign in fails, display a message to the user.
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);

                        }
<<<<<<< HEAD

                    }
                });

    }

    //로그인
=======
                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
<<<<<<< HEAD

=======
        //showProgressDialog();
        // [START sign_in_with_email]
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
<<<<<<< HEAD
                        if (task.isSuccessful()) { //로그인 성공시

=======
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent intent = new Intent(getApplicationContext(),ListActivity.class);
                            intent.putExtra("userid",mEmailField.getText().toString());
<<<<<<< HEAD
                        } else {

=======
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "아이디 또는 비밀번호를 잘못 입력하셨습니다.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
<<<<<<< HEAD

                        if (!task.isSuccessful()) {
                            mStatusTextView.setText(R.string.auth_failed);
                        }

                    }
                });

=======
                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            mStatusTextView.setText(R.string.auth_failed);
                        }
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
    }
    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }
    private void sendEmailVerification() {
<<<<<<< HEAD

        findViewById(R.id.verifyEmailButton).setEnabled(false);

=======
        // Disable button
        findViewById(R.id.verifyEmailButton).setEnabled(false);
        // Send verification email
        // [START send_email_verification]
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {

                    @Override

                    public void onComplete(@NonNull Task<Void> task) {
<<<<<<< HEAD

=======
                        // [START_EXCLUDE]
                        // Re-enable button
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
                        findViewById(R.id.verifyEmailButton).setEnabled(true);
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(MainActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
<<<<<<< HEAD

                    }

                });

=======
                        // [END_EXCLUDE]
                    }

                });
        // [END send_email_verification]
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
    }
    private boolean validateForm() {
        boolean valid = true;
        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }
        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }
        return valid;
    }
    private void updateUI(FirebaseUser user) {
<<<<<<< HEAD

=======
        //hideProgressDialog();
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
        if (user != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
            findViewById(R.id.emailPasswordButtons).setVisibility(View.GONE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.GONE);
            findViewById(R.id.signedInButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.verifyEmailButton).setEnabled(!user.isEmailVerified());
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);
            findViewById(R.id.emailPasswordButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.VISIBLE);
            findViewById(R.id.signedInButtons).setVisibility(View.GONE);
        }
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.emailCreateAccountButton) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.emailSignInButton) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.signOutButton) {
            signOut();
        } else if (i == R.id.verifyEmailButton) {
            sendEmailVerification();
        }
    }
}