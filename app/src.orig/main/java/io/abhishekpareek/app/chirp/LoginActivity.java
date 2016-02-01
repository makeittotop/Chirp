package io.abhishekpareek.app.chirp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = SignupActivity.class.getSimpleName();

    @Bind(R.id.loginText)
    EditText mLoginText;
    @Bind(R.id.loginPasswordText)
    EditText mLoginPasswordText;
    @Bind(R.id.login_coordinator)
    CoordinatorLayout mLoginCoordinator;
    private Snackbar mSnackbar;
    @Bind(R.id.loginButton)
    Button mLoginButton;
    @Bind(R.id.signupTextView)
    TextView mSignupTextView;
    @Bind(R.id.resetPassTextView) TextView mResetPassTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Request spinner in the action bar
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSnackbar = Snackbar
                .make(mLoginCoordinator, "Passwords do not match", Snackbar.LENGTH_INDEFINITE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mSignupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginText = mLoginText.getText().toString().trim();
                final String passwordText = mLoginPasswordText.getText().toString().trim();

                String msg = "Empty fields not allowed";
                final Bundle bundle = new Bundle();

                if (loginText.isEmpty() || passwordText.isEmpty()) {
                    msg = "Empty fields not allowed";
                    bundle.putString("payload", msg);

                    AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
                    alertDialogFragment.setArguments(bundle);
                    alertDialogFragment.show(getFragmentManager(), "error_title");

                    mSnackbar.setText(msg).show();

                } else {
                    setProgressBarIndeterminateVisibility(true);

                    ParseUser.logInInBackground(loginText, passwordText, new LogInCallback() {
                        /**
                         * Override this function with the code you want to run after the save is complete.
                         *
                         * @param user The user that logged in, if the username and password is valid.
                         * @param e    The exception raised by the login, or {@code null} if it succeeded.
                         */
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            setProgressBarIndeterminateVisibility(false);

                            if (user != null) {
                                mSnackbar.setText("Login Successful!").show();

                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            } else {
                                String msg = e.getMessage();
                                bundle.putString("payload", msg);

                                AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
                                alertDialogFragment.setArguments(bundle);
                                alertDialogFragment.show(getFragmentManager(), "error_title");
                            }
                        }
                    });
                }
            }
        });

        mResetPassTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(i);
            }
        });
    }
}
