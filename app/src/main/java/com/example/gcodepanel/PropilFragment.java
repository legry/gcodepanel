package com.example.gcodepanel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

public class PropilFragment extends Fragment {

    private ContentAdapter propil;

    public ContentAdapter getPropil() {
        return propil;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.recycler, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
        Bundle args = getArguments();
        String mymode = Objects.requireNonNull(args).getString("mymode");
        propil = new ContentAdapter(Objects.requireNonNull(getContext()), Objects.requireNonNull(mymode));
        recyclerView.setAdapter(propil);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // Set padding for Tiles (not needed for Cards/Lists!)
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}
