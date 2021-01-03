package org.iptime.mascore.musiconcloud;

/**
 * Created by Owner on 2017-02-28.
 */

import android.graphics.drawable.Drawable;

public class SongListViewItem {
    private String rankStr;
    private Drawable iconDrawable;
    private String titleStr;
    private String artistsStr;
    private String albumStr;
    private String songIdStr;

    public void setRank(String rank) {
        rankStr = rank;
    }
    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setArtists(String artists) {
        artistsStr = artists ;
    }
    public void setAlbum(String album) {
        albumStr = album;
    }
    public void setSongId(String songId) {
        songIdStr = songId;
    }

    public String getRank() {
        return this.rankStr;
    }
    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getArtists() {
        return this.artistsStr ;
    }
    public String getAlbum() {
        return this.albumStr;
    }
    public String getSongId() {
        return this.songIdStr;
    }
}