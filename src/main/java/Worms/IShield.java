package Worms;

public interface IShield {
    default void useShield(Worm worm) {
        worm.Shield = true;
    }
}
