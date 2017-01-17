package colormemory.test.com.colourmemory.fragment;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import colormemory.test.com.colourmemory.R;
import colormemory.test.com.colourmemory.database.DBHelper;

/**
 * Created by nitin.tyagi on 1/16/2017.
 */

public class NameDialogFragment extends DialogFragment {

    private int score;
    private int rank;
    private long insertedId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.name_dialog_fragment, container, false);

        TextView tvScore = (TextView) view.findViewById(R.id.tv_score_in_dialog);
        TextView tvRank = (TextView) view.findViewById(R.id.tv_rank_in_dialog);
        final EditText etName = (EditText) view.findViewById(R.id.et_name);
        Button btnSave = (Button) view.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().length() < 1) {
                    etName.setError(getString(R.string.please_enter_your_name));
                    etName.requestFocus();
                    return;
                }
                new DBHelper(getActivity()).updateNameInDB(etName.getText().toString(), insertedId);
                dismiss();
            }
        });

        tvScore.setText(getString(R.string._score, score));
        tvRank.setText(getString(R.string._rank, rank));
        return view;
    }


    /**
     * this method is use for setting the score.
     * @param score
     */
    public void setScore(int score){
        this.score = score;
    }

    /**
     * this method is use for setting the rank.
     * @param rank
     */
    public void setRank(int rank){
        this.rank = rank;
    }

    /**
     * this method is use for setting the database insertedId.
     * @param insertedId
     */
    public void setInsertedId(long insertedId){
        this.insertedId = insertedId;
    }
}
