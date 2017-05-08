package com.gonyb.share.domain.param;

/**
 * 请求车辆信息
 * Created by Gonyb on 2017/5/5.
 */
public class BicycleParam {

    /**
     * share_code : 00000
     * area : 宝安
     * city : 深圳
     * latitude : 15.125615
     * longitude : 154.154823
     * province : 广东
     */

    private String share_code;
    private String area;
    private String city;
    private double latitude;
    private double longitude;
    private String province;

    public String getShare_code() {
        return share_code;
    }

    public void setShare_code(String share_code) {
        this.share_code = share_code;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
