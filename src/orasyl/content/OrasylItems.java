package orasyl.content;

import arc.graphics.Color;
import mindustry.type.Item;

/**
 * Items propios de Orasyl. Nivel superior, igual que mindustry.content.Items
 * - no va dentro de las subcategorías de OrasylBlocks porque un Item no es
 * un Block.
 *
 * "stone" no existe en el Mindustry actual (era de versiones clásicas, ver
 * mindustry.content.Items - no aparece en la lista). Se crea aquí de cero
 * para que "rudimentary-quarry" (OrasylProduction) tenga algo que producir.
 */
public class OrasylItems{

    public static Item stone;

    public static void load(){

        stone = new Item("stone", Color.valueOf("8d8d8d")){{
            alwaysUnlocked = true;
        }};

    }

}
