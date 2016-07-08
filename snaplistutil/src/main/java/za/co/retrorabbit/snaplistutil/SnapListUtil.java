package za.co.retrorabbit.snaplistutil;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by wsche on 2016/06/03.
 */
public class SnapListUtil {

    public static void addSnapLayoutManager(RecyclerView recyclerView, LayoutManagerType layoutManagerType, int orientation, boolean reversed) {
        switch (layoutManagerType) {
            case LINEAR:
                SmoothLinearLayoutManager smoothLinearLayoutManager = SmoothLinearLayoutManager.getInstance(recyclerView.getContext(), orientation, reversed);
                recyclerView.setLayoutManager(smoothLinearLayoutManager);

                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    Direction direction = Direction.DOWN;

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        switch (newState) {
                            case RecyclerView.SCROLL_STATE_DRAGGING:
                            case RecyclerView.SCROLL_STATE_SETTLING:
                                ((SmoothLinearLayoutManager) recyclerView.getLayoutManager()).setMPI(25f);
                                break;
                            case RecyclerView.SCROLL_STATE_IDLE:
                                SmoothLinearLayoutManager layoutManager = (SmoothLinearLayoutManager) recyclerView.getLayoutManager();
                                layoutManager.setMPI(150f);
                                if (layoutManager.findFirstCompletelyVisibleItemPosition() != layoutManager.findFirstVisibleItemPosition()) {

                                    switch (direction) {
                                        case DOWN:
                                            layoutManager.smoothScrollToPosition(recyclerView, new RecyclerView.State(), layoutManager.findLastVisibleItemPosition());
                                            break;
                                        case UP:
                                            layoutManager.smoothScrollToPosition(recyclerView, new RecyclerView.State(), layoutManager.findFirstVisibleItemPosition());
                                            break;
                                    }
                                }
                                break;
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        switch (((SmoothLinearLayoutManager) recyclerView.getLayoutManager()).getOrientation()) {
                            //HORISONTAL
                            case 0:
                                if (dx < 0) {
                                    // Recycle view scrolling up...
                                    direction = Direction.UP;
                                } else if (dx > 0) {
                                    // Recycle view scrolling down...
                                    direction = Direction.DOWN;
                                }
                                break;
                            //VERTICAL
                            case 1:
                                if (dy < 0) {
                                    // Recycle view scrolling up...
                                    direction = Direction.UP;
                                } else if (dy > 0) {
                                    // Recycle view scrolling down...
                                    direction = Direction.DOWN;
                                }
                                break;
                        }

                    }
                });
        }
    }

    public static void addSnapLayoutManager(ListView listView, LayoutManagerType layoutManagerType, int orientation, boolean reversed) {
        switch (layoutManagerType) {
            case LINEAR:
                listView.setOnScrollListener(new ListView.OnScrollListener() {
                    Direction direction = Direction.DOWN;
                    private int mLastFirstVisibleItem = 0;

                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        switch (scrollState) {
                            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                                // ((SmoothLinearLayoutManager) recyclerView.getLayoutManager()).setMPI(25f);
                                break;
                            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:

                                //layoutManager.setMPI(150f);
                                //if (layoutManager.findFirstCompletelyVisibleItemPosition() != layoutManager.findFirstVisibleItemPosition()) {

                                switch (direction) {
                                    case DOWN:
                                        view.smoothScrollToPosition(view.getLastVisiblePosition());
                                        break;
                                    case UP:
                                        view.smoothScrollToPosition(view.getFirstVisiblePosition());
                                        break;
                                }
                                //}
                                break;
                        }
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        if (mLastFirstVisibleItem < firstVisibleItem) {
                            // Recycle view scrolling down...
                            direction = Direction.DOWN;
                        }
                        if (mLastFirstVisibleItem > firstVisibleItem) {
                            // Recycle view scrolling up...
                            direction = Direction.UP;
                        }
                        mLastFirstVisibleItem = firstVisibleItem;
                    }

                });
                break;
        }
    }

    private enum Direction {
        UP,
        DOWN
    }

    public enum LayoutManagerType {
        LINEAR,
        GRID,
        STAGGERED
    }

    static class SmoothLinearLayoutManager extends LinearLayoutManager {

        private float MILLISECONDS_PER_INCH = 25f;
        private float MPI;

        public static SmoothLinearLayoutManager getInstance(Context context, int orientation, boolean reverseLayout) {
            return new SmoothLinearLayoutManager(context, orientation, reverseLayout);
        }

        public SmoothLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
                                           int position) {
            LinearSmoothScroller linearSmoothScroller =
                    new LinearSmoothScroller(recyclerView.getContext()) {
                        @Override
                        public PointF computeScrollVectorForPosition(int targetPosition) {
                            return SmoothLinearLayoutManager.this
                                    .computeScrollVectorForPosition(targetPosition);
                        }

                        @Override
                        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                            return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
                        }
                    };
            linearSmoothScroller.setTargetPosition(position);
            startSmoothScroll(linearSmoothScroller);
        }

        public void setMPI(float MPI) {
            this.MILLISECONDS_PER_INCH = MPI;
        }
    }
}
