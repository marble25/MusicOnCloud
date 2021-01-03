package org.iptime.mascore.musiconcloud;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SongListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<SongListViewItem> listViewItemSongList = new ArrayList<SongListViewItem>() ;

    // ListViewAdapter의 생성자
    public SongListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemSongList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "list_songs" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_songs, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView rankTextView = (TextView) convertView.findViewById(R.id.text_rank);
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.image_albumArt);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.text_songname);
        TextView artistTextView = (TextView) convertView.findViewById(R.id.text_artists);
        TextView albumTextView = (TextView) convertView.findViewById(R.id.text_album);
        TextView songIdTextView = (TextView) convertView.findViewById(R.id.text_songId);

        // Data Set(listViewItemSongList)에서 position에 위치한 데이터 참조 획득
        SongListViewItem songListViewItem = listViewItemSongList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        rankTextView.setText(songListViewItem.getRank());
        iconImageView.setImageDrawable(songListViewItem.getIcon());
        titleTextView.setText(songListViewItem.getTitle());
        artistTextView.setText(songListViewItem.getArtists());
        albumTextView.setText(songListViewItem.getAlbum());
        songIdTextView.setText(songListViewItem.getSongId());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemSongList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String rank, Drawable icon, String title, String artists, String album, String songId) {
        SongListViewItem item = new SongListViewItem();

        item.setRank(rank);
        item.setIcon(icon);
        item.setTitle(title);
        item.setArtists(artists);
        item.setAlbum(album);
        item.setSongId(songId);

        listViewItemSongList.add(item);
    }
}
