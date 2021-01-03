package org.iptime.mascore.musiconcloud.Melon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Owner on 2017-02-27.
 */

public class MelonController {
    private final MelonChartAPI melonChartAPI = new MelonChartAPI();
    private final MelonSearchAPI melonSearchAPI = new MelonSearchAPI();
    private final MelonDJAPI melonDJAPI = new MelonDJAPI();

    private List melonDJCategory;
    private List melonChart;

    public void setMelonChart() {
        melonChart = melonChartAPI.getMelonChart();
    }

    public List getMelonChart() {
        return melonChart;
    }

    public List getMelonSearchResult(String keyword) {
        return melonSearchAPI.getMelonSearchResult(keyword);
    }

    public void setMelonDJCategory() {
        melonDJCategory = melonDJAPI.getMelonDJCategory();
    }

    public List getMelonDJCategory() {
        return melonDJCategory;
    }

    public List getMelonDJDetail(int firstIndex, int secondIndex) { // categoryId가 [firstIndex]이고 offeringId가 [secondIndex]인 DJ 플레이리스트 안의 음악을 가져옴
        Map firstMap = (HashMap)melonDJCategory.get(firstIndex);
        List list = (ArrayList)firstMap.get("sub");
        Map secondMap = (HashMap)list.get(secondIndex);
        return melonDJAPI.getMelonDJDetail((int)secondMap.get("categoryId"), (int)secondMap.get("offeringId"));
    }
}
