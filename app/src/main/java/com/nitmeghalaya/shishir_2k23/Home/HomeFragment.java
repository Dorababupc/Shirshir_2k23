package com.nitmeghalaya.shishir_2k23.Home;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nitmeghalaya.shishir_2k23.Gallery.GalleryActivity;
import com.nitmeghalaya.shishir_2k23.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private SliderAdapter sliderAdapter;
    private ArrayList<SliderDataModel> sliderDataArrayList;
    FirebaseFirestore db;
    private SliderView sliderView;

    //For Gallery
    private RecyclerView recyclerView,photorecyclerView;
    private ArrayList<GalleryModel> galleryDataArrayList;
    private ArrayList<PhotoModel> photoOfTheDayArrayList;
    private GalleryAdapter galleryAdapter;
    private Photo_Of_The_Day_Adapter photo_of_the_day_adapter;

    private RecyclerView youtubeRecyclerview;
    private ArrayList<YoutubeDataModel> youtubeDataModelArrayList;
    private YoutubeAdapter youtubeAdapter;
    private ProgressBar galleryprogressBar, sliderprogressBar, youtubeprogressBar;
    private CountDownTimer countDownTimer;
    private TextView countdownTextView,more_gallery;

    //Social Media
    ImageView ivFacebook, ivInstagram, ivLinkedIn, ivTwitter;

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
//        String userId=user.getUid();
        //Assigning variable for Social Media
        ivFacebook = view.findViewById(R.id.facebook);
        ivInstagram = view.findViewById(R.id.instagram);
        ivTwitter = view.findViewById(R.id.twitter);
        ivLinkedIn = view.findViewById(R.id.linkdIn);
        galleryprogressBar = view.findViewById(R.id.gallery_progressBar);
        sliderprogressBar = view.findViewById(R.id.slider_progressBar);
        youtubeprogressBar = view.findViewById(R.id.youtube_progressBar);

        galleryprogressBar.setVisibility(View.VISIBLE);
        sliderprogressBar.setVisibility(View.VISIBLE);
       // youtubeprogressBar.setVisibility(View.VISIBLE);
        db = FirebaseFirestore.getInstance();
        more_gallery = view.findViewById(R.id.more_gallery);

        //This Section is for countDown Timer
        countdownTextView = view.findViewById(R.id.countdown_timer);

        // Initialize the countdown timer with a duration of 60 seconds and an interval of 1 second
        Calendar eventDate = Calendar.getInstance();
        eventDate.set(2023, 3, 01, 10, 0, 0); // Month is zero-based, so 2 means March
        long eventDateInMillis = eventDate.getTimeInMillis();

        countDownTimer = new CountDownTimer(eventDateInMillis - System.currentTimeMillis(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(days);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));

                String remainingTime = days + " days " + hours + " hours " + minutes + " minutes " + seconds + " seconds";
                //String countdownText = String.format(Locale.getDefault(), "%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
                countdownTextView.setText(remainingTime);
            }

            @Override
            public void onFinish() {
                countdownTextView.setText("Time's up!");
            }
        };


        // Start the countdown timer
        countDownTimer.start();

        //youtubeDataModelArrayList = new ArrayList<>();

        youtubeRecyclerview = view.findViewById(R.id.recyclerview_youtuve);
        youtubeDataModelArrayList = new ArrayList<>();
        youtubeRecyclerview.setHasFixedSize(true);
        youtubeRecyclerview.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));

//        YoutubeDataModel youtubeDataModel = new YoutubeDataModel("https://www.youtube.com/watch?v=iGGiksqehYc");
//        youtubeDataModelArrayList.add(youtubeDataModel);
//        youtubeDataModel = new YoutubeDataModel("https://www.youtube.com/watch?v=iGGiksqehYc");
//        youtubeDataModelArrayList.add(youtubeDataModel);
//        youtubeDataModel = new YoutubeDataModel("https://www.youtube.com/watch?v=iGGiksqehYc");
//        youtubeDataModelArrayList.add(youtubeDataModel);
//        youtubeDataModel = new YoutubeDataModel("https://www.youtube.com/watch?v=iGGiksqehYc");
//        youtubeDataModelArrayList.add(youtubeDataModel);
//        youtubeDataModel = new YoutubeDataModel("https://www.youtube.com/watch?v=iGGiksqehYc");
//        youtubeDataModelArrayList.add(youtubeDataModel);
        youtubeAdapter = new YoutubeAdapter(youtubeDataModelArrayList,view.getContext());
        youtubeRecyclerview.setAdapter(youtubeAdapter);

        loadYoutubeVideos();
        // creating a new array list for our array list.
        sliderDataArrayList = new ArrayList<>();
        galleryDataArrayList = new ArrayList<>();
        photoOfTheDayArrayList = new ArrayList<>();

        // initializing our slider view and
        // firebase firestore instance.
        sliderView = view.findViewById(R.id.image_slider);
        recyclerView = view.findViewById(R.id.gallery_recycler);
        photorecyclerView = view.findViewById(R.id.photoOFTheDay_recycler);



        recyclerView.setHasFixedSize(true);
        // adding horizontal layout manager for our recycler view.
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL, false));
        // adding our array list to our recycler view adapter class.
        galleryAdapter = new GalleryAdapter(view.getContext(),galleryDataArrayList);
        recyclerView.setAdapter(galleryAdapter);

        //For photo of the Day
        photorecyclerView.setHasFixedSize(true);
        photorecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));
        photo_of_the_day_adapter = new Photo_Of_The_Day_Adapter(view.getContext(),photoOfTheDayArrayList);
        photorecyclerView.setAdapter(photo_of_the_day_adapter);
        //calling our method to load images.
        loadImages();
        loadGalleryImage();
        loadPhotoOfTheDayImage();


        ivFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/shishir.nitm?mibextid=ZbWKwL"));
                // Replace "https://www.facebook.com/" with the URL of the user's Facebook profile if you want to link to a specific profile.
                startActivity(intent);
            }
        });
        ivInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/shishir_nitm?igshid=YmMyMTA2M2Y="));
                // Replace "https://www.facebook.com/" with the URL of the user's Facebook profile if you want to link to a specific profile.
                startActivity(intent);
            }
        });
        ivTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/shishir_nitm?s=09"));
                // Replace "https://www.facebook.com/" with the URL of the user's Facebook profile if you want to link to a specific profile.
                startActivity(intent);
            }
        });

        ivLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        more_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GalleryActivity.class);
                startActivity(intent);
            }
        });

    }

    private void openLink(String sAppLink, String sPackage, String sWebLink){

        try {
            Uri uri = Uri.parse(sAppLink);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setPackage(sPackage);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException activityNotFoundException){
            Uri uri = Uri.parse(sWebLink);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
           // intent.setPackage(sPackage);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private  void loadImages(){
        db.collection("Slider").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                sliderprogressBar.setVisibility(View.GONE);
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    SliderDataModel sliderDataModel = documentSnapshot.toObject(SliderDataModel.class);
                    SliderDataModel model = new SliderDataModel();

                    // below line is use for setting our
                    // image url for our modal class.
                    model.setImgUrl(sliderDataModel.getImgUrl());

                    // after that we are adding that
                    // data inside our array list.
                    sliderDataArrayList.add(model);

                    // after adding data to our array list we are passing
                    // that array list inside our adapter class.
                    sliderAdapter = new SliderAdapter(getContext(),sliderDataArrayList);

                    // below line is for setting adapter
                    // to our slider view
                    sliderView.setSliderAdapter(sliderAdapter);

                    //below line is for setting animation to our slider.
                    sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);

                    sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                    sliderView.setScrollTimeInSec(3);
                    sliderView.setAutoCycle(true);
                    sliderView.startAutoCycle();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                sliderprogressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Fail to load slider data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadGalleryImage(){
        db.collection("Gallery").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                galleryprogressBar.setVisibility(View.GONE);
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                GalleryModel galleryDataModel = documentSnapshot.toObject(GalleryModel.class);
                GalleryModel model = new GalleryModel();

                // below line is use for setting our
                // image url for our modal class.
                model.setImgUrl(galleryDataModel.getImgUrl());

                // after that we are adding that
                // data inside our array list.
                galleryDataArrayList.add(model);

                // after adding data to our array list we are passing
                // that array list inside our adapter class.
                galleryAdapter = new GalleryAdapter(getContext(),galleryDataArrayList);

                recyclerView.setAdapter(galleryAdapter);
            }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                galleryprogressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Fail to load slider data.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Photo Of the Day
    private void loadPhotoOfTheDayImage(){
        db.collection("PhotoOfTheDay").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                galleryprogressBar.setVisibility(View.GONE);
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    PhotoModel galleryDataModel = documentSnapshot.toObject(PhotoModel.class);
                    PhotoModel model = new PhotoModel();

                    // below line is use for setting our
                    // image url for our modal class.
                    model.setImgUrl(galleryDataModel.getImgUrl());

                    // after that we are adding that
                    // data inside our array list.
                    photoOfTheDayArrayList.add(model);

                    // after adding data to our array list we are passing
                    // that array list inside our adapter class.
                    photo_of_the_day_adapter = new Photo_Of_The_Day_Adapter(getContext(),photoOfTheDayArrayList);

                    photorecyclerView.setAdapter(photo_of_the_day_adapter);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                galleryprogressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Fail to load slider data.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadYoutubeVideos(){
        db.collection("Youtube videos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               // galleryprogressBar.setVisibility(View.GONE);
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    YoutubeDataModel youtubeDataModel = documentSnapshot.toObject(YoutubeDataModel.class);
                    YoutubeDataModel model = new YoutubeDataModel();

                    // below line is use for setting our
                    // image url for our modal class.
                    model.setLink(youtubeDataModel.getLink());

                    // after that we are adding that
                    // data inside our array list.
                    youtubeDataModelArrayList.add(model);

                    // after adding data to our array list we are passing
                    // that array list inside our adapter class.
                    youtubeAdapter = new YoutubeAdapter(youtubeDataModelArrayList, getContext());

                    youtubeRecyclerview.setAdapter(youtubeAdapter);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //galleryprogressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Fail to load slider data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}