package com.skrrtnick.fishncook;

import com.epicbot.api.os.model.game.GameState;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.event.ChatMessageEvent;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.script.ScriptManifest;
import com.epicbot.api.shared.util.paint.frame.PaintFrame;
import com.epicbot.api.shared.util.time.Time;
import com.skrrtnick.fishncook.data.Constants;
import com.skrrtnick.fishncook.data.Locations;
import com.skrrtnick.fishncook.data.State;
import com.skrrtnick.fishncook.tasks.Fish;
import com.skrrtnick.fishncook.tasks.Walk;
import com.skrrtnick.fishncook.tasks.Bank;
import com.skrrtnick.fishncook.tasks.Cook;

import java.awt.*;


@ScriptManifest(name = "Skrrt Fish and Cook", gameType = GameType.OS)


public class SkrrtFishnCook extends LoopScript {

    private int burntFish;
    private int cookedShrimp, cookedAnchovies;
    private int cookingStartLvl, cookingCurrentLvl, fishingStartLvl, fishingCurrentLvl;
    private long startTime;
    public static State state = State.STARTING;
    Bank bank = new Bank(getAPIContext());
    Cook cook = new Cook(getAPIContext());
    Fish fish = new Fish(getAPIContext());
    Walk walk = new Walk(getAPIContext());

    @Override
    public boolean onStart(String... strings) {
        startTime = System.currentTimeMillis();
        fishingStartLvl = getAPIContext().skills().fishing().getRealLevel();
        cookingStartLvl = getAPIContext().skills().cooking().getRealLevel();

        return true;
    }

    @Override
    protected void onPaint(Graphics2D g, APIContext a) {
        PaintFrame pf = new PaintFrame("Fish n Cook");
        pf.addLine("Runtime", Time.getFormattedRuntime(startTime));
        pf.addLine("State:", state.getName());
        if(cookingCurrentLvl-cookingStartLvl > 0){
            pf.addLine("Cooking Level: ",cookingCurrentLvl + "(" + (cookingCurrentLvl - cookingStartLvl) + ")");
        } else {
            pf.addLine("Cooking Level: ",cookingCurrentLvl);
        }
        if(fishingCurrentLvl - fishingStartLvl > 0){
            pf.addLine("Fishing Level: ",fishingCurrentLvl + "(" + (fishingCurrentLvl - fishingStartLvl) + ")");
        } else {
            pf.addLine("Fishing Level: ",fishingCurrentLvl);
        }
        pf.addLine("Cooked Shrimps: ", cookedShrimp);
        pf.addLine("Cooked Anchovies: ", cookedAnchovies);
        pf.addLine("Burnt Fish: ", burntFish);
        pf.draw(g, 4, 204, a);
    }

    @Override
    protected void onChatMessage(ChatMessageEvent a) {

        if (a.getMessage().getType() == 105 && a.getMessage().getText().contains("burn")) {
            burntFish++;
        }
        if (a.getMessage().getType() == 105 && a.getMessage().getText().equals("You successfully cook some shrimps.")) {
            cookedShrimp++;
        }
        if (a.getMessage().getType() == 105 && a.getMessage().getText().equals("You successfully cook some anchovies.")) {
            cookedAnchovies++;
        }

    }

    @Override
    protected int loop() {
        APIContext ctx = getAPIContext();
        if (ctx.game().getGameState().is(GameState.LOGGED_IN)) {

            cookingCurrentLvl = getAPIContext().skills().cooking().getRealLevel();
            fishingCurrentLvl = getAPIContext().skills().fishing().getRealLevel();


            if(fishingStartLvl == 0 | cookingStartLvl == 0){
                fishingStartLvl = getAPIContext().skills().fishing().getRealLevel();
                cookingStartLvl = getAPIContext().skills().cooking().getRealLevel();
            }



            if (ctx.dialogues().canContinue()) {
                Time.getHumanReaction();
                ctx.dialogues().selectContinue();
            }

            if (!fish.hasFishingNet(ctx) && Locations.FISHING_SPOT.getArea().contains(ctx.localPlayer().get())) {
                fish.grabFishingNet(ctx);

            } else if (!fish.hasFishingNet(ctx) && Locations.FISHING_SPOT.getArea().contains(ctx.localPlayer().getLocation()) && !cook.hasCookedFish(ctx)) {
                fish.grabFishingNet(ctx);

            } else if (!cook.hasCookedFish(ctx) && !ctx.inventory().isFull() & !ctx.localPlayer().isAnimating()) {
                walk.walkToFish(ctx);
            }

            if (Locations.FISHING_SPOT.getArea().contains(ctx.localPlayer().getLocation()) && fish.hasFishingNet(ctx) && !ctx.inventory().isFull() && !ctx.localPlayer().isAnimating()) {
                fish.doFishing(ctx);


            } else if (ctx.inventory().isFull() && !Locations.STOVE.getArea().contains(ctx.localPlayer().getLocation())) {
                walk.walkToStove(ctx);

            } else if (!ctx.localPlayer().isAnimating() && Locations.STOVE.getArea().contains(ctx.localPlayer().getLocation()) && cook.containsRaw(ctx)) {
                cook.cookFood(ctx);

            }
            if (!cook.hasRawFish(ctx) && ctx.inventory().isFull()) {
                ctx.inventory().dropAllExcept(Constants.SMALL_FISHING_NET, Constants.SHRIMPS, Constants.ANCHOVIES);

            }
            if (cook.hasCookedFish(ctx) && !cook.hasRawFish(ctx)) {
                walk.walkToBank(ctx);
                System.out.println(Locations.LUMBRIDGE_BANK.getArea().contains(ctx.localPlayer().getLocation()));

            }
            if (Locations.LUMBRIDGE_BANK.getArea().contains(ctx.localPlayer().getLocation())) {
                if (bank.openBank(ctx)) {
                    bank.depositAll(ctx);
                }
            } else {
                return 500;
            }
        }
        return 800;

    }
}
