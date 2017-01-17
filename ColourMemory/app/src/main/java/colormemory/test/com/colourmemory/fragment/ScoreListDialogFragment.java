package colormemory.test.com.colourmemory.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import colormemory.test.com.colourmemory.R;
import colormemory.test.com.colourmemory.adapter.ScoreListRecyclerAdapter;
import colormemory.test.com.colourmemory.data.ListItemBean;
import colormemory.test.com.colourmemory.database.DBHelper;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


/**
 * Created by nitin.tyagi on 1/16/2017.
 */

public class ScoreListDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.score_list_dialog_layout, container, false);

        List<ListItemBean> list = new DBHelper(getActivity()).getScoreList();

        if (v instanceof RecyclerView) {
            RecyclerView mRecyclerView = (RecyclerView) v;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            ScoreListRecyclerAdapter adapter = new ScoreListRecyclerAdapter(list);
            mRecyclerView.setAdapter(adapter);
        }
        return v;
    }
}


