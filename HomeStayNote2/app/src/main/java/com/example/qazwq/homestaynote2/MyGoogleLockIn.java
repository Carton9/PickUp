package com.example.qazwq.homestaynote2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by qazwq on 2017/7/5.
 */

public class MyGoogleLockIn implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener  {
    static final int GOOGLESIGNIN=5623298;
    MainPanel parentPanel;
    public GoogleApiClient mGoogleApiClient;
    String name;
    String email;
    String ID;
    boolean isLogIn;
    public class LogInDataPacket{
        public String name;
        public String email;
        public String ID;
    }
    public MyGoogleLockIn(MainPanel parentPanel){
        isLogIn=false;
        this.parentPanel=parentPanel;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(parentPanel)
                .enableAutoManage(parentPanel /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }
    @Override
    public void onClick(View v) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        parentPanel.startActivityForResult(signInIntent,GOOGLESIGNIN);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GOOGLESIGNIN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            name=acct.getDisplayName();
            email=acct.getEmail();
            ID=acct.getId();
            isLogIn=true;
        }
    }
    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(parentPanel.getApplicationContext(), "Account sign out",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(parentPanel.getApplicationContext(), "Google sign in failed",
                Toast.LENGTH_SHORT).show();
    }
}
