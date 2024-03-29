package com.nitmeghalaya.shishir_2k23.Team;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nitmeghalaya.shishir_2k23.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamFragment extends Fragment {
    RecyclerView view;
    EventAdapterTeam myAdapter;
    DatabaseReference mbase;
    Parcelable recyclerViewState;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TeamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamFragment newInstance(String param1, String param2) {
        TeamFragment fragment = new TeamFragment();
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the state of the RecyclerView
        recyclerViewState = view.getLayoutManager().onSaveInstanceState();
        outState.putParcelable("recyclerViewState", recyclerViewState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable("recyclerViewState");
        }
        view = (RecyclerView) v.findViewById(R.id.recview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        view.setLayoutManager(layoutManager);
        mbase= FirebaseDatabase.getInstance("https://shishir-2k23-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Events");
        FirebaseRecyclerOptions<String> options
                = new FirebaseRecyclerOptions.Builder<String>()
                .setQuery(mbase,String.class)
                .build();
        myAdapter = new EventAdapterTeam(options,v.getContext());
        // Connecting Adapter class with the Recycler view*/
        view.setAdapter(myAdapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        myAdapter.startListening();
        if (recyclerViewState != null) {
            view.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }


}