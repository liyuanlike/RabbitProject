package com.global.component.domain;

import java.io.Serializable;

/**
 * @apiNote 网格员发送给指挥系统的执行状况信息
 */
public class GridManExecution implements Serializable {
    private GridManInstrction gridManInstrction;

    public GridManInstrction getGridManInstrction() {
        return gridManInstrction;
    }

    public void setGridManInstrction(GridManInstrction gridManInstrction) {
        this.gridManInstrction = gridManInstrction;
    }
}
