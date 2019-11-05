package com.saili.hzz.core.vo;

import com.google.common.collect.Lists;

import java.util.List;

/***
 * 地区巡河统计数据图表 vo
 * @author: whuab_mc
 * @date: 2019-09-11 16:20:16:20
 * @version: V1.0
 */
public class AreaRiverPatrolStatsEChartsVo {
    public static final String TARGET_NAME = "应巡数量";
    public static final String VALUE_NAME = "已巡数量";
    public static final String PASS_RATE_NAME = "完成率";

    private String[] legend;
    /**
     * x轴 data
     */
    private String[] xAxis;
    /**
     * y轴 data
     */
    private String[] yAxis;
    /**
     * series data
     */
    private List<Serie> series;

    public AreaRiverPatrolStatsEChartsVo() {
    }

    public String[] getLegend() {
        return legend;
    }

    public void setLegend(String[] legend) {
        this.legend = legend;
    }

    public String[] getxAxis() {
        return xAxis;
    }

    public void setxAxis(String[] xAxis) {
        this.xAxis = xAxis;
    }

    public String[] getyAxis() {
        return yAxis;
    }

    public void setyAxis(String[] yAxis) {
        this.yAxis = yAxis;
    }

    public List<Serie> getSeries() {
        if (null == this.series) {
            this.series = Lists.newArrayList();
        }
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }

    public static class Serie {
        public final static String LINE_TYPE = "line";
        public final static String BAR_TYPE = "bar";
        public final static String PIE_TYPE = "pie";

        private String name;
        private String type;
        private String stack;
        private int barWidth;
        private int barGap;
        private double[] data;

        public Serie() {
        }

        public Serie(String name, String type, double[] data) {
            this.name = name;
            this.type = type;
            this.data = data;
        }

        /**
         * 折线图
         * @param name
         * @param data
         * @return
         */
        public static Serie createLine(String name, double[] data) {
            return new Serie(name, LINE_TYPE, data);
        }

        /**
         * 柱状图
         * @param name
         * @param data
         * @return
         */
        public static Serie createBar(String name, double[] data) {
            return new Serie(name, BAR_TYPE, data);
        }

        /**
         * 饼状图
         * @param name
         * @param data
         * @return
         */
        public static Serie createPie(String name, double[] data) {
            return new Serie(name, PIE_TYPE, data);
        }

        /**
         * 柱状图
         * @param name
         * @param data
         * @return
         */
        public static Serie createStackBar(String name, String stack, double[] data) {
            return new Serie(name, BAR_TYPE, data);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStack() {
            return stack;
        }

        public void setStack(String stack) {
            this.stack = stack;
        }

        public int getBarWidth() {
            return barWidth;
        }

        public void setBarWidth(int barWidth) {
            this.barWidth = barWidth;
        }

        public int getBarGap() {
            return barGap;
        }

        public void setBarGap(int barGap) {
            this.barGap = barGap;
        }

        public double[] getData() {
            return data;
        }

        public void setData(double[] data) {
            if (null == this.data || this.data.length == 0) {
                this.data = new double[data.length];
            }
            this.data = data;
        }
    }
}
