package com.saili.hzz.core.constant;

/**
 * @author whuab_mc
 */
public final class Constants {

    /**
     * 河流 编码
     */
    public static final String RIVER_ENAME = "river";
    /**
     * 河流 名称
     */
    public static final String RIVER_NAME = "河流";

    /**
     * 湖泊 编码
     */
    public static final String LAKE_ENAME = "lake";
    /**
     * 湖泊 名称
     */
    public static final String LAKE_NAME = "湖泊";

    /**
     * 河段 编码
     */
    public static final String REACH_ENAME = "reach";
    /**
     * 河段 名称
     */
    public static final String REACH_NAME = "河段";

    /**
     * 水库 编码
     */
    public static final String RESERVOIR_ENAME = "reservoir";
    /**
     * 水库 名称
     */
    public static final String RESERVOIR_NAME = "水库";

    public static final class Activiti {
        /**
         * 定义流程定义KEY，必须以“PD_”开头
         * 组成结构：string[]{"流程标识","业务主表表名"}
         */
        public static final String[] PD_PROBLEM = new String[]{"problem_process", "t_sl_base_river_lake_patrol_event"};

        public static final class ProblemNode {
            /**
             * 村级河长节点id
             */
            public static final String HAMLET_RIVER_NODE_ID = "hamletRiver";
            /**
             * 乡级河长节点id
             */
            public static final String TOWN_RIVER_NODE_ID = "townRiver";
            /**
             * 县级河长节点id
             */
            public static final String COUNTY_RIVER_NODE_ID = "countyRiver";
            /**
             * 市级河长节点id
             */
            public static final String CITY_RIVER_NODE_ID = "cityRiver";
            /**
             * 省级河长节点
             */
            public static final String PROVINCE_RIVER_NODE_ID = "provinceRiver";
        }
    }

    /**
     * 区划树结构指定查询的根编码
     * 想要知道区划的编码可以执行sql：select code from t_sl_division where divisionName = '需要查询区划的名称';
     * 当前区划编码为【江苏省】的区划编码
     */
    public static final String ROOT_DIVISION_CODE = "320000000000";

    /**
     * 默认市级行政区划编码为徐州市的编码
     */
    public static final String DEFAULT_CITY_DIVISION_CODE = "320300000000";

    public static final class Dict {
        /**
         * 河长类型 分类编码
         */
        public static final String HZ_TYPE_TYPE_GROUP_CODE = "river_duty_user_type";
    }

    public static final class HzType {
        /**
         * 第一总河长编码
         */
        public static final String FIST_PRESIDENT_HZ_CODE = "1";
        /**
         * 总河长编码
         */
        public static final String PRESIDENT_HZ_CODE = "2";
        /**
         * 副总河长编码
         */
        public static final String VICE_PRESIDENT_HZ_CODE = "3";
        /**
         * 河长编码
         */
        public static final String HZ_CODE = "4";
        /**
         * 副河长编码
         */
        public static final String VICE_HZ_CODE = "5";
    }

    public static final class RiverLakeType {
        /**
         * 河流编码
         */
        public static final String RIVER_CODE = "10";
        /**
         * 湖泊编码
         */
        public static final String LAKE_CODE = "11";
        /**
         * 河段编码
         */
        public static final String REACH_CODE = "12";
        /**
         * 水库编码
         */
        public static final String RESERVOIR_CODE = "13";


        /**
         * 取水口编码
         */
        public static final String INTAKE_CODE = "20";
        /**
         * 水功能区编码
         */
        public static final String WATER_FUNCTION_CODE = "21";
        /**
         * 测站编码
         */
        public static final String SURVEY_STATION_CODE = "22";
        /**
         * 排污口编码
         */
        public static final String SEWAGE_OUTLET_CODE = "23";
    }

    public static final class DivisionLevel {
        /**
         * 省级
         */
        public static final String PROVINCE_LEVEL = "province";
        /**
         * 市级
         */
        public static final String CITY_LEVEL = "city";
        /**
         * 县级
         */
        public static final String COUNTY_LEVEL = "county";
        /**
         * 乡级
         */
        public static final String TOWN_LEVEL = "town";
        /**
         * 村级
         */
        public static final String VILLAGE_LEVEL = "village";
    }

    /**
     * @author whuab_mc
     * @version 1.0
     * @Description 日期格式化key
     * @since 2019-10-16
     */
    public static final class DateFormatKeys {
        public final static String DATE_TIME_FORMAT = "yyyyMMddHHmmss";

        public final static String DATE_TIME_FORMAT_CN = "yyyy年MM月dd日 HH:mm:ss";
        public static final String DATE_10_FORMAT = "yyyy-MM-dd";

        public static final String DATE_6_FORMAT = "yyyyMM";
        public static final String DATE_14_FORMAT = "yyyy-MM-dd HH:mm";
        public static final String DATE_16_FORMAT = "yyyy-MM-dd HH:mm:ss";
        public static final String TIME_4_FORMAT = "HH:mm";
        public static final String DATE_18_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    }

    public static final class JoddDateFormat {
        public final static String DATE_TIME = "YYYY-MM-DD hh:mm:ss";
        public final static String DATE = "YYYY-MM-DD";
        public final static String DATE_HOUR = "YYYY-MM-DD hh:mm";
    }
}
