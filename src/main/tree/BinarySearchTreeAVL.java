package tree;

import java.util.ArrayList;
import java.util.List;

import estrut.Tree;

public class BinarySearchTreeAVL {

    // Classe interna para representar um nó da árvore
    private class Node {
        int valor;
        int altura;
        Node left, right;

        public Node(int item) {
            valor = item;
            altura = 1;
            left = right = null;
        }
    }

    private Node root;

    // Restante do código...

    // Função auxiliar para obter a altura de um nó
    private int altura(Node node) {
        if (node == null) {
            return 0;
        }
        return node.altura;
    }

    // Função auxiliar para atualizar a altura de um nó
    private void atualizarAltura(Node node) {
        node.altura = Math.max(altura(node.left), altura(node.right)) + 1;
    }

    // Função auxiliar para realizar rotação à direita em torno do nó 'y'
    private Node rotacaoDireita(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Realiza a rotação
        x.right = y;
        y.left = T2;

        // Atualiza as alturas
        atualizarAltura(y);
        atualizarAltura(x);

        // Retorna o novo nó raiz
        return x;
    }

    // Função auxiliar para realizar rotação à esquerda em torno do nó 'x'
    private Node rotacaoEsquerda(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Realiza a rotação
        y.left = x;
        x.right = T2;

        // Atualiza as alturas
        atualizarAltura(x);
        atualizarAltura(y);

        // Retorna o novo nó raiz
        return y;
    }

    // Função para inserir um valor na árvore AVL
    public void insereElemento(int valor) {
        root = insereElementoRecursivo(root, valor);
    }

    private Node insereElementoRecursivo(Node node, int valor) {
        // Realiza a inserção como em uma BST normal
        if (node == null) {
            return new Node(valor);
        }

        if (valor < node.valor) {
            node.left = insereElementoRecursivo(node.left, valor);
        } else if (valor > node.valor) {
            node.right = insereElementoRecursivo(node.right, valor);
        } else {
            // Duplicados não são permitidos em uma BST
            return node;
        }

        // Atualiza a altura do nó atual
        atualizarAltura(node);

        // Verifica o balanceamento e realiza as rotações, se necessário
        int balanceamento = altura(node.left) - altura(node.right);

        // Caso de desbalanceamento à esquerda
        if (balanceamento > 1) {
            // Rotação simples à direita ou rotação dupla à esquerda-direita
            if (valor < node.left.valor) {
                return rotacaoDireita(node);
            } else {
                node.left = rotacaoEsquerda(node.left);
                return rotacaoDireita(node);
            }
        }
        // Caso de desbalanceamento à direita
        if (balanceamento < -1) {
            // Rotação simples à esquerda ou rotação dupla à direita-esquerda
            if (valor > node.right.valor) {
                return rotacaoEsquerda(node);
            } else {
                node.right = rotacaoDireita(node.right);
                return rotacaoEsquerda(node);
            }
        }
        return node;
    }
    
// Implementação da busca de elemento
    
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
    public int minimo() {
        if (root == null)
            throw new IllegalStateException("Árvore vazia");
        Node current = root;
        while (current.left != null)
            current = current.left;
        return current.valor;
    }

    // Implementação do máximo
    public int maximo() {
        if (root == null)
            throw new IllegalStateException("Árvore vazia");
        Node current = root;
        while (current.right != null)
            current = current.right;
        return current.valor;
    }
    
    // Implementação da remoção
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
            root.valor = minimo();

            // Remove o sucessor em ordem
            root.right = removeRecursivo(root.right, root.valor);
        }
        return root;
    }

    // Implementação da travessia em pré-ordem
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
    public int tamanho() {
        return tamanhoRecursivo(root);
    }
    
    private int tamanhoRecursivo(Node root) {
        if (root == null)
            return 0;
        else
            return tamanhoRecursivo(root.left) + 1 + tamanhoRecursivo(root.right);
    }
}
