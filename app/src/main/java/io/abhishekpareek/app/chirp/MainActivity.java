package io.abhishekpareek.app.chirp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private final static String TAG = MainActivity.class.getSimpleName();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private  static final int GET_PHOTO_REQUEST_CODE = 300;
    private  static final int GET_VIDEO_REQUEST_CODE = 400;

    private Uri mFileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /*
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
        */

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            toLogin();
        }
    }

    private void toLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /** Create a file Uri for saving an image or video */
    private Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        if (!(Environment.getExternalStorageState()).equals(Environment.MEDIA_MOUNTED)) {
            String msg = "External Card Not Found";
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(msg)
                    .setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.show();

            return null;
        }

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Chirp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("Chirp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        Log.d(TAG, mediaFile.toString());
        return mediaFile;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_logout) {
            setProgressBarIndeterminateVisibility(true);

            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    setProgressBarIndeterminateVisibility(false);

                    if (e == null) {
                        toLogin();
                    } else {
                        String msg = e.getMessage();
                        Bundle bundle = new Bundle();
                        bundle.putString("payload", msg);

                        AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
                        alertDialogFragment.setArguments(bundle);
                        alertDialogFragment.show(getFragmentManager(), "error_title");
                    }
                }
            });

        } else if (id == R.id.action_camera) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(R.array.camera_options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent;
                    switch (which) {
                        case 0:
                            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            mFileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);

                            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                            break;
                        case 1:
                            intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            mFileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
                            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                            // Restrict duration for a video
                            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);

                            startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
                            break;
                        case 2:
                            intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(intent, GET_PHOTO_REQUEST_CODE);
                            break;
                        case 3:
                            intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("video/*");
                            startActivityForResult(intent, GET_VIDEO_REQUEST_CODE);
                            break;
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void galleryAddPic() {
    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
    mediaScanIntent.setData(mFileUri);
    this.sendBroadcast(mediaScanIntent);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE):
                if (resultCode == RESULT_OK) {
                    galleryAddPic();

                    // Image captured and saved to fileUri specified in the Intent
                    String msg = "Image saved to the gallery!";
                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                        /*
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(msg)
                                .setPositiveButton("OK", null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        */
                } else if (resultCode == RESULT_CANCELED) {
                        // User cancelled the image capture
                } else {
                        // Image capture failed, advise user
                        //
                }
                break;
            case (CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE):
                if (resultCode == RESULT_OK) {
                    galleryAddPic();

                    // Video captured and saved to fileUri specified in the Intent
                    String msg = "Video saved to the gallery";
                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                        /*
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(msg)
                                .setPositiveButton("OK", null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        */
                } else if (resultCode == RESULT_CANCELED) {
                        // User cancelled the video capture
                } else {
                        // Video capture failed, advise user
                }
                break;
            case (GET_PHOTO_REQUEST_CODE):
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String msg = "Chosen photo: " + data.getData();
                        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case (GET_VIDEO_REQUEST_CODE):
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String msg = "Chosen video: " + data.getData();
                        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                break;
        }
    }
}
