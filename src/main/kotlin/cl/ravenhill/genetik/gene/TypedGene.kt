package genetikt.gene

import cl.ravenhill.genetik.gene.IGene
import java.util.*

/**
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.2
 */
class TypedGene<DNA> : IGene<DNA> {

  /** Dictionary that contains all the valid DNA sequences. */
  var alphabet: Map<String, DNA>
    private set

  /** DNA of this gene. */
  override var dna: DNA
    private set

  /** String that identifies this gene. */
  private var key: String

//region Constructors
  /**
   * Creates a random gene.
   *
   * @param anAlphabet
   *    Map containing all the valid values that can take a gene.
   */
  constructor(anAlphabet: Map<String, DNA>) {
    alphabet = anAlphabet
    val keys = ArrayList(alphabet.keys)
    key = keys[Random().nextInt(keys.size)]
    dna = alphabet[key]!!
  }

  /**
   * Creates a gene from a key.
   *
   * @param key
   *    Name of the object this gene represents.
   * @param anAlphabet
   *    Map containing all the valid values that can take the gene.
   *    `key` must be a key contained in the dictionary.
   */
  constructor(key: String, anAlphabet: Map<String, DNA>) {
    alphabet = anAlphabet
    this.key = key
    dna = anAlphabet[key]!!
  }
//endregion

//region Utility functions
  /**
   * Returns a copy of this gene.
   */
  fun copy() = TypedGene(key, alphabet)

  override fun copyTo(other: IGene<*>) {
    other.copyFromTypedGene(this)
  }

  @Suppress("UNCHECKED_CAST")
  override fun copyFromTypedGene(other: TypedGene<*>) {
    dna = other.dna as DNA
    key = other.key
    alphabet = other.alphabet as Map<String, DNA>
  }

  /**
   * Checks if this gene is equal to another.
   *
   * @param other
   *    Gene to be checked.
   * @return
   *    `true` if the two genes are equal, `false` if not.
   */
  override fun equals(other: Any?): Boolean {
    return other is TypedGene<*> && this.dna?.equals(other.dna) == true
        && this.alphabet == other.alphabet
  }

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode(): Int {
    var result = alphabet.hashCode()
    result = 31 * result + (dna?.hashCode() ?: 0)
    result = 31 * result + key.hashCode()
    return result
  }

  /**
   * Returns a string representation of this gene.
   */
  override fun toString() = key

  fun replaceWith(value: String) {
    key = value
    dna = alphabet[key]!!
  }
//endregion
}