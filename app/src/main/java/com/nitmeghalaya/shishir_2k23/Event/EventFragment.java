package com.nitmeghalaya.shishir_2k23.Event;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nitmeghalaya.shishir_2k23.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    FirebaseFirestore db;
    private ArrayList<EventModel> eventModelArrayList;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private String mParam1;
    private String mParam2;

    public EventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventFragment newInstance(String param1, String param2) {
        EventFragment fragment = new EventFragment();
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
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.event_recyclerView);
        eventModelArrayList = new ArrayList<EventModel>();
        db = FirebaseFirestore.getInstance();

        recyclerView.setHasFixedSize(true);

//        eventModelArrayList.add(new EventModel("Dance and dram",R.drawable.one));
//        eventModelArrayList.add(new EventModel("Panach and Singing",R.drawable.two));
//        eventModelArrayList.add(new EventModel("Mobile and PC Gaming",R.drawable.three));
//        eventModelArrayList.add(new EventModel("Group Dance",R.drawable.five));
//        eventModelArrayList.add(new EventModel("Departmental Event",R.drawable.four));
//        eventModelArrayList.add(new EventModel("Dance and dram",R.drawable.six));

        eventAdapter = new EventAdapter(view.getContext(),eventModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(eventAdapter);

        loadGalleryImage();
    }

    private void loadGalleryImage(){
        db.collection("Event List").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    EventModel eventModel = documentSnapshot.toObject(EventModel.class);
//                    eventModel.setId(documentSnapshot.getId());
                    EventModel model = new EventModel();
                    // below line is use for setting our
                    // image url for our modal class.
                    model.setEvent_name(eventModel.getEvent_name());
                    model.setImgUrl(eventModel.getImgUrl());
                    model.setEvent_type(eventModel.getEvent_type());
                    model.setPdfUrl(eventModel.getPdfUrl());
                    model.setId(documentSnapshot.getId());
                    // after that we are adding that
                    // data inside our array list.
                    eventModelArrayList.add(model);

                    // after adding data to our array list we are passing
                    // that array list inside our adapter class.
                    eventAdapter = new EventAdapter(getContext(),eventModelArrayList);

                    recyclerView.setAdapter(eventAdapter);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Fail to load slider data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}