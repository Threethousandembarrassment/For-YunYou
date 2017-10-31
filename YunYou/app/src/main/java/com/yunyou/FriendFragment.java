package com.yunyou;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by HL on 2017/9/28 0028.
 */
public class FriendFragment extends Fragment {

    public FriendFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        Log.e("HEHE", "222222222222");
        return view;
    }
}
