package orasyl.content;

import mindustry.world.meta.Attribute;

/**
 * Atributos personalizados de Orasyl.
 *
 * Attribute NO es un enum - es una clase abierta con un método estático
 * Attribute.add(name) que registra uno nuevo en tiempo de ejecución (mismo
 * mecanismo que usa Mindustry para sus propios water/oil/heat/spores/etc,
 * ver mindustry.world.meta.Attribute). Por eso un mod SÍ puede crear los
 * suyos propios sin tocar el motor.
 *
 * Debe cargarse ANTES que cualquier floor/block que use rockiness en su
 * attributes.set(...) - por eso Orasyl.loadContent() llama a
 * OrasylAttributes.load() antes que OrasylBlocks.load().
 */
public class OrasylAttributes{

    /** Qué tanta piedra tiene un tile. 0 = nada, 1 = 100% roca. */
    public static Attribute rockiness;

    public static void load(){
        rockiness = Attribute.add("rockiness");
    }

}
