package com.skrrtnick.fishncook.tasks;

import com.epicbot.api.shared.APIContext;
import com.skrrtnick.fishncook.SkrrtFishnCook;
import com.skrrtnick.fishncook.data.Locations;
import com.skrrtnick.fishncook.data.State;

public class Walk {
    APIContext ctx;
    public Walk(APIContext ctx) {
        this.ctx = ctx;
    }

    public boolean walkToFish(APIContext ctx) {
        if (!Locations.FISHING_SPOT.getArea().contains(ctx.localPlayer().get()) && !ctx.localPlayer().isMoving()) {
            ctx.webWalking().walkTo(Locations.FISHING_SPOT.getArea().getRandomTile());
        }
        return Locations.FISHING_SPOT.getArea().contains(ctx.localPlayer().get());
    }

    public void walkToBank(APIContext ctx) {
        if (!Locations.LUMBRIDGE_BANK.getArea().contains(ctx.localPlayer().get())) {
            SkrrtFishnCook.state = State.WALKING;
            ctx.webWalking().walkToBank();

        }

    }
    public boolean walkToStove(APIContext ctx) {
        System.out.println("Walking to stove starting");
        if (!Locations.STOVE.getArea().contains(ctx.localPlayer().get())) {
            SkrrtFishnCook.state = State.WALKING;
            ctx.webWalking().walkTo(Locations.STOVE.getArea().getRandomTile());        }
        return Locations.STOVE.getArea().contains(ctx.localPlayer().get());
    }

}
