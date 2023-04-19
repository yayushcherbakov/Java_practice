package com.sc.game.transfer;

import java.io.Serializable;

public class TransferObject implements Serializable {
    private ActionType actionType;
    private Object data;

    public TransferObject() {
    }

    public TransferObject(ActionType actionType) {
        this.actionType = actionType;
    }

    public TransferObject(ActionType actionType, Object data) {
        this.actionType = actionType;
        this.data = data;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public Object getData() {
        return data;
    }
}
