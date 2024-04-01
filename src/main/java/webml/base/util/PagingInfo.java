package webml.base.util;

import lombok.Data;

import java.util.ArrayList;

@Data
public class PagingInfo {
    private long totalCnt;
    private int pageNum;
    private int pageSize;
    private int offset;
    private long totalPageNum;
    private int pageGroupSize = 5;
    private int prevGroupStartPageNum;
    private int nextGroupStartPageNum;
    private int lastGroupStartPageNum;
    private ArrayList<Integer> list = null;

    public PagingInfo(long totalCnt, int pageNum, int pageSize) {
        this.totalCnt = totalCnt;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        if (pageNum != 0) {
            this.totalPageNum = totalCnt / (long)pageSize;
            if (totalCnt % pageSize > 0) {
                this.totalPageNum = this.totalPageNum + 1;
            }
        }

        this.prevGroupStartPageNum = 0;
        this.nextGroupStartPageNum = 0;
        int nowGroupStartPageNum = (pageNum - 1) / this.pageGroupSize * this.pageGroupSize + 1;
        this.prevGroupStartPageNum = nowGroupStartPageNum - this.pageGroupSize;
        this.nextGroupStartPageNum = nowGroupStartPageNum + this.pageGroupSize;
        if (this.prevGroupStartPageNum < 0) {
            this.prevGroupStartPageNum = 1;
        }

        if (pageSize > 0 && totalCnt > 0) {
            this.list = new ArrayList<Integer>();
            for (int i = 0; i < this.pageGroupSize; ++i) {
                int nowPage = i + nowGroupStartPageNum;
                if (this.totalPageNum >= nowPage) {
                    this.list.add(nowPage);
                }
            }
        }

        if (this.totalPageNum <= this.pageGroupSize) {
            this.lastGroupStartPageNum = 1;
        } else {
            this.lastGroupStartPageNum = ((int)this.totalPageNum - 1) / this.pageGroupSize * this.pageGroupSize + 1;
        }
        if (this.nextGroupStartPageNum > this.totalPageNum) {
            this.nextGroupStartPageNum = this.lastGroupStartPageNum;
        }

        this.offset = (this.pageNum - 1) * this.pageSize;
    }
}
