package orasyl.content;

import arc.graphics.Color;
import mindustry.type.Liquid;

/**
 * Tipos de líquido (Liquid) propios de Orasyl - el equivalente a
 * mindustry.content.Liquids.
 *
 * OJO CON EL NOMBRE: esta clase existe aparte de OrasylLiquids porque ese
 * nombre ya lo pediste para los BLOQUES que manejan líquidos (bombas,
 * conductos, tanques). Un Liquid y un Block-que-mueve-líquidos son dos
 * ContentType distintos en Mindustry (liquid vs block), así que aquí se
 * separan para no mezclar los dos conceptos bajo el mismo nombre de clase.
 *
 * Debe cargarse después de OrasylStatusEffects (salty-water usa
 * OrasylStatusEffects.saltyRusting como su `effect`).
 */
public class OrasylLiquidTypes{

    public static Liquid saltyWater;

    public static void load(){

        // Réplica de mindustry.content.Liquids.water en todo menos el color
        // y el status effect asociado (ver Liquid.effect - "el status
        // effect asociado" a este líquido, mismo mecanismo que hace que el
        // agua vanilla dé "wet").
        saltyWater = new Liquid("salty-water", Color.valueOf("3B877C")){{
            heatCapacity = 0.4f;
            boilPoint = 0.5f;
            gasColor = Color.grays(0.9f);
            alwaysUnlocked = true;

            effect = OrasylStatusEffects.saltyRusting;
        }};

    }

}
