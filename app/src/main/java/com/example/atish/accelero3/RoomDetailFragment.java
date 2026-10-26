package com.example.atish.accelero3;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atish.accelero3.dummy.DummyContent;

/**
 * A fragment representing a single Room detail screen.
 * This fragment is either contained in {@link MainActivity}
 * (or {@link RoomListActivity}) in two-pane mode on tablets, or in a
 * {@link RoomDetailActivity} on handsets.
 */
public class RoomDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RoomDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(arguments.getString(ARG_ITEM_ID));

            Activity activity = getActivity();
            CollapsingToolbarLayout appBarLayout = activity == null ? null
                    : (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null && mItem != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_room_detail, container, false);

        // Show the dummy content as text in a TextView.
        TextView detailView = (TextView) rootView.findViewById(R.id.room_detail);
        if (detailView == null) {
            return rootView;
        }
        if (mItem == null) {
            detailView.setText(R.string.room_not_found);
            return rootView;
        }

        Activity activity = getActivity();
        boolean hasCollapsingTitle = activity != null
                && activity.findViewById(R.id.toolbar_layout) != null;
        String details = mItem.details == null ? "" : mItem.details;
        if (hasCollapsingTitle) {
            detailView.setText(details);
        } else {
            // Two-pane layout has no collapsing toolbar, so keep the room name
            // with the detail body.
            String content = mItem.content == null ? "" : mItem.content;
            detailView.setText(content + "\n\n" + details);
        }

        return rootView;
    }
}
