package com.fang.templet.view.indexListView;

import java.util.Collections;
import java.util.List;

import com.fang.templet.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactListAdapter extends ArrayAdapter<ContactItemInterface> {

    private int resource; // store the resource layout id for 1 row
    private boolean inSearchMode = false;
    private ContactsSectionIndexer indexer = null;

    public ContactListAdapter(Context _context, int _resource,
                              List<ContactItemInterface> _items) {
        super(_context, _resource, _items);
        resource = _resource;
        Collections.sort(_items, new ContactItemComparator());
        setIndexer(new ContactsSectionIndexer(_items));
    }

    /**
     * 从item 不居中获取textview
     *
     * @param rowView
     * @return
     */
    public TextView getSectionTextView(View rowView) {
        TextView sectionTextView = (TextView) rowView
                .findViewById(R.id.sectionTextView);
        return sectionTextView;
    }

    /**
     * 设置是显示索引字母还是不显示
     *
     * @param rowView
     * @param item
     * @param position
     */
    public void showSectionViewIfFirstItem(View rowView,
                                           ContactItemInterface item, int position) {
        TextView sectionTextView = getSectionTextView(rowView);
        if (inSearchMode) {
            sectionTextView.setVisibility(View.GONE);
        } else {
            if (indexer.isFirstItemInSection(position)) {
                String sectionTitle = indexer.getSectionTitle(item
                        .getItemForIndex());
                sectionTextView.setText(sectionTitle);
                sectionTextView.setVisibility(View.VISIBLE);
            } else
                sectionTextView.setVisibility(View.GONE);
        }
    }

    /**
     * @param parentView
     * @param item
     * @param position
     */
    public void populateDataForRow(View parentView, ContactItemInterface item,
                                   int position) {
        View infoView = parentView.findViewById(R.id.infoRowContainer);
        TextView nameView = (TextView) infoView.findViewById(R.id.cityName);
        nameView.setText(item.getItemForIndex());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup parentView;
        ContactItemInterface item = getItem(position);
        if (convertView == null) {
            parentView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, parentView, true);
        } else {
            parentView = (LinearLayout) convertView;
        }
        showSectionViewIfFirstItem(parentView, item, position);
        populateDataForRow(parentView, item, position);
        return parentView;
    }

    public boolean isInSearchMode() {
        return inSearchMode;
    }

    public void setInSearchMode(boolean inSearchMode) {
        this.inSearchMode = inSearchMode;
    }

    public ContactsSectionIndexer getIndexer() {
        return indexer;
    }

    public void setIndexer(ContactsSectionIndexer indexer) {
        this.indexer = indexer;
    }

}
