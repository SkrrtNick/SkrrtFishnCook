package tasks;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.entity.SceneObject;
import com.epicbot.api.shared.entity.WidgetChild;
import com.epicbot.api.shared.util.time.Time;
import data.Constants;
import data.Vars;

public class Cook {
    public Cook(APIContext ctx){

    }

    public boolean containsRaw(APIContext ctx){
        return ctx.inventory().contains(Constants.RAW_ANCHOVIES) | ctx.inventory().contains(Constants.RAW_SHRIMPS);
    }

    public void cookFood(APIContext ctx){
        SceneObject range = ctx.objects().query().nameMatches("Range").results().nearest();
        System.out.println(range);

        if (range !=null){
            if (range.isVisible() && range.interact("Cook")){
                Vars.state="Cooking";
                Time.sleep(10000, () -> cookingWidgetOpen(ctx));
                cookingWidget(ctx);
                Time.sleep(1200, () -> ctx.localPlayer().isAnimating());
                System.out.println("Widget:" + cookingWidget(ctx));
            }




        }

    }
    public boolean cookingWidget(APIContext ctx){
        WidgetChild cookingScreen = ctx.widgets().query().visible().group(270).filter(o -> o.getName().equals("Raw shrimps")).results().first();
        if (cookingScreen !=null){
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
        return ctx.widgets().query().visible().group(270).filter(o -> o.getName().equals("Raw shrimps")).results().first() != null;
    }
}
