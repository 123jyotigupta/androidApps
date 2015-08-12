package com.example.developeri.novasample;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ImageView;
import android.provider.MediaStore;
import android.net.Uri;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Environment;
import java.io.IOException;
import java.io.File;
import java.io.OutputStream;
import android.app.Activity;
import java.io.FileNotFoundException;
import android.database.Cursor;
import org.apache.http.HttpResponse;
import android.content.res.Resources;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONException;
import android.provider.MediaStore.MediaColumns;
import org.apache.http.entity.StringEntity;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class signUpScreen extends ActionBarActivity {
    final Context context = this;
    private static final int REQUEST_CAMERA = 100;
    private static final int SELECT_FILE = 1;
    private String selectedImagePath;
    ImageView profileimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        setContentView(R.layout.activity_sign_up_screen);
        Button registerBtn = (Button) findViewById(R.id.signBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonobj = new JSONObject();
                try {
                    EditText nameTxt = (EditText) findViewById(R.id.userNameView);
                    EditText usernameTxt = (EditText) findViewById(R.id.nameView);
                    EditText passTxt = (EditText) findViewById(R.id.passView);
                    EditText emailTxt = (EditText) findViewById(R.id.emailView);
                    EditText confirmPassTxt = (EditText) findViewById(R.id.confirmPassView);

                    if (!usernameTxt.getText().toString().matches("") && !passTxt.getText().toString().matches("") && !emailTxt.getText().toString().matches("")&& !nameTxt.getText().toString().matches("")) {
                        if (passTxt.getText().toString().trim().equals(confirmPassTxt.getText().toString().trim())) {
                            jsonobj.put("email", emailTxt.getText().toString());
                            jsonobj.put("name", nameTxt.getText().toString());
                            jsonobj.put("username", usernameTxt.getText().toString());
                            jsonobj.put("password", passTxt.getText().toString());
                        } else {
                            builder1.setTitle("Error Message");
                            builder1.setMessage("Password and confirm Password does not match");
                            builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog closed
                                }
                            });
                            AlertDialog alertdialog = builder1.create();
                            alertdialog.show();
                            return;

                        }
                    } else {

                        builder1.setTitle("Error Message");
                        builder1.setMessage("Please fill all values");
                        builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog closed
                            }
                        });
                        AlertDialog alertdialog = builder1.create();
                        alertdialog.show();
                        return;
                    }

                } catch (JSONException e) {
                    Log.i("response", "error");
                }
                new MysyncTask().execute(jsonobj.toString());
            }

            ;
        });

        profileimage = (ImageView)findViewById(R.id.profileView);
        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeImage();
            }
        });


    }


   public void takeImage(){
       final CharSequence[] items = { "Take Photo", "Choose from Library",
               "Cancel" };

       AlertDialog.Builder builder = new AlertDialog.Builder(this);
       builder.setTitle("Add Photo!");
       builder.setItems(items, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int item) {
               if (items[item].equals("Take Photo")) {
                   Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                   File f = new File(android.os.Environment
                           .getExternalStorageDirectory(), "temp.jpg");
                   intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                   startActivityForResult(intent, REQUEST_CAMERA);
               } else if (items[item].equals("Choose from Library")) {
                   Intent intent = new Intent(
                           Intent.ACTION_PICK,
                           android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   intent.setType("image/*");
                   startActivityForResult(
                           Intent.createChooser(intent, "Select File"),
                           SELECT_FILE);
               } else if (items[item].equals("Cancel")) {
                   dialog.dismiss();
               }
           }
       });
       builder.show();
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                File f = new File(Environment.getExternalStorageDirectory()
                        .toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bm,bm1;
                    BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

                    bm = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            btmapOptions);

                     bm = Bitmap.createScaledBitmap(bm, 300, 300, false);
                    bm1 = Bitmap.createScaledBitmap(bm, 300, 300, false);
                    Matrix mat = new Matrix();
                    mat.postRotate(90);
                    Bitmap bMapRotate = Bitmap.createBitmap(bm1,0,0,bm1.getWidth(),bm1.getHeight(),mat,true);
                    profileimage.setImageBitmap(bMapRotate);
                    //profileimage.setImageBitmap(bm);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream fOut = null;
                    File file = new File(path, String.valueOf(System
                            .currentTimeMillis()) + ".jpg");
                    try {
                        fOut = new FileOutputStream(file);
                        bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                        fOut.flush();
                        fOut.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();

                String tempPath = getPath(selectedImageUri,this);
                Bitmap bm,bm1;
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
                bm1= Bitmap.createScaledBitmap(bm, 300, 300, false);
                Matrix mat = new Matrix();
                mat.postRotate(90);
                Bitmap bMapRotate = Bitmap.createBitmap(bm1,0,0,bm1.getWidth(),bm1.getHeight(),mat,true);
                profileimage.setImageBitmap(bMapRotate);
            }
        }

    };


    public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaColumns.DATA };
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_up_screen, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

  private class MysyncTask extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            postData(params[0]);
            return null;

        }



        public void postData(String data) {
            //make api call here
            DefaultHttpClient httpclient = new DefaultHttpClient();
            //  String url = getString(R.string.url_name);
            HttpPost post = new HttpPost("http://nova.mybluemix.net/api/profiles");
            String responseText = null;

            try {

                StringEntity se = new StringEntity(data);
                se.setContentType("application/json;charset=UTF-8");
                post.setEntity(se);
                HttpResponse httpresponse = httpclient.execute(post);
                responseText = EntityUtils.toString(httpresponse.getEntity());
                Log.i("response",responseText);
                JSONObject response = new JSONObject(responseText);
                  //String test =  response.getString("error");
                if(response.has("summary")) {
                    if (response.getString("summary").equals("1 attribute is invalid")) {
                        Log.i("response", "1 attribute is invalid");
                        return;
                    }
                }else{
                        Log.i("response","okkkk");
                        finish();
//                        Intent loginScreen = new Intent(signUpScreen.this,mainScreen.class);
//                        startActivity(loginScreen);
                    }


            } catch (IOException e) {
                Log.i("response", "http error");
            }
            catch(JSONException e) {
            }
            }

        }

    }







