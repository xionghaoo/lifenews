package xh.lifenews.lifepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xh.lifenews.GlideLoader;
import xh.lifenews.R;
import xh.lifenews.model.Constants;
import xh.lifenews.utils.TransitionHelper;
import xh.lifenews.utils.Util;
import xh.lifenews.model.object.LifeEntry;

public class LifeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private LifePageAdapter mPageAdapter;

    public LifeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LifeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LifeFragment newInstance(String param1, String param2) {
        LifeFragment fragment = new LifeFragment();
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
        View v = inflater.inflate(R.layout.fragment_life, container, false);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar_life);
        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_life_title);
        title.setText(Util.getString(getActivity(), R.string.toolbar_title_life));

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);

        mPageAdapter = new LifePageAdapter(getActivity(),
                getLifeItems(), new LifePageAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(View v, LifeEntry item) {
                showItemDetailPage(v, item);
            }
        });

        StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mPageAdapter);
        //item数量固定时，可以设置这个
        mRecyclerView.setHasFixedSize(true);

        return v;
    }

    public List<LifeEntry> getLifeItems() {
        List<LifeEntry> items = new ArrayList<>();
        for (int i = 0; i < Constants.LIEF_ITEM_NAMES.length; i++) {
            LifeEntry entry = new LifeEntry();
            entry.setName(Constants.LIEF_ITEM_NAMES[i]);
            entry.setType(i);
            items.add(entry);
        }
        return items;
    }

    private void showItemDetailPage(View v, LifeEntry entry) {
        ImageView imageView = (ImageView) v.findViewById(R.id.item_life_icon);
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(
                getActivity(),
                false,
                new Pair<>(imageView, getString(R.string.transition_reveal1))
        );
        Intent i = new Intent(getActivity(), LifeDetailActivity.class);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);
        i.putExtra(LifeDetailActivity.EXTRA_ITEM_TYPE, entry.getType());
        startActivity(i, options.toBundle());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


//    public interface OnFragmentInteractionListener {
//        void changeTabHostState(boolean isShow);
//    }
}
