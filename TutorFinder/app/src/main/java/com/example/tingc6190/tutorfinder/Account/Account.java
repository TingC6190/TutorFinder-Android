package com.example.tingc6190.tutorfinder.Account;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tingc6190.tutorfinder.DataObject.AccountPicture;
import com.example.tingc6190.tutorfinder.DataObject.Student;
import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.Profile.Profile;
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.Search.Tutor;
import com.example.tingc6190.tutorfinder.Setting.Setting;
import com.example.tingc6190.tutorfinder.TutorForm.TutorFormInitial;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class Account extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private HomeActivity homeActivity;
    private RoundedImageView accountImage;
    private Uri imageUri;
    private Student student;
    private String email;
    private View settingButton;
    private TextView transaction_tv;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    AccountListener accountListener;

    public Account() {
    }

    public interface AccountListener
    {
        void getAccountListener(String imageUrl);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof AccountListener)
        {
            accountListener = (AccountListener) context;
        }
        else
        {
            Log.d("error", "must implement AccountListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        storageReference = FirebaseStorage.getInstance().getReference("pictures");
       //databaseReference = FirebaseDatabase.getInstance().getReference().child("users/tutors/osibdZ6yDTatt0H1T8H60GgaBE42/picture");

        homeActivity = (HomeActivity) getActivity();

        student = homeActivity.getCurrentStudent();
        email = homeActivity.getStudentEmail();




        return inflater.inflate(R.layout.content_account_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            if (student != null)
            {
                Button logoutButton = getView().findViewById(R.id.logout_button);
                Button applyTutorButton = getView().findViewById(R.id.apply_tutor_button);
                TextView email_tv = getView().findViewById(R.id.account_email);
                TextView name_tv = getView().findViewById(R.id.account_name);
                TextView aboutMe_tv = getView().findViewById(R.id.account_aboutme);
                View cameraButton = getView().findViewById(R.id.account_camera_button);
                accountImage = getView().findViewById(R.id.account_image);
                settingButton = getView().findViewById(R.id.setting_button);
                transaction_tv = getView().findViewById(R.id.account_transactions);

                String name = student.getFirstName() + " " + student.getLastName();
                String newEmail = email;
                String aboutMe = student.getAboutMe();

                name_tv.setText(name);
                email_tv.setText(newEmail);
                //aboutMe_tv.setText(aboutMe);

                if (aboutMe.trim().equals(""))
                {
                    String newAboutMe = "Please write a short bio about yourself.";
                    aboutMe_tv.setText(newAboutMe);
                }
                else
                {
                    aboutMe_tv.setText(aboutMe);
                }


//                if (tutor.getPicture() != null)
//                {
//                    if (!tutor.getPicture().equals(""))
//                    {
//                        Picasso.get().load(tutor.getPicture()).into(vh.profilePicture);
//                    }
//                }

                if (student.getPicture() != null)
                {
                    if (!student.getPicture().equals(""))
                    {
                        Picasso.get().load(student.getPicture()).into(accountImage);
                    }
                }


                logoutButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        homeActivity.logOut();
                    }
                });

                applyTutorButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //launch initial tutor form
                        getFragmentManager().beginTransaction()
                                .replace(R.id.content_container, new TutorFormInitial())
                                .addToBackStack("initial form")
                                .commit();
                    }
                });

                cameraButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImage();
                    }
                });

                settingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getFragmentManager().beginTransaction()
                                .replace(R.id.content_container, new Setting())
                                .addToBackStack("setting form")
                                .commit();
                    }
                });

                transaction_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getFragmentManager().beginTransaction()
                                .replace(R.id.content_container, new Transactions())
                                .addToBackStack("account")
                                .commit();
                    }
                });
            }
        }
    }

    private void selectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK
                && data != null && data.getData() != null)
        {
            imageUri = data.getData();

            Picasso.get().load(imageUri).into(accountImage);

            uploadImage();
        }
    }

    //get the extension of our image
    private String getImageExtension(Uri uri)
    {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage()
    {
        if (imageUri != null)
        {
            final StorageReference imageReference = storageReference.child(System.currentTimeMillis()
            + "." + getImageExtension(imageUri));

            //add our image file to our database
            imageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //databaseReference.setValue(uri.toString());

                                    accountListener.getAccountListener(uri.toString());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else
        {
            Toast.makeText(getContext(), "no image", Toast.LENGTH_SHORT).show();
        }
    }
}
