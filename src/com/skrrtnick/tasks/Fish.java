package com.skrrtnick.tasks;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.entity.NPC;
import com.epicbot.api.shared.entity.SceneObject;
import com.epicbot.api.shared.util.time.Time;
import com.skrrtnick.SkrrtFishnCook;
import com.skrrtnick.data.Constants;
import com.skrrtnick.data.State;

public class Fish {
    APIContext ctx;
    public Fish(APIContext ctx) {
        this.ctx = ctx;
    }

    public boolean hasFishingNet(APIContext ctx) {

        System.out.println(ctx.inventory().getCount(Constants.SMALL_FISHING_NET));

        if(ctx.inventory().getCount(Constants.SMALL_FISHING_NET) > 1){

            return ctx.inventory().contains(Constants.SMALL_FISHING_NET);

        }
        return false;


    }

    public boolean grabFishingNet(APIContext ctx) {
        SceneObject fishingNet = ctx.objects().query().named("Small fishing net").actions("Take").results().nearest();
        if (fishingNet != null) {
            fishingNet.click();
            Time.getHumanReaction();
        }
        return hasFishingNet(ctx);
        }

    public void doFishing(APIContext ctx){
        NPC fishingSpot = ctx.npcs().query().named("Fishing spot").actions("Net").results().nearest();
        if(fishingSpot!=null && fishingSpot.interact("Net")){
            SkrrtFishnCook.state = State.FISHING;
            Time.getHumanReaction();
            Time.sleep(1200, () -> ctx.localPlayer().isAnimating());
        }
        }

}

