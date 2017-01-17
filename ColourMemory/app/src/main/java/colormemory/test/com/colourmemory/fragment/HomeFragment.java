package colormemory.test.com.colourmemory.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import colormemory.test.com.colourmemory.R;
import colormemory.test.com.colourmemory.adapter.CustomAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private CustomAdapter customAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        GridView gridView = (GridView)view.findViewById(R.id.gridViewCards);
        Button reset = (Button)view.findViewById(R.id.btn_reset);
        Button highScore = (Button)view.findViewById(R.id.btn_high_score);
        TextView txtViewScore = (TextView) view.findViewById(R.id.tv_score);

        //reset button click event.
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != customAdapter){
                    customAdapter.resetGame();
                }
            }
        });

        //high score button click event.
        highScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHighScoreTable();
            }
        });

        customAdapter = new CustomAdapter(getActivity() ,txtViewScore );
        gridView.setAdapter(customAdapter);
        return view;
    }

    /**
     * this method is use for displaying ScoreList Dialog Fragment.
     */
    private void showHighScoreTable(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ScoreListDialogFragment scoreListDialogFragment = new ScoreListDialogFragment();
        scoreListDialogFragment.show(ft,"scoreListFrag");
    }

}
