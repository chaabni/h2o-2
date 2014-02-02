package water;

import water.H2O.H2OCountedCompleter;

/**
 * A typed atomic update.
 */
public abstract class TAtomic<T extends Iced> extends Atomic<TAtomic<T>> {
  /** Atomically update an old value to a new one.
   * @param old  The old value, it may be null.  It is a defensive copy.
   * @return The new value; if null if this atomic update no longer needs to be run
   */
  public abstract T atomic(T old);

  public TAtomic(){}
  public TAtomic(H2OCountedCompleter completer){super(completer);}
  @Override public Value atomic(Value val) {
    T old = val == null ? null : (T)(val.get().clone());
    T nnn = atomic(old);
    return  nnn == null ? null : new Value(_key,nnn,val==null?Value.ICE:val._persist);
  }
  @Override public void onSuccess( Value old ) { onSuccess(old==null?null:(T)old.get()); }
  // Upcast the old value to T
  public void onSuccess( T old ) { }
}
