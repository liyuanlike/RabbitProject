package com.global.component.domain;

import java.io.Serializable;

/**
 * @apiNote 指挥系统向网格员系统发送的指令类型
 */
public class GridManInstrction implements Serializable {
    private GridManEventMessage gridManEventMessage;

    public GridManEventMessage getGridManEventMessage() {
        return gridManEventMessage;
    }

    public void setGridManEventMessage(GridManEventMessage gridManEventMessage) {
        this.gridManEventMessage = gridManEventMessage;
    }
}
