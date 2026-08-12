package orasyl.content;

import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.AttributeCrafter;

import static mindustry.type.ItemStack.with;

/**
 * Bloques de producción de Orasyl: drills, factories, cualquier block que
 * transforme o extraiga recursos.
 *
 * Llamada desde OrasylBlocks.load().
 */
public class OrasylProduction{

    public static Block rudimentaryQuarry;

    public static void load(){

        // AttributeCrafter es la clase real que usa Mindustry para bloques
        // que dependen de un atributo del terreno (ej. cultivator lee
        // Attribute.spores). Aquí lee OrasylAttributes.rockiness en vez de
        // cualquier atributo vanilla.
        //
        // "eficiencia inicial de 0%, dependiente exclusivamente de los
        // terrenos con su atributo":
        //   - baseEfficiency = 0f -> sin rockiness debajo, produce 0.
        //   - boostScale/maxBoost -> escala 1:1 con la suma de rockiness de
        //     las 4 tiles bajo el bloque (size = 2), tope en 300% con las
        //     4 tiles en basalt (rockiness 1.0 c/u -> suma 4.0, capada a 3).
        //   - Deliberadamente NO consume power/liquid/items: "exclusivamente"
        //     se tomó al pie de la letra, así que lo único que lo hace
        //     producir es el terreno. Si más adelante quieres que también
        //     necesite energía, es agregar un consumePower(...) aquí.
        rudimentaryQuarry = new AttributeCrafter("rudimentary-quarry"){{
            // Costo de construcción: PLACEHOLDER, no especificaste materiales -
            // ajústalo cuando tengas la economía de Nambara más definida.
            requirements(Category.production, with(Items.copper, 30, Items.lead, 20));

            size = 2;
            attribute = OrasylAttributes.rockiness;
            baseEfficiency = 0f;

            // Rediseño completo de la escala. El problema no era solo el tope:
            // con boostScale=1, un parche de 4 tiles IDÉNTICAS suma su
            // rockiness x4 (por eso basalt a 100% mostraba 400%, stone/
            // volcanic-stone a 75% mostraban 300%, y con maxBoost=3 todo lo
            // que sumaba 3.0+ quedaba aplanado al mismo número - ahí es
            // donde basalt y stone se volvían indistinguibles).
            //
            // boostScale=0.25f promedia en vez de sumar (divide entre las 4
            // tiles), así que un parche homogéneo de rockiness R muestra
            // ~R*100% de eficiencia directamente - el mismo número que ya
            // definiste al crear el atributo (stone 75% -> ~75% eficiencia,
            // basalt 100% -> ~100%, sandstone-floor 50% -> ~50%). maxBoost=1f
            // es el tope real: 4 tiles de basalt (el rockiness más alto que
            // existe, 1.0 c/u) dan exactamente 100% sin recorte - ya no hay
            // techo artificial por debajo de lo que el mejor terreno merece.
            boostScale = 0.25f;
            maxBoost = 1f;

            outputItem = new ItemStack(OrasylItems.stone, 1);
            craftTime = 90f;
            hasItems = true;
        }};

    }

}
