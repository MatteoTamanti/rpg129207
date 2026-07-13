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
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameStateRepository {

    private static final String SAVE_FILE_NAME = "savedState.xml";
    private static final String PLAYER_TYPE = "Player";
    private static final String ENEMY_TYPE = "Enemy";
    private static final String NPC_TYPE = "NPC";

    private final File file = new File(SAVE_FILE_NAME);

    public void save(GameState gameState) {
        try {
            Document document = createDocument();

            Element root = document.createElement("gameState");
            document.appendChild(root);

            Element entitiesElement = document.createElement("entities");
            root.appendChild(entitiesElement);

            for (Entity entity : gameState.getEntities()) {
                entitiesElement.appendChild(createEntityElement(document, entity));
            }

            writeToFile(document);
            System.out.println("Game saved");

        } catch (Exception e) {
            System.err.println("Error saving game " + e.getMessage());
        }
    }

    private Document createDocument() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.newDocument();
    }

    private Element createEntityElement(Document document, Entity entity) {
        Element entityElement = document.createElement("entity");
        entityElement.setAttribute("type", entity.getClass().getSimpleName());

        entityElement.appendChild(createTextElement(document, "x", String.valueOf(entity.getX())));
        entityElement.appendChild(createTextElement(document, "y", String.valueOf(entity.getY())));

        if (entity instanceof NPC npc) {
            entityElement.appendChild(createNpcDialogueElement(document, npc));
        }

        return entityElement;
    }

    private Element createNpcDialogueElement(Document document, NPC npc) {
        Element dialogueElement = document.createElement("dialogue");
        dialogueElement.setAttribute("currentLine", String.valueOf(npc.getCurrentLine()));

        for (String line : npc.getDialogueLines()) {
            dialogueElement.appendChild(createTextElement(document, "line", line));
        }

        return dialogueElement;
    }

    private Element createTextElement(Document document, String tagName, String value) {
        Element element = document.createElement(tagName);
        element.appendChild(document.createTextNode(value));
        return element;
    }

    private void writeToFile(Document document) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(file));
    }

    public GameState load() {
        if (!file.exists()) {
            return null;
        }

        try {
            Document document = parseDocument();
            List<Entity> entities = readEntities(document);
            Map map = new MapGenerator().generateMap();
            return new GameState(map, entities);

        } catch (Exception e) {
            System.err.println("Error loading game " + e.getMessage());
            return null;
        }
    }

    private Document parseDocument() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        return document;
    }

    private List<Entity> readEntities(Document document) {
        List<Entity> entities = new ArrayList<>();
        NodeList nodes = document.getElementsByTagName("entity");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            entities.add(readEntity(element));
        }

        return entities;
    }

    private Entity readEntity(Element element) {
        String type = element.getAttribute("type");
        double x = readDoubleChild(element, "x");
        double y = readDoubleChild(element, "y");

        return switch (type) {
            case PLAYER_TYPE -> new Player(x, y);
            case ENEMY_TYPE -> new Enemy(x, y);
            case NPC_TYPE -> readNpc(element, x, y);
            default -> {
                System.err.println("Unknown entity type: " + type);
                yield null;
            }
        };
    }

    private NPC readNpc(Element entityElement, double x, double y) {
        Element dialogueElement = (Element) entityElement.getElementsByTagName("dialogue").item(0);
        int currentLine = Integer.parseInt(dialogueElement.getAttribute("currentLine"));

        NodeList lineNodes = dialogueElement.getElementsByTagName("line");
        List<String> dialogueLines = new ArrayList<>();
        for (int i = 0; i < lineNodes.getLength(); i++) {
            dialogueLines.add(lineNodes.item(i).getTextContent());
        }

        return new NPC(x, y, dialogueLines, currentLine);
    }

    private double readDoubleChild(Element parent, String tagName) {
        return Double.parseDouble(parent.getElementsByTagName(tagName).item(0).getTextContent());
    }
}