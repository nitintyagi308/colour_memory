package colormemory.test.com.colourmemory.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import colormemory.test.com.colourmemory.R;
import colormemory.test.com.colourmemory.data.ListItemBean;

/**
 * Created by nitin.tyagi on 1/16/2017.
 */

public class ScoreListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ScoreListRecyclerAdapter";
    private final List<ListItemBean> list;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public ScoreListRecyclerAdapter( List<ListItemBean> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.score_list_item_layout, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.score_list_item_layout, parent, false);
            return new HeaderViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // to differentiate between the header row and a normal score row
        if (holder instanceof ItemViewHolder) {
            ListItemBean item = getItem(position);
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mTVRank.setVisibility(View.VISIBLE);
            itemViewHolder.mTVName.setText(item.getName());
            itemViewHolder.mTVRank.setText(String.valueOf(item.getRank()));
            itemViewHolder.mTVScore.setText(String.valueOf(item.getScore()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private ListItemBean getItem(int position) {
        /*Returning position -1 because there is an extra row of the header for which we do not
        * have the data*/
        return list.get(position - 1);
    }


    /**
     * Returns true if the row is a header else return false for score row.
     * @param position position in the list
     * @return true/false
     */
    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    /**
     * Returning list.size() + 1, because of the header view. Now the getView will be called 1 extra
     * time than the size of the list.
     * @return the list count + 1
     */
    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    /**
     * View Holder for the header. Reused the same Layout just made it bold and italic
     */
    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View view) {
            super(view);
            ((TextView) (view.findViewById(R.id.tv_rank_score_list_dialog))).setTypeface(null, Typeface.BOLD_ITALIC);
            ((TextView) view.findViewById(R.id.tv_name_score_list_dialog)).setTypeface(null, Typeface.BOLD_ITALIC);
            ((TextView) view.findViewById(R.id.tv_score_score_list_dialog)).setTypeface(null, Typeface.BOLD_ITALIC);
        }
    }

    /**
     * View holder for the score row.
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTVRank;
        public final TextView mTVName;
        public final TextView mTVScore;

        public ItemViewHolder(View view) {
            super(view);
            mView = view;
            mTVRank = (TextView) view.findViewById(R.id.tv_rank_score_list_dialog);
            mTVName = (TextView) view.findViewById(R.id.tv_name_score_list_dialog);
            mTVScore = (TextView) view.findViewById(R.id.tv_score_score_list_dialog);
        }
    }

}
