package tasks;

import com.epicbot.api.shared.APIContext;
import data.Constants;
import data.Vars;

public class Walk {

    public Walk(APIContext ctx) {

    }

    public boolean walkToFish(APIContext ctx) {
        if (!Constants.FISHING_SPOT.contains(ctx.localPlayer().get())) {
            ctx.webWalking().walkTo(Constants.FISHING_SPOT.getRandomTile());
            Vars.state = "Walking to fishing spot";
        }
        return Constants.FISHING_SPOT.contains(ctx.localPlayer().getLocation());
    }

    public void walkToBank(APIContext ctx) {
        if (!Constants.LUMBRIDGE_BANK.contains(ctx.localPlayer().getLocation())) {
            Vars.state = "Walking to bank";
            ctx.webWalking().walkToBank();

        }

    }


    public boolean walkToStove(APIContext ctx) {
        System.out.println("Walking to stove starting");
        if (!Constants.STOVE.contains(ctx.localPlayer().get())) {
            Vars.state = "Walking to stove";
            ctx.walking().walkTo(Constants.STOVE.getRandomTile());        }
        System.out.println("Walking to stove ending");
        return Constants.STOVE.contains(ctx.localPlayer().getLocation());
    }

}
