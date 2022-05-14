package util.stack;

import java.util.Comparator;

/**
 * @author Samuel f. Ruiz
 * @version 1.0
 * @since 24/06/20
 */
public class Stack<T> {

    private Node<T> head;
    private final Comparator<T> tComparator;

    /**
     * crea la pila con el comparador especificado
     * @param tComparator comparador de la pila
     */
    public Stack(Comparator<T> tComparator) {
        this.tComparator = tComparator;
    }

    /**
     * agrega el objeto a la pila
     * @param data objeto a agregar
     */
    public void push(T data){
        Node<T> newNode = new Node<>(data);
        if (!isEmpty()) {
            newNode.setNext(head);
        }
        head = newNode;
    }

    /**
     * Saca y obtiene el siguiente objeto de la pila
     * @return siguiente objeto en la pila
     */
    public T pop(){
        if (!isEmpty()) {
            Node<T> node = head;
            head = head.getNext();
            return node.getData();
        }
        return null;
    }

    /**
     * obtiene el siguiente objeto en al pila sin retirarlo
     * @return siguiente objeto en la pila
     */
    public T peek(){
        if (!isEmpty()){
            return head.getData();
        }
        return null;
    }

    /**
     * Verifica si el objeto ingresado está en la pila segun
     * el comparator de la pila
     * @param data objeto a verificar
     * @return si se encuentra en la pila o no
     */
    public boolean exist(T data){
        if (!isEmpty()){
            Node<T> node = head;
            while (node != null && tComparator.compare(node.getData(),data) != 0){
                node = node.getNext();
            }
            return node != null;
        }
        return false;
    }

    /**
     * @return si la pila no tiene elementos,
     */
    public boolean isEmpty(){
        return head == null;
    }
}
