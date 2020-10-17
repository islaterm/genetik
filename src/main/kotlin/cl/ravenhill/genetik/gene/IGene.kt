package cl.ravenhill.genetik.gene

import genetikt.gene.CharGene
import genetikt.gene.TypedGene


/**
 * Genes are the basic elements of the `genetik` package.
 * They contain the actual information of the individuals of a population.
 * In this context, the information contained in a gene is called DNA.
 *
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.1
 */
interface IGene<out DNA> {

    /** DNA of this gene. */
    val dna: DNA

    /**
     * Copy the gene into another.
     *
     * @param other Gene that's going to be overwritten with this one.
     */
    fun copyTo(other: IGene<*>)

    /**
     * Copy a `CharGene` info into this gene.
     *
     * @param other
     *    Gene that's going to overwite this gene.
     * @throws TypeMismatchException
     *    If the two genes are incompatible.
     */
    fun copyFromCharGene(other: CharGene) {
        throw TypeMismatchException()
    }

    class TypeMismatchException : Exception()

    /**
     * Copy a `TypedGene` info into this gene.
     *
     * @param other
     *    Gene that's going to overwrite this gene.
     * @throws TypeMismatchException
     *    If the two genes are incompatible.
     */
    fun copyFromTypedGene(other: TypedGene<*>) {
        throw TypeMismatchException()
    }
}

