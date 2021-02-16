package com.skrrtnick.tasks;

import com.epicbot.api.shared.APIContext;
import com.skrrtnick.SkrrtFishnCook;
import com.skrrtnick.data.Constants;
import com.skrrtnick.data.State;

public class Bank {
    APIContext ctx;
    public Bank(APIContext ctx) {
        this.ctx = ctx;
    }

    public boolean openBank() {
        if (!ctx.bank().isOpen()) {
            SkrrtFishnCook.state = State.BANKING;
        }
        return ctx.bank().open();
    }
    public void depositAll(){
        if(!ctx.inventory().isEmpty()){
            ctx.bank().depositAllExcept(Constants.SMALL_FISHING_NET);
        }
    }
}
