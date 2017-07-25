package com.research.andrade.andar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;


/**
 * A simple {@link Fragment} subclass.
 */
public class Update extends Fragment {

    //private EditText hanap;
    private ListView tweetsLv;


    public Update() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_update, container, false);


        //hanap = (EditText) v.findViewById(R.id.svTweets);
        tweetsLv = (ListView) v.findViewById(R.id.lvTweets);

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("dost_pagasa")
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity())
                .setTimeline(userTimeline)
                .build();

        tweetsLv.setAdapter(adapter);
        tweetsLv.setTextFilterEnabled(true);
        /*hanap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/


        return v;
    }


}
