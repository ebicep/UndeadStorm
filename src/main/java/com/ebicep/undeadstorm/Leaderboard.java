package com.ebicep.undeadstorm;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Leaderboard implements Serializable, MouseListener {

    private ArrayList<User> users;
    private int numberOfPages;
    private int page = 1;

    private boolean nameSorted = false;
    private boolean killsSorted = true;
    private boolean dateSorted = false;

    private Rectangle nameArrow = new Rectangle(545, 165, 40, 30);
    private Rectangle killsArrow = new Rectangle(826, 165, 40, 30);
    private Rectangle dateArrow = new Rectangle(1145, 165, 40, 30);

    private boolean nameClicked = false;
    private boolean killsClicked = true;
    private boolean dateClicked = false;

    private Polygon right = new Polygon(new int[]{1350, 1350, 1400}, new int[]{680, 720, 700}, 3);
    private Polygon left = new Polygon(new int[]{150, 150, 100}, new int[]{680, 720, 700}, 3);

    public Leaderboard() {
        users = new ArrayList<>();
        numberOfPages = users.size() / 10 + 1;
        sortKills(false);
    }

    public void drawSelf(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Helvetica", Font.BOLD, 40));
        Color brown = new Color(82, 53, 6);
        g2d.setColor(brown);
        g2d.fillRect(200, 150, 1100, 575);

        g2d.setColor(Color.gray);
        g2d.setStroke(new BasicStroke(4.5f));
        int offset = 0;
        for (int i = 0; i < 11; i++) {
            g2d.setColor(Color.gray);
            g2d.drawLine(200, 150 + offset, 1300, 150 + offset);
            offset += 52.27;
        }

        g2d.setColor(Color.white);
        offset = 0;

        int counter = page * 10 - 9;
        for (int i = 0; i < 10; i++) {
            g2d.drawString(counter + ".", 260, 244f + offset);
            offset += 52.27;
            counter++;
        }

        g2d.setFont(new Font("Helvetica", Font.BOLD, 35));
        g2d.drawString("RANK #", 220, 190);
        g2d.drawString("NAME", 450, 190);
        g2d.drawString("KILLS", 725, 190);
        g2d.drawString("DATE", 1050, 190);

        g2d.setColor(Color.white);
        if (nameClicked)
            g2d.setColor(Color.black);
        g2d.drawString("\u2195", 555, 187.5f);
        g2d.setColor(Color.white);
        if (killsClicked)
            g2d.setColor(Color.black);
        g2d.drawString("\u2195", 830, 187.5f);
        g2d.setColor(Color.white);
        if (dateClicked)
            g2d.setColor(Color.black);
        g2d.drawString("\u2195", 1147.5f, 187.5f);

        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(10f));
        g2d.drawRect(200, 150, 1100, 575);


        g2d.setColor(Color.white);
        offset = 0;
        for (User u : users) {
            g2d.drawString(u.getName(), 450, 244f + offset);
            g2d.drawString(u.getKills() + "", 725, 244f + offset);
            g2d.drawString(u.getDate(), 1050, 244f + offset);
            offset += 52.27;
        }

        /*
        g2d.setColor(Color.white);
        if (page < numberOfPages)
            g2d.fillPolygon(right);
        if (page > 1)
            g2d.fillPolygon(left);

        //TODO fix bug where players dont sort correcltly ( kills ) on other pages than 1

        offset = 0;
        if (users.size() < 11) {
            for (User u : users) {
                g2d.drawString(u.getName(), 450, 244f + offset);
                g2d.drawString(u.getKills() + "", 725, 244f + offset);
                g2d.drawString(u.getDate(), 1050, 244f + offset);
                offset += 52.27;
            }
        } else {
            int beginningUser = page * 10 - 9;
            int endUser = Math.min(users.size(), beginningUser + 10);
            for (int i = beginningUser; i < endUser; i++) {
                User u = users.get(i);
                g2d.drawString(u.getName(), 450, 244f + offset);
                g2d.drawString(u.getKills() + "", 725, 244f + offset);
                g2d.drawString(u.getDate(), 1050, 244f + offset);
                offset += 52.27;
            }
        }

         */


    }

    public void printUsers() {
        for (User u : users) {
            System.out.println(u);
        }
    }

    public void clearUsers() {
        sortKills(false);
        if (users.size() > 10) {
            for (int i = 10; i < users.size(); i++) {
                users.remove(i);
                i--;
            }
        }
    }

    public void addUser(User toBeAdded) {
        users.add(toBeAdded);
        numberOfPages = users.size() / 10 + 1;
    }

    public void sortKills(boolean reversed) {
        nameSorted = false;
        dateSorted = false;
        if (!reversed)
            users.sort(Comparator.comparing(User::getKills).reversed());
        else
            users.sort(Comparator.comparing(User::getKills));

    }

    public void sortName(boolean reversed) {
        killsSorted = false;
        dateSorted = false;
        if (!reversed) {
            users.sort(Comparator.comparing(User::getName));
            nameSorted = true;
        } else {
            users.sort(Comparator.comparing(User::getName).reversed());
            nameSorted = false;
        }
    }

    public void sortDate(boolean reversed) {
        nameSorted = false;
        killsSorted = false;
        if (!reversed)
            users.sort(Comparator.comparing(User::getDate));
        else
            users.sort(Comparator.comparing(User::getDate).reversed());

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 1) {
            if (nameArrow.contains(MouseInfo.getPointerInfo().getLocation().getX() - 210, MouseInfo.getPointerInfo().getLocation().getY() - 170)) {
                sortName(!nameSorted);
                nameSorted = !nameSorted;
                nameClicked = true;
                killsClicked = false;
                dateClicked = false;
            } else if (killsArrow.contains(MouseInfo.getPointerInfo().getLocation().getX() - 210, MouseInfo.getPointerInfo().getLocation().getY() - 170)) {
                sortKills(!killsSorted);
                killsSorted = !killsSorted;
                nameClicked = false;
                killsClicked = true;
                dateClicked = false;
            } else if (dateArrow.contains(MouseInfo.getPointerInfo().getLocation().getX() - 210, MouseInfo.getPointerInfo().getLocation().getY() - 170)) {
                sortDate(!dateSorted);
                dateSorted = !dateSorted;
                nameClicked = false;
                killsClicked = false;
                dateClicked = true;
            }
            /*else if (right.contains(MouseInfo.getPointerInfo().getLocation().getX() - 210, MouseInfo.getPointerInfo().getLocation().getY() - 170)) {
                if (page + 1 <= numberOfPages) {
                    page++;
                }
            } else if (left.contains(MouseInfo.getPointerInfo().getLocation().getX() - 210, MouseInfo.getPointerInfo().getLocation().getY() - 170)) {
                if (page > 0) {
                    page--;
                }
            }

             */
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
