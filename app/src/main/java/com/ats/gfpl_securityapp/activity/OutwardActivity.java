package com.ats.gfpl_securityapp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.gfpl_securityapp.BuildConfig;
import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.model.Info;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.Outward;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.ats.gfpl_securityapp.utils.CustomSharedPreference;
import com.ats.gfpl_securityapp.utils.PermissionsUtil;
import com.ats.gfpl_securityapp.utils.RealPathUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutwardActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvOutwardName,tvOutDate,tvExpectedDate,tvToName,tvPhoto1;
    Outward model;
    String strIntent;
    private Button btnSubmit;
    private ImageView ivCamera1,ivPhoto1;
    Login loginUser;

    File folder = new File(Environment.getExternalStorageDirectory() + File.separator, "gfpl_security");
    File f;

    Bitmap myBitmap1 = null;
    public static String path1, imagePath1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outward);

        tvOutwardName=(TextView)findViewById(R.id.tvOutward);
        tvOutDate=(TextView)findViewById(R.id.tvOutDate);
        tvExpectedDate=(TextView)findViewById(R.id.tvExpDate);
        tvToName=(TextView)findViewById(R.id.tvToName);
        tvPhoto1=(TextView)findViewById(R.id.tvPhoto1);
        ivCamera1=(ImageView)findViewById(R.id.ivCamera1);
        ivPhoto1=(ImageView)findViewById(R.id.ivPhoto1);
        btnSubmit=(Button) findViewById(R.id.btnSubmit);

        if (PermissionsUtil.checkAndRequestPermissions(OutwardActivity.this)) {
        }

        String closeOutward = getIntent().getStringExtra("model");
        Gson gson = new Gson();
        try {
            model = gson.fromJson(closeOutward, Outward.class);
            Log.e("Close Outward", "-----------------------" + model);
            tvOutwardName.setText(model.getOutwardName());
            tvOutDate.setText(model.getDateOut());
            tvExpectedDate.setText(model.getDateInExpected());
            tvToName.setText(model.getToName());

        }catch(Exception e)
        {
            e.printStackTrace();
        }

        try {
             strIntent = getIntent().getStringExtra("meeting");

            Log.e("strIntent","--------------"+strIntent);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        try {
            String userStr = CustomSharedPreference.getString(OutwardActivity.this, CustomSharedPreference.KEY_USER);
            loginUser = gson.fromJson(userStr, Login.class);
            Log.e("LOGIN USER : ", "--------USER-------" + loginUser);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        ivCamera1.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivCamera1) {

            showCameraDialog("Photo1");

        }else if(v.getId()==R.id.btnSubmit)
        {
            if (imagePath1 != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OutwardActivity.this, R.style.AlertDialogTheme);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to submit ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //saveVisitor(visitor);
                        //Log.e("VISITOR", "-----------------------" + visitor);

                        ArrayList<String> pathArray = new ArrayList<>();
                        ArrayList<String> fileNameArray = new ArrayList<>();

                        String photo1 = "";

                        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if (imagePath1 != null) {

                            pathArray.add(imagePath1);

                            File imgFile1 = new File(imagePath1);
                            int pos = imgFile1.getName().lastIndexOf(".");
                            String ext = imgFile1.getName().substring(pos + 1);
                            photo1 = sdf.format(Calendar.getInstance().getTimeInMillis()) + "_p1." + ext;
                            fileNameArray.add(photo1);
                        }

                        //visitor.setPersonPhoto(photo1);
                       // sendImage(pathArray, fileNameArray, visitor);
                        if(strIntent.equalsIgnoreCase("Close Outward")) {

                            if(model.getExInt1()==2)
                            {
                                saveOutWard(model.getGpOutwardId(), loginUser.getEmpId(), 2, photo1);
                            }else{
                                saveOutWard(model.getGpOutwardId(), loginUser.getEmpId(), 1, photo1);
                            }

//                            saveOutWard(model.getGpOutwardId(), loginUser.getEmpId(), 1, photo1);
                        }else if(strIntent.equalsIgnoreCase("In Outward"))
                        {
                            saveOutWard(model.getGpOutwardId(), loginUser.getEmpId(), 2, photo1);
                        }

                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                Toast.makeText(OutwardActivity.this, "Please Select  Photo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveOutWard(int gpOutwardId, Integer empId, int status, String photo1) {

        Log.e("PARAMETER","                 OUTWARD PASS ID     "+gpOutwardId +"              EMP ID       "+empId +"       SATAUS      "+status  +"      PHOTO         "+photo1);

        if (Constants.isOnline(OutwardActivity.this)) {
            final CommonDialog commonDialog = new CommonDialog(OutwardActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateOutwardGatepassStatus(gpOutwardId,empId,status,photo1);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("APPROVE & REJECT EMP: ", " - " + response.body());

                            if (!response.body().getError()) {

                                //MainActivity activity = (MainActivity) getApplicationContext();

                                Toast.makeText(OutwardActivity.this, "Success", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                //intent.putExtra("model", "Close Meeting");
                                intent.putExtra("model", "Outward Close");
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            } else {
                                Toast.makeText(OutwardActivity.this, "Unable to process", Toast.LENGTH_SHORT).show();
                            }

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(OutwardActivity.this, "Unable to process", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(OutwardActivity.this, "Unable to process", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    Toast.makeText(OutwardActivity.this, "Unable to process", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(OutwardActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void showCameraDialog(final String type) {
        // android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        // builder.setTitle("Choose");
//        builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (type.equalsIgnoreCase("Photo1")) {
//                    Intent pictureActionIntent = null;
//                    pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pictureActionIntent, 101);
//                }
//            }
//        });
        // builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
        //  @Override
        //  public void onClick(DialogInterface dialog, int which) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (type.equalsIgnoreCase("Photo1")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    f = new File(folder + File.separator, "" + Calendar.getInstance().getTimeInMillis()+ "_p1.jpg");
                    String authorities = BuildConfig.APPLICATION_ID + ".provider";
                    Uri imageUri = FileProvider.getUriForFile(getApplicationContext(), authorities, f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent, 102);
                }

            } else {

                if (type.equalsIgnoreCase("Photo1")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    f = new File(folder + File.separator, "" + Calendar.getInstance().getTimeInMillis()+ "_p1.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent, 102);
                }

            }
        } catch (Exception e) {
            ////Log.e("select camera : ", " Exception : " + e.getMessage());
        }
        // }
        //  });
        // builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String realPath;
        Bitmap bitmap = null;

        if (resultCode == RESULT_OK && requestCode == 102) {
            try {
                String path = f.getAbsolutePath();
                File imgFile = new File(path);
                if (imgFile.exists()) {
                    myBitmap1 = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ivPhoto1.setImageBitmap(myBitmap1);

                    myBitmap1 = shrinkBitmap(imgFile.getAbsolutePath(), 720, 720);

                    try {
                        FileOutputStream out = new FileOutputStream(path);
                        myBitmap1.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                        Log.e("Image Saved  ", "---------------");

                    } catch (Exception e) {
                        Log.e("Exception : ", "--------" + e.getMessage());
                        e.printStackTrace();
                    }
                }
                imagePath1 = f.getAbsolutePath();
                tvPhoto1.setText("" + f.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == 101) {
            try {
                realPath = RealPathUtil.getRealPathFromURI_API19(getApplicationContext(), data.getData());
                Uri uriFromPath = Uri.fromFile(new File(realPath));
                myBitmap1 = getBitmapFromCameraData(data, getApplicationContext());

                ivPhoto1.setImageBitmap(myBitmap1);
                imagePath1 = uriFromPath.getPath();
                tvPhoto1.setText("" + uriFromPath.getPath());

                try {

                    FileOutputStream out = new FileOutputStream(uriFromPath.getPath());
                    myBitmap1.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                    //Log.e("Image Saved  ", "---------------");

                } catch (Exception e) {
                    // Log.e("Exception : ", "--------" + e.getMessage());
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
                // Log.e("Image Compress : ", "-----Exception : ------" + e.getMessage());
            }
        }
    }

    public static Bitmap getBitmapFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

        String picturePath = cursor.getString(columnIndex);
        path1 = picturePath;
        cursor.close();

        Bitmap bitm = shrinkBitmap(picturePath, 720, 720);
        Log.e("Image Size : ---- ", " " + bitm.getByteCount());

        return bitm;
        // return BitmapFactory.decodeFile(picturePath, options);
    }

    public static Bitmap shrinkBitmap(String file, int width, int height) {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }
}
