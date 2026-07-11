package it.unicam.cs.mpgc.rpg129207.persistence;

import it.unicam.cs.mpgc.rpg129207.model.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameStateRepository {
    private final File file = new File("savedState.xml");

    public void save(GameState gameState) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("gameState");
            document.appendChild(root);

            Element entitiesElement = document.createElement("entities");
            root.appendChild(entitiesElement);

            for (Entity entity : gameState.getEntities()) {
                Element entityElement = document.createElement("entity");
                entityElement.setAttribute("type", entity.getClass().getSimpleName());

                Element x = document.createElement("x");
                x.appendChild(document.createTextNode(String.valueOf(entity.getX())));

                Element y = document.createElement("y");
                y.appendChild(document.createTextNode(String.valueOf(entity.getY())));

                entityElement.appendChild(x);
                entityElement.appendChild(y);
                entitiesElement.appendChild(entityElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(file));

            System.out.println("Game saved");
        } catch (Exception e) {
            System.err.println("Error saving game " + e.getMessage());
        }
    }

    public GameState load() {
        if (!file.exists()) return null;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            List<Entity> entities = new ArrayList<>();
            var nodes = document.getElementsByTagName("entity");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                String type = element.getAttribute("type");

                double x = Double.parseDouble(element.getElementsByTagName("x").item(0).getTextContent());
                double y = Double.parseDouble(element.getElementsByTagName("y").item(0).getTextContent());

                if (type.equals("Player")) {
                    entities.add(new Player(100, 10, x, y));
                } else if (type.equals("Enemy")) {
                    entities.add(new Enemy(30, 5, x, y));
                } else {
                    System.err.println("Unknown entity type: " + type);
                }
            }

            Map map = new MapGenerator().generateMap();
            return new GameState(map, entities);

        } catch (Exception e) {
            System.err.println("Error loading game " + e.getMessage());
            return null;
        }
    }
}