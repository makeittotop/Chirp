package io.abhishekpareek.app.chirp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SignUpCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResetPasswordActivity extends AppCompatActivity {
    @Bind(R.id.emailEditText) EditText mEmail;
    @Bind(R.id.resetPasswordButton)
    Button mResetPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        mResetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();

                String msg = "Empty fields not allowed";
                final Bundle bundle = new Bundle();

                if (email.isEmpty()) {
                    msg = "Empty fields is not allowed";
                    bundle.putString("payload", msg);

                    AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
                    alertDialogFragment.setArguments(bundle);
                    alertDialogFragment.show(getFragmentManager(), "error_title");
                } else {
                    // Confirm
                    msg = "Please confirm";
                    AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                    builder.setTitle(ResetPasswordActivity.this.getString(R.string.confirm_title))
                            //.setMessage(context.getString(R.string.error_message))
                            .setMessage(msg)
                            .setPositiveButton(ResetPasswordActivity.this.getString(R.string.confirm_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                // An email was successfully sent with reset instructions.
                                                String msg = "Password reset initiated. Please check your email for further instructions.";
                                                AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                                                builder.setTitle(getApplicationContext().getString(R.string.alert_title))
                                                        //.setMessage(context.getString(R.string.error_message))
                                                        .setMessage(msg)
                                                        .setPositiveButton(getApplicationContext().getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                finish();
                                                            }
                                                        });

                                                AlertDialog dialog = builder.create();
                                                dialog.show();
                                            } else {
                                                // Something went wrong. Look at the ParseException to see what's up.
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
                            .setNegativeButton(ResetPasswordActivity.this
                                    .getString(R.string.confirm_cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.create().show();
                }
            }
        });
    }
}
