package genetikt.chromosome

import genetikt.gene.TypedGene

/**
 * Factory for creating instances of `CharChromosome`.
 *
 * @property  size
 *    Number of genes of the chromosome.
 * @property  target
 *    **(Optional)** Target of the chromosome.
 * @property  alphabet
 *    **(Optional)** Alphabet of the chromosome.
 * @see CharChromosome
 *
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.2
 * @version 1.2
 */
class TypedChromosomeFactory<DNA>(
    private var size: Int,
    private var target: Array<TypedGene<DNA>>? = null,
    private var alphabet: Map<String, DNA>
) : IChromosomeFactory<TypedGene<DNA>> {

  /** Builds a new chromosome. */
  override fun build() = TypedChromosome(size, target, alphabet)
}