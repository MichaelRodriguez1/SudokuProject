package util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * @author Samuel F. Ruiz
 * @since 11/09/20
 */
public class StringIterator implements Iterator<Character> {

    private int currentChar;
    private final String string;

    /**
     * cra un iterador con los caracteres del String ingresasdo
     * @param string cadena de la cual se extraerán los caracteres
     */
    public StringIterator(String string) {
        if (string == null) throw new NullPointerException();
        this.string = string;
    }

    @Override
    public boolean hasNext() {
        return currentChar < string.length();
    }

    @Override
    public Character next() {
        if (!hasNext()) throw new NoSuchElementException();
        return string.charAt(currentChar++);
    }

    @Override
    public void forEachRemaining(Consumer<? super Character> action) {
        while (hasNext()){
            action.accept(next());
        }
    }
}
