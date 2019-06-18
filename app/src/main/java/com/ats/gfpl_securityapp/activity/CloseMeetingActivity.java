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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.gfpl_securityapp.BuildConfig;
import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.model.VisitorList;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.ats.gfpl_securityapp.utils.PermissionsUtil;
import com.ats.gfpl_securityapp.utils.RealPathUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CloseMeetingActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvVisitor, tvPurpose, tvType, tvOutCome, tvPhoto1, tvPhoto2;
    private ImageView ivCamera1, ivCamera2, ivPhoto1, ivPhoto2;
    private Button btnCloseMeeting;
    private EditText edRemark;
    VisitorList model;
    File folder = new File(Environment.getExternalStorageDirectory() + File.separator, "gfpl_security");
    File f;
     VisitorList visitorList;
    Bitmap myBitmap1 = null, myBitmap2 = null;
    public static String path1, imagePath1 = null, imagePath2 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_meeting);
        setTitle("Close Meeting");

        tvVisitor = findViewById(R.id.tvVisitor);
        tvPurpose = findViewById(R.id.tvPurpose);
        tvType = findViewById(R.id.tvType);
        tvOutCome = findViewById(R.id.tvOutCome);
        tvPhoto1 = findViewById(R.id.tvPhoto1);
        tvPhoto2 = findViewById(R.id.tvPhoto2);
        ivCamera1 = findViewById(R.id.ivCamera1);
        ivCamera2 = findViewById(R.id.ivCamera2);
        ivPhoto1 = findViewById(R.id.ivPhoto1);
        ivPhoto2 = findViewById(R.id.ivPhoto2);
        btnCloseMeeting = findViewById(R.id.btnCloseMeeting);
        edRemark = findViewById(R.id.edRemark);

        ivCamera1.setOnClickListener(this);
        ivCamera2.setOnClickListener(this);
        btnCloseMeeting.setOnClickListener(this);

        if (PermissionsUtil.checkAndRequestPermissions(this)) {
        }

        createFolder();

        String closeMetting = getIntent().getStringExtra("model");
        Gson gson = new Gson();
        try {
            model = gson.fromJson(closeMetting, VisitorList.class);
            Log.e("Close metting", "-----------------------" + model);
            tvVisitor.setText(model.getPersonName());
            tvPurpose.setText(model.getPurposeHeading());
           // tvOutCome.setText(model.getMeetingDiscussion());
            if(model.getVisitType()==1) {
                tvType.setText("Appointment");
            }else if(model.getVisitType()==2)
            {
                tvType.setText("Random");
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivCamera1) {

            showCameraDialog("Photo1");

        } else if (v.getId() == R.id.ivCamera2) {

            showCameraDialog("Photo2");

        } else if (v.getId() == R.id.btnCloseMeeting) {
            String strRemark;
            boolean isValidRemark=false;

            strRemark=edRemark.getText().toString();

            if (strRemark.isEmpty()) {
                edRemark.setError("required");
            } else {
                edRemark.setError(null);
                isValidRemark = true;
            }

            if(isValidRemark)
            {
                visitorList = new VisitorList(model.getGatepassVisitorId(),model.getVisitDateIn(),model.getSecurityIdIn(),model.getPersonName(),model.getPersonCompany(),model.getPersonPhoto(),model.getMobileNo(),model.getIdProof(),model.getIdProof1(),model.getOtherPhoto(),model.getPurposeId(),model.getPurposeHeading(),model.getPurposeRemark(),model.getEmpIds(),model.getEmpName(),model.getGateId(),model.getGatePasstype(),4,model.getVisitType(),model.getInTime(),model.getVisitCardId(),model.getVisitCardNo(),model.getTakeMobile(),strRemark,model.getUploadPhoto(),model.getVisitOutTime(), (int) model.getTotalTimeDifference(),model.getSecurityIdOut(),model.getVisitDateOut(),model.getUserSignImage(),model.getDelStatus(),model.getIsUsed(),model.getExInt1(),model.getExInt2(),model.getExInt3(),model.getExVar1(),model.getExVar2(),model.getExVar3(),model.getSecurityInName(),model.getSecurityOutName(),model.getPurposeHeading(),model.getGateName(),model.getAssignEmpName());
                if (imagePath1 == null && imagePath2 == null) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(CloseMeetingActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you want to close visitor ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            saveVisitor(visitorList);
                            Log.e("VISITOR CLOSING LIST", "-----------------------" + visitorList);

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
                    final ArrayList<String> pathArray = new ArrayList<>();
                    final ArrayList<String> fileNameArray = new ArrayList<>();

                    AlertDialog.Builder builder = new AlertDialog.Builder(CloseMeetingActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you want to close visitor ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                    String photo1 = "", photo2 = "";

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");

                    if (imagePath1 != null) {

                        pathArray.add(imagePath1);

                        File imgFile1 = new File(imagePath1);
                        int pos = imgFile1.getName().lastIndexOf(".");
                        String ext = imgFile1.getName().substring(pos + 1);
                        photo1 = sdf.format(System.currentTimeMillis()) + "_p1." + ext;
                        fileNameArray.add(photo1);
                        visitorList.setUploadPhoto(photo1);
                    }

                    if (imagePath2 != null) {

                        pathArray.add(imagePath2);

                        File imgFile2 = new File(imagePath2);
                        int pos2 = imgFile2.getName().lastIndexOf(".");
                        String ext2 = imgFile2.getName().substring(pos2 + 1);
                        photo2 = sdf.format(System.currentTimeMillis()) + "_p2." + ext2;
                        fileNameArray.add(photo2);
                        visitorList.setExVar2(photo2);

                    }
                    
                    sendImage(pathArray, fileNameArray, visitorList);

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
                }
            }

        }
    }

    private void sendImage(final ArrayList<String> filePath, final ArrayList<String> fileName, final VisitorList visitorList) {
        Log.e("PARAMETER : ", "   FILE PATH : " + filePath + "            FILE NAME : " + fileName  +"           VISITER BIN  "+visitorList);

        final CommonDialog commonDialog = new CommonDialog(CloseMeetingActivity.this, "Loading", "Please Wait...");
        commonDialog.show();

        File imgFile = null;

        MultipartBody.Part[] uploadImagesParts = new MultipartBody.Part[filePath.size()];

        for (int index = 0; index < filePath.size(); index++) {
            Log.e("ATTACH ACT", "requestUpload:  image " + index + "  " + filePath.get(index));
            imgFile = new File(filePath.get(index));
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), imgFile);
            uploadImagesParts[index] = MultipartBody.Part.createFormData("file", "" + fileName.get(index), surveyBody);
        }

        // RequestBody imgName = RequestBody.create(MediaType.parse("text/plain"), "photo1");
        RequestBody imgType = RequestBody.create(MediaType.parse("text/plain"), "1");

        Call<JSONObject> call = Constants.myInterface.imageUpload(uploadImagesParts, fileName, imgType);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                commonDialog.dismiss();

                imagePath1 = null;
                imagePath2 = null;

                Log.e("Response : ", "--" + response.body());
//
//                if (filePath.size() > 0) {
//                    for (int i = 0; i < filePath.size(); i++) {
//                        visitorList.setUploadPhoto(fileName.get(i));
//
//                    }
//                }
                saveVisitor(visitorList);
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.e("Error : ", "--" + t.getMessage());
                commonDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(CloseMeetingActivity.this, "Unable To Process", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveVisitor(VisitorList visitorList) {
        Log.e("PARAMETER","---------------------------------------VISITOR CLOSING--------------------------"+visitorList);

        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(CloseMeetingActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<VisitorList> listCall = Constants.myInterface.saveGatepassVisitor(visitorList);
            listCall.enqueue(new Callback<VisitorList>() {
                @Override
                public void onResponse(Call<VisitorList> call, Response<VisitorList> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE VISITOR CLOSING : ", " ------------------------------SAVE VISITOR CLOSING------------------------- " + response.body());
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

//                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                            ft.replace(R.id.content_frame, new VisitorGatePassListFragment(), "DashFragment");
//                            ft.commit();
                            commonDialog.dismiss();
                            finish();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            AlertDialog.Builder builder = new AlertDialog.Builder(CloseMeetingActivity.this, R.style.AlertDialogTheme);
                            builder.setTitle("" + CloseMeetingActivity.this.getResources().getString(R.string.app_name));
                            builder.setMessage("Unable to process! please try again.");

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(CloseMeetingActivity.this, R.style.AlertDialogTheme);
                        builder.setTitle("" + CloseMeetingActivity.this.getResources().getString(R.string.app_name));
                        builder.setMessage("Unable to process! please try again.");

                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }

                @Override
                public void onFailure(Call<VisitorList> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    AlertDialog.Builder builder = new AlertDialog.Builder(CloseMeetingActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle("" + CloseMeetingActivity.this.getResources().getString(R.string.app_name));
                    builder.setMessage("Unable to process! please try again.");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
        } else {
            Toast.makeText(CloseMeetingActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }


    }


    public void createFolder() {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }


    //------------------------------------------IMAGE-----------------------------------------------


    public void showCameraDialog(final String type) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CloseMeetingActivity.this, R.style.AlertDialogTheme);
        builder.setTitle("Choose");
        builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (type.equalsIgnoreCase("Photo1")) {
                    Intent pictureActionIntent = null;
                    pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pictureActionIntent, 101);
                } else if (type.equalsIgnoreCase("Photo2")) {
                    Intent pictureActionIntent = null;
                    pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pictureActionIntent, 201);
                }
            }
        });
        builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if (type.equalsIgnoreCase("Photo1")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" + System.currentTimeMillis() + "_p1.jpg");
                            String authorities = BuildConfig.APPLICATION_ID + ".provider";
                            Uri imageUri = FileProvider.getUriForFile(CloseMeetingActivity.this, authorities, f);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 102);
                        } else if (type.equalsIgnoreCase("Photo2")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" + System.currentTimeMillis() + "_p2.jpg");
                            String authorities = BuildConfig.APPLICATION_ID + ".provider";
                            Uri imageUri = FileProvider.getUriForFile(CloseMeetingActivity.this, authorities, f);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 202);
                        }

                    } else {

                        if (type.equalsIgnoreCase("Photo1")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" + System.currentTimeMillis() + "_p1.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 102);
                        } else if (type.equalsIgnoreCase("Photo2")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = new File(folder + File.separator, "" + System.currentTimeMillis() + "_p2.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(intent, 202);
                        }

                    }
                } catch (Exception e) {
                    ////Log.e("select camera : ", " Exception : " + e.getMessage());
                }
            }
        });
        builder.show();
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
                realPath = RealPathUtil.getRealPathFromURI_API19(CloseMeetingActivity.this, data.getData());
                Uri uriFromPath = Uri.fromFile(new File(realPath));
                myBitmap1 = getBitmapFromCameraData(data, CloseMeetingActivity.this);

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
        } else if (resultCode == RESULT_OK && requestCode == 202) {
            try {
                String path = f.getAbsolutePath();
                File imgFile = new File(path);
                if (imgFile.exists()) {
                    myBitmap2 = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ivPhoto2.setImageBitmap(myBitmap2);

                    myBitmap2 = shrinkBitmap(imgFile.getAbsolutePath(), 720, 720);

                    try {
                        FileOutputStream out = new FileOutputStream(path);
                        myBitmap2.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                        Log.e("Image Saved  ", "---------------");

                    } catch (Exception e) {
                        Log.e("Exception : ", "--------" + e.getMessage());
                        e.printStackTrace();
                    }
                }
                imagePath2 = f.getAbsolutePath();
                tvPhoto2.setText("" + f.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == 201) {
            try {
                realPath = RealPathUtil.getRealPathFromURI_API19(CloseMeetingActivity.this, data.getData());
                Uri uriFromPath = Uri.fromFile(new File(realPath));
                myBitmap2 = getBitmapFromCameraData(data, CloseMeetingActivity.this);

                ivPhoto2.setImageBitmap(myBitmap2);
                imagePath2 = uriFromPath.getPath();
                tvPhoto2.setText("" + uriFromPath.getPath());

                try {

                    FileOutputStream out = new FileOutputStream(uriFromPath.getPath());
                    myBitmap2.compress(Bitmap.CompressFormat.PNG, 100, out);
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
