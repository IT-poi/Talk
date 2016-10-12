package com.cuit.talk.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cuit.talk.activity.MainActivity;
import com.cuit.talk.activity.R;

/**
 * Created by inori on 16/10/5.
 */
public class FriendExpandableListAdapter implements ExpandableListAdapter {

    private Context context;

    private String[] armTypes;

    private String[][] arms;

    public FriendExpandableListAdapter(Context context, String[]armTypes, String[][] arms) {
        this.context = context;
        this.armTypes = armTypes;
        this.arms = arms;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    /**
     * @return 组的长度
     */
    @Override
    public int getGroupCount() {
        return armTypes.length;
    }

    /**
     *
     * @param groupPosition
     * @return 子组的长度
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return arms[groupPosition].length;
    }

    /**
     *
     * @param groupPosition
     * @return 指定groupPosition组的内容
     */
    @Override
    public Object getGroup(int groupPosition) {
        return armTypes[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return arms[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 组选项的外观
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        HolderGroup groupHolder;
        if (null == convertView){
            groupHolder = new HolderGroup();
            convertView = LayoutInflater.from(context).inflate(R.layout.zu_layout, parent, false);
    //        groupHolder.groupImage = (ImageView)convertView.findViewById(R.id.image1);
            groupHolder.groupText = (TextView)convertView.findViewById(R.id.text1);
            convertView.setTag(groupHolder);
        }else {
            groupHolder = (HolderGroup) convertView.getTag();
        }
        groupHolder.groupText.setText(getGroup(groupPosition).toString());

        return convertView;
    }

    /**
     * 自选项的外观
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        HolderChild holder = new HolderChild();
        if (null == convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.child_layout, parent, false);
            holder.childImage = (ImageView)convertView.findViewById(R.id.image2);
            holder.childText = (TextView)convertView.findViewById(R.id.text2);
            convertView.setTag(holder);
        }else {
            holder = (HolderChild) convertView.getTag();
        }
        holder.childImage.setImageResource(R.drawable.head);
        holder.childImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "点击了头像", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
        holder.childText.setText(getChild(groupPosition, childPosition).toString());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    public class HolderGroup{
        private ImageView groupImage;
        private TextView groupText;

    }

    public class HolderChild{

        private ImageView childImage;
        private TextView childText;
    }

}
