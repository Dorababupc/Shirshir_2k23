package com.nitmeghalaya.shishir_2k23.Event;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.nitmeghalaya.shishir_2k23.EventRegisterActivity;
import com.nitmeghalaya.shishir_2k23.R;
import com.nitmeghalaya.shishir_2k23.Registration.Group_Registration_Activity;
import com.nitmeghalaya.shishir_2k23.Team.TeamFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final Context context;
    private final ArrayList<EventModel> eventModelArrayList;
    int total_register;

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
        //Defining Warning popup registration

        Picasso.get().load(model.getImgUrl()).into(holder.eventIV);
        //holder.eventIV.setImageResource(model.getEvent_image());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection(registerCount);
        CollectionReference collectionRefGroup = db.collection(registerCount+"_Group");

        collectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                total_register = queryDocumentSnapshots.size();
                // holder.countRegisterId.setText("" + size);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Error fetching collection size", e);
            }
        });

        collectionRefGroup.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int size = queryDocumentSnapshots.size()+total_register;
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
        holder.rule_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(model.getPdfUrl()));
                holder.eventRule.getContext().startActivity(intent);
            }
        });
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String outerString = model.getId(); // the outer string to check
        // Query the 'likes' collection with the outer string as a filter
//        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Likes")
                .whereEqualTo("eventId", outerString)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Get the array of strings for the current document
                            ArrayList<String> innerStrings = (ArrayList<String>) document.get("userIds");
                            holder.likeCount.setText(String.valueOf(innerStrings.size()));
                            // Check if the inner string is present in the array of strings
                            if(firebaseUser!=null){
                                if (innerStrings.contains(firebaseUser.getUid())) {
                                    holder.liked=true;
                                    holder.love.setImageResource(R.drawable.ic_baseline_favorite_24);
                                }
                            }

                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });

        holder.love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firebaseUser==null){
                    Toast.makeText(holder.love.getContext(),"You need to login to like",Toast.LENGTH_LONG).show();
                }
                else{
                    if(holder.liked){
                        holder.liked=false;
                        holder.love.setImageResource(R.drawable.ic_baseline_favorite_border_24);

                        CollectionReference likesRef = db.collection("Likes");

                        likesRef.whereEqualTo("eventId", model.getId()).get()
                                .addOnSuccessListener(queryDocumentSnapshots -> {
                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        ArrayList<String> innerStrings = (ArrayList<String>) documentSnapshot.get("userIds");
                                        if (innerStrings != null) {
                                            innerStrings.remove(firebaseUser.getUid());
                                            documentSnapshot.getReference().update("userIds", innerStrings);
                                        }
                                    }
                                });

                    }
                    else{
                        holder.liked=true;
                        holder.love.setImageResource(R.drawable.ic_baseline_favorite_24);
                        Map<String, Object> updateMap = new HashMap<>();
                        updateMap.put("userIds", FieldValue.arrayUnion(firebaseUser.getUid()));
                        db.collection("Likes").whereEqualTo("eventId", model.getId()).get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Update the document with the new array value
                                    db.collection("Likes").document(document.getId()).update(updateMap)
                                            .addOnSuccessListener(aVoid -> {
                                                // Update successful
                                                Log.d(TAG, "Document updated");
                                            })
                                            .addOnFailureListener(e -> {
                                                // Update failed
                                                Log.e(TAG, "Error updating document", e);
                                            });
                                }
                            } else {
                                Log.e(TAG, "Error getting documents: ", task.getException());
                            }
                        });

                    }
                }
            }
        });






            View.OnClickListener onClickListener = new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    if (model.getEvent_type().equals("solo")){
                        View warningpopupView = LayoutInflater.from(context).inflate(R.layout.warning_popup_screen, null);
                        ImageView cancelworningpopup =  warningpopupView.findViewById(R.id.warning_icon);
                        PopupWindow warningpopupWindow = new PopupWindow(warningpopupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        cancelworningpopup.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                warningpopupWindow.dismiss();
                                Intent intent = new Intent(context, EventRegisterActivity.class);
                                intent.putExtra("event_name", model.getEvent_name());
                                context.startActivity(intent);
                            }
                        });
                        warningpopupWindow.setOutsideTouchable(true);
                        warningpopupWindow.setFocusable(true);
                        warningpopupWindow.setClippingEnabled(false);
                        warningpopupWindow.setWidth(900); // Set the width of the popup window to 800 pixels
                        warningpopupWindow.setHeight(600);
                        //Set the background of the PopupWindow
                        //popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        //Show the PopupWindow at the center of the screen
                        warningpopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                        //Dismiss the PopupWindow on outside touch
                        warningpopupWindow.setOutsideTouchable(true);
                        warningpopupWindow.setFocusable(true);
                        warningpopupWindow.setTouchable(true);
                        //Show the PopupWindow
                        warningpopupWindow.showAsDropDown(view);


                    } else if (model.getEvent_type().equals("duet")) {
                        View warningpopupView = LayoutInflater.from(context).inflate(R.layout.warning_popup_screen, null);
                        ImageView cancelworningpopup =  warningpopupView.findViewById(R.id.warning_icon);
                        PopupWindow warningpopupWindow = new PopupWindow(warningpopupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        cancelworningpopup.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                warningpopupWindow.dismiss();
                                Intent intent = new Intent(context, Group_Registration_Activity.class);
                                intent.putExtra("event_name", model.getEvent_name());
                                context.startActivity(intent);
                            }
                        });
                        warningpopupWindow.setOutsideTouchable(true);
                        warningpopupWindow.setFocusable(true);
                        warningpopupWindow.setClippingEnabled(false);
                        warningpopupWindow.setWidth(900); // Set the width of the popup window to 800 pixels
                        warningpopupWindow.setHeight(600);
                        //Set the background of the PopupWindow
                        //popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        //Show the PopupWindow at the center of the screen
                        warningpopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                        //Dismiss the PopupWindow on outside touch
                        warningpopupWindow.setOutsideTouchable(true);
                        warningpopupWindow.setFocusable(true);
                        warningpopupWindow.setTouchable(true);
                        //Show the PopupWindow
                        warningpopupWindow.showAsDropDown(view);



                    }
                    else if (model.getEvent_type().equals("both")){
                        //Toast.makeText(context, "This for both", Toast.LENGTH_SHORT).show();
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
                                //Dismiss the PopupWindow
                                popupWindow.dismiss();
                                View warningpopupView = LayoutInflater.from(context).inflate(R.layout.warning_popup_screen, null);
                                ImageView cancelworningpopup =  warningpopupView.findViewById(R.id.warning_icon);
                                PopupWindow warningpopupWindow = new PopupWindow(warningpopupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                cancelworningpopup.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        warningpopupWindow.dismiss();
                                        Intent intent = new Intent(context, EventRegisterActivity.class);
                                        intent.putExtra("event_name", model.getEvent_name());
                                        context.startActivity(intent);
                                    }
                                });
                                warningpopupWindow.setOutsideTouchable(true);
                                warningpopupWindow.setFocusable(true);
                                warningpopupWindow.setClippingEnabled(false);
                                warningpopupWindow.setWidth(900); // Set the width of the popup window to 800 pixels
                                warningpopupWindow.setHeight(600);
                                //Set the background of the PopupWindow
                                //popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                //Show the PopupWindow at the center of the screen
                                warningpopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                                //Dismiss the PopupWindow on outside touch
                                warningpopupWindow.setOutsideTouchable(true);
                                warningpopupWindow.setFocusable(true);
                                warningpopupWindow.setTouchable(true);
                                //Show the PopupWindow
                                warningpopupWindow.showAsDropDown(view);
                            }
                        });
                        group.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Start the new activity


                                //Dismiss the PopupWindow
                                popupWindow.dismiss();
                                View warningpopupView = LayoutInflater.from(context).inflate(R.layout.warning_popup_screen, null);
                                ImageView cancelworningpopup =  warningpopupView.findViewById(R.id.warning_icon);
                                PopupWindow warningpopupWindow = new PopupWindow(warningpopupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                cancelworningpopup.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        warningpopupWindow.dismiss();
                                        Intent intent = new Intent(context, Group_Registration_Activity.class);
                                        intent.putExtra("event_name", model.getEvent_name());
                                        context.startActivity(intent);
                                    }
                                });
                                warningpopupWindow.setOutsideTouchable(true);
                                warningpopupWindow.setFocusable(true);
                                warningpopupWindow.setClippingEnabled(false);
                                warningpopupWindow.setWidth(900); // Set the width of the popup window to 800 pixels
                                warningpopupWindow.setHeight(600);
                                //Set the background of the PopupWindow
                                //popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                //Show the PopupWindow at the center of the screen
                                warningpopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                                //Dismiss the PopupWindow on outside touch
                                warningpopupWindow.setOutsideTouchable(true);
                                warningpopupWindow.setFocusable(true);
                                warningpopupWindow.setTouchable(true);
                                //Show the PopupWindow
                                warningpopupWindow.showAsDropDown(view);
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
            };
            holder.eventRegisterTV.setOnClickListener(onClickListener);
            holder.eventreg_icon.setOnClickListener(onClickListener);
        }

        @Override
        public int getItemCount() {
            return eventModelArrayList.size();
        }
//    private class EventViewHolder extends RecyclerView.ViewHolder{
//        private final ImageView eventIV;
//        private final TextView eventNameTV;
//        private final TextView eventRegisterTV;
//        private final ImageView eventreg_icon;
//        private final TextView eventRule;
//        private final ImageView rule_icon;
////        private final TextView eventTeam;
////        private final ImageView team_icon;
//        private final TextView countRegisterId;
//        private final TextView likeCount;
//        private final ImageView love;
//        private final TextView countRegisterId;
//        private  boolean liked;
//
//        public EventViewHolder(View itemView){
//            super(itemView);
//            eventIV = itemView.findViewById(R.id.eventImage);
//            eventNameTV = itemView.findViewById(R.id.eventName);
//
//            liked=false;
//            eventRegisterTV = itemView.findViewById(R.id.register_id);
//            eventreg_icon = itemView.findViewById(R.id.register_link);
//            eventRule = itemView.findViewById(R.id.rule_id);
//            rule_icon = itemView.findViewById(R.id.rule_link);
////            eventTeam = itemView.findViewById(R.id.team_id);
////            team_icon = itemView.findViewById(R.id.team_link);
//            likeCount = itemView.findViewById(R.id.like_count);
//            love = itemView.findViewById(R.id.love);
//            countRegisterId = itemView.findViewById(R.id.count_register_id);
//
//
////            itemView.findViewById(R.id.register_id).setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    Toast.makeText(context, "Registered button Clicked", Toast.LENGTH_SHORT).show();
////                   // AppCompatActivity activity = (AppCompatActivity) view.getContext();
////                    Intent intent = new Intent(context, EventRegisterActivity.class);
////                    intent.putExtra("event_name", (CharSequence) eventNameTV);
////                    context.startActivity(intent);
////
////                }
////            });
//            itemView.findViewById(R.id.rule_id).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context, "Rule button Clicked", Toast.LENGTH_SHORT).show();
//                }
//            });
//            View.OnClickListener onClickListener = new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //Toast.makeText(context, "Team Button is Clicked", Toast.LENGTH_SHORT).show();
////                    Intent intent = new Intent(context, TeamFragment.class);
////                    context.startActivity(intent);
//                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                    TeamFragment teamFragment = new TeamFragment();
//                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,teamFragment).addToBackStack(null).commit();
//                }
//            };
//
////            itemView.findViewById(R.id.team_id).setOnClickListener(onClickListener);
////            itemView.findViewById(R.id.team_link).setOnClickListener(onClickListener);
//        }
//    }
//}
        public class EventViewHolder extends RecyclerView.ViewHolder{
            private final ImageView eventIV;
            private final TextView eventNameTV;
            private final TextView eventRegisterTV;
            private final ImageView eventreg_icon;
            private final TextView eventRule;
            private final ImageView rule_icon;
            private final TextView countRegisterId;
            private final TextView likeCount;
            private final ImageView love;
            private boolean liked;

            public EventViewHolder(View itemView){
                super(itemView);
                eventIV = itemView.findViewById(R.id.eventImage);
                eventNameTV = itemView.findViewById(R.id.eventName);
                likeCount = itemView.findViewById(R.id.like_count);
                love = itemView.findViewById(R.id.love);
                liked=false;
                eventRegisterTV = itemView.findViewById(R.id.register_id);
                eventreg_icon = itemView.findViewById(R.id.register_link);
                eventRule = itemView.findViewById(R.id.rule_id);
                rule_icon = itemView.findViewById(R.id.rule_link);
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
                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(context, "Team Button is Clicked", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(context, TeamFragment.class);
//                    context.startActivity(intent);
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        TeamFragment teamFragment = new TeamFragment();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,teamFragment).addToBackStack(null).commit();
                    }
                };
            }
        }
    }
