package genetikt.chromosome

import cl.ravenhill.genetik.gene.IGene

/**
 * Factory for creating chromosomes.
 *
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.1
 */
interface IChromosomeFactory<G : IGene<*>> {

  /** Builds a new chromosome. */
  fun build(): IChromosome<G>
}