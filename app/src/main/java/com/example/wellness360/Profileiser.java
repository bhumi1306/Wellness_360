package com.example.wellness360;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.result.contract.ActivityResultContracts;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import android.app.AlertDialog;
import android.content.DialogInterface;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profileiser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profileiser extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PICK_IMAGE = 100;
    private ImageView imageView;
    private ActivityResultLauncher<String> gallerylauncher;
    private static final String SHARED_PREFS_KEY = "editable_text_shared_prefs";
    private static final String TEXT_KEY = "text_key";
    private SharedPreferences sharedPreferences;
    private String text;
    private String mParam1;
    private String mParam2;
    public TextView userid;
    Button btngoal;
    Button docu;
    Button remind;
    Button plan;
    Button logout;
    private static final String KEY_IMAGE_URI = "imageUri";
    private Uri imageUri;
    public Profileiser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profileiser.
     */
    // TODO: Rename and change types and number of parameters
    public static Profileiser newInstance(String param1,String param2) {
        Profileiser fragment = new Profileiser();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (savedInstanceState != null) {
            imageUri = savedInstanceState.getParcelable(KEY_IMAGE_URI);
            if (imageUri != null) {
                imageView.setImageURI(imageUri);
                makeImageViewRound(imageView);
            }
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_IMAGE_URI, imageUri);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profileiser,container,false);
        userid =view.findViewById(R.id.textView24);
        btngoal = view.findViewById(R.id.button15);
        docu = view.findViewById(R.id.button12);
        remind = view.findViewById(R.id.button14);
        plan = view.findViewById(R.id.button16);
        logout= view.findViewById(R.id.button17);
        imageView = view.findViewById(R.id.imageView23);
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT_KEY,"");
        userid.setText(text);
        userid.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasfocus){
                if(!hasfocus){
                    saveTextToSharedPreferences(userid.getText().toString());
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        gallerylauncher= registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result!=null){
                    imageView.setImageURI(result);
                    makeImageViewRound(imageView);
                }
            }
        });

        btngoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btngoal.setTextColor(getResources().getColor(R.color.purp));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btngoal.setTextColor(getResources().getColor(R.color.profiletext));
                    }
                },200);
                Intent intent =new Intent(getActivity(), goal.class);
                startActivity(intent);
            }
        });

        docu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docu.setTextColor(getResources().getColor(R.color.purp));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        docu.setTextColor(getResources().getColor(R.color.profiletext));
                    }
                },200);
//                Intent intent =new Intent(getActivity(), Documents.class);
                Intent intent =new Intent(getActivity(), ADP.class);
                startActivity(intent);
            }
        });
        remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remind.setTextColor(getResources().getColor(R.color.purp));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        remind.setTextColor(getResources().getColor(R.color.profiletext));
                    }
                },200);
                Intent intent = new Intent(getActivity(),Notificationdisplay.class);
                startActivity(intent);
            }
        });
        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plan.setTextColor(getResources().getColor(R.color.purp));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        plan.setTextColor(getResources().getColor(R.color.profiletext));
                    }
                },200);
                showChooseDialog();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences= requireActivity().getSharedPreferences(MainActivity2.PREFS_NAME,0);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putBoolean("hasLoggedIn",false);
                editor.commit();
                Intent loginIntent = new Intent(getActivity(), MainActivity2.class);
                startActivity(loginIntent);
                getActivity().finish();
            }
        });

        return  view;
    }
//    public void updateTextView(String text){
//        userid.setText(text);
//    }
//    public void updateimage(String url){
//        Glide.with(this).load(url).into(profile);
//    }
//    private String editeddata;

    private void showChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Active PLans: ");

        String[] activityOptions = {"Meditation Plan", "Steps Plan", "Meal Plan"};
        builder.setItems(activityOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        startActivity(new Intent(requireContext(), startmed.class));
                        break;
                    case 1:
                        startActivity(new Intent(requireContext(), Startsteps.class));
                        break;
                    case 2:
                        startActivity(new Intent(requireContext(), Startmeal.class));
                        break;
                }
            }
        });

        builder.show();
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Profile Picture");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Remove profile picture"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                removeProfilePicture();
                                break;
                        }
                    }
                });
    pictureDialog.show();
    }
    private void choosePhotoFromGallery() {
        gallerylauncher.launch("image/*");
    }
    private void makeImageViewRound(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        Bitmap bitmapround = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        Canvas canvas = new Canvas(bitmapround);
        canvas.drawRoundRect((new RectF(0,0,bitmap.getWidth(),bitmap.getHeight())),bitmap.getWidth()/2,bitmap.getHeight()/2,paint);
        imageView.setImageDrawable(new BitmapDrawable(getResources(),bitmapround));
    }

    private void saveTextToSharedPreferences(String text) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT_KEY, text);
        editor.apply();
}
    private void removeProfilePicture() {
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.rounded));
    }

}