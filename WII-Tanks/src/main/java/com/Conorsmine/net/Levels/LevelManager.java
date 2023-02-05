package com.Conorsmine.net.Levels;

import com.Conorsmine.net.Entities.GameObjects;
import com.Conorsmine.net.Rendering.ModelLoader;

import java.util.UUID;

/*
* Later on, instead of creating a class per level, rather there will be a .json file for each.
* This class will then handle the deserialization.
* */
public class LevelManager {


   public static void loadLevel(Level level, ModelLoader loader) {
      clearLevel();

      for (GameObjects obj : level.placeLevel(loader)) {
         GameObjects.objectMap.put(UUID.randomUUID(), obj);
      }
   }

   public static void clearLevel() {
      GameObjects.objectMap.clear();
   }
}
