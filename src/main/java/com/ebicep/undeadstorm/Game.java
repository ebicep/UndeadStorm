package com.ebicep.undeadstorm;

import com.ebicep.undeadstorm.MiniGames.DinoGame.DinoGame;
import com.ebicep.undeadstorm.MiniGames.PongGame.Pong;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class Game extends JComponent implements KeyListener, MouseListener, MouseMotionListener {

    private static final int width = 1500;
    private static final int height = 750;

    private static Map MAP = new Map(width, height);

    public static Map getMap() {
        return MAP;
    }

    private Leaderboard leaderboard = new Leaderboard();

    private Leaderboard getLeaderboard() {
        return leaderboard;
    }

    private ImageIcon gameBackground = new ImageIcon(Game.class.getResource("/images/gameStart.png"));
    private ImageIcon playSelected = new ImageIcon(Game.class.getResource("/images/playSelected.png"));
    private ImageIcon leaderboardSelected = new ImageIcon(Game.class.getResource("/images/leaderboardSelected.png"));
    private ImageIcon exitSelected = new ImageIcon(Game.class.getResource("/images/exitSelected.png"));
    private ImageIcon leaderboardScreen = new ImageIcon(Game.class.getResource("/images/leaderboard2.png"));

    private Rectangle playRect = new Rectangle(124, 302, 326, 188);
    private Rectangle leaderboardRect = new Rectangle(660, 310, 780, 160);
    private Rectangle exitRect = new Rectangle(515, 560, 255, 155);

    private boolean inMenu = true;

    private boolean onPlay = false;
    private boolean onLeaderBoard = false;
    private boolean onExit = false;

    private boolean readBoard = true;

    private boolean pressedPlay = false;
    private boolean pressedLeaderboard = false;
    private boolean pressedExit = false;

    private static int[] soundEffects;
    private GameAudio clips;
    private URL backgroundMusicURL;
    private URL bossMusicURL;
    private Clip backgroundClip;
    private Clip bossClip;
    private static int[] music;

    private boolean playMenuSound = true;
    private boolean playClickedSound = true;

    public static void changeSoundEffect(int i) {
        soundEffects[i] = 1;
    }

    private static boolean playBackgroundMusic = true;
    private static boolean bossLevel = false;

    public static void setPlayBackgroundMusic(boolean playBackgroundMusic) {
        Game.playBackgroundMusic = playBackgroundMusic;
    }

    public static void setBossLevel(boolean bossLevel) {
        Game.bossLevel = bossLevel;
    }

    private LoadingScreen loadingScreen;
    private boolean doneLoading = false;
    private long nextLoad;

    private int miniGameType = (int) (Math.random() * 2);
    private Pong pong = new Pong();
    private DinoGame dinoGame = new DinoGame();
    private long future;

    JFrame gui;
    public Game() {
        gui = new JFrame(); //This makes the gui box
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Makes sure program can close
        gui.setTitle("UndeadStorm"); //This is the title of the game, you can change it
        gui.setPreferredSize(new Dimension(width + 5, height + 30)); //Setting the size for gui
        gui.setResizable(false); //Makes it sso the gui cant be resized
        gui.getContentPane().add(this); //Adding this class to the gui

        /*If after you finish everything, you can declare your buttons or other things
         *at this spot. AFTER gui.getContentPanaae().add(this) and BEFORE gui.pack();
         */

        gui.pack(); //Packs everything together
        gui.setLocation(210,150); //Makes so the gui opens in the center of screen - FALSE
        gui.setVisible(true); //Makes the gui visible
        gui.addKeyListener(this);//stating that this object will listen to the keyboard
        gui.addMouseListener(this); //stating that this object will listen to the Mouse
        gui.addMouseMotionListener(this); //stating that this object will acknowledge when the Mouse moves

        gui.addKeyListener(MAP);//stating that this object will listen to the keyboard
        gui.addMouseListener(MAP); //stating that this object will listen to the Mouse
        gui.addMouseMotionListener(MAP); //stating that this object will acknowledge when the Mouse moves



        soundEffects = new int[11];
        music = new int[2];
        clips = new GameAudio();
        try {
            backgroundMusicURL = getClass().getResource("/sounds/backgroundMusic.wav");
            bossMusicURL = getClass().getResource("/sounds/bossMusic.wav");
            backgroundClip = AudioSystem.getClip();
            bossClip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(backgroundMusicURL);
            AudioInputStream ais2 = AudioSystem.getAudioInputStream(bossMusicURL);
            backgroundClip.open(ais);
            bossClip.open(ais2);

        } catch(Exception e) {
            e.printStackTrace();
        }

        loadingScreen = new LoadingScreen();
        nextLoad = System.currentTimeMillis() + 8500;
        future = System.currentTimeMillis() + 60000;
    }

    public void paintComponent(Graphics g) {
        if (inMenu) {
            g.drawImage(gameBackground.getImage(), 0, 0, 1500, 750, null);
            if (playRect.contains(MouseInfo.getPointerInfo().getLocation().getX() - 210, MouseInfo.getPointerInfo().getLocation().getY() - 170)) {
                g.drawImage(playSelected.getImage(), 0, 0, 1500, 750, null);
                onPlay = true;
            } else if (leaderboardRect.contains(MouseInfo.getPointerInfo().getLocation().getX() - 210, MouseInfo.getPointerInfo().getLocation().getY() - 170)) {
                g.drawImage(leaderboardSelected.getImage(), 0, 0, 1500, 750, null);
                onLeaderBoard = true;
            } else if (exitRect.contains(MouseInfo.getPointerInfo().getLocation().getX() - 210, MouseInfo.getPointerInfo().getLocation().getY() - 170)) {
                g.drawImage(exitSelected.getImage(), 0, 0, 1500, 750, null);
                onExit = true;
            } else {
                onPlay = false;
                onLeaderBoard = false;
                onExit = false;
            }
        }

        if (pressedPlay) {
            if (!doneLoading) {
                if (miniGameType == 0) {
                    g.setColor(Color.black);
                    g.fillRect(0, 0, 1500, 750);
                    pong.drawSelf(g);
                } else if (miniGameType == 1) {
                    g.setColor(Color.white);
                    g.fillRect(0, 0, 1500, 750);
                    dinoGame.drawSelf(g);
                }
                if (System.currentTimeMillis() > nextLoad) {
                    loadingScreen.load();
                    nextLoad = System.currentTimeMillis() + 8500;
                }
                loadingScreen.drawSelf(g);
                if (System.currentTimeMillis() > future)
                    doneLoading = true;
            } else
                MAP.paintComponent(g);
        } else if (pressedLeaderboard) {
            g.drawImage(leaderboardScreen.getImage(), 0, 0, 1500, 750, null);
            if (readBoard) {
                try {
                    FileInputStream file = new FileInputStream("Leaderboard.obj");
                    ObjectInputStream inputStream = new ObjectInputStream(file);
                    leaderboard = (Leaderboard) inputStream.readObject();
                } catch (ClassNotFoundException | IOException ignored) {
                }
                leaderboard.clearUsers();
                readBoard = false;
            }
            leaderboard.drawSelf(g);
        }


    }

    public void loop() {
        if(gui.getX() != 210 || gui.getY() != 150)
            gui.setLocation(210,150);
        if (playMenuSound) {
            soundEffects[0] = 1;
            playMenuSound = false;
        }
        if (pressedPlay) {
            if (!doneLoading) {
                if (miniGameType == 0)
                    pong.act();
                else if (miniGameType == 1)
                    dinoGame.act();
            } else {
                clips.stop(0);
                if(playBackgroundMusic) {
                    music[0] = 1;
                    playBackgroundMusic = false;
                } else if(bossLevel) {
                    music[1] = 1;
                    bossLevel = false;
                }
                if (MAP.act()) {
                    String name = JOptionPane.showInputDialog("Enter Name");
                    while (name != null && name.length() > 13) {
                        name = JOptionPane.showInputDialog("Name must be shorter than 14 characters!");
                    }
                    try {
                        FileInputStream file = new FileInputStream("Leaderboard.obj");
                        ObjectInputStream inputStream = new ObjectInputStream(file);
                        leaderboard = (Leaderboard) inputStream.readObject();
                    } catch (ClassNotFoundException | IOException ignored) {
                    }
                    leaderboard.addUser(new User(name, MAP.getTotalKills()));
                    leaderboard.clearUsers();
                    try {
                        FileOutputStream file = new FileOutputStream("Leaderboard.obj");
                        ObjectOutputStream outStream = new ObjectOutputStream(file);
                        outStream.writeObject(leaderboard);
                    } catch (FileNotFoundException f) {
                        System.out.println(f);
                    } catch (IOException i) {
                        System.out.println("Error when constructing outStream");
                    }
                    MAP.reset();
                    inMenu = false;
                    pressedPlay = false;
                    pressedLeaderboard = true;
                    readBoard = true;
                    playBackgroundMusic = true;
                }
            }
        } else if (pressedExit) {
            System.exit(0);
        }

        //Do not write below this
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (!doneLoading)
            dinoGame.getPlayer().keyPressed(e);
        if (e.getKeyCode() == 27) {
            if (pressedLeaderboard) {
                pressedLeaderboard = false;
                inMenu = true;
            }
        } else if (!doneLoading && e.getKeyCode() == 10) {
            doneLoading = true;
        }
        if(e.getKeyCode() == 75 && doneLoading)
            getMap().getPlayer().addHealth(-getMap().getPlayer().getMaxHealth());
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
        if (pressedLeaderboard)
            leaderboard.mouseClicked(e);
        int button = e.getButton();
        if (button == 1) {
            if (playClickedSound) {
                soundEffects[1] = 1;
                playClickedSound = false;
            }
            if (onPlay) {
                inMenu = false;
                pressedPlay = true;
                onPlay = false;
            }
            if (onLeaderBoard) {
                inMenu = false;
                pressedLeaderboard = true;
                readBoard = true;
                onLeaderBoard = false;
            }
            if (onExit)
                pressedExit = true;
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

    public void start(final int ticks) {
        Thread gameThread = new Thread(() -> {
            while (true) {
                loop();
                try {
                    Thread.sleep(1000 / ticks);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.start();

        Thread soundEffectMusic = new Thread(() -> {
            while (true) {
                for (int i = 0; i < soundEffects.length; i++) {
                    if (soundEffects[i] == 1) {
                        if (i == 10) {
                            clips.loop(i);
                        } else {
                            clips.play(i);
                        }
                        soundEffects[i] = 0;
                    } else if (i == 7 && soundEffects[i] == 0 && MAP.getPlayer().getHealth() > 20)
                        clips.stop(i);
                    else if (i == 10 && soundEffects[i] == 0 && !MAP.getPlayer().isAutoFire())
                        clips.stop(i);
                }
                try {
                    Thread.sleep(1000 / ticks);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        soundEffectMusic.start();

        Thread backgroundMusic = new Thread(() -> {
            while (true) {
                if(music[0] == 1) {
                    backgroundClip.start();
                    music[0] = 0;
                }
                else if(music[1] == 1) {
                    backgroundClip.stop();
                    bossClip.start();
                    music[1] = 0;
                }
                if(inMenu || pressedLeaderboard) {
                    backgroundClip.stop();
                    bossClip.stop();
                }
                try {
                    Thread.sleep(1000 / ticks);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        backgroundMusic.start();
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        String currentPath=Game.class
                .getProtectionDomain()
                .getCodeSource().getLocation()
                .toURI().getPath()
                .replace('/', File.separator.charAt(0)).substring(1);
        if(args.length==0 && Runtime.getRuntime().maxMemory()/1024/1024<980) {
            Runtime.getRuntime().exec("java -Xmx1024m -jar "+currentPath+" restart");
            return;
        }
        Game g = new Game();
        g.start(60);
    }

}



