package colormemory.test.com.colourmemory.adapter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import colormemory.test.com.colourmemory.R;
import colormemory.test.com.colourmemory.data.GridItemState;
import colormemory.test.com.colourmemory.database.DBHelper;
import colormemory.test.com.colourmemory.fragment.NameDialogFragment;

/**
 * Created by nitin.tyagi on 1/13/2017.
 */

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private static LayoutInflater inflater = null;
    private TextView scoreTxtView;
    private List<GridItemState> mCards = null;
    private GridItemState mCardStateofFirstClickedItem;
    private GridItemState mCardStateOfSecondClickedItem;
    private int mGoneCardCount = 0;
    private int mScore = 0;
    private int mOpenCardCount = 0;
    private int frstPos, secPos;
    private final int DELAY = 1000;


    public CustomAdapter(Context context, TextView scoreTxt) {
        this.context = context;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        scoreTxtView = scoreTxt;
        setListOfColor();
    }

    /**
     * this method is use for setting the list of color card.
     */
    private void setListOfColor() {
        List<Integer> content = new ArrayList<>();
        content.add(R.drawable.colour1);
        content.add(R.drawable.colour2);
        content.add(R.drawable.colour3);
        content.add(R.drawable.colour4);
        content.add(R.drawable.colour5);
        content.add(R.drawable.colour6);
        content.add(R.drawable.colour7);
        content.add(R.drawable.colour8);
        content.add(R.drawable.colour1);
        content.add(R.drawable.colour2);
        content.add(R.drawable.colour3);
        content.add(R.drawable.colour4);
        content.add(R.drawable.colour5);
        content.add(R.drawable.colour6);
        content.add(R.drawable.colour7);
        content.add(R.drawable.colour8);
        Collections.shuffle(content);
        mCards = new ArrayList<>(content.size());

        for (int i = 0; i < content.size(); i++) {
            GridItemState card = new GridItemState();
            card.setPosition(i);
            card.setGone(false);
            card.setContent(content.get(i));
            card.setOpen(false);
            mCards.add(card);
        }
    }

    @Override
    public int getCount() {
        return mCards.size();
    }

    @Override
    public Object getItem(int i) {
        return mCards.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        if (view == null) {
            view = inflater.inflate(R.layout.grid_row, null);
            holder.img = (ImageView) view.findViewById(R.id.grid_item);
            view.setTag(holder);
        }
        holder = (Holder) view.getTag();
        holder.img.setTag(i);
        final ImageView imageView = holder.img;

        GridItemState gridItemState = mCards.get(i);

        //set the default background if card is not in gone state.
        if (!gridItemState.getGone()) {
            holder.img.setBackgroundResource(R.drawable.card_bg);
        }

        //hide and show the card based on the gone state.
        if (gridItemState.getGone()) {
            holder.img.setVisibility(View.INVISIBLE);
        } else {
            holder.img.setVisibility(View.VISIBLE);
        }

        //set the score.
        if (null != scoreTxtView)
            scoreTxtView.setText(String.valueOf(mScore));

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int position = (int) view.getTag();

                //if click on any open card then return .
                if (mCards.get(position).getOpen()) {
                    return;
                }

                mOpenCardCount++;
                if (mOpenCardCount > 2) {
                    return;
                }

                if (mOpenCardCount == 1) {
                    //first card open
                    frstPos = position;
                    mCardStateofFirstClickedItem = mCards.get(frstPos);
                    mCardStateofFirstClickedItem.setOpen(true);
                    imageView.setBackgroundResource(mCards.get(position).getContent());
                } else if (mOpenCardCount == 2) {
                    secPos = position;
                    mCardStateOfSecondClickedItem = mCards.get(position);
                    mCardStateOfSecondClickedItem.setOpen(true);
                    imageView.setBackgroundResource(mCards.get(position).getContent());

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mOpenCardCount = 0;
                            if (mCardStateofFirstClickedItem.getContent() == mCardStateOfSecondClickedItem.getContent()) {
                                //imageView.setBackgroundColor(context.getResources().getColor(android.R.color.black));
                                mCardStateofFirstClickedItem.setGone(true);
                                mCardStateOfSecondClickedItem.setGone(true);
                                mScore = mScore + 2;

                                mGoneCardCount = mGoneCardCount + 2;
                                Log.d("TEST", "GONE Item Count === " + mGoneCardCount);
                                if (mGoneCardCount == getCount()) {
                                    //game over now show the game over dialog.
                                    showGameOverDialog();
                                }
                            } else {
                                mCardStateofFirstClickedItem.setOpen(false);
                                mCardStateOfSecondClickedItem.setOpen(false);
                                mCardStateofFirstClickedItem.setGone(false);
                                mCardStateOfSecondClickedItem.setGone(false);
                                mScore = mScore - 1;
                            }
                            notifyDataSetChanged();
                        }
                    }, DELAY);
                }
            }

        });
        return view;
    }


    /**
     * this method is use for showing the game over dialog.
     */
    private void showGameOverDialog() {

        DBHelper dbHelper = new DBHelper(context);
        long insertId = dbHelper.insertScore(mScore);
        int rank = dbHelper.getRankForInsertId(insertId);

        FragmentTransaction ft = ((Activity) context).getFragmentManager().beginTransaction();
        NameDialogFragment nameDialogFragment = new NameDialogFragment();
        nameDialogFragment.setScore(mScore);
        nameDialogFragment.setRank(rank);
        nameDialogFragment.setInsertedId(insertId);
        nameDialogFragment.show(ft, "nameDialogFrag");
    }


    /**
     * this method is use for reset the game.
     */
    public void resetGame() {
        //reset the game..
        mCards = null;
        setListOfColor();
        mScore = 0;
        mGoneCardCount = 0;
        notifyDataSetChanged();

    }

    public class Holder {
        ImageView img;
    }
}
