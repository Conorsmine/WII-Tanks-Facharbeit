package com.Conorsmine.net.Levels;

import com.Conorsmine.net.Entities.GameObjects;
import com.Conorsmine.net.Rendering.ModelLoader;

public abstract class Level {

    /**
     * @return Returns a list of all needed GameObjects for this level.
     */
    public abstract GameObjects[] placeLevel(ModelLoader loader);
}
