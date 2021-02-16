package data;

import com.epicbot.api.shared.model.Area;
import com.epicbot.api.shared.model.Tile;

public class Constants {

    /**
     * Raw fish
     *
     */

    public final static int RAW_SHRIMPS = 317;
    public final static int RAW_ANCHOVIES = 321;

    /**
     * Cooked fish
     *
     */

    public final static int SHRIMPS = 315;
    public final static int ANCHOVIES = 319;

    /**
     * Fishing net
     */

    public final static int SMALL_FISHING_NET = 303;

    /**
     * Areas
     *
     */
    public final static Area LUMBRIDGE_BANK = new Area(new Tile(3207, 3219, 2), new Tile(3210, 3218, 2));
    public final static Area FISHING_SPOT = new Area( new Tile(3239,3155), new Tile(3244,3151));
    public final static Area STOVE = new Area(new Tile(3237,3195),new Tile(3230,3198));





}
