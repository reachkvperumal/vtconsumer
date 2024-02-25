package com.kv.carrier.vt.demo.consumer.graph;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JsonGraphBuilder {

    public void apply() {
        // Parse the JSON string
        String jsonString = "[...]"; // Replace with your actual JSON string
        JSONArray jsonArray = new JSONArray(jsonString);

        // Create a directed com.kv.jdk21.graph
        DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        // Map client IDs to nodes
        Map<String, String> clientToNodeMap = new HashMap<>();

        // Process each object in the array
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            JSONArray clientAccounts = jsonObj.getJSONArray("ClientAccounts");

            // Process each client account
            for (int j = 0; j < clientAccounts.length(); j++) {
                JSONObject clientAccount = clientAccounts.getJSONObject(j);

                // Create or get node for the client
                String clientId = clientAccount.getString("ClientId");
                String node;
                if (!clientToNodeMap.containsKey(clientId)) {
                    node = clientId + ": " + clientAccount.getString("DisplayName");
                    graph.addVertex(node);
                    clientToNodeMap.put(clientId, node);
                } else {
                    node = clientToNodeMap.get(clientId);
                }

                // Get parent information if available
                String parentId = clientAccount.optString("LegalParentId", null);

                // Add edge to parent if it exists
                if (parentId != null && clientToNodeMap.containsKey(parentId)) {
                    graph.addEdge(clientToNodeMap.get(parentId), node);
                }

                // Additional processing for other properties as needed
                // ...

            }
        }

        // Use the com.kv.jdk21.graph for desired operations
        System.out.println("Graph:");
        for (String vertex : graph.vertexSet()) {
            System.out.println("- " + vertex);
        }
        System.out.println("Edges:");
        for (DefaultEdge edge : graph.edgeSet()) {
            System.out.println("  - " + graph.getEdgeSource(edge) + " -> " + graph.getEdgeTarget(edge));
        }
    }
}

