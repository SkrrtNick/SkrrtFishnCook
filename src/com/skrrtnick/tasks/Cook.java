package com.skrrtnick.tasks;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.entity.SceneObject;
import com.epicbot.api.shared.entity.WidgetChild;
import com.epicbot.api.shared.util.time.Time;
import com.skrrtnick.SkrrtFishnCook;
import com.skrrtnick.data.Constants;
import com.skrrtnick.data.State;


public class Cook {
    APIContext ctx;
    public Cook(APIContext ctx){
        this.ctx = ctx;
    }

    public boolean containsRaw(){
        return ctx.inventory().contains(Constants.RAW_ANCHOVIES) | ctx.inventory().contains(Constants.RAW_SHRIMPS);
    }

    public void cookFood(){
        SceneObject range = ctx.objects().query().nameMatches("Range").results().nearest();
        System.out.println(range);

        if (range !=null){
            if (range.isVisible() && range.interact("Cook")){
                Time.sleep(10000, () -> cookingWidgetOpen());
                cookingWidget();
                Time.sleep(1200, () -> ctx.localPlayer().isAnimating());
                SkrrtFishnCook.state = State.COOKING;
                System.out.println("Widget:" + cookingWidget());
            }




        }

    }
    public boolean cookingWidget(){
        WidgetChild cookingScreen = ctx.widgets().query().visible().group(270).filter(o -> o.getName().equals("Raw shrimps")).results().first();
        if (cookingScreen != null){
            return cookingScreen.interact();
        }
        return false;
    }

    public boolean hasRawFish(){
        return ctx.inventory().contains(Constants.RAW_SHRIMPS) | ctx.inventory().contains(Constants.RAW_ANCHOVIES);
    }
    public boolean hasCookedFish(){
        return ctx.inventory().contains(Constants.ANCHOVIES)|ctx.inventory().contains(Constants.SHRIMPS);
    }

    public boolean cookingWidgetOpen(){
        return ctx.widgets().query().visible().group(270).filter(o -> o.getName().equals("Raw shrimps")).results().first() != null;
    }
}
