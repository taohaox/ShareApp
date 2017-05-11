package com.gonyb.share;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 检查日期 给刘芬妹子写的
 * Created by Gonyb on 2017/5/9.
 */
public class LiuFenCheckDate {

    public static void main(String[] args){
        System.out.println(123);
        Random random = new Random();
        List<Map<String,String>> mapList = new ArrayList<>();

        SimpleDateFormat simple = new SimpleDateFormat("HH:mm:ss");
        for(int i=0;i<3;i++){
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY,random.nextInt(10));
            Map<String,String> map = new HashMap<>();
            map.put("start", simple.format(calendar.getTime()));
            calendar.add(Calendar.HOUR_OF_DAY,2);
            map.put("end", simple.format(calendar.getTime()));
            map.put("isUsed", "true");
            mapList.add(map);
        }
        //打印list
        System.out.println("原始数据");
        mapList.forEach(map->{
            System.out.print("map[");
            map.forEach((k,v)->System.out.print(k+" = "+v+"\t"));
            System.out.println("]");
        });
        boolean b = checkDate(mapList);
        if (b) {
            System.out.println("检查通过");
        } else {
            System.out.println("时间不正确");
        }

    }

    public static boolean checkDate(List<Map<String,String>> list){
        List<Map<String,Long>> mapList = new ArrayList<>();

        //将数据添加到 mapList
        for(Map<String,String> map:list){
            String startStr = map.get("start");
            String endStr = map.get("end");
            String isUsed = map.get("isUsed");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(new Date());

            SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                if ("true".equals(isUsed)) {
                    long startTime = simple.parse(format + " " + startStr).getTime();
                    long endTime = simple.parse(format + " " + endStr).getTime();
                    if(startTime>endTime){
                        return false;
                    }
                    HashMap<String,Long> hashMap = new HashMap<>();
                    hashMap.put("start",startTime);
                    hashMap.put("end",endTime);
                    mapList.add(hashMap);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        //给mapList排序  按开始时间 从小到大排
        mapList.sort((map1,map2)->{
            Long start1 = map1.get("start");
            Long start2 = map2.get("start");
            return start1>start2?1:-1;
        });
        //打印map 查看排序
        System.out.println();
        mapList.forEach(map->{
            System.out.print("map[");
            map.forEach((k,v)->System.out.print(k+" = "+v+"\t"));
            System.out.println("]");
        });

        if (mapList.size() == 1) { //如果只传过来一个时间
            return true;
        } else if (mapList.size() == 2) { //如果传过来两个时间
            Map<String, Long> map1 = mapList.get(0);
            Map<String, Long> map2 = mapList.get(1);
            if(map1.get("end")>map2.get("start")){//如果第一个的结束时间大于第二个的结束时间 则返回false
                return false;
            }
        } else if (mapList.size() == 3) {//如果传过来三个时间
            Map<String, Long> map1 = mapList.get(0);
            Map<String, Long> map2 = mapList.get(1);
            Map<String, Long> map3 = mapList.get(2);
            if(map1.get("end")>map2.get("start")){//如果第一个的结束时间大于第二个的结束时间 则返回false
                return false;
            }
            if(map2.get("end")>map3.get("start")){//如果第二个的结束时间大于第三个的结束时间 则返回false
                return false;
            }
        }
        return true;
    }
}
