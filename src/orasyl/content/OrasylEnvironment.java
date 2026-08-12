package orasyl.content;

import mindustry.content.Blocks;
import mindustry.content.Liquids;
import mindustry.graphics.CacheLayer;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.meta.Attribute;

/**
 * Bloques "naturales" de Orasyl: floors de terreno (tierra, roca, agua...).
 * Todo lo que el jugador NO construye - lo que ya está puesto en el mapa.
 *
 * Llamada desde OrasylBlocks.load(), que es ahora el punto central que
 * orquesta esta clase junto con OrasylProduction, OrasylDistribution y
 * OrasylLiquids.
 *
 * Cuando existan más floors que deban blendear entre sí (por ejemplo una futura
 * variante "dry-grass-2"), se les asigna el mismo blendGroup para que no se
 * dibuje un borde entre ellos:
 *
 *   dryDirt = new Floor("dry-dirt"){{
 *       blendGroup = dryGrass; // comparten grupo -> sin línea de borde entre ambos
 *   }};
 *
 * Por defecto blendGroup = this (el floor solo blendea consigo mismo).
 *
 * NOTA sobre "agua profunda no deja construir": no es un campo aparte que
 * haya que tocar. Build.java bloquea construcción normal cuando
 * floor.isDeep() es true, y Floor.isDeep() está definido literalmente como
 * "drownTime > 0". O sea que drownTime > 0 ya resuelve, a la vez, que no se
 * pueda construir encima Y que las unidades terrestres se ahoguen ahí.
 *
 * NOTA sobre mapColor: NO se fija a mano en ningún floor de aquí abajo.
 * Mindustry lo sobrescribe siempre con el pixel central del sprite en
 * createIcons() - fijarlo en Java no tendría efecto. El color del minimapa
 * se controla desde el sprite, no desde el código.
 */
public class OrasylEnvironment{

    public static Block
        dryGrass,
        volcanicStone,
        sandstoneFloor,
        deepSaltyWater,
        saltyWater,
        sandSaltyWater,
        deeperWater,
        abyssWater,
        dryShrubs,
        sandstoneWall;

    public static void load(){

        dryGrass = new Floor("dry-grass", 3){{
            // Afinidad al agua: 5%
            attributes.set(Attribute.water, 0.05f);
        }};

        volcanicStone = new Floor("volcanic-stone", 4){{
            // Afinidad al agua: -15% (misma mecánica que usa "salt" en vanilla,
            // que también usa un valor negativo: -0.3f)
            attributes.set(Attribute.water, -0.15f);
            attributes.set(OrasylAttributes.rockiness, 0.75f);
        }};

        sandstoneFloor = new Floor("sandstone-floor", 3){{
            attributes.set(OrasylAttributes.rockiness, 0.5f);
        }};

        // --- Rockiness sobre floors VANILLA existentes ---
        // No se crean de nuevo - se modifica el Content ya construido por el
        // juego base. Para cuando esto corre (loadContent() de nuestro mod),
        // Blocks.stone y Blocks.basalt ya existen, así que solo se les añade
        // el atributo. attributes es un campo de Block, no hace falta cast a Floor.
        Blocks.stone.attributes.set(OrasylAttributes.rockiness, 0.75f);
        Blocks.basalt.attributes.set(OrasylAttributes.rockiness, 1f);

        // --- Familia de agua salada, de más profunda a más costera ---
        // Los tres comparten liquidDrop = OrasylLiquidTypes.saltyWater (no
        // Liquids.water vanilla) y cacheLayer = CacheLayer.water, y todos
        // pueden dar el status "salty-rusting" (OrasylStatusEffects, cargado
        // antes que OrasylBlocks desde Orasyl.loadContent()). La duración baja
        // hacia la costa, siguiendo el mismo patrón de intensidad decreciente
        // que ya usan speedMultiplier y liquidMultiplier en esta familia.

        deepSaltyWater = new Floor("deep-salty-water", 1){{
            isLiquid = true;
            liquidDrop = OrasylLiquidTypes.saltyWater;
            liquidMultiplier = 1.5f;      // más rendimiento por ser la más profunda
            speedMultiplier = 0.2f;       // ralentización fuerte
            drownTime = 200f;             // isDeep() = true -> ahoga Y bloquea construcción
            status = OrasylStatusEffects.saltyRusting;
            statusDuration = 120f;
            cacheLayer = CacheLayer.water;
        }};

        saltyWater = new Floor("salty-water", 1){{
            isLiquid = true;
            liquidDrop = OrasylLiquidTypes.saltyWater;
            liquidMultiplier = 1f;        // rendimiento medio
            speedMultiplier = 0.5f;       // ralentiza menos que la profunda
            // drownTime se deja en 0 (default) a propósito: isDeep() = false,
            // así que sí se puede construir encima y no ahoga.
            status = OrasylStatusEffects.saltyRusting;
            statusDuration = 90f;
            cacheLayer = CacheLayer.water;
        }};

        sandSaltyWater = new Floor("sand-salty-water", 3){{
            isLiquid = true;
            liquidDrop = OrasylLiquidTypes.saltyWater;
            liquidMultiplier = 0.5f;      // la que menos rinde, por ser la más costera
            speedMultiplier = 0.75f;      // ralentiza menos que salty-water
            status = OrasylStatusEffects.saltyRusting;
            statusDuration = 60f;
            cacheLayer = CacheLayer.water;
        }};

        // --- Aguas extremas, más allá del "deep-water" vanilla ---
        // Referencia vanilla (mindustry.content.Blocks.deepwater):
        //   speedMultiplier = 0.2f, drownTime = 200f, liquidMultiplier = 1.5f
        // deeper-water y abyss-water escalan hacia ambos extremos: más lentas,
        // ahogan más rápido (drownTime MENOR = menos tiempo hasta morir) y
        // rinden más líquido por bomba que el deep-water vanilla. El sprite de
        // cada una tiene además su pixel central fijado a un tono deliberadamente
        // más oscuro que el vanilla, para que se distingan bien en el minimapa.

        deeperWater = new Floor("deeper-water", 1){{
            isLiquid = true;
            liquidDrop = Liquids.water;
            liquidMultiplier = 1.8f;   // > 1.5f del deep-water vanilla
            speedMultiplier = 0.12f;   // más ralentizadora que el 0.2f vanilla
            drownTime = 130f;          // menor que el 200f vanilla -> ahoga más rápido
            cacheLayer = CacheLayer.water;
        }};

        abyssWater = new Floor("abyss-water", 1){{
            isLiquid = true;
            liquidDrop = Liquids.water;
            liquidMultiplier = 2.2f;   // un nivel por encima de deeper-water
            speedMultiplier = 0.06f;   // casi inmovilizante
            drownTime = 70f;           // ahoga más rápido que deeper-water
            cacheLayer = CacheLayer.water;
        }};

        // --- Paredes naturales (StaticWall, no confundir con las Wall
        // construibles como copper-wall). variants = 2 ya es el default de
        // StaticWall, se deja explícito por claridad. La versión "large" NO
        // es un content aparte: StaticWall trae un campo `large` cargado
        // automáticamente desde el sprite "<id>-large" (64x64, un cluster
        // 2x2 sin costuras) y lo usa solo quando 4 tiles iguales quedan
        // juntos en cuadrícula - mismo mecanismo que shrubs/salt-wall vanilla.

        dryShrubs = new StaticWall("dry-shrubs"){{
            variants = 2;
        }};

        sandstoneWall = new StaticWall("sandstone-wall"){{
            variants = 2;
        }};

    }

}
