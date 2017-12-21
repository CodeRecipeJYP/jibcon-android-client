package com.asuscomm.smarts.jibcon_client_android.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.asuscomm.smarts.jibcon_client_android.services.MyFirebaseInstanceIDService;
import com.asuscomm.smarts.jibcon_client_android.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class SigningActivity extends AppCompatActivity {
    private static final String TAG = "SigningActivity";
    private static final int RC_SIGN_IN = 123;
    private TextView tvToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signing);

        Log.d(TAG, "onCreate: ");

        tvToken = findViewById(R.id.tv_token);

        startAuthUI();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Signout Clicked");
                signout();
            }
        });
    }

    private void signout() {
        Log.d(TAG, "signout: ");
        AuthUI.getInstance()
                .signOut(SigningActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    private void onSigninSuccess() {
        Log.d(TAG, "onSigninSuccess: ");

        // Successfully signed in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(TAG, "onSigninSuccess: user=" + user);
        tvToken.setText(MyFirebaseInstanceIDService.getToken());

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void startAuthUI() {
        Log.d(TAG, "startAuthUI: ");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null && !currentUser.isAnonymous()) {
            Log.d(TAG, "startAuthUI: already signed in, uid=" + currentUser.getUid());
            onSigninSuccess();
        } else {
            // Choose authentication providers
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                    new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
//                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                    new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build());
            onSigninSuccess();
            // Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == ResultCodes.OK) {
                onSigninSuccess();
            } else {
                // Sign in failed, check response for error code
                // ...
                Log.d(TAG, "onActivityResult: signin failed");
            }
        }
    }

}
