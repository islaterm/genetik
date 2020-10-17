package genetikt.chromosome

import cl.ravenhill.genetik.gene.IGene

/**
 * A chromosome consists of an array of genes.
 *
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.1
 * @see IGene
 */
interface IChromosome<G : IGene<*>> {

  /** Genes that make up the chromosome. */
  val genes: Array<G>

  /** Number of genes in the chromosome. */
  val size: Int
    get() = genes.size

  /** Creates a copy of this chromosome. */
  fun copy(): IChromosome<G>

  /**
   * Mutates a chromosome according to it's mutation rate.
   *
   * @param mutationRate  Probability with which a gene will mutate.
   */
  fun mutate(mutationRate: Double)

  val target: Array<G>

  fun replaceGeneAt(geneIndex: Int, value: Any)
}