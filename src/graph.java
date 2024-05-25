import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class graph {
    private hashMap<String, myArrayList<String>> adjacencyList;

    public graph() {
        this.adjacencyList = new hashMap<>();
    }

    public void addNode(String node) {
        adjacencyList.putIfAbsent(node, new myArrayList<>());
    }

    public void addEdge(String source, String destination) {
        adjacencyList.putIfAbsent(source, new myArrayList<>());
        adjacencyList.putIfAbsent(destination, new myArrayList<>());
        adjacencyList.get(source).add(destination);
    }

    public myArrayList<String> getNeighbors(String node) {
        return adjacencyList.getOrDefault(node, new myArrayList<>());
    }

    public void printGraphToFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            for (hashMap.Node<String, myArrayList<String>> entry : adjacencyList.table) {
                while (entry != null) {
                    String node = entry.key;
                    myArrayList<String> neighbors = entry.value;

                    // Çıktıyı dosyaya yaz
                    bufferedWriter.write(node + " -> ");
                    for (Object neighbor : neighbors) {
                        bufferedWriter.write(neighbor + " ");
                    }
                    // Her satırın sonuna yeni bir satır ekleyin
                    bufferedWriter.newLine();
                    entry = entry.next;
                }
            }

            System.out.println("Graph dosyaya yazıldı.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}