package orasyl.content;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.type.StatusEffect;

/**
 * Registro de todos los StatusEffect propios de Orasyl.
 * Mismo patrón que OrasylBlocks: campos public static + load(), llamado
 * desde Orasyl.loadContent().
 *
 * Sobre "tint": el campo real en StatusEffect se llama `color`, pero su
 * propio comentario en el código fuente dice literalmente "Tint color of
 * effect" - por eso aquí "tint" y `color` son la misma cosa.
 *
 * Sobre `effect`: para que `effectChance` tenga algo visible que mostrar,
 * hace falta asignarle un Effect de partículas (si se deja en Fx.none,
 * que es el default, effectChance no dispara nada). No lo pediste
 * explícitamente, así que elegí Fx.corrosionVapor como valor por
 * defecto -es el mismo que usa el "corroded" vanilla y encaja
 * temáticamente con un efecto de "oxidación salina". Es una línea y se
 * cambia fácil si quieres otra cosa.
 */
public class OrasylStatusEffects{

    public static StatusEffect saltyRusting;

    public static void load(){

        saltyRusting = new StatusEffect("salty-rusting"){{
            // damage es daño POR FRAME, no por segundo (60 frames/seg).
            // 2f/60f = 2 de daño continuo por segundo real.
            damage = 2f / 60f;

            // intervalDamageTime es el espaciado en TICKS entre golpes de
            // intervalDamage (60 ticks = 1 seg). 8 ticks = 60/8 = 7.5 veces/seg
            // (sin tocar, ya estaba en el rango pedido). El problema real era
            // intervalDamage: a 15 por golpe, 7.5 golpes/seg son 112.5/seg -
            // eso era lo que realmente destrozaba unidades tier bajo, no el
            // daño continuo. Bajado a 2: 2*7.5 = 15/seg desde el intervalo,
            // más 2/seg constantes = ~17/seg total.
            // (Para referencia: original ~632.7/seg -> ajuste anterior
            // ~117.5/seg -> ahora ~17/seg.)
            intervalDamage = 2f;
            intervalDamageTime = 8f;
            intervalDamagePierce = true;

            color = Color.valueOf("89e8b2");

            // "que pueda aparecer seguido pero no siempre": bastante más alto
            // que el rango típico vanilla (~0.04 a 0.15), pero sin llegar a 1.
            effectChance = 0.6f;
            effect = Fx.corrosionVapor;
        }};

    }

}
