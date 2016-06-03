package com.synchron.ncpl.synchron;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.Query;

/**
 * Created by NCPL on 5/23/2016.
 */
public class ChatListAdapter extends FirebaseListAdapter<Chat> {

    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String mUsername;
    private String input;


    public ChatListAdapter(Query ref, Activity activity, int layout, String mUsername,String input) {
        super(ref, Chat.class, layout, activity);
        this.mUsername = mUsername;
        this.input = input;

    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param chat An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(View view, Chat chat) {
       // Map a Chat object to an entry in our listview
        String author = chat.getAuthor();
        String message = chat.getMessage();
        //String image = chat.getImage();
        TextView authorText = (TextView) view.findViewById(R.id.author);
        authorText.setText(author);
        TextView authorMessage = (TextView) view.findViewById(R.id.message);
        authorMessage.setText(message);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)authorText.getLayoutParams();
        //LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) authorMessage.getLayoutParams();
        // If the message was sent by this user, color it differently
        int sdk = Build.VERSION.SDK_INT;
        if (author != null && author.equals(mUsername)) {
            if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {

                authorText.setTextColor(Color.BLUE);
            } else {
                authorText.setTextColor(Color.GRAY);
            }
            layoutParams.gravity = Gravity.END;
        } else {
            if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                authorMessage.setTextColor(Color.DKGRAY);
            } else {
                authorMessage.setTextColor(Color.GRAY);
            }
            layoutParams.gravity = Gravity.START;
        }
        // Map a Chat object to an entry in our listview
        String author1 = chat.getAuthor();
        String message1 = chat.getMessage();
        //String image = chat.getImage();
        TextView authorText1 = (TextView) view.findViewById(R.id.author);
        authorText.setText(author);
        TextView authorMessage1 = (TextView) view.findViewById(R.id.message);
        authorMessage.setText(message1);
        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams)authorMessage1.getLayoutParams();
        //LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) authorMessage.getLayoutParams();
        // If the message was sent by this user, color it differently
        int sdk1 = Build.VERSION.SDK_INT;
        if (author != null && author.equals(mUsername)) {
            if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {

                authorMessage1.setTextColor(Color.GRAY);
            } else {
                authorMessage1.setTextColor(Color.GRAY);
            }
            layoutParams1.gravity = Gravity.END;
        } else {
            if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                authorText1.setTextColor(Color.GRAY);
            } else {
                authorText1.setTextColor(Color.GRAY);
            }
            layoutParams1.gravity = Gravity.START;
        }
    }


}
