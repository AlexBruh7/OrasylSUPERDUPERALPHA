package orasyl.content;

/**
 * Punto central que orquesta TODO el contenido de tipo Block de Orasyl.
 * No define nada por sí misma - cada categoría vive en su propia clase, y
 * esta solo llama a sus load() en orden. Orasyl.loadContent() llama
 * únicamente a este load(), no a los de cada categoría por separado.
 *
 *   OrasylEnvironment   -> floors de terreno (tierra, roca, agua...)
 *   OrasylProduction    -> drills, factories, extractores
 *   OrasylDistribution  -> bandas, distribuidores, puentes
 *   OrasylLiquids       -> bombas, conductos, tanques
 *
 * IMPORTANTE sobre nombres: mientras loadContent() se ejecuta, Mindustry
 * antepone automáticamente el "name" del mod.hjson al id que le pasas al
 * constructor (ver ContentLoader.transformName). Con name: "orasyl", esto
 * significa que:
 *
 *   new Floor("dry-grass", 3)  ->  su id interno REAL termina siendo "orasyl-dry-grass"
 *
 * Tú SIEMPRE pasas el id corto ("dry-grass") al constructor - el prefijo es
 * automático. Pero donde SÍ hay que escribirlo completo es en bundle.properties,
 * porque esa transformación ya ocurrió para cuando se busca el nombre visible:
 *
 *   block.orasyl-dry-grass.name = Dry Grass
 *
 * Para referenciar un campo desde fuera, ahora se accede vía la categoría
 * dueña del contenido, no desde aquí. Ej: OrasylEnvironment.dryGrass,
 * no OrasylBlocks.dryGrass (ya no existe ese campo en esta clase).
 */
public class OrasylBlocks{

    public static void load(){
        OrasylEnvironment.load();
        OrasylProduction.load();
        OrasylDistribution.load();
        OrasylLiquids.load();
    }

}
