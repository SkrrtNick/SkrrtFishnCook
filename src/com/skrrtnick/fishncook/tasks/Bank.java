package com.skrrtnick.fishncook.tasks;

import com.epicbot.api.shared.APIContext;
import com.skrrtnick.fishncook.SkrrtFishnCook;
import com.skrrtnick.fishncook.data.Constants;
import com.skrrtnick.fishncook.data.State;

public class Bank {
    APIContext ctx;
    public Bank(APIContext ctx) {
        this.ctx = ctx;
    }

    public boolean openBank(APIContext ctx) {
        if (!ctx.bank().isOpen()) {
            SkrrtFishnCook.state = State.BANKING;
        }
        return ctx.bank().open();
    }
    public void depositAll(APIContext ctx){
        if(!ctx.inventory().isEmpty()){
            ctx.bank().depositAllExcept(Constants.SMALL_FISHING_NET);
        }
    }
}
