package tree;

import java.util.ArrayList;
import java.util.List;

import estrut.Tree;

public class BinarySearchTree implements Tree {
    
    private Node root;

    // Classe interna para representar um nó da árvore
    private class Node {
        int valor;
        Node left, right;

        public Node(int item) {
            valor = item;
            left = right = null;
        }
    }

    // Implementação da busca de elemento
    @Override
    public boolean buscaElemento(int valor) {
        return buscaElementoRecursivo(root, valor);
    }

    private boolean buscaElementoRecursivo(Node root, int valor) {
        if (root == null)
            return false;
        if (valor == root.valor)
            return true;
        if (valor < root.valor)
            return buscaElementoRecursivo(root.left, valor);
        return buscaElementoRecursivo(root.right, valor);
    }

    // Implementação do mínimo
    @Override
    public int minimo() {
        if (root == null)
            throw new IllegalStateException("Árvore vazia");
        Node current = root;
        while (current.left != null)
            current = current.left;
        return current.valor;
    }

    // Implementação do máximo
    @Override
    public int maximo() {
        if (root == null)
            throw new IllegalStateException("Árvore vazia");
        Node current = root;
        while (current.right != null)
            current = current.right;
        return current.valor;
    }

    // Implementação da inserção de elemento
    @Override
    public void insereElemento(int valor) {
        root = insereElementoRecursivo(root, valor);
    }

    private Node insereElementoRecursivo(Node root, int valor) {
        if (root == null) {
            root = new Node(valor);
            return root;
        }
        if (valor < root.valor)
            root.left = insereElementoRecursivo(root.left, valor);
        else if (valor > root.valor)
            root.right = insereElementoRecursivo(root.right, valor);
        return root;
    }

    // Implementação da remoção
    @Override
    public void remove(int valor) {
        root = removeRecursivo(root, valor);
    }

    private Node removeRecursivo(Node root, int valor) {
        if (root == null)
            return root;
        if (valor < root.valor)
            root.left = removeRecursivo(root.left, valor);
        else if (valor > root.valor)
            root.right = removeRecursivo(root.right, valor);
        else {
            // Nó com somente um filho ou sem filho
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            // Nó com dois filhos: obtém o sucessor em ordem (menor nó na subárvore direita)
            root.valor = minValue(root.right);

            // Remove o sucessor em ordem
            root.right = removeRecursivo(root.right, root.valor);
        }
        return root;
    }

    private int minValue(Node root) {
        int minv = root.valor;
        while (root.left != null) {
            minv = root.left.valor;
            root = root.left;
        }
        return minv;
    }

    // Implementação da travessia em pré-ordem
    @Override
    public int[] preOrdem() {
        List<Integer> resultado = new ArrayList<>();
        preOrdemRecursivo(root, resultado);
        return resultado.stream().mapToInt(Integer::intValue).toArray();
    }

    private void preOrdemRecursivo(Node root, List<Integer> resultado) {
        if (root != null) {
            resultado.add(root.valor);
            preOrdemRecursivo(root.left, resultado);
            preOrdemRecursivo(root.right, resultado);
        }
    }

    // Implementação da travessia em ordem
    @Override
    public int[] emOrdem() {
        List<Integer> resultado = new ArrayList<>();
        emOrdemRecursivo(root, resultado);
        return resultado.stream().mapToInt(Integer::intValue).toArray();
    }

    private void emOrdemRecursivo(Node root, List<Integer> resultado) {
        if (root != null) {
            emOrdemRecursivo(root.left, resultado);
            resultado.add(root.valor);
            emOrdemRecursivo(root.right, resultado);
        }
    }

    // Implementação da travessia em pós-ordem
    @Override
    public int[] posOrdem() {
        List<Integer> resultado = new ArrayList<>();
        posOrdemRecursivo(root, resultado);
        return resultado.stream().mapToInt(Integer::intValue).toArray();
    }

    private void posOrdemRecursivo(Node root, List<Integer> resultado) {
        if (root != null) {
            posOrdemRecursivo(root.left, resultado);
            posOrdemRecursivo(root.right, resultado);
            resultado.add(root.valor);
        }
    }
}