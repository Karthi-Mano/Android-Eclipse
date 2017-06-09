package com.example.weather.beans;

import java.util.List;

public class CityBean {

	/**
     * errNum : 0
     * errMsg : success
     * retData : [{"province_cn":"北京","district_cn":"北京","name_cn":"北京","name_en":"beijing","area_id":"101010100"},{"province_cn":"北京","district_cn":"北京","name_cn":"海淀","name_en":"haidian","area_id":"101010200"},{"province_cn":"北京","district_cn":"北京","name_cn":"朝阳","name_en":"chaoyang","area_id":"101010300"},{"province_cn":"北京","district_cn":"北京","name_cn":"顺义","name_en":"shunyi","area_id":"101010400"},{"province_cn":"北京","district_cn":"北京","name_cn":"怀柔","name_en":"huairou","area_id":"101010500"},{"province_cn":"北京","district_cn":"北京","name_cn":"通州","name_en":"tongzhou","area_id":"101010600"},{"province_cn":"北京","district_cn":"北京","name_cn":"昌平","name_en":"changping","area_id":"101010700"},{"province_cn":"北京","district_cn":"北京","name_cn":"延庆","name_en":"yanqing","area_id":"101010800"},{"province_cn":"北京","district_cn":"北京","name_cn":"丰台","name_en":"fengtai","area_id":"101010900"},{"province_cn":"北京","district_cn":"北京","name_cn":"石景山","name_en":"shijingshan","area_id":"101011000"},{"province_cn":"北京","district_cn":"北京","name_cn":"大兴","name_en":"daxing","area_id":"101011100"},{"province_cn":"北京","district_cn":"北京","name_cn":"房山","name_en":"fangshan","area_id":"101011200"},{"province_cn":"北京","district_cn":"北京","name_cn":"密云","name_en":"miyun","area_id":"101011300"},{"province_cn":"北京","district_cn":"北京","name_cn":"门头沟","name_en":"mentougou","area_id":"101011400"},{"province_cn":"北京","district_cn":"北京","name_cn":"平谷","name_en":"pinggu","area_id":"101011500"}]
     */

    private int errNum;
    private String errMsg;
    /**
     * province_cn : 北京
     * district_cn : 北京
     * name_cn : 北京
     * name_en : beijing
     * area_id : 101010100
     */

    private List<RetDataBean> retData;

    public int getErrNum () {
        return errNum;
    }

    public void setErrNum (int errNum) {
        this.errNum = errNum;
    }

    public String getErrMsg () {
        return errMsg;
    }

    public void setErrMsg (String errMsg) {
        this.errMsg = errMsg;
    }

    public List<RetDataBean> getRetData () {
        return retData;
    }

    public void setRetData (List<RetDataBean> retData) {
        this.retData = retData;
    }

    public static class RetDataBean {
        private String province_cn;
        private String district_cn;
        private String name_cn;
        private String name_en;
        private String area_id;

        public String getProvince_cn () {
            return province_cn;
        }

        public void setProvince_cn (String province_cn) {
            this.province_cn = province_cn;
        }

        public String getDistrict_cn () {
            return district_cn;
        }

        public void setDistrict_cn (String district_cn) {
            this.district_cn = district_cn;
        }

        public String getName_cn () {
            return name_cn;
        }

        public void setName_cn (String name_cn) {
            this.name_cn = name_cn;
        }

        public String getName_en () {
            return name_en;
        }

        public void setName_en (String name_en) {
            this.name_en = name_en;
        }

        public String getArea_id () {
            return area_id;
        }

        public void setArea_id (String area_id) {
            this.area_id = area_id;
        }
    }
	
}
