package com.skrrtnick;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.event.ChatMessageEvent;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.script.ScriptManifest;
import com.epicbot.api.shared.util.paint.frame.PaintFrame;
import com.epicbot.api.shared.util.time.Time;
import com.skrrtnick.data.Locations;
import com.skrrtnick.data.State;
import com.skrrtnick.data.Constants;
import com.skrrtnick.tasks.Bank;
import com.skrrtnick.tasks.Cook;
import com.skrrtnick.tasks.Fish;
import com.skrrtnick.tasks.Walk;
import java.awt.*;


@ScriptManifest(name = "Skrrt Fish and Cook", gameType = GameType.OS)


public class SkrrtFishnCook extends LoopScript {

    private int burntFish;
    private int cookedShrimp, cookedAnchovies;
    private int cookingStartLvl, cookingCurrentLvl, fishingStartLvl, fishingCurrentLvl;
    private long startTime;
    public static State state;
    Bank bank = new Bank(getAPIContext());
    Cook cook = new Cook(getAPIContext());
    Fish fish = new Fish(getAPIContext());
    Walk walk = new Walk(getAPIContext());

    @Override
    public boolean onStart(String... strings) {
        startTime = System.currentTimeMillis();
        fishingStartLvl = getAPIContext().skills().fishing().getCurrentLevel();
        cookingStartLvl = getAPIContext().skills().cooking().getCurrentLevel();

        return true;
    }

    @Override
    protected void onPaint(Graphics2D g, APIContext a) {
        PaintFrame pf = new PaintFrame("Fish n Cook");
        pf.addLine("Runtime", Time.getFormattedRuntime(startTime));
        pf.addLine("State:", state.getName());
        pf.addLine("Cooked Shrimps: ", cookedShrimp);
        pf.addLine("Cooked Anchovies: ", cookedAnchovies);
        pf.addLine("Burnt Fish: ", burntFish);
        pf.draw(g, 4, 280, a);
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

        if (!fish.hasFishingNet() && Locations.FISHING_SPOT.getArea().contains(ctx.localPlayer().get())) {
            fish.grabFishingNet();

        } else if (!fish.hasFishingNet() && !Locations.FISHING_SPOT.getArea().contains(ctx.localPlayer().getLocation()) && !cook.hasCookedFish()) {
            walk.walkToFish();
            fish.grabFishingNet();

        } else if (!cook.hasCookedFish() && fish.hasFishingNet() && !ctx.inventory().isFull() & !ctx.localPlayer().isAnimating()) {
            walk.walkToFish();
        }

        if (Locations.FISHING_SPOT.getArea().contains(ctx.localPlayer().getLocation()) && fish.hasFishingNet() && !ctx.inventory().isFull() && !ctx.localPlayer().isAnimating()) {
            fish.doFishing();


        } else if (ctx.inventory().isFull() && !Locations.STOVE.getArea().contains(ctx.localPlayer().getLocation())) {
            walk.walkToStove(ctx);

        } else if (!ctx.localPlayer().isAnimating() && Locations.STOVE.getArea().contains(ctx.localPlayer().getLocation()) && cook.containsRaw()) {
            cook.cookFood();

        } if (!cook.hasRawFish() && ctx.inventory().isFull()){
            ctx.inventory().dropAllExcept(Constants.SMALL_FISHING_NET,Constants.SHRIMPS,Constants.ANCHOVIES);

        } if (cook.hasCookedFish() && !cook.hasRawFish()){
            walk.walkToBank();

        } if(Locations.LUMBRIDGE_BANK.getArea().contains(ctx.localPlayer().getLocation()));{
            if(bank.openBank()){
                bank.depositAll();
            }
        }
        return 800;


//        } else if (Constants.STOVE.contains(ctx.localPlayer().getLocation()) && (ctx.inventory().contains(Constants.RAW_ANCHOVIES) || ctx.inventory().contains(Constants.RAW_ANCHOVIES))) {
//            cook.cookFood(ctx);
//        }
    }



//        if (Constants.FISHING_SPOT.contains(ctx.localPlayer().getLocation())) {
//            System.out.println("We are at the fishing spot.");
//            }
//


}
