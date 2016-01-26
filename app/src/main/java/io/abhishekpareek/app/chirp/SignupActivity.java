package io.abhishekpareek.app.chirp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = SignupActivity.class.getSimpleName();

    @Bind(R.id.signupButton)
    Button mSignupButton;
    @Bind(R.id.usernameText)
    EditText mUsernameText;

    @Bind(R.id.fnameText) EditText mFnameText;
    @Bind(R.id.lnameText) EditText mLnameText;
    @Bind(R.id.loginPasswordText) EditText mPasswordText;
    @Bind(R.id.retypePasswordText) EditText mRetypePasswordText;
    @Bind(R.id.phoneText) EditText mPhoneText;
    @Bind(R.id.signup_coordinator)
    CoordinatorLayout mSignupCoordinator;

    private Snackbar mSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        mSnackbar = Snackbar
                .make(mSignupCoordinator, "Passwords do not match", Snackbar.LENGTH_INDEFINITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = mUsernameText.getText().toString().trim();
                final String passwordText = mPasswordText.getText().toString().trim();
                String retypePasswordText = mRetypePasswordText.getText().toString().trim();
                final String fnameText = mFnameText.getText().toString().trim();
                final String lnameText = mLnameText.getText().toString().trim();
                String phoneText = mPhoneText.getText().toString().trim();

                String msg = "Empty fields not allowed";
                final Bundle bundle = new Bundle();

                if (username.isEmpty() || passwordText.isEmpty() || retypePasswordText.isEmpty() ||
                        fnameText.isEmpty() || lnameText.isEmpty() || phoneText.isEmpty()) {
                    msg = "Empty fields not allowed";
                    bundle.putString("payload", msg);

                    AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
                    alertDialogFragment.setArguments(bundle);
                    alertDialogFragment.show(getFragmentManager(), "error_title");

                    mSnackbar.setText(msg).show();

                } else if (passwordText.length() < 6) {
                    msg = "Password should not be less than six digits";
                    bundle.putString("payload", msg);

                    AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
                    alertDialogFragment.setArguments(bundle);
                    alertDialogFragment.show(getFragmentManager(), "error_title");
                } else if (!passwordText.equals(retypePasswordText)) {
                    msg = "Passwords do not match";
                    bundle.putString("payload", msg);

                    AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
                    alertDialogFragment.setArguments(bundle);
                    alertDialogFragment.show(getFragmentManager(), "error_title");

                    mSnackbar.setText(msg).show();
                } else {
                    // Confirm
                    msg = "Please confirm";
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setTitle(SignupActivity.this.getString(R.string.confirm_title))
                            //.setMessage(context.getString(R.string.error_message))
                            .setMessage(msg)
                            .setPositiveButton(SignupActivity.this.getString(R.string.confirm_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ParseUser newUser = new ParseUser();
                                    newUser.setUsername(username);
                                    newUser.setEmail(username);
                                    newUser.setPassword(passwordText);
                                    newUser.put("firstName", fnameText);
                                    newUser.put("lastName", lnameText);

                                    setProgressBarIndeterminateVisibility(true);

                                    newUser.signUpInBackground(new SignUpCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            setProgressBarIndeterminateVisibility(false);

                                            if (e == null) {
                                                mSnackbar.setText("Signup Successful!").show();
                                                finish();
                                                /*
                                                Intent i = new Intent(SignupActivity.this, MainActivity.class);
                                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(i);
                                                */
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
                            })
                            .setNegativeButton(SignupActivity.this
                                    .getString(R.string.confirm_cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.create().show();
                }
            }
        });

        View.OnFocusChangeListener comparePasswds = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String passwd = mPasswordText.getText().toString().trim();
                String retypedPasswd = mRetypePasswordText.getText().toString().trim();

                if (!hasFocus)
                    if (passwd.equals(retypedPasswd) && !passwd.isEmpty() && !passwd.isEmpty()) {
                        Log.d(TAG, String.format("%s - %s match", passwd, retypedPasswd));
                        mSnackbar.dismiss();
                    }
                    else if (!passwd.isEmpty() && !passwd.isEmpty()){
                        Log.d(TAG, String.format("%s - %s no match", passwd, retypedPasswd));
                        mSnackbar.setText("Passwords do not match");
                        mSnackbar.show();
                    } else {
                        mSnackbar.setText("Password cannot be empty");
                        mSnackbar.show();
                    }
            }
        };

        mPasswordText.setOnFocusChangeListener(comparePasswds);
        mRetypePasswordText.setOnFocusChangeListener(comparePasswds);
    }

}
