package com.synchron.ncpl.synchron;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {
    
    EditText edit_fname,edit_profesion,edit_location,edit_mail,edit_phone,edit_mobile,edit_pass,edit_newPass,edit_reEnterPass;
    String firstName,proffesion,location,eMail,phone,mobile,newpassword,reEnterpassword,userId;
    String fname,uProffesion,uLocation,uMail,uPhone,uMobile,uPassword,profile,editImage;
    Button ok,back;
    private String selectedImagePath = "";
    final private int PICK_IMAGE = 1;
    final private int CAPTURE_IMAGE = 2;
    private String imgPath;
    CircleImageView profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        edit_fname = (EditText) findViewById(R.id.edit_fname);
        edit_profesion = (EditText) findViewById(R.id.edit_profile_proffesion);
        edit_location = (EditText) findViewById(R.id.edit_profile_location);
        edit_mail = (EditText) findViewById(R.id.edit_profile_mail);
        edit_phone = (EditText) findViewById(R.id.edit_profil_phone);
        edit_mobile = (EditText) findViewById(R.id.edit_profile_mobile);
        edit_pass = (EditText) findViewById(R.id.edit_profile_password);
        edit_newPass = (EditText) findViewById(R.id.edit_profile_newPass);
        edit_reEnterPass = (EditText) findViewById(R.id.edit_profile_reenter_pass);
        profileImage = (CircleImageView) findViewById(R.id.btn_prifile_pic);
        ok = (Button) findViewById(R.id.btn_save);
        back = (Button) findViewById(R.id.btn_back);

        Intent intent = getIntent();
        userId = intent.getStringExtra("UserId");
        fname = intent.getStringExtra("name");
        uProffesion = intent.getStringExtra("position");
        uLocation = intent.getStringExtra("address");
        uMail = intent.getStringExtra("email");
        uPhone = intent.getStringExtra("phone");
        uMobile = intent.getStringExtra("mobile");
        uPassword = intent.getStringExtra("password");
        profile = intent.getStringExtra("profile_img");
        //Toast.makeText(EditProfile.this, userId+""+uPassword, Toast.LENGTH_SHORT).show();
        edit_fname.setText(fname);
        edit_profesion.setText(uProffesion);
        edit_location.setText(uLocation);
        edit_mail.setText(uMail);
        edit_phone.setText(uPhone);
        edit_mobile.setText(uMobile);
        edit_pass.setText(uPassword);
        Glide.with(EditProfile.this).load(profile).into(profileImage);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = edit_fname.getText().toString().trim();
                proffesion = edit_profesion.getText().toString().trim();
                location = edit_location.getText().toString().trim();
                eMail = edit_mail.getText().toString().trim();
                phone = edit_phone.getText().toString().trim();
                mobile = edit_mobile.getText().toString().trim();
                //password = edit_pass.getText().toString().trim();
                newpassword = edit_newPass.getText().toString().trim();
                reEnterpassword = edit_reEnterPass.getText().toString().trim();

                loadUrl("http://www.synchron.6elements.net/webservices/update_profile.php?UserId="+userId+"&firstName="+firstName+"&proffesion="+proffesion+"&location="+location+"&phone="+phone+"&mobile="+mobile+"&password="+uPassword+"&newPassword="+newpassword+"&reEnterPassword="+reEnterpassword+"");
                //loadUrl("http://www.synchron.6elements.net/webservices/update_profile.php?UserId=22&firstName=NarayanaReddy&proffesion=1&location=Hyderabad&phone=8923446555&mobile=8976543233&password=sari@456&newPassword=sari@1234&reEnterPassword=sari@1234");
            }

        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    final Intent intent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            setImageUri());
                    startActivityForResult(intent, CAPTURE_IMAGE);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(
                            Intent.createChooser(intent, ""),
                            PICK_IMAGE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }
    public Uri setImageUri() {
        // Store image in dcim
        File file = new File(Environment.getExternalStorageDirectory()
                + "/DCIM/", "image" + new Date().getTime() + ".png");
        Uri imgUri = Uri.fromFile(file);
        this.imgPath = file.getAbsolutePath();
        return imgUri;
    }

    public String getImagePath() {
        return imgPath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == PICK_IMAGE) {
                selectedImagePath = getAbsolutePath(data.getData());
                profileImage.setImageBitmap(decodeFile(selectedImagePath));
                Toast.makeText(getApplicationContext(),selectedImagePath, Toast.LENGTH_LONG).show();
            } else if (requestCode == CAPTURE_IMAGE) {
                selectedImagePath = getImagePath();
                profileImage.setImageBitmap(decodeFile(selectedImagePath));
                Toast.makeText(getApplicationContext(),selectedImagePath, Toast.LENGTH_LONG).show();
            } else {
                super.onActivityResult(requestCode, resultCode,
                        data);
            }
        }

    }


    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of
            // 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE
                    && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }

    public String getAbsolutePath(Uri uri) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public void loadUrl(String url) {
        // JsonArrayRequest jsonRequest = new JsonArrayRequest();
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                Log.e("My Response", response.toString());
            }
        }, new Response.ErrorListener() {
        //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.

            }
        }) {

        };
        Volley.newRequestQueue(this).add(MyStringRequest);
     AlertDialog alertDialog = new AlertDialog.Builder(
             EditProfile.this).create();

     // Setting Dialog Title
     //alertDialog.setTitle("Feedback");

     // Setting Dialog Message
     alertDialog.setMessage("Sucessfully Updated");

     // Setting Icon to Dialog
     //alertDialog.setIcon(R.drawable.signin_checkbox);

     // Setting OK Button
     alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int which) {
             edit_fname.setText("");
             edit_profesion.setText("");
             edit_location.setText("");
             edit_mobile.setText("");
             edit_phone.setText("");
             edit_newPass.setText("");
             edit_reEnterPass.setText("");
         }
     });

     // Showing Alert Message
     alertDialog.show();

    }
}
