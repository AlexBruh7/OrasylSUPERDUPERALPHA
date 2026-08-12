package orasyl;

import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import orasyl.content.*;

public class Orasyl extends Mod{

    public Orasyl(){
        Log.info("[Orasyl] Constructor del mod ejecutado.");

        // Se dispara una sola vez, cuando el cliente ya cargó todo el juego.
        // Útil para diálogos, hooks de UI, o cosas que dependan del atlas de sprites ya cargado.
        Events.on(ClientLoadEvent.class, e -> {
            Log.info("[Orasyl] Cliente cargado, mod listo.");
        });
    }

    @Override
    public void loadContent(){
        // Este método se llama UNA vez al iniciar el juego, antes de que exista
        // cualquier partida. Aquí es donde se registran (no se "usan") los
        // Content del mod: Items, Blocks, UnitTypes, Planets, etc.
        //
        // Orden recomendado según lo definido en el documento de diseño:
        //   1. Items / Liquids propios de Nambara (arena, agua salada, etc.)
        //   2. Blocks propios de Nambara (logística básica estilo Serpulo)      <- ya iniciado
        //   3. UnitTypes de la facción Turba
        //   4. El Planet "Nambara" en sí (usa los anteriores)
        //   5. Más adelante: el Planet "Orasyl" (la estrella, no jugable)

        OrasylAttributes.load();    // primero: Blocks/StatusEffects pueden referenciar atributos
        OrasylStatusEffects.load(); // antes de Blocks/LiquidTypes, ambos pueden referenciar un status
        OrasylItems.load();         // antes de Blocks: rudimentary-quarry produce OrasylItems.stone
        OrasylLiquidTypes.load();   // usa OrasylStatusEffects.saltyRusting como effect
        OrasylBlocks.load();

        Log.info("[Orasyl] loadContent() ejecutado - contenido registrado.");
    }

}
