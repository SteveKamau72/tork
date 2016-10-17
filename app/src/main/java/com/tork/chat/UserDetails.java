package com.tork.chat;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.tork.AccountPreferences;
import com.tork.R;
import com.tork.SignIn;

/**
 * Created by steve on 10/13/16.
 */
public class UserDetails extends DialogFragment implements GoogleApiClient.OnConnectionFailedListener {
    // Default maximum disk usage in bytes
    private static final int DEFAULT_DISK_USAGE_BYTES = 25 * 1024 * 1024;
    // Default cache folder name
    private static final String DEFAULT_CACHE_DIR = "photos";
    Activity activity;
    AccountPreferences ap;
    View v;
    ImageLoader.ImageCache imageCache;
    ImageLoader imageLoader;
    private ProgressDialog mProgressDialog;
    private GoogleApiClient mGoogleApiClient;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.user_dialog_layout, container, false);
        activity = getActivity();
        //request window without title
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        showDialog(v);
        return v;

    }

    private void showDialog(View v) {

        //initialize read from preferences
        ap = new AccountPreferences(activity);
        TextView name = (TextView) v.findViewById(R.id.name);
        TextView email = (TextView) v.findViewById(R.id.email);
        name.setText(ap.getUserName());
        email.setText(ap.getEmail());
        CircularImageNetworkView imgView = (CircularImageNetworkView) v.findViewById(R.id.profile);
        imageLoader = ChatApplication.getInstance().getImageLoader();
        imgView.setImageUrl(ap.getImageUrl(), imageLoader);
        Button sign_out = (Button) v.findViewById(R.id.btn_sign_out);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

    }

    private void signOut() {
        showProgressDialog();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
//                        updateUI(false);
                        hideProgressDialog();
                        dismiss();
                        ap.setLoggedIn(false);
                        Intent i = new Intent(activity, SignIn.class);
                        startActivity(i);
                        activity.finish();
                    }
                });

    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Logging out...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
}
