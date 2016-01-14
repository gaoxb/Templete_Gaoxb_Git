package com.fang.templet.view.indexListView;

import java.util.Arrays;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.widget.SectionIndexer;

public class ContactsSectionIndexer implements SectionIndexer {

    private static String OTHER = "#";
    private static String[] mSections = {OTHER, "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z"};

    private static int OTHER_INDEX = 0; // OTHER 的索引值

    private int[] mPositions; // 每一部分的起始位置

    private int mCount; // 总共的条目数量

    public ContactsSectionIndexer(List<ContactItemInterface> contacts) {
        mCount = contacts.size();
        initPositions(contacts);
    }

    public String getSectionTitle(String indexableItem) {
        int sectionIndex = getSectionIndex(indexableItem);
        return mSections[sectionIndex];
    }

    /**
     * 获取拼音首字母在mSections中的位置
     * @param indexableItem
     * @return
     */
    public int getSectionIndex(String indexableItem) {
        if (indexableItem == null) {
            return OTHER_INDEX;
        }
        indexableItem = indexableItem.trim();
        String firstLetter = OTHER;
        if (indexableItem.length() == 0) {
            return OTHER_INDEX;
        } else {
            firstLetter = String.valueOf(indexableItem.charAt(0)).toUpperCase(); //获取第一个首字母 大写
        }
        int sectionCount = mSections.length;
        for (int i = 0; i < sectionCount; i++) {
            if (mSections[i].equals(firstLetter)) {
                return i;
            }
        }
        return OTHER_INDEX;

    }

    /**
     * 初始化每一个组别的大小也就是出现的位置
     * @param contacts
     */
    public void initPositions(List<ContactItemInterface> contacts) {

        int sectionCount = mSections.length;
        mPositions = new int[sectionCount];
        Arrays.fill(mPositions, -1);//初始化位置数组
        int itemIndex = 0;
        for (ContactItemInterface contact : contacts) {
            String indexableItem = contact.getItemForIndex();
            int sectionIndex = getSectionIndex(indexableItem);
            if (mPositions[sectionIndex] == -1)
                mPositions[sectionIndex] = itemIndex;
            itemIndex++;
        }
        int lastPos = -1;
        // now loop through, for all the ones not found, set position to the one
        // before them
        // this is to make sure the array is sorted for binary search to work
        for (int i = 0; i < sectionCount; i++) {
            if (mPositions[i] == -1)
                mPositions[i] = lastPos;
            lastPos = mPositions[i];
        }
    }

    @Override
    public int getPositionForSection(int section) {
        if (section < 0 || section >= mSections.length) {
            return -1;
        }
        return mPositions[section];
    }

    @Override
    public int getSectionForPosition(int position) {
        if (position < 0 || position >= mCount) {
            return -1;
        }
        int index = Arrays.binarySearch(mPositions, position);
		/*
         * Consider this example: section positions are 0, 3, 5; the supplied
		 * position is 4. The section corresponding to position 4 starts at
		 * position 3, so the expected return value is 1. Binary search will not
		 * find 4 in the array and thus will return -insertPosition-1, i.e. -3.
		 * To get from that number to the expected value of 1 we need to negate
		 * and subtract 2.
		 */
        return index >= 0 ? index : -index - 2;
    }

    @Override
    public Object[] getSections() {
        return mSections;
    }

    /**
     * 是不是第一部分的第一个字母
     * @param position
     * @return
     */
    public boolean isFirstItemInSection(int position) {
        int section = Arrays.binarySearch(mPositions, position);
        return (section > -1);
    }

}
