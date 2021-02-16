package tasks;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.entity.NPC;
import com.epicbot.api.shared.entity.SceneObject;
import com.epicbot.api.shared.util.time.Time;
import data.Constants;
import data.Vars;

public class Fish {
    public Fish(APIContext ctx) {

    }

    public boolean hasFishingNet(APIContext ctx) {
        return ctx.inventory().contains(Constants.SMALL_FISHING_NET);
    }


    public boolean grabFishingNet(APIContext ctx) {
        SceneObject fishingNet = ctx.objects().query().named("Small fishing net").actions("Take").reachable().results().nearest();
        if (fishingNet != null) {
            fishingNet.click();
            Time.getHumanReaction();
        }
        Vars.state = "Grabbing fishing net";
        return hasFishingNet(ctx);
    }
    public void doFishing(APIContext ctx){
        NPC fishingSpot = ctx.npcs().query().named("Fishing spot").actions("Net").results().nearest();
        if(fishingSpot!=null && fishingSpot.interact("Net")){
            Vars.state = "Fishing";
            Time.getHumanReaction();
            Time.sleep(1200, () -> ctx.localPlayer().isAnimating());
        }
        }

}

