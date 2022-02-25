package com.techpasya.aujar.visualize;

import com.techpasya.aujar.graph.AjEdge;
import com.techpasya.aujar.graph.EdgeType;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class GraphBuilder {

    private static final String STARTING_DIAGRAM =
            "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"{width}px\" height=\"{height}px\">\n"
                    + "<script>\n"
                    + " window.addEventListener('DOMContentLoaded', () => {\n"
                    + "   function showTip (id) {\n"
                    + "     console.log(id);"
                    + "     document.getElementById(id.baseVal).style.display = \"block\";\n"
                    + "   }\n"
                    + "   function hideTip(id) {\n"
                    + "     console.log(id);"
                    + "     document.getElementById(id.baseVal).style.display = \"none\";\n"
                    + "   }\n"
                    + "   document.querySelectorAll('circle').forEach( (c) => {\n"
                    + "     c.addEventListener('mouseover', (x) => {\n"
                    + "       showTip(c.className);\n"
                    + "     })\n"
                    + "     c.addEventListener('mouseout', (x) => {\n"
                    + "       console.log(c);"
                    + "       hideTip(c.className);\n"
                    + "     })\n"
                    + "   })\n"
                    + " })\n"
                    + "</script>"
                    + "  <g>\n"
                    + "    <text x=\"20\" y=\"20\" fill=\"black\" font-size=\"12\">INTERFACES are in blue; and OTHERS are in green</text>\n"
                    + "    <text x=\"20\" y=\"40\" fill=\"black\" font-size=\"12\">EXTENDS are in pink; IMPLEMENTS are in purple; and CONTAINS are in orange</text>\n"
                    + "  </g>\n";

    private static final String INTERFACE_COLOR = "#6699cc";
    private static final String NON_INTERFACE_COLOR = "#74c365";
    private static final String CONTAINS_ARROW = "▶";
    private static final String NON_CONTAINS_ARROW = "▷";
    private static final int MIN_WIDTH = 500;
    private static final int MIN_HEIGHT = 500;

    private static final String CLASS_DIAGRAM = "  <g>\n"
            + "    <circle class=\"{class}\"  cx=\"{cx}\" cy=\"{cy}\" r=\"{r}\" fill=\"{color}\" stroke=\"black\" stroke-width=\"3\" />\n"
            + "    <text x=\"{cx}\" y=\"{cyt}\" font-size=\"20\" fill=\"black\" text-anchor=\"middle\">{className}</text>\n"
            + "  </g>\n";

    private static final String MOUSEOVER_TEXT = "<g>\n" +
            "  <text id=\"{id}\" x=\"{x}\" y=\"{y}\" display=\"none\" >\n" +
            "   <tspan x=\"{x}\" dy=\"1.2em\" fill=\"red\" font-size=\"40px\">{mainclass}</tspan>\n" +
            "   <tspan x=\"{x}\" dy=\"1.2em\" font-size=\"35px\">IMPLEMENTS : {implements}</tspan>\n" +
            "   <tspan x=\"{x}\" dy=\"1.2em\" font-size=\"35px\">EXTENDS : {extends}</tspan>\n" +
            "   <tspan x=\"{x}\" dy=\"1.2em\" font-size=\"35px\">CONTAINS : {contains}</tspan>\n" +
            "  </text>\n" +
            " </g>";

    private static final String ARROW_IN_PATH =
            " <text font-size=\"35px\" fill=\"{color}\" dominant-baseline=\"central\">\n"
                    + "  <textPath href=\"#{id}\" startOffset=\"10%\" >{arrowhead}</textPath>\n"
                    + "  <textPath href=\"#{id}\" startOffset=\"30%\" >{arrowhead}</textPath>\n"
                    + "  <textPath href=\"#{id}\" startOffset=\"60%\" >{arrowhead}</textPath>\n"
                    + "  <textPath href=\"#{id}\" startOffset=\"80%\" >{arrowhead}</textPath>\n"
                    + "  <textPath href=\"#{id}\" startOffset=\"95%\" >{arrowhead}</textPath>\n"
                    + " </text>\n";

    private static final String INTERFACE_PATH = "  <path stroke-dasharray=\"8, 8\" id=\"{id}\" d=\"M{xs},{ys} Q{xc},{yc} {xe},{ye}\" fill=\"none\" stroke=\"{color}\" stroke-width=\"6\" />\n";

    private static final String NON_INTERFACE_PATH = "  <path id=\"{id}\" d=\"M{xs},{ys} Q{xc},{yc} {xe},{ye}\" fill=\"none\" stroke=\"{color}\" stroke-width=\"6\" />\n";

    private static final String CONTAINS_COLOR = "#f58231";
    private static final String EXTENDS_COLOR = "#f032e6";
    private static final String IMPLEMENTS_COLOR = "purple";

    private static final String ENDING_DIAGRAM = "\n</svg>";
    private static final int RADIUS_OF_CLASS_DIAGRAM = 70;

    /**
     * Takes the ClassComponent and DownloadPath and downloads visualize image to downloadpath.
     */


    public static void build(final Map<Class<?>, List<AjEdge>> adjClasses, final String downloadPath)
            throws IOException {
        Path filePath = Paths.get(downloadPath);
        boolean isDir = Files.isDirectory(filePath);
        if (isDir) {
            if (!Files.exists(filePath)) {
                throw new FileNotFoundException("Download Directory Does not exists");
            }
            filePath = Paths.get(downloadPath, "graph.svg");
        }

        final Set<Class<?>> keySet = adjClasses.keySet();
        final int noOfClasses = keySet.size();
        List<Point> points = getCoordinates(noOfClasses);
        Map<Class<?>, Point> classInSvg = linkClassToPoint(keySet, points);

        final StringBuilder sb = new StringBuilder(STARTING_DIAGRAM.replace("{width}",
                (Math.max((noOfClasses * 315), MIN_WIDTH)) + "").replace("{height}",
                (Math.max((noOfClasses * 315), MIN_HEIGHT) + "")));

        //Drawing edges (relations)
        for (Map.Entry<Class<?>, Point> entry : classInSvg.entrySet()) {
            List<AjEdge> edges = adjClasses.get(entry.getKey());

            for (AjEdge edge : edges) {
                Point startingPoint = entry.getValue();
                Point endingPoint = classInSvg.get(edge.getDestination());

                if (startingPoint.equals(endingPoint)) {
                    startingPoint = new Point(startingPoint.getX() - RADIUS_OF_CLASS_DIAGRAM,
                            startingPoint.getY());
                    endingPoint = new Point(endingPoint.getX() + RADIUS_OF_CLASS_DIAGRAM, endingPoint.getY());
                }

                final String edgeColor = getColorForPath(edge.getTypeOfRelation());
                final String id = edge.getSource().toString() + edge.getDestination().toString() + edge
                        .getTypeOfRelation();
                final String arrowHead = getArrowHead(edge.getTypeOfRelation());
                final String path = edge.getTypeOfRelation().equals(EdgeType.IMPLEMENTS) ? INTERFACE_PATH : NON_INTERFACE_PATH;
                sb.append(path.replace("{xs}", startingPoint.getX() + "")
                        .replace("{ys}", startingPoint.getY() + "")
                        .replace("{xc}",
                                ((startingPoint.getX() + endingPoint.getX()) / getRandomDivider()) + "")
                        .replace("{yc}",
                                ((startingPoint.getY() + endingPoint.getY()) / getRandomDivider()) + "")
                        .replace("{xe}", endingPoint.getX() + "")
                        .replace("{ye}", endingPoint.getY() + "")
                        .replace("{id}", id)
                        .replace("{color}", edgeColor));

                sb.append(ARROW_IN_PATH.replace("{id}", id).replace("{color}", edgeColor).replace("{arrowhead}", arrowHead));
            }
        }

        //Drawing vertex (classes)
        for (Map.Entry<Class<?>, Point> entry : classInSvg.entrySet()) {
            final String classToString = entry.getKey().toString();
            String[] splitTypeAndName = classToString.split(" ");
            final boolean isInterface = splitTypeAndName[0].equalsIgnoreCase("interface");
            String className = splitTypeAndName[1];
            String[] classPaths = className.split("\\.");
            className = classPaths[classPaths.length - 1];
            Point point = entry.getValue();

            sb.append(CLASS_DIAGRAM.replace("{cx}", point.getX() + "")
                    .replace("{class}", splitTypeAndName[1])
                    .replace("{cy}", point.getY() + "")
                    .replace("{cyt}", (point.getY() + 5.0) + "")
                    .replace("{color}", isInterface ? INTERFACE_COLOR : NON_INTERFACE_COLOR)
                    .replace("{className}", className)
                    .replace("{r}", RADIUS_OF_CLASS_DIAGRAM + ""));

            sb.append(MOUSEOVER_TEXT.replace("{id}", splitTypeAndName[1])
                    .replace("{x}", point.getX() + RADIUS_OF_CLASS_DIAGRAM + "")
                    .replace("{y}", point.getY() - (RADIUS_OF_CLASS_DIAGRAM / 2.0) + "")
                    .replace("{mainclass}", splitTypeAndName[1])
                    .replace("{implements}", "classes")
                    .replace("{extends}", "classes")
                    .replace("{contains}", "classes"));
        }
        sb.append(ENDING_DIAGRAM);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write(sb.toString());
        }
    }


    private static Map<Class<?>, Point> linkClassToPoint(Set<Class<?>> clazzes, List<Point> points) {
        Map<Class<?>, Point> classInSvg = new HashMap<>();
        int i = 0;
        for (Class<?> clazz : clazzes) {
            Point p = points.get(i);
            classInSvg.put(clazz, p);
            i++;
        }
        return classInSvg;
    }

    private static String getColorForPath(EdgeType type) {
        if (type.equals(EdgeType.EXTENDS)) {
            return EXTENDS_COLOR;
        } else if (type.equals(EdgeType.IMPLEMENTS)) {
            return IMPLEMENTS_COLOR;
        } else {
            return CONTAINS_COLOR;
        }
    }


    private static String getArrowHead(EdgeType type) {
        if (type.equals(EdgeType.CONTAINS)) {
            return CONTAINS_ARROW;
        } else {
            return NON_CONTAINS_ARROW;
        }
    }

    /**
     *
     */
    private static double getRandomDivider() {
        double random = Math.random();
        if (random <= 0.25) {
            return 4.0;
        } else if (random <= 0.5) {
            return 5.0;
        } else if (random <= 0.75) {
            return 6.0;
        } else {
            return 7.0;
        }
    }


    private static List<Point> getCoordinates(final int noOfClass) {
        List<Point> points = new ArrayList<>();
        int multiplier = 100;
        final int centerXY = noOfClass * multiplier + 50;
        final int radius = noOfClass * RADIUS_OF_CLASS_DIAGRAM;
        double increment = 360.0 / noOfClass;
        double theta = 0;

        while (theta < 360) {
            double newX = centerXY + (radius * Math.cos(Math.toRadians(theta)));
            double newY = centerXY + (radius * Math.sin(Math.toRadians(theta)));
            points.add(new Point(newX, newY));
            theta = theta + increment;
        }
        return points;
    }


}
