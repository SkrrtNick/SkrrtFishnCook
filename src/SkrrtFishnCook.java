import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.event.ChatMessageEvent;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.script.ScriptManifest;
import com.epicbot.api.shared.util.paint.frame.PaintFrame;
import com.epicbot.api.shared.util.time.Time;
import data.Constants;
import data.Vars;
import tasks.Bank;
import tasks.Cook;
import tasks.Fish;
import tasks.Walk;

import java.awt.*;

import static data.Vars.startTime;


@ScriptManifest(name = "Skrrt Fish and Cook", gameType = GameType.OS)

//public class SkrrtFishnCook extends LoopScript {
//    public int cookingStartLvl;
//    public int fishingStartLvl;

//    @Override
//    public boolean onStart(String... strings) {
//        fishingStartLvl = getAPIContext().skills().fishing().getCurrentLevel();
//        cookingStartLvl = getAPIContext().skills().cooking().getCurrentLevel();
//
//
//        return true;
//    }


public class SkrrtFishnCook extends LoopScript {
    private int burntFish;
    private int cookedShrimp;
    private int cookedAnchovies;
    Bank bank = new Bank(getAPIContext());
    Walk walk = new Walk(getAPIContext());
    Fish fish = new Fish(getAPIContext());
    Cook cook = new Cook(getAPIContext());
    public int cookingStartLvl, cookingCurrentLvl;
    public int fishingStartLvl, fishingCurrentLvl;

    @Override
    public boolean onStart(String... strings) {
        Vars.startTime = System.currentTimeMillis();
        fishingStartLvl = getAPIContext().skills().fishing().getCurrentLevel();
        cookingStartLvl = getAPIContext().skills().cooking().getCurrentLevel();

        return true;
    }

    @Override
    protected void onPaint(Graphics2D g, APIContext a) {
        PaintFrame pf = new PaintFrame("SkrrtFishnCook");
        pf.addLine("Runtime", formatTime(System.currentTimeMillis() - startTime,true));
        pf.addLine("State:", Vars.state);
        pf.addLine("Cooked Shrimps: ", Vars.shrimpCooked);
        pf.addLine("Cooked Anchovies: ", Vars.anchoviesCooked);
        pf.addLine("Burnt Fish: ", Vars.burntFish);
        pf.draw(g, 4, 280, a);
    }

    public final String formatTime(final long ms, final boolean hour) {
        long s = ms / 1000, m = s / 60, h = m / 60;
        s %= 60;
        m %= 60;
        if (hour == true)
            return String.format("%02d:%02d:%02d", h, m, s);
        else
            return String.format("%02d:%02d", m, s);
    }

    @Override
    protected void onChatMessage(ChatMessageEvent a) {
        super.onChatMessage(a);
        if (a.getMessage().getType() == 0 && a.getMessage().getText().contains("You accidentally burn the shrimps.")) {
            burntFish++;
        }
        if (a.getMessage().getType() == 0 && a.getMessage().getText().contains("You successfully cook some shrimps.")) {
            cookedShrimp++;
        }
        if (a.getMessage().getType() == 0 && a.getMessage().getText().contains("You successfully cook some anchovies.")) {
            cookedAnchovies++;
        }

    }


    @Override
    protected int loop() {
        APIContext ctx = getAPIContext();

        if(ctx.dialogues().canContinue()){
            Time.getHumanReaction();
            ctx.dialogues().selectContinue();
        }

        if (!fish.hasFishingNet(ctx) && Constants.FISHING_SPOT.contains(ctx.localPlayer().getLocation())) {
            fish.grabFishingNet(ctx);

        } else if (!fish.hasFishingNet(ctx) && !Constants.FISHING_SPOT.contains(ctx.localPlayer().getLocation()) && !cook.hasCookedFish(ctx)) {
            walk.walkToFish(ctx);
            fish.grabFishingNet(ctx);

        } else if (!cook.hasCookedFish(ctx) && fish.hasFishingNet(ctx) && !ctx.inventory().isFull() & !ctx.localPlayer().isAnimating()) {
            walk.walkToFish(ctx);
        }

        if (Constants.FISHING_SPOT.contains(ctx.localPlayer().getLocation()) && fish.hasFishingNet(ctx) && !ctx.inventory().isFull() && !ctx.localPlayer().isAnimating()) {
            fish.doFishing(ctx);


        } else if (ctx.inventory().isFull() && !Constants.STOVE.contains(ctx.localPlayer().getLocation())) {
            walk.walkToStove(ctx);

        } else if (!ctx.localPlayer().isAnimating() && Constants.STOVE.contains(ctx.localPlayer().getLocation()) && cook.containsRaw(ctx)) {
            cook.cookFood(ctx);

        } if (!cook.hasRawFish(ctx) && ctx.inventory().isFull()){
            ctx.inventory().dropAllExcept(Constants.SMALL_FISHING_NET,Constants.SHRIMPS,Constants.ANCHOVIES);

        } if (cook.hasCookedFish(ctx) && !cook.hasRawFish(ctx)){
            walk.walkToBank(ctx);

        } if(Constants.LUMBRIDGE_BANK.contains(ctx.localPlayer().getLocation()));{
            if(bank.openBank(ctx)){
                bank.depositAll(ctx);
            }
        }



        return 0;


//        } else if (Constants.STOVE.contains(ctx.localPlayer().getLocation()) && (ctx.inventory().contains(Constants.RAW_ANCHOVIES) || ctx.inventory().contains(Constants.RAW_ANCHOVIES))) {
//            cook.cookFood(ctx);
//        }
    }



//        if (Constants.FISHING_SPOT.contains(ctx.localPlayer().getLocation())) {
//            System.out.println("We are at the fishing spot.");
//            }
//


}
