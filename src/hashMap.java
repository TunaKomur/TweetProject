import java.io.FileWriter;
import java.io.IOException;

public class hashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private int capacity;
    private float loadFactor;
    private int size;
    public Node<K, V>[] table;

    public hashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public hashMap(int capacity, float loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.size = 0;
        this.table = new Node[capacity];
    }

    public void putIfAbsent(K key, V value) {
        int index = hash(key);
        Node<K, V> node = table[index];
        while (node != null) {
            if ((node.key == null && key == null) || (node.key != null && node.key.equals(key))) {
                // Anahtar zaten varsa hiçbir şey yapma
                return;
            }
            node = node.next;
        }
        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;
        if (size > capacity * loadFactor) {
            resize();
        }
    }
    public V getOrDefault(K key, V defaultValue) {
        int index = hash(key);
        Node<K, V> node = table[index];
        while (node != null) {
            if ((node.key == null && key == null) || (node.key != null && node.key.equals(key))) {
                // Anahtar bulundu, değeri döndür
                return node.value;
            }
            node = node.next;
        }
        // Anahtar bulunamadı, varsayılan değeri döndür
        return defaultValue;
    }

    public static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private int hash(K key) {
        if (key == null) {
            return 0; // Özel durumu ele al
        }
        return Math.abs(key.hashCode()) % capacity;
    }

    public void put(K key, V value) {
        int index = hash(key);
        Node<K, V> node = table[index];
        while (node != null) {
            if ((node.key == null && key == null) || (node.key != null && node.key.equals(key))) {
                node.value = value;
                return;
            }
            node = node.next;
        }
        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;
        if (size > capacity * loadFactor) {
            resize();
        }
    }

    public V get(K key) {

        int index = hash(key);
        Node<K, V> node = table[index];

        while (node != null) {
            if ((node.key == null && key == null) || (node.key != null && node.key.equals(key))) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    public void remove(K key) {

        int index = hash(key);
        Node<K, V> node = table[index];
        Node<K, V> prev = null;

        while (node != null) {
            if ((node.key == null && key == null) || (node.key != null && node.key.equals(key))) {
                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return;
            }
            prev = node;
            node = node.next;
        }
    }

    private void resize() {

        int newCapacity = capacity * 2;
        Node<K, V>[] newTable = new Node[newCapacity];

        for (Node<K, V> oldNode : table) {

            while (oldNode != null) {
                Node<K, V> next = oldNode.next;
                int newIndex = hash(oldNode.key);
                oldNode.next = newTable[newIndex];
                newTable[newIndex] = oldNode;
                oldNode = next;
            }
        }
        table = newTable;
        capacity = newCapacity;
    }

    public static void writehashMapToFile(hashMap<String, user> hashMap, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {

            // hashMap'teki her girişi dosyaya yaz
            for (hashMap.Node<String, user> node : hashMap.table) {
                while (node != null) {
                    String line = node.key + " -> " + node.value + "\n";
                    writer.write(line);
                    node = node.next;
                }
            }

            System.out.println("hashMap dosyaya yazıldı.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean containsKey(K key) {
        int index = hash(key);
        Node<K, V> node = table[index];

        while (node != null) {
            if ((node.key == null && key == null) || (node.key != null && node.key.equals(key))) {
                return true;
            }
            node = node.next;
        }

        return false;
    }

}