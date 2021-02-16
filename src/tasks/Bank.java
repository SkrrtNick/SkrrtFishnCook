package tasks;

import com.epicbot.api.shared.APIContext;
import data.Constants;

public class Bank {

    public Bank(APIContext apiContext) {

    }

    public boolean openBank(APIContext ctx) {
        if (!ctx.bank().isOpen()) {
        }
        return ctx.bank().open();
    }
    public void depositAll(APIContext ctx){
        if(!ctx.inventory().isEmpty()){
            ctx.bank().depositAllExcept(Constants.SMALL_FISHING_NET);
        }
    }
}
