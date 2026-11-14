package org.telegram.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;

public class BulogramSettingsActivity extends BaseFragment {
    private static final int TYPE_BUTTON = 0;
    private static final int TYPE_SHADOW = 1;

    private static final int ROW_MAIN = 0;
    private static final int ROW_CHATS = 1;
    private static final int ROW_APPEARANCE = 2;
    private static final int ROW_EXPERIMENTAL = 3;
    private static final int ROW_SHADOW_1 = 4;
    private static final int ROW_EXPORT = 5;
    private static final int ROW_IMPORT = 6;
    private static final int ROW_RESET = 7;
    private static final int ROW_RESTART = 8;
    private static final int ROW_SHADOW_2 = 9;
    private static final int ROW_ABOUT = 10;
    private static final int ROW_SHADOW_3 = 11;
    private static final int ROW_COUNT = 12;

    private RecyclerListView listView;

    @Override
    protected boolean useSimpleActionBar() {
        return true;
    }

    @Override
    public View createView(Context context) {
        setHasOwnBackground(true);
        simpleActionBar.setBackButtonImage(R.drawable.ic_ab_back);
        simpleActionBar.setTitle("Настройки Bulogram");
        simpleActionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1) {
                    finishFragment();
                }
            }
        });

        FrameLayout frameLayout = new FrameLayout(context);
        fragmentView = frameLayout;

        listView = new RecyclerListView(context);
        listView.setSectionsDrawBackground(true);
        listView.setSections();
        listView.setLayoutManager(new LinearLayoutManager(context));
        listView.setVerticalScrollBarEnabled(false);
        listView.setAdapter(new ListAdapter(context));
        listView.setOnItemClickListener((view, position) -> onRowClicked(position));
        frameLayout.addView(listView, LayoutHelper.createFrame(
                LayoutHelper.MATCH_PARENT,
                LayoutHelper.MATCH_PARENT
        ));
        return fragmentView;
    }

    private void onRowClicked(int position) {
        if (getParentActivity() == null) {
            return;
        }
        Toast.makeText(getParentActivity(), "Скоро", Toast.LENGTH_SHORT).show();
    }

    private static String rowTitle(int position) {
        switch (position) {
            case ROW_MAIN: return "Основные";
            case ROW_CHATS: return "Чаты";
            case ROW_APPEARANCE: return "Внешний вид";
            case ROW_EXPERIMENTAL: return "Экспериментальные";
            case ROW_EXPORT: return "Экспорт настроек";
            case ROW_IMPORT: return "Импорт настроек";
            case ROW_RESET: return "Сброс настроек";
            case ROW_RESTART: return "Перезапустить приложение";
            case ROW_ABOUT: return "О проекте";
            default: return "";
        }
    }

    // Иконки: пока временная для всех пунктов. Меняй здесь по одной на нужную.
    private static int rowIcon(int position) {
        return R.drawable.settings_gift;
    }

    private class ListAdapter extends RecyclerListView.SelectionAdapter {
        private final Context context;

        private ListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getItemCount() {
            return ROW_COUNT;
        }

        @Override
        public boolean isEnabled(RecyclerView.ViewHolder holder) {
            return holder.getItemViewType() == TYPE_BUTTON;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            if (viewType == TYPE_BUTTON) {
                view = new TextCell(context);
            } else {
                view = new ShadowSectionCell(context);
            }
            view.setLayoutParams(new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
            ));
            return new RecyclerListView.Holder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder.getItemViewType() == TYPE_BUTTON) {
                boolean divider = position != ROW_EXPERIMENTAL && position != ROW_RESTART && position != ROW_ABOUT;
                ((TextCell) holder.itemView).setTextAndIcon(rowTitle(position), rowIcon(position), divider);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == ROW_SHADOW_1 || position == ROW_SHADOW_2 || position == ROW_SHADOW_3) {
                return TYPE_SHADOW;
            }
            return TYPE_BUTTON;
        }
    }

    @Override
    protected RecyclerListView getListViewForSimpleGlass() {
        return listView;
    }

    @Override
    public boolean isSupportEdgeToEdge() {
        return true;
    }
}
