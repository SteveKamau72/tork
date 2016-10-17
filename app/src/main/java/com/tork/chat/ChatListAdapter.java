package com.tork.chat;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.Query;
import com.tork.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author greg
 * @since 6/21/13
 * <p/>
 * This class is an example of how to use FirebaseListAdapter. It uses the <code>Chat</code> class to encapsulate the
 * data for each individual chat message
 */
public class ChatListAdapter extends FirebaseListAdapter<Chat> {

    Activity activity;
    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String mUsername;

    public ChatListAdapter(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, Chat.class, layout, activity);
        this.mUsername = mUsername;
        this.activity = activity;
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
        Log.d("author", author);
        //set views
        TextView messageText = (TextView) view.findViewById(R.id.message);
        TextView authorText = (TextView) view.findViewById(R.id.author);
        //set time ago
        TextView tvTimeAgo = (TextView) view.findViewById(R.id.time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        // I assume d-M, you may refer to M-d for month-day instead.
        Date date = null;
        // You will need try/catch around this
        try {
            date = formatter.parse(chat.getDayTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = date.getTime();
        Log.d("another time", String.valueOf(millis));
        TimeAgo timeAgo = new TimeAgo(activity);
        tvTimeAgo.setText(String.valueOf(timeAgo.timeAgo(millis)));
        //set textviews
        authorText.setText(author + ": ");
        messageText.setText(chat.getMessage());

        CircularImageNetworkView img = (CircularImageNetworkView) view.findViewById(R.id.profile);
        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.chat_view);
        // If the message was sent by this user, color it differently
        if (author.equals(mUsername)) {
            Log.d("author", author);
            authorText.setVisibility(View.GONE);
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                rl.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.chat_bubble_me));
            } else {
                rl.setBackground(activity.getResources().getDrawable(R.drawable.chat_bubble_me));
            }
            // text view layout params
            RelativeLayout.LayoutParams messageTextViewLayoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            messageTextViewLayoutParams.setMargins(120, 10, 15, 10);
            // add a rule to align to the right
            messageTextViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            rl.setPadding(20, 10, 10, 10);
            // make sure the rule was applied
            rl.setLayoutParams(messageTextViewLayoutParams);
            messageText.setTextColor(activity.getResources().getColor(R.color.black));
            img.setVisibility(View.GONE);
            tvTimeAgo.setTextColor(activity.getResources().getColor(R.color.colorPrimary));

        } else {
            img.setVisibility(View.GONE);

            authorText.setVisibility(View.VISIBLE);
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                rl.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.chat_bubble));
            } else {
                rl.setBackground(activity.getResources().getDrawable(R.drawable.chat_bubble));
            }
            // text view layout params
            RelativeLayout.LayoutParams messageTextViewLayoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            messageTextViewLayoutParams.setMargins(10, 10, 120, 10);
//            messageTextViewLayoutParams.setMargins(100, 10, 120, 10);
            // add a rule to align to the right
            messageTextViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            rl.setPadding(10, 10, 10, 10);
            // make sure the rule was applied
            rl.setLayoutParams(messageTextViewLayoutParams);
            messageText.setTextColor(activity.getResources().getColor(R.color.white));
            tvTimeAgo.setTextColor(activity.getResources().getColor(R.color.dark_blue));
        }

    }
}
