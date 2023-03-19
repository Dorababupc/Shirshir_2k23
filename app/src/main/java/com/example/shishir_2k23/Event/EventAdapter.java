package com.example.shishir_2k23.Event;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shishir_2k23.EventRegisterActivity;
import com.example.shishir_2k23.R;
import com.example.shishir_2k23.Registration.Group_Registration_Activity;
import com.example.shishir_2k23.Team.TeamFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final Context context;
    private final ArrayList<EventModel> eventModelArrayList;

    public EventAdapter(Context context, ArrayList<EventModel> eventModelArrayList){
        this.context = context;
        this.eventModelArrayList = eventModelArrayList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_item,parent,false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        EventModel model = eventModelArrayList.get(position);
       // EventModel m=eventModelArrayList.get(position);
        holder.eventNameTV.setText(model.getEvent_name());
        String registerCount = model.getEvent_name().toString();
        String event_type = model.getEvent_type().toString();
      //  Log.d("event_type",model.getEvent_type());
//        holder.countRegisterId.setText(""+model.getRegistration_count());
        Picasso.get().load(model.getImgUrl()).into(holder.eventIV);
        //holder.eventIV.setImageResource(model.getEvent_image());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection(registerCount);

        collectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int size = queryDocumentSnapshots.size();
                holder.countRegisterId.setText("" + size);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Error fetching collection size", e);
            }
        });
        holder.eventRule.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(model.getPdfUrl()));
                holder.eventRule.getContext().startActivity(intent);

            }
        });

        holder.eventRegisterTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getEvent_type().equals("solo")){

                    Intent intent = new Intent(context, EventRegisterActivity.class);
                    intent.putExtra("event_name", model.getEvent_name());
                    context.startActivity(intent);
                } else if (model.getEvent_type().equals("duet")) {
                    Intent intent = new Intent(context, Group_Registration_Activity.class);
                    intent.putExtra("event_name", model.getEvent_name());
                    context.startActivity(intent);

                }
                else if (model.getEvent_type().equals("both")){
                    Toast.makeText(context, "This for both", Toast.LENGTH_SHORT).show();
                    View popupView = LayoutInflater.from(context).inflate(R.layout.event_type_popup_screen, null);

                    TextView solo = popupView.findViewById(R.id.solo_reg);
                    TextView group = popupView.findViewById(R.id.group_reg);
                    ImageView cancel = popupView.findViewById(R.id.cancel_popup);
                    //Create the PopupWindow object
                    PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    //Set the click listener on the TextView
                    solo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Start the new activity
                            Intent intent = new Intent(context, EventRegisterActivity.class);
                            intent.putExtra("event_name", model.getEvent_name());
                            context.startActivity(intent);

                            //Dismiss the PopupWindow
                            popupWindow.dismiss();
                        }
                    });
                    group.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Start the new activity
                            Intent intent = new Intent(context, Group_Registration_Activity.class);
                            intent.putExtra("event_name", model.getEvent_name());
                            context.startActivity(intent);

                            //Dismiss the PopupWindow
                            popupWindow.dismiss();
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });


                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setFocusable(true);
                    popupWindow.setClippingEnabled(false);
                    popupWindow.setWidth(800); // Set the width of the popup window to 800 pixels
                    popupWindow.setHeight(600);
                    //Set the background of the PopupWindow
                    //popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    //Show the PopupWindow at the center of the screen
                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                    //Dismiss the PopupWindow on outside touch
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setFocusable(true);
                    popupWindow.setTouchable(true);
                    //Show the PopupWindow
                    popupWindow.showAsDropDown(view);
                }

            }
        });




    }

    @Override
    public int getItemCount() {
        return eventModelArrayList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder{
        private final ImageView eventIV;
        private final TextView eventNameTV;
        private final TextView eventRegisterTV;
        private final TextView eventRule;
        private final TextView eventTeam;
        private final TextView countRegisterId;

        public EventViewHolder(View itemView){
            super(itemView);
            eventIV = itemView.findViewById(R.id.eventImage);
            eventNameTV = itemView.findViewById(R.id.eventName);

            eventRegisterTV = itemView.findViewById(R.id.register_id);
            eventRule = itemView.findViewById(R.id.rule_id);
            eventTeam = itemView.findViewById(R.id.team_id);
            countRegisterId = itemView.findViewById(R.id.count_register_id);


//            itemView.findViewById(R.id.register_id).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context, "Registered button Clicked", Toast.LENGTH_SHORT).show();
//                   // AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                    Intent intent = new Intent(context, EventRegisterActivity.class);
//                    intent.putExtra("event_name", (CharSequence) eventNameTV);
//                    context.startActivity(intent);
//
//                }
//            });
            itemView.findViewById(R.id.rule_id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Rule button Clicked", Toast.LENGTH_SHORT).show();
                }
            });
            itemView.findViewById(R.id.team_id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Team Button is Clicked", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(context, TeamFragment.class);
//                    context.startActivity(intent);
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    TeamFragment teamFragment = new TeamFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,teamFragment).addToBackStack(null).commit();
                }
            });
        }
    }
}
