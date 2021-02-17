package com.skrrtnick.fishncook.tasks;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.entity.SceneObject;
import com.epicbot.api.shared.entity.WidgetChild;
import com.epicbot.api.shared.util.time.Time;
import com.skrrtnick.fishncook.SkrrtFishnCook;
import com.skrrtnick.fishncook.data.Constants;
import com.skrrtnick.fishncook.data.State;


public class Cook {
    APIContext ctx;
    public Cook(APIContext ctx){
        this.ctx = ctx;
    }

    public boolean containsRaw(APIContext ctx){
        return ctx.inventory().contains(Constants.RAW_ANCHOVIES) | ctx.inventory().contains(Constants.RAW_SHRIMPS);
    }

    public void cookFood(APIContext ctx){
        SceneObject range = ctx.objects().query().nameMatches("Range").results().nearest();

        if (range !=null){
            if (range.isVisible() && range.interact("Cook")){
                Time.sleep(10000, () -> cookingWidgetOpen(ctx));
                cookingWidgetShrimps(ctx);
                Time.sleep(1200, () -> ctx.localPlayer().isAnimating());
                cookingWidgetAnchovies(ctx);
                Time.sleep(1200, () -> ctx.localPlayer().isAnimating());
                SkrrtFishnCook.state = State.COOKING;
            }
        }
    }
    public boolean cookingWidgetShrimps(APIContext ctx){
        WidgetChild cookingScreen = ctx.widgets().query().visible().group(270).filter(o -> o.getName().contains("hrimps")).results().first();
        if (cookingScreen != null){
            return cookingScreen.interact();
        }
        return false;
    }
    public boolean cookingWidgetAnchovies(APIContext ctx){
        WidgetChild cookingScreen = ctx.widgets().query().visible().group(270).filter(o -> o.getName().contains("nchovies")).results().first();
        if (cookingScreen != null){
            return cookingScreen.interact();
        }
        return false;
    }

    public boolean hasRawFish(APIContext ctx){
        return ctx.inventory().contains(Constants.RAW_SHRIMPS) | ctx.inventory().contains(Constants.RAW_ANCHOVIES);
    }
    public boolean hasCookedFish(APIContext ctx){
        return ctx.inventory().contains(Constants.ANCHOVIES)|ctx.inventory().contains(Constants.SHRIMPS);
    }

    public boolean cookingWidgetOpen(APIContext ctx){
        return ctx.widgets().query().visible().group(270).filter(o -> o.getName().equals("Shrimps")).results().first()  != null ;
    }
}
