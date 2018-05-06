package cn.myhomespace.zhou.spider_consumer.object;

import java.util.List;

/**
 * Created by zhouw on 2018/5/6.
 */
public class TableResponse<T> extends Response{

    private int total;
    private List<T> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
