/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.K;
import static javafx.scene.input.KeyCode.V;
import javax.imageio.ImageIO;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.swing.ImageIcon;

/**
 *
 * @author pearl
 */
/**
 *
 * @author pearl
 */
public class Home_User extends javax.swing.JFrame {

    /**
     * Index for panel selection: 0 - Welcome; 1 - Melody; 2 - Rhythm; 3 -
     * Volume.
     */
    int panelIndex;
    /**
     * X position on screen - used to enable drag-moving
     */
    int pressX;
    /**
     * Y posotion on screen - used to enable drag-moving
     */
    int pressY;
    /**
     * Flag to distinguish if the pitch button A is pressed: 0 - not pressed, 1
     * - pressed.
     */
    int aFlag;
    /**
     * Flag to distinguish if the pitch button A# is pressed: 0 - not pressed, 1
     * - pressed.
     */
    int a_Flag;
    /**
     * Flag to distinguish if the pitch button B is pressed: 0 - not pressed, 1
     * - pressed.
     */
    int bFlag;
    /**
     * Flag to distinguish if the pitch button C is pressed: 0 - not pressed, 1
     * - pressed.
     */
    int cFlag;
    /**
     * Flag to distinguish if the pitch button C# is pressed: 0 - not pressed, 1
     * - pressed.
     */
    int c_Flag;
    /**
     * Flag to distinguish if the pitch button D is pressed: 0 - not pressed, 1
     * - pressed.
     */
    int dFlag;
    /**
     * Flag to distinguish if the pitch button D# is pressed: 0 - not pressed, 1
     * - pressed.
     */
    int d_Flag;
    /**
     * Flag to distinguish if the pitch button E is pressed: 0 - not pressed, 1
     * - pressed.
     */
    int eFlag;
    /**
     * Flag to distinguish if the pitch button F is pressed: 0 - not pressed, 1
     * - pressed.
     */
    int fFlag;
    /**
     * Flag to distinguish if the pitch button F# is pressed: 0 - not pressed, 1
     * - pressed.
     */
    int f_Flag;
    /**
     * Flag to distinguish if the pitch button G is pressed: 0 - not pressed, 1
     * - pressed.
     */
    int gFlag;
    /**
     * Flag to distinguish if the pitch button G# is pressed: 0 - not pressed, 1
     * - pressed.
     */
    int g_Flag;

    /**
     * The shifting steps from the first pitch in the original row to the first
     * pitch C in chromatic order.
     */
    int firstIndex;
    /**
     * The output string for Clock Output.
     */
    String clockstr;    //About Clock
    /**
     * The output string for Matrix Output.
     */
    String matrixstr;   //About the Matrix
    /**
     * The output string for all the log output.
     */
    String logstr;      //Other output information
    /**
     * The output string for Tip output in TipFrame.
     */
    String tip;         //Tip information
    /**
     * The user input pitch series represented by pitch name.
     */
    ArrayList<String> origin;
    /**
     * The original row (by converting the pitch name in "origin" into numbers
     * according to the Pitch Clock) used to generate the 12-tone Matrix.
     */
    int[] pitchClass;
    /**
     * The Map storing the clock: to match Pitch names and their assigned value.
     */
    HashMap<String, Integer> clockorder;
    /**
     * The 12-tone Matrix (All the algorithm implemetations are related to this
     * matrix).
     */
    Matrix matrix;
    /**
     * The MusicPlayer handles all MIDI features: MIDI sequence organising,
     * track generating, MIDI File writing and playing.
     */
    MusicPlayer musicPlayer;
    /**
     * If "Melody Change" has been clicked.
     */
    int mClicked;
    /**
     * The index of clicked rhythm button, minuet duration row - 1; minuet time
     * point - 2; 12-tone melody with time point - 3.
     */
    int rhyClicked;
    /**
     * The index of clicked volume button, minuet volume - 1; 12-tone melody
     * with time point rhythm and volume serialism - 2.
     */
    int volClicked;
    /**
     * If "Play" (on Melody Panel) clicked, the icon changes to rectangle and
     * tag changes to "Stop", else icon changes to triangle and tag changes to
     * "Play".
     */
    int mPlayClicked;
    /**
     * If "Play" (on Rhythm Panel) clicked, the icon changes to rectangle and
     * tag changes to "Stop", else icon changes to triangle and tag changes to
     * "Play".
     */
    int rPlayClicked;
    /**
     * If "Play" (on Volume Panel) clicked, the icon changes to rectangle and
     * tag changes to "Stop", else icon changes to triangle and tag changes to
     * "Play".
     */
    int vPlayClicked;

    /**
     * Creates new form Home_User
     */
    public Home_User() {
        initComponents();
        jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();

        jPanel3.add(welcome);
        jPanel3.repaint();
        jPanel3.revalidate();

        panelIndex = 0;
        musicPlayer = new MusicPlayer();

        musicPlayer.setUpBGM(panelIndex);

        aFlag = 0;
        a_Flag = 0;
        bFlag = 0;
        cFlag = 0;
        c_Flag = 0;
        dFlag = 0;
        d_Flag = 0;
        eFlag = 0;
        fFlag = 0;
        f_Flag = 0;
        gFlag = 0;
        g_Flag = 0;
        clockstr = "";
        matrixstr = "";
        logstr = "Original Chromatic Pitch Order:\n";
        logstr += "0  1   2  3  4 5  6  7  8   9 10  11\n";
        logstr += "C C# D D# E F F# G G# A A# B\n";
        origin = new ArrayList<String>(); // The original row (user input)
        matrix = new Matrix();

        mClicked = 0;
        rhyClicked = 0;
        volClicked = 0;

        mPlayClicked = 0;
        rPlayClicked = 0;
        vPlayClicked = 0;

    }

    /**
     * Modify the lilypond file (.ly) by deleting "\midi {}" line.
     *
     * @param filename the original .ly filename.
     * @param newFilename the new .ly filename (without "\midi {}" line).
     */
    private String lyFileChange(String filename, String newFilename, String title) throws IOException {
        String tip = null;
        int count = 0;
        //read and write new file by jump out the "  \midi {}" line.
        BufferedReader br = new BufferedReader(new FileReader(filename));
        File UIFile = new File(newFilename);
        // if File doesn't exists, then create it
        if (!UIFile.exists()) {
            UIFile.createNewFile();
        }
        FileWriter fw = new FileWriter(UIFile.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        String line;
        while ((line = br.readLine()) != null) {
            count++;
            if (count == 3) {
                bw.write("\n");
                bw.write("\\header {\n");
                bw.write("title = \\markup { \"" + title + "\" }\n");
                bw.write("}");
            }
            if (line.matches(".*midi.*")) {
                continue;
            }
            bw.write(line + "\n");
        }
        bw.flush();
        bw.close();
        br.close();
        tip = "Success.";
        return tip;
    }

    /**
     * Get the current orinal row, save the pitch classes pressed by buttons
     * into the array "origin".
     */
    private void reloadOrgTune() {
        if (origin.isEmpty()) {
            orgTune.setText("Press Buttons to Enter the Original Tunes Series");
            orgTune.setBackground(Color.LIGHT_GRAY);
            orgTune.setFont(new Font("Tahoma", Font.PLAIN, 24));
        } else {
            String s = "";
            for (int i = 0; i < origin.size(); i++) {
                s += origin.get(i);
            }
            orgTune.setText(s);
            orgTune.setBackground(Color.YELLOW);
            orgTune.setFont(new Font("Tahoma", Font.BOLD, 28));
        }
    }

    /**
     * Generate the Pitch Clock, by getting the first pitch class in original
     * row, assign it to 0 and the rest pitch class in chromatic order. Sort the
     * Clock map and print in acsending order.
     */
    private void setClock() {
        //Generate the Clock Diagram model--The Clock Map
        String[] tones = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        clockorder = new HashMap<String, Integer>();
        clockorder.put(origin.get(0), 0);
        firstIndex = Arrays.asList(tones).indexOf(origin.get(0)); //Arrays.binarysearch cannot always work on unsorted array. 
        logstr += "The firstIndex is: " + firstIndex + "\n";
        for (int i = 0; i < 12; i++) {
            if (i - firstIndex >= 0) {
                clockorder.put(tones[i], i - firstIndex);
            } else {
                clockorder.put(tones[i], i - firstIndex + 12);
            }
        }
        clockstr += "&nbsp;The Clock Map:<br>";
        //Sort clockorder by value
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(clockorder.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                    Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<String, Integer> sortedClock = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedClock.put(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Integer> entry : sortedClock.entrySet()) {
            clockstr += "&nbsp;Key = " + entry.getKey() + ", Value = " + entry.getValue() + "<br>";
        }
    }

    /**
     * Generate the 12-tone matrix and print it out.
     */
    private void setMatrix() {
        //Generate the Original Pitch Class
        pitchClass = new int[12];
        for (int i = 0; i < 12; i++) {
            pitchClass[i] = clockorder.get(origin.get(i));
        }
        logstr += "The original row pitch class:\n";
        for (int i = 0; i < 11; i++) {
            logstr += pitchClass[i] + ".";
        }
        logstr += pitchClass[11] + "\n";

        //Generate the Matrix
        matrixstr += "&nbsp;The Matrix:<br>";
        matrix = new Matrix(pitchClass);
        for (int i = 0; i < 12; i++) {
            matrixstr += "&nbsp;";
            for (int j = 0; j < 12; j++) {
                matrixstr += matrix.matrix[i][j] + "&emsp;";
            }
            matrixstr += "<br>";
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logFrame = new javax.swing.JFrame();
        logPanel = new javax.swing.JPanel();
        output = new javax.swing.JLabel();
        log = new javax.swing.JLabel();
        matx = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        logArea = new javax.swing.JTextArea();
        tipFrame = new javax.swing.JFrame();
        tipPanel = new javax.swing.JPanel();
        tipLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        mBtn = new javax.swing.JButton();
        rBtn = new javax.swing.JButton();
        vBtn = new javax.swing.JButton();
        wBtn = new javax.swing.JButton();
        exitBtn = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        welcome = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        mPanel = new javax.swing.JPanel();
        mStave = new javax.swing.JLabel();
        mPlay = new javax.swing.JLabel();
        mSaveMP3 = new javax.swing.JLabel();
        melodyBtn = new javax.swing.JButton();
        f_Btn = new javax.swing.JLabel();
        gBtn = new javax.swing.JLabel();
        g_Btn = new javax.swing.JLabel();
        aBtn = new javax.swing.JLabel();
        a_Btn = new javax.swing.JLabel();
        bBtn = new javax.swing.JLabel();
        dBtn = new javax.swing.JLabel();
        d_Btn = new javax.swing.JLabel();
        eBtn = new javax.swing.JLabel();
        fBtn = new javax.swing.JLabel();
        cBtn = new javax.swing.JLabel();
        c_Btn = new javax.swing.JLabel();
        orgTune = new javax.swing.JLabel();
        mClear = new javax.swing.JButton();
        rPanel = new javax.swing.JPanel();
        rStave = new javax.swing.JLabel();
        rPlay = new javax.swing.JLabel();
        rSaveMP3 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        dur2 = new javax.swing.JTextField();
        dur3 = new javax.swing.JTextField();
        dur1 = new javax.swing.JTextField();
        minuetTPBtn = new javax.swing.JButton();
        minuetDRBtn = new javax.swing.JButton();
        melodyTPBtn = new javax.swing.JButton();
        vPanel = new javax.swing.JPanel();
        vStave = new javax.swing.JLabel();
        vPlay = new javax.swing.JLabel();
        vSaveMP3 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        vol1 = new javax.swing.JTextField();
        vol2 = new javax.swing.JTextField();
        vol3 = new javax.swing.JTextField();
        VAndRAndMBtn = new javax.swing.JButton();
        minuetVolBtn = new javax.swing.JButton();

        logFrame.setMinimumSize(new java.awt.Dimension(960, 880));
        logFrame.setSize(new java.awt.Dimension(960, 880));

        logPanel.setBackground(new java.awt.Color(166, 136, 98));

        output.setFont(new java.awt.Font("Comic Sans MS", 1, 30)); // NOI18N
        output.setForeground(new java.awt.Color(255, 204, 0));
        output.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        output.setText("Output");

        log.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        log.setForeground(new java.awt.Color(255, 204, 0));
        log.setText("Clock");
        log.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        log.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 51), new java.awt.Color(255, 204, 0), new java.awt.Color(255, 204, 0)));

        matx.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        matx.setForeground(new java.awt.Color(255, 204, 0));
        matx.setText("Matrix");
        matx.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        matx.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), new java.awt.Color(255, 204, 0), new java.awt.Color(255, 204, 0)));

        logArea.setBackground(new java.awt.Color(166, 136, 98));
        logArea.setColumns(20);
        logArea.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        logArea.setForeground(new java.awt.Color(255, 204, 0));
        logArea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), new java.awt.Color(255, 204, 0), new java.awt.Color(255, 204, 0)));
        jScrollPane1.setViewportView(logArea);

        javax.swing.GroupLayout logPanelLayout = new javax.swing.GroupLayout(logPanel);
        logPanel.setLayout(logPanelLayout);
        logPanelLayout.setHorizontalGroup(
            logPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(output, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(logPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(logPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(logPanelLayout.createSequentialGroup()
                        .addComponent(log, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(matx, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(15, 15, 15))
        );
        logPanelLayout.setVerticalGroup(
            logPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(output, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(logPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(log, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(matx, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout logFrameLayout = new javax.swing.GroupLayout(logFrame.getContentPane());
        logFrame.getContentPane().setLayout(logFrameLayout);
        logFrameLayout.setHorizontalGroup(
            logFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        logFrameLayout.setVerticalGroup(
            logFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tipFrame.setBackground(new java.awt.Color(130, 113, 93));

        tipPanel.setBackground(new java.awt.Color(130, 113, 93));

        tipLabel.setBackground(new java.awt.Color(166, 136, 98));
        tipLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        tipLabel.setForeground(new java.awt.Color(255, 204, 0));
        tipLabel.setText("jLabel5");

        javax.swing.GroupLayout tipPanelLayout = new javax.swing.GroupLayout(tipPanel);
        tipPanel.setLayout(tipPanelLayout);
        tipPanelLayout.setHorizontalGroup(
            tipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tipPanelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(tipLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 1006, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );
        tipPanelLayout.setVerticalGroup(
            tipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tipPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(tipLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tipFrameLayout = new javax.swing.GroupLayout(tipFrame.getContentPane());
        tipFrame.getContentPane().setLayout(tipFrameLayout);
        tipFrameLayout.setHorizontalGroup(
            tipFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1106, Short.MAX_VALUE)
            .addGroup(tipFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(tipPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tipFrameLayout.setVerticalGroup(
            tipFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 285, Short.MAX_VALUE)
            .addGroup(tipFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(tipPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(193, 163, 79));

        jPanel2.setBackground(new java.awt.Color(196, 166, 80));
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel2MouseDragged(evt);
            }
        });
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel2MousePressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Jokerman", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(229, 220, 220));
        jLabel1.setText("<html><p style=\"line-height:30%; font-size:55pt\">Serial</p><p style=\"line-height:45%; font-size:48pt\"> Music</p><p style=\"line-height:40%; font-size:35pt\"> Maker</p></html>");

        mBtn.setBackground(new java.awt.Color(255, 204, 0));
        mBtn.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        mBtn.setForeground(new java.awt.Color(122, 122, 122));
        mBtn.setText("Melody");
        mBtn.setMaximumSize(new java.awt.Dimension(135, 40));
        mBtn.setMinimumSize(new java.awt.Dimension(135, 40));
        mBtn.setPreferredSize(new java.awt.Dimension(135, 40));
        mBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mBtnActionPerformed(evt);
            }
        });

        rBtn.setBackground(new java.awt.Color(255, 204, 0));
        rBtn.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        rBtn.setForeground(new java.awt.Color(122, 122, 122));
        rBtn.setText("Rhythm");
        rBtn.setMaximumSize(new java.awt.Dimension(135, 40));
        rBtn.setMinimumSize(new java.awt.Dimension(135, 40));
        rBtn.setPreferredSize(new java.awt.Dimension(135, 40));
        rBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rBtnActionPerformed(evt);
            }
        });

        vBtn.setBackground(new java.awt.Color(255, 204, 0));
        vBtn.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        vBtn.setForeground(new java.awt.Color(122, 122, 122));
        vBtn.setText("Volume");
        vBtn.setMaximumSize(new java.awt.Dimension(135, 40));
        vBtn.setMinimumSize(new java.awt.Dimension(135, 40));
        vBtn.setPreferredSize(new java.awt.Dimension(135, 40));
        vBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vBtnActionPerformed(evt);
            }
        });

        wBtn.setBackground(new java.awt.Color(255, 204, 0));
        wBtn.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        wBtn.setForeground(new java.awt.Color(122, 122, 122));
        wBtn.setText("Welcome");
        wBtn.setMaximumSize(new java.awt.Dimension(135, 40));
        wBtn.setMinimumSize(new java.awt.Dimension(135, 40));
        wBtn.setPreferredSize(new java.awt.Dimension(135, 40));
        wBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wBtnActionPerformed(evt);
            }
        });

        exitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Home/icons8_Close_Window_48.png"))); // NOI18N
        exitBtn.setText("jLabel3");
        exitBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(wBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(vBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                                .addComponent(rBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(mBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(wBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 51), new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 51), new java.awt.Color(255, 255, 51)));
        jPanel3.setPreferredSize(new java.awt.Dimension(865, 690));

        welcome.setBackground(new java.awt.Color(132, 115, 95));
        welcome.setMaximumSize(new java.awt.Dimension(865, 690));
        welcome.setMinimumSize(new java.awt.Dimension(865, 690));
        welcome.setPreferredSize(new java.awt.Dimension(865, 690));

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 51));
        jLabel2.setText("<html><div style='font-size:36;'>Welcome to Serial Music Maker! </div><br><p style='font-size:30;'>Here, you first need to provide your original 12-tone series in the Melody section, then you can see what pieces of music can be produced by variating the tone series (using Schoenberg's 12-tone theory), rhythm (using Babbitt's Duration Row System and Time Point System) and volume (Similar method as Time Point System applied).</p></html>");

        javax.swing.GroupLayout welcomeLayout = new javax.swing.GroupLayout(welcome);
        welcome.setLayout(welcomeLayout);
        welcomeLayout.setHorizontalGroup(
            welcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, welcomeLayout.createSequentialGroup()
                .addContainerGap(73, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 739, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
        welcomeLayout.setVerticalGroup(
            welcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(welcomeLayout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        mPanel.setBackground(new java.awt.Color(132, 115, 95));
        mPanel.setPreferredSize(new java.awt.Dimension(865, 690));

        mStave.setFont(new java.awt.Font("Comic Sans MS", 1, 30)); // NOI18N
        mStave.setForeground(new java.awt.Color(255, 204, 0));
        mStave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Home/icons8_Document_48.png"))); // NOI18N
        mStave.setText("Stave");
        mStave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mStaveMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mStaveMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mStaveMouseExited(evt);
            }
        });

        mPlay.setFont(new java.awt.Font("Comic Sans MS", 1, 30)); // NOI18N
        mPlay.setForeground(new java.awt.Color(255, 204, 0));
        mPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Home/icons8_Play_48.png"))); // NOI18N
        mPlay.setText("Play");
        mPlay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mPlayMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mPlayMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mPlayMouseExited(evt);
            }
        });

        mSaveMP3.setFont(new java.awt.Font("Comic Sans MS", 1, 30)); // NOI18N
        mSaveMP3.setForeground(new java.awt.Color(255, 204, 0));
        mSaveMP3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Home/icons8_Musical_48.png"))); // NOI18N
        mSaveMP3.setText("Save as MP3");
        mSaveMP3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mSaveMP3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mSaveMP3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mSaveMP3MouseExited(evt);
            }
        });

        melodyBtn.setBackground(new java.awt.Color(0, 153, 51));
        melodyBtn.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        melodyBtn.setForeground(new java.awt.Color(255, 255, 0));
        melodyBtn.setText("Change Melody");
        melodyBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                melodyBtnMouseClicked(evt);
            }
        });
        melodyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                melodyBtnActionPerformed(evt);
            }
        });

        f_Btn.setBackground(new java.awt.Color(255, 255, 51));
        f_Btn.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        f_Btn.setForeground(new java.awt.Color(239, 191, 0));
        f_Btn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        f_Btn.setText("F#");
        f_Btn.setOpaque(true);
        f_Btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f_BtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                f_BtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                f_BtnMouseExited(evt);
            }
        });

        gBtn.setBackground(new java.awt.Color(255, 255, 51));
        gBtn.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        gBtn.setForeground(new java.awt.Color(239, 191, 0));
        gBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gBtn.setText("G");
        gBtn.setOpaque(true);
        gBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                gBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                gBtnMouseExited(evt);
            }
        });

        g_Btn.setBackground(new java.awt.Color(255, 255, 51));
        g_Btn.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        g_Btn.setForeground(new java.awt.Color(239, 191, 0));
        g_Btn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        g_Btn.setText("G#");
        g_Btn.setOpaque(true);
        g_Btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                g_BtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                g_BtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                g_BtnMouseExited(evt);
            }
        });

        aBtn.setBackground(new java.awt.Color(255, 255, 51));
        aBtn.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        aBtn.setForeground(new java.awt.Color(239, 191, 0));
        aBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        aBtn.setText("A");
        aBtn.setOpaque(true);
        aBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                aBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                aBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                aBtnMouseExited(evt);
            }
        });

        a_Btn.setBackground(new java.awt.Color(255, 255, 51));
        a_Btn.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        a_Btn.setForeground(new java.awt.Color(239, 191, 0));
        a_Btn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        a_Btn.setText("A#");
        a_Btn.setOpaque(true);
        a_Btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                a_BtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                a_BtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                a_BtnMouseExited(evt);
            }
        });

        bBtn.setBackground(new java.awt.Color(255, 255, 51));
        bBtn.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        bBtn.setForeground(new java.awt.Color(239, 191, 0));
        bBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bBtn.setText("B");
        bBtn.setOpaque(true);
        bBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bBtnMouseExited(evt);
            }
        });

        dBtn.setBackground(new java.awt.Color(255, 255, 51));
        dBtn.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        dBtn.setForeground(new java.awt.Color(239, 191, 0));
        dBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dBtn.setText("D");
        dBtn.setOpaque(true);
        dBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                dBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                dBtnMouseExited(evt);
            }
        });

        d_Btn.setBackground(new java.awt.Color(255, 255, 51));
        d_Btn.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        d_Btn.setForeground(new java.awt.Color(239, 191, 0));
        d_Btn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        d_Btn.setText("D#");
        d_Btn.setOpaque(true);
        d_Btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                d_BtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                d_BtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                d_BtnMouseExited(evt);
            }
        });

        eBtn.setBackground(new java.awt.Color(255, 255, 51));
        eBtn.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        eBtn.setForeground(new java.awt.Color(239, 191, 0));
        eBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eBtn.setText("E");
        eBtn.setOpaque(true);
        eBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                eBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                eBtnMouseExited(evt);
            }
        });

        fBtn.setBackground(new java.awt.Color(255, 255, 51));
        fBtn.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        fBtn.setForeground(new java.awt.Color(239, 191, 0));
        fBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fBtn.setText("F");
        fBtn.setOpaque(true);
        fBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                fBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                fBtnMouseExited(evt);
            }
        });

        cBtn.setBackground(new java.awt.Color(255, 255, 0));
        cBtn.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        cBtn.setForeground(new java.awt.Color(239, 191, 0));
        cBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cBtn.setText("C");
        cBtn.setOpaque(true);
        cBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cBtnMouseExited(evt);
            }
        });

        c_Btn.setBackground(new java.awt.Color(255, 255, 0));
        c_Btn.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        c_Btn.setForeground(new java.awt.Color(239, 191, 0));
        c_Btn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        c_Btn.setText("C#");
        c_Btn.setOpaque(true);
        c_Btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                c_BtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                c_BtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                c_BtnMouseExited(evt);
            }
        });

        orgTune.setBackground(new java.awt.Color(204, 204, 204));
        orgTune.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        orgTune.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        orgTune.setText("Press Buttons to Enter the Original Tunes Series");
        orgTune.setOpaque(true);

        mClear.setBackground(new java.awt.Color(0, 153, 51));
        mClear.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
        mClear.setForeground(new java.awt.Color(255, 255, 0));
        mClear.setText("Clear");
        mClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mClearMouseClicked(evt);
            }
        });
        mClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mPanelLayout = new javax.swing.GroupLayout(mPanel);
        mPanel.setLayout(mPanelLayout);
        mPanelLayout.setHorizontalGroup(
            mPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mPanelLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(mStave, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95)
                .addComponent(mPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addComponent(mSaveMP3, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
            .addGroup(mPanelLayout.createSequentialGroup()
                .addGap(157, 157, 157)
                .addGroup(mPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(mPanelLayout.createSequentialGroup()
                        .addComponent(mClear, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(melodyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mPanelLayout.createSequentialGroup()
                        .addGroup(mPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mPanelLayout.createSequentialGroup()
                                .addGroup(mPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(f_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(37, 37, 37)
                                .addGroup(mPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(c_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(gBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36)
                                .addComponent(dBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(mPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(aBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(d_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(39, 39, 39)
                                .addGroup(mPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(eBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(a_Btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(mPanelLayout.createSequentialGroup()
                                .addGap(195, 195, 195)
                                .addComponent(g_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(mPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(orgTune, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mPanelLayout.setVerticalGroup(
            mPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mPanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(mPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mStave, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mSaveMP3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67)
                .addComponent(orgTune, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addGroup(mPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(d_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addGroup(mPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(g_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(aBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(a_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(f_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(gBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(54, 54, 54)
                .addGroup(mPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(melodyBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(101, Short.MAX_VALUE))
        );

        rPanel.setBackground(new java.awt.Color(132, 115, 95));
        rPanel.setPreferredSize(new java.awt.Dimension(865, 690));

        rStave.setFont(new java.awt.Font("Comic Sans MS", 1, 30)); // NOI18N
        rStave.setForeground(new java.awt.Color(255, 204, 0));
        rStave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Home/icons8_Document_48.png"))); // NOI18N
        rStave.setText("Stave");
        rStave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rStaveMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                rStaveMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                rStaveMouseExited(evt);
            }
        });

        rPlay.setFont(new java.awt.Font("Comic Sans MS", 1, 30)); // NOI18N
        rPlay.setForeground(new java.awt.Color(255, 204, 0));
        rPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Home/icons8_Play_48.png"))); // NOI18N
        rPlay.setText("Play");
        rPlay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rPlayMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                rPlayMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                rPlayMouseExited(evt);
            }
        });

        rSaveMP3.setFont(new java.awt.Font("Comic Sans MS", 1, 30)); // NOI18N
        rSaveMP3.setForeground(new java.awt.Color(255, 204, 0));
        rSaveMP3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Home/icons8_Musical_48.png"))); // NOI18N
        rSaveMP3.setText("Save as MP3");
        rSaveMP3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSaveMP3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                rSaveMP3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                rSaveMP3MouseExited(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 204, 51));
        jLabel25.setText("<html><div style='font-size: 30'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Enter Three Numbers From 1 to 12: </div><p style='font-size:26'>(Each number stands for the duration of a tone, 1=16<sup>th</sup> note)</p></html>");

        dur2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        dur2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        dur3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        dur3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        dur1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        dur1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        minuetTPBtn.setBackground(new java.awt.Color(0, 153, 51));
        minuetTPBtn.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        minuetTPBtn.setForeground(new java.awt.Color(255, 255, 0));
        minuetTPBtn.setText("Minuet with Time Point Rhythm");
        minuetTPBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minuetTPBtnMouseClicked(evt);
            }
        });

        minuetDRBtn.setBackground(new java.awt.Color(0, 153, 51));
        minuetDRBtn.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        minuetDRBtn.setForeground(new java.awt.Color(255, 255, 0));
        minuetDRBtn.setText("Minuet with Duration Row Rhythm");
        minuetDRBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minuetDRBtnActionPerformed(evt);
            }
        });

        melodyTPBtn.setBackground(new java.awt.Color(0, 153, 51));
        melodyTPBtn.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        melodyTPBtn.setForeground(new java.awt.Color(255, 255, 0));
        melodyTPBtn.setText("12-tone Melody with Time Point Rhythm");
        melodyTPBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                melodyTPBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout rPanelLayout = new javax.swing.GroupLayout(rPanel);
        rPanel.setLayout(rPanelLayout);
        rPanelLayout.setHorizontalGroup(
            rPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rPanelLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(rStave, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95)
                .addComponent(rPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rSaveMP3, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
            .addGroup(rPanelLayout.createSequentialGroup()
                .addGroup(rPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rPanelLayout.createSequentialGroup()
                        .addGap(203, 203, 203)
                        .addComponent(dur1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(134, 134, 134)
                        .addComponent(dur2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(134, 134, 134)
                        .addComponent(dur3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(rPanelLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addGroup(rPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(minuetTPBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(minuetDRBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(melodyTPBtn))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        rPanelLayout.setVerticalGroup(
            rPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rPanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(rPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rStave, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSaveMP3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(rPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dur3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dur2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dur1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(minuetDRBtn)
                .addGap(28, 28, 28)
                .addComponent(minuetTPBtn)
                .addGap(28, 28, 28)
                .addComponent(melodyTPBtn)
                .addContainerGap())
        );

        vPanel.setBackground(new java.awt.Color(132, 115, 95));
        vPanel.setPreferredSize(new java.awt.Dimension(865, 690));

        vStave.setFont(new java.awt.Font("Comic Sans MS", 1, 30)); // NOI18N
        vStave.setForeground(new java.awt.Color(255, 204, 0));
        vStave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Home/icons8_Document_48.png"))); // NOI18N
        vStave.setText("Stave");
        vStave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vStaveMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                vStaveMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                vStaveMouseExited(evt);
            }
        });

        vPlay.setFont(new java.awt.Font("Comic Sans MS", 1, 30)); // NOI18N
        vPlay.setForeground(new java.awt.Color(255, 204, 0));
        vPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Home/icons8_Play_48.png"))); // NOI18N
        vPlay.setText("Play");
        vPlay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vPlayMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                vPlayMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                vPlayMouseExited(evt);
            }
        });

        vSaveMP3.setFont(new java.awt.Font("Comic Sans MS", 1, 30)); // NOI18N
        vSaveMP3.setForeground(new java.awt.Color(255, 204, 0));
        vSaveMP3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Home/icons8_Musical_48.png"))); // NOI18N
        vSaveMP3.setText("Save as MP3");
        vSaveMP3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vSaveMP3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                vSaveMP3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                vSaveMP3MouseExited(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 204, 51));
        jLabel26.setText("<html><div style='font-size: 30'>Enter Three Numbers From 0 to 11: </div><p style='font-size:26'>(0 stands for ppppp, 11 stands for fffff)</p></html>");

        vol1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        vol1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        vol2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        vol2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        vol3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        vol3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        VAndRAndMBtn.setBackground(new java.awt.Color(0, 153, 51));
        VAndRAndMBtn.setFont(new java.awt.Font("Tahoma", 0, 34)); // NOI18N
        VAndRAndMBtn.setForeground(new java.awt.Color(255, 255, 0));
        VAndRAndMBtn.setText("12-tone Melody with Rhythm and Volume Serialism");
        VAndRAndMBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VAndRAndMBtnMouseClicked(evt);
            }
        });
        VAndRAndMBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VAndRAndMBtnActionPerformed(evt);
            }
        });

        minuetVolBtn.setBackground(new java.awt.Color(0, 153, 51));
        minuetVolBtn.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        minuetVolBtn.setForeground(new java.awt.Color(255, 255, 0));
        minuetVolBtn.setText("Minuet with Volume Serialism");
        minuetVolBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minuetVolBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout vPanelLayout = new javax.swing.GroupLayout(vPanel);
        vPanel.setLayout(vPanelLayout);
        vPanelLayout.setHorizontalGroup(
            vPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vPanelLayout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addComponent(vStave, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95)
                .addComponent(vPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94)
                .addComponent(vSaveMP3, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
            .addGroup(vPanelLayout.createSequentialGroup()
                .addGroup(vPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vPanelLayout.createSequentialGroup()
                        .addGap(203, 203, 203)
                        .addComponent(vol1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(134, 134, 134)
                        .addComponent(vol2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(134, 134, 134)
                        .addComponent(vol3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(vPanelLayout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(vPanelLayout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(minuetVolBtn))
                    .addGroup(vPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(VAndRAndMBtn)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        vPanelLayout.setVerticalGroup(
            vPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vPanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(vPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vSaveMP3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vStave, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addGroup(vPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vol1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vol2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vol3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addComponent(minuetVolBtn)
                .addGap(36, 36, 36)
                .addComponent(VAndRAndMBtn)
                .addGap(99, 99, 99))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 865, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(rPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(vPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(mPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(welcome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(rPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(vPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(mPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(welcome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 871, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Enable the window move when drag the jPanel2.
     *
     * @param evt
     */
    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - pressX, y - pressY);
    }//GEN-LAST:event_jPanel2MouseDragged

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed
        // TODO add your handling code here:
        pressX = evt.getX();
        pressY = evt.getY();
    }//GEN-LAST:event_jPanel2MousePressed

    /**
     * Show Melody Panel when press Melody Button
     *
     * @param evt
     */
    private void mBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mBtnActionPerformed
        // TODO add your handling code here:
        panelIndex = 1;
        musicPlayer.setUpBGM(panelIndex);
        //Removing Panel
        jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();
        //Adding Panel
        jPanel3.add(mPanel);
        jPanel3.repaint();
        jPanel3.revalidate();
    }//GEN-LAST:event_mBtnActionPerformed

    /**
     * Show Rhythm Panel when press Rhythm Button
     *
     * @param evt
     */
    private void rBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rBtnActionPerformed
        // TODO add your handling code here:
        panelIndex = 2;
        musicPlayer.setUpBGM(panelIndex);
        //Removing Panel
        jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();
        //Adding Panel
        jPanel3.add(rPanel);
        jPanel3.repaint();
        jPanel3.revalidate();
    }//GEN-LAST:event_rBtnActionPerformed

    /**
     * Show Volume Panel when press Volume Button
     *
     * @param evt
     */
    private void vBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vBtnActionPerformed
        // TODO add your handling code here:
        panelIndex = 3;
        musicPlayer.setUpBGM(panelIndex);
        //Removing Panel
        jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();
        //Adding Panel
        jPanel3.add(vPanel);
        jPanel3.repaint();
        jPanel3.revalidate();
    }//GEN-LAST:event_vBtnActionPerformed

    /**
     * Show Welcome Panel and play background music (Minuet) when press Welcome
     * Button.
     *
     * @param evt
     */
    private void wBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wBtnActionPerformed
        // TODO add your handling code here:
        panelIndex = 0;
        musicPlayer.setUpBGM(panelIndex);
        //Removing Panel
        jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();
        //Adding Panel
        jPanel3.add(welcome);
        jPanel3.repaint();
        jPanel3.revalidate();
    }//GEN-LAST:event_wBtnActionPerformed

    /**
     * Press exit button to close the window and exit the program.
     *
     * @param evt
     */
    private void exitBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitBtnMouseClicked
        System.exit(0);
    }//GEN-LAST:event_exitBtnMouseClicked

    private void VAndRAndMBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VAndRAndMBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VAndRAndMBtnActionPerformed

    private void cBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cBtnMouseEntered
        if (cFlag != 1) {
            cBtn.setBackground(new Color(255, 204, 0));
            cBtn.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_cBtnMouseEntered

    private void cBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cBtnMouseExited
        if (cFlag != 1) {
            cBtn.setBackground(new Color(255, 255, 0));
            cBtn.setForeground(new Color(255, 204, 0));
        }
    }//GEN-LAST:event_cBtnMouseExited

    private void c_BtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_c_BtnMouseEntered
        if (c_Flag != 1) {
            c_Btn.setBackground(new Color(255, 204, 0));
            c_Btn.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_c_BtnMouseEntered

    private void c_BtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_c_BtnMouseExited
        if (c_Flag != 1) {
            c_Btn.setBackground(new Color(255, 255, 0));
            c_Btn.setForeground(new Color(255, 204, 0));
        }
    }//GEN-LAST:event_c_BtnMouseExited

    private void dBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dBtnMouseEntered
        if (dFlag != 1) {
            dBtn.setBackground(new Color(255, 204, 0));
            dBtn.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_dBtnMouseEntered

    private void dBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dBtnMouseExited
        if (dFlag != 1) {
            dBtn.setBackground(new Color(255, 255, 0));
            dBtn.setForeground(new Color(255, 204, 0));
        }
    }//GEN-LAST:event_dBtnMouseExited

    private void d_BtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_BtnMouseEntered
        if (d_Flag != 1) {
            d_Btn.setBackground(new Color(255, 204, 0));
            d_Btn.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_d_BtnMouseEntered

    private void d_BtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_BtnMouseExited
        if (d_Flag != 1) {
            d_Btn.setBackground(new Color(255, 255, 0));
            d_Btn.setForeground(new Color(255, 204, 0));
        }
    }//GEN-LAST:event_d_BtnMouseExited

    private void eBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eBtnMouseEntered
        if (eFlag != 1) {
            eBtn.setBackground(new Color(255, 204, 0));
            eBtn.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_eBtnMouseEntered

    private void eBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eBtnMouseExited
        if (eFlag != 1) {
            eBtn.setBackground(new Color(255, 255, 0));
            eBtn.setForeground(new Color(255, 204, 0));
        }
    }//GEN-LAST:event_eBtnMouseExited

    private void fBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fBtnMouseEntered
        if (fFlag != 1) {
            fBtn.setBackground(new Color(255, 204, 0));
            fBtn.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_fBtnMouseEntered

    private void fBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fBtnMouseExited
        if (fFlag != 1) {
            fBtn.setBackground(new Color(255, 255, 0));
            fBtn.setForeground(new Color(255, 204, 0));
        }
    }//GEN-LAST:event_fBtnMouseExited

    private void f_BtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f_BtnMouseEntered
        if (f_Flag != 1) {
            f_Btn.setBackground(new Color(255, 204, 0));
            f_Btn.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_f_BtnMouseEntered

    private void f_BtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f_BtnMouseExited
        if (f_Flag != 1) {
            f_Btn.setBackground(new Color(255, 255, 0));
            f_Btn.setForeground(new Color(255, 204, 0));
        }
    }//GEN-LAST:event_f_BtnMouseExited

    private void gBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gBtnMouseEntered
        if (gFlag != 1) {
            gBtn.setBackground(new Color(255, 204, 0));
            gBtn.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_gBtnMouseEntered

    private void gBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gBtnMouseExited
        if (gFlag != 1) {
            gBtn.setBackground(new Color(255, 255, 0));
            gBtn.setForeground(new Color(255, 204, 0));
        }
    }//GEN-LAST:event_gBtnMouseExited

    private void g_BtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_g_BtnMouseEntered
        if (g_Flag != 1) {
            g_Btn.setBackground(new Color(255, 204, 0));
            g_Btn.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_g_BtnMouseEntered

    private void g_BtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_g_BtnMouseExited
        if (g_Flag != 1) {
            g_Btn.setBackground(new Color(255, 255, 0));
            g_Btn.setForeground(new Color(255, 204, 0));
        }
    }//GEN-LAST:event_g_BtnMouseExited

    private void aBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aBtnMouseEntered
        if (aFlag != 1) {
            aBtn.setBackground(new Color(255, 204, 0));
            aBtn.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_aBtnMouseEntered

    private void aBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aBtnMouseExited
        if (aFlag != 1) {
            aBtn.setBackground(new Color(255, 255, 0));
            aBtn.setForeground(new Color(255, 204, 0));
        }
    }//GEN-LAST:event_aBtnMouseExited

    private void a_BtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a_BtnMouseEntered
        if (a_Flag != 1) {
            a_Btn.setBackground(new Color(255, 204, 0));
            a_Btn.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_a_BtnMouseEntered

    private void a_BtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a_BtnMouseExited
        if (a_Flag != 1) {
            a_Btn.setBackground(new Color(255, 255, 0));
            a_Btn.setForeground(new Color(255, 204, 0));
        }
    }//GEN-LAST:event_a_BtnMouseExited

    private void bBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bBtnMouseEntered
        if (bFlag != 1) {
            bBtn.setBackground(new Color(255, 204, 0));
            bBtn.setForeground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_bBtnMouseEntered

    private void bBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bBtnMouseExited
        if (bFlag != 1) {
            bBtn.setBackground(new Color(255, 255, 0));
            bBtn.setForeground(new Color(255, 204, 0));
        }
    }//GEN-LAST:event_bBtnMouseExited

    private void cBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cBtnMouseClicked
        cBtn.setBackground(new Color(255, 204, 0));
        cBtn.setForeground(new Color(166, 136, 98));
        if (cFlag != 1) {
            origin.add("C");
            cFlag = 1;
            reloadOrgTune();
        }
    }//GEN-LAST:event_cBtnMouseClicked

    private void c_BtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_c_BtnMouseClicked
        c_Btn.setBackground(new Color(255, 204, 0));
        c_Btn.setForeground(new Color(166, 136, 98));
        if (c_Flag != 1) {
            origin.add("C#");
            c_Flag = 1;
            reloadOrgTune();
        }
    }//GEN-LAST:event_c_BtnMouseClicked

    private void dBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dBtnMouseClicked
        dBtn.setBackground(new Color(255, 204, 0));
        dBtn.setForeground(new Color(166, 136, 98));
        if (dFlag != 1) {
            origin.add("D");
            dFlag = 1;
            reloadOrgTune();
        }
    }//GEN-LAST:event_dBtnMouseClicked

    private void d_BtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_BtnMouseClicked
        d_Btn.setBackground(new Color(255, 204, 0));
        d_Btn.setForeground(new Color(166, 136, 98));
        if (d_Flag != 1) {
            origin.add("D#");
            d_Flag = 1;
            reloadOrgTune();
        }
    }//GEN-LAST:event_d_BtnMouseClicked

    private void eBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eBtnMouseClicked
        eBtn.setBackground(new Color(255, 204, 0));
        eBtn.setForeground(new Color(166, 136, 98));
        if (eFlag != 1) {
            origin.add("E");
            eFlag = 1;
            reloadOrgTune();
        }
    }//GEN-LAST:event_eBtnMouseClicked

    private void fBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fBtnMouseClicked
        fBtn.setBackground(new Color(255, 204, 0));
        fBtn.setForeground(new Color(166, 136, 98));
        if (fFlag != 1) {
            origin.add("F");
            fFlag = 1;
            reloadOrgTune();
        }
    }//GEN-LAST:event_fBtnMouseClicked

    private void f_BtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f_BtnMouseClicked
        f_Btn.setBackground(new Color(255, 204, 0));
        f_Btn.setForeground(new Color(166, 136, 98));
        if (f_Flag != 1) {
            origin.add("F#");
            f_Flag = 1;
            reloadOrgTune();
        }
    }//GEN-LAST:event_f_BtnMouseClicked

    private void gBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gBtnMouseClicked
        gBtn.setBackground(new Color(255, 204, 0));
        gBtn.setForeground(new Color(166, 136, 98));
        if (gFlag != 1) {
            origin.add("G");
            gFlag = 1;
            reloadOrgTune();
        }
    }//GEN-LAST:event_gBtnMouseClicked

    private void g_BtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_g_BtnMouseClicked
        g_Btn.setBackground(new Color(255, 204, 0));
        g_Btn.setForeground(new Color(166, 136, 98));
        if (g_Flag != 1) {
            origin.add("G#");
            g_Flag = 1;
            reloadOrgTune();
        }
    }//GEN-LAST:event_g_BtnMouseClicked

    private void aBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aBtnMouseClicked
        aBtn.setBackground(new Color(255, 204, 0));
        aBtn.setForeground(new Color(166, 136, 98));
        if (aFlag != 1) {
            origin.add("A");
            aFlag = 1;
            reloadOrgTune();
        }
    }//GEN-LAST:event_aBtnMouseClicked

    private void a_BtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a_BtnMouseClicked
        a_Btn.setBackground(new Color(255, 204, 0));
        a_Btn.setForeground(new Color(166, 136, 98));
        if (a_Flag != 1) {
            origin.add("A#");
            a_Flag = 1;
            reloadOrgTune();
        }
    }//GEN-LAST:event_a_BtnMouseClicked

    private void bBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bBtnMouseClicked
        bBtn.setBackground(new Color(255, 204, 0));
        bBtn.setForeground(new Color(166, 136, 98));
        if (bFlag != 1) {
            origin.add("B");
            bFlag = 1;
            reloadOrgTune();
        }
    }//GEN-LAST:event_bBtnMouseClicked

    /**
     * Clear all the pressed pitches and all the generated matrix and melody to
     * re-enter, re-start.
     *
     * @param evt
     */
    private void mClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mClearActionPerformed
        origin.clear();
        //Clear the JLabel for Clock and Matrix by define new JLabel.
        log = new javax.swing.JLabel();
        matx = new javax.swing.JLabel();

        log.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        log.setForeground(new java.awt.Color(255, 204, 0));
        log.setText("Clock");
        log.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        log.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 51), new java.awt.Color(255, 204, 0), new java.awt.Color(255, 204, 0)));

        matx.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        matx.setForeground(new java.awt.Color(255, 204, 0));
        matx.setText("Matrix");
        matx.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        matx.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), new java.awt.Color(255, 204, 0), new java.awt.Color(255, 204, 0)));

        clockstr = "";
        matrixstr = "";
        logArea.setText(""); //To clear the content of the textarea, using either area.setText("") or area.setText(null).
        try {
            matrix.melodySoundList.clear();
            matrix.originList.clear();
            matrix.wholeList.clear();
            matrix.segments.clear();
        } catch (Exception e) {
        }
        aFlag = 0;
        a_Flag = 0;
        bFlag = 0;
        cFlag = 0;
        c_Flag = 0;
        dFlag = 0;
        d_Flag = 0;
        eFlag = 0;
        fFlag = 0;
        f_Flag = 0;
        gFlag = 0;
        g_Flag = 0;
        aBtn.setBackground(new Color(255, 255, 0));
        aBtn.setForeground(new Color(255, 204, 0));
        a_Btn.setBackground(new Color(255, 255, 0));
        a_Btn.setForeground(new Color(255, 204, 0));
        bBtn.setBackground(new Color(255, 255, 0));
        bBtn.setForeground(new Color(255, 204, 0));
        cBtn.setBackground(new Color(255, 255, 0));
        cBtn.setForeground(new Color(255, 204, 0));
        c_Btn.setBackground(new Color(255, 255, 0));
        c_Btn.setForeground(new Color(255, 204, 0));
        dBtn.setBackground(new Color(255, 255, 0));
        dBtn.setForeground(new Color(255, 204, 0));
        d_Btn.setBackground(new Color(255, 255, 0));
        d_Btn.setForeground(new Color(255, 204, 0));
        eBtn.setBackground(new Color(255, 255, 0));
        eBtn.setForeground(new Color(255, 204, 0));
        fBtn.setBackground(new Color(255, 255, 0));
        fBtn.setForeground(new Color(255, 204, 0));
        f_Btn.setBackground(new Color(255, 255, 0));
        f_Btn.setForeground(new Color(255, 204, 0));
        gBtn.setBackground(new Color(255, 255, 0));
        gBtn.setForeground(new Color(255, 204, 0));
        g_Btn.setBackground(new Color(255, 255, 0));
        g_Btn.setForeground(new Color(255, 204, 0));
        reloadOrgTune();

        logstr = "Original Chromatic Pitch Order:\n";
        logstr += "0  1   2  3  4 5  6  7  8   9 10  11\n";
        logstr += "C C# D D# E F F# G G# A A# B\n";
        mClicked = 0;
        rhyClicked = 0;
        volClicked = 0;
        mPlayClicked = 0;
        rPlayClicked = 0;
        vPlayClicked = 0;
    }//GEN-LAST:event_mClearActionPerformed

    private void mClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mClearMouseClicked

    }//GEN-LAST:event_mClearMouseClicked

    private void melodyBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_melodyBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_melodyBtnMouseClicked

    /**
     * Perform 12-tone Matrix and melody generation on Melody Panel.
     *
     * @param evt
     */
    private void melodyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_melodyBtnActionPerformed
        if (mClicked == 0) {
            if (origin.size() != 12) {
                tip = "Please press all pitch buttons.";
                tipLabel.setText(tip);
                tipFrame.setSize(600, 280);
                tipFrame.setTitle("Tip");
                tipFrame.setVisible(true);
            } else {
                setClock();
                setMatrix();
                log.setText("<html>" + clockstr + "</html>");
                matx.setText("<html>" + matrixstr + "</html>");
                logstr = matrix.Melody_Generation(logstr, firstIndex);
                musicPlayer.setUpPlayerMelody(matrix);
                logstr += "\nWriting MIDI File Success.";
                logArea.append(logstr);
                logFrame.setSize(965, 880);
                logFrame.setVisible(true);
                mClicked = 1;
            }
        } else {
            tip = "Please clear the previous settings to start again.";
            tipLabel.setText(tip);
            tipFrame.setSize(600, 280);
            tipFrame.setTitle("Tip");
            tipFrame.setVisible(true);
        }
    }//GEN-LAST:event_melodyBtnActionPerformed

    /**
     * Perform .mid file playing using the sequencer defined in MusicPlayer.java
     * when clicking on the Play button on Melody Panel.
     *
     * @param evt
     */
    private void mPlayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mPlayMouseClicked
        if (mClicked == 1) {
            if (mPlayClicked % 2 == 0) {
                Image img;
                try {
                    img = ImageIO.read(getClass().getResource("icons8_Stop_48.png"));
                    mPlay.setIcon(new ImageIcon(img));
                    mPlay.setText("Stop");
                } catch (IOException ex) {
                    Logger.getLogger(Home_User.class.getName()).log(Level.SEVERE, null, ex);
                }
                musicPlayer.startMidi("melody.mid");
                mPlayClicked++;
                // The mPlay button change to "Play" when the sequencer musicPlayer.midiPlayer has done playing.
                musicPlayer.midiPlayer.addMetaEventListener(new MetaEventListener() {
                    public void meta(MetaMessage m) {
                        // A message of this type is automatically sent
                        if (m.getType() == 47) //47 - reach the end of the sequence
                        {
                            try {
                                Image img = ImageIO.read(getClass().getResource("icons8_Play_48.png"));
                                mPlay.setIcon(new ImageIcon(img));
                                mPlay.setText("Play");
                                mPlayClicked++;
                            } catch (IOException ex) {
                                Logger.getLogger(Home_User.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            musicPlayer.midiPlayer.stop();
                            musicPlayer.midiPlayer.close();
                        }

                    }
                });

            } else {
                musicPlayer.stop();
                mPlayClicked++;
                Image img;
                try {
                    img = ImageIO.read(getClass().getResource("icons8_Play_48.png"));
                    mPlay.setIcon(new ImageIcon(img));
                    mPlay.setText("Play");
                } catch (IOException ex) {
                    Logger.getLogger(Home_User.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            tip = "Please generate the matrix and melody first (by clicking on the \"Change Melody\" button)";
            tipLabel.setText(tip);
            tipFrame.setSize(1100, 280);
            tipFrame.setTitle("Tip");
            tipFrame.setVisible(true);
        }
    }//GEN-LAST:event_mPlayMouseClicked

    private void mPlayMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mPlayMouseEntered
        mPlay.setFont(new Font("Jokerman", Font.BOLD, 30));
    }//GEN-LAST:event_mPlayMouseEntered

    private void mPlayMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mPlayMouseExited
        mPlay.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
    }//GEN-LAST:event_mPlayMouseExited

    private void mStaveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mStaveMouseEntered
        mStave.setFont(new Font("Jokerman", Font.BOLD, 30));
    }//GEN-LAST:event_mStaveMouseEntered

    private void mStaveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mStaveMouseExited
        mStave.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
    }//GEN-LAST:event_mStaveMouseExited

    private void mSaveMP3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mSaveMP3MouseEntered
        mSaveMP3.setFont(new Font("Jokerman", Font.BOLD, 30));
    }//GEN-LAST:event_mSaveMP3MouseEntered

    private void mSaveMP3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mSaveMP3MouseExited
        mSaveMP3.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
    }//GEN-LAST:event_mSaveMP3MouseExited

    private void rStaveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rStaveMouseEntered
        rStave.setFont(new Font("Jokerman", Font.BOLD, 30));
    }//GEN-LAST:event_rStaveMouseEntered

    private void rStaveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rStaveMouseExited
        rStave.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
    }//GEN-LAST:event_rStaveMouseExited

    private void rPlayMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rPlayMouseEntered
        rPlay.setFont(new Font("Jokerman", Font.BOLD, 30));
    }//GEN-LAST:event_rPlayMouseEntered

    private void rPlayMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rPlayMouseExited
        rPlay.setFont(new Font("Comic Sans MS", Font.BOLD, 30)); // TODO add your handling code here:
    }//GEN-LAST:event_rPlayMouseExited

    private void rSaveMP3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSaveMP3MouseEntered
        rSaveMP3.setFont(new Font("Jokerman", Font.BOLD, 30));
    }//GEN-LAST:event_rSaveMP3MouseEntered

    private void rSaveMP3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSaveMP3MouseExited
        rSaveMP3.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
    }//GEN-LAST:event_rSaveMP3MouseExited

    private void vStaveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vStaveMouseEntered
        vStave.setFont(new Font("Jokerman", Font.BOLD, 30));
    }//GEN-LAST:event_vStaveMouseEntered

    private void vStaveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vStaveMouseExited
        vStave.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
    }//GEN-LAST:event_vStaveMouseExited

    private void vPlayMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vPlayMouseEntered
        vPlay.setFont(new Font("Jokerman", Font.BOLD, 30));
    }//GEN-LAST:event_vPlayMouseEntered

    private void vPlayMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vPlayMouseExited
        vPlay.setFont(new Font("Comic Sans MS", Font.BOLD, 30));// TODO add your handling code here:
    }//GEN-LAST:event_vPlayMouseExited

    private void vSaveMP3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSaveMP3MouseEntered
        vSaveMP3.setFont(new Font("Jokerman", Font.BOLD, 30));
    }//GEN-LAST:event_vSaveMP3MouseEntered

    private void vSaveMP3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSaveMP3MouseExited
        vSaveMP3.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
    }//GEN-LAST:event_vSaveMP3MouseExited

    /**
     * Apply Duration Row Rhythm on Minuet (Bach) on Rhythm Panel.
     *
     * @param evt
     */
    private void minuetDRBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minuetDRBtnActionPerformed
        try {
            int a = Integer.parseInt(dur1.getText());
            int b = Integer.parseInt(dur2.getText());
            int c = Integer.parseInt(dur3.getText());

            if (a >= 1 && a <= 12 && b >= 1 && b <= 12 && c >= 1 && c <= 12) {
                try {
                    matrix.setTrithm(a, b, c);
                    logstr = matrix.Duration_Row(logstr);
                    musicPlayer.setUpPlayerMinuetDurationRow(matrix);
                    logstr += "\nWriting MIDI File Success.";
                    logArea.append(logstr);
                    logFrame.setSize(965, 880);
                    logFrame.setVisible(true);
                    rhyClicked = 1;
                } catch (Exception e2) {
                    tip = "Please generate the melody first.";
                    tipLabel.setText(tip);
                    tipFrame.setSize(800, 280);
                    tipFrame.setTitle("Tip");
                    tipFrame.setVisible(true);
                }
            } else {
                tip = "The duration is 1 to 12, please re-enter.";
                tipLabel.setText(tip);
                tipFrame.setSize(800, 280);
                tipFrame.setTitle("Tip");
                tipFrame.setVisible(true);
            }
        } catch (NumberFormatException e) {
            tip = "Please, do not leave any box blank.";
            tipLabel.setText(tip);
            tipFrame.setSize(800, 280);
            tipFrame.setTitle("Tip");
            tipFrame.setVisible(true);
        }
    }//GEN-LAST:event_minuetDRBtnActionPerformed

    /**
     * Perform .mid file playing using the sequencer defined in MusicPlayer.java
     * when clicking on the Play button on Rhythm Panel.
     *
     * @param evt
     */
    private void rPlayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rPlayMouseClicked
        if (rhyClicked == 0) {
            tip = "Please generate the duration series first (by clicking on any one of the 3 buttons below)";
            tipLabel.setText(tip);
            tipFrame.setSize(1100, 280);
            tipFrame.setTitle("Tip");
            tipFrame.setVisible(true);
        }
        if (rhyClicked == 1) {
            if (rPlayClicked % 2 == 0) {
                rPlayClicked++;
                Image img;
                try {
                    img = ImageIO.read(getClass().getResource("icons8_Stop_48.png"));
                    rPlay.setIcon(new ImageIcon(img));
                    rPlay.setText("Stop");
                } catch (IOException ex) {
                    Logger.getLogger(Home_User.class.getName()).log(Level.SEVERE, null, ex);
                }
                musicPlayer.startMidi("minuetDR.mid");
                // The rPlay button change to "Play" when the sequencer musicPlayer.midiPlayer has done playing.
                musicPlayer.midiPlayer.addMetaEventListener(new MetaEventListener() {
                    public void meta(MetaMessage m) {
                        // A message of this type is automatically sent
                        if (m.getType() == 47) //47 - reach the end of the sequence
                        {
                            try {
                                Image img = ImageIO.read(getClass().getResource("icons8_Play_48.png"));
                                rPlay.setIcon(new ImageIcon(img));
                                rPlay.setText("Play");
                                rPlayClicked++;
                            } catch (IOException ex) {
                                Logger.getLogger(Home_User.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            musicPlayer.midiPlayer.stop();
                            musicPlayer.midiPlayer.close();
                        }

                    }
                });

            } else {
                musicPlayer.stop();
                rPlayClicked++;
                Image img;
                try {
                    img = ImageIO.read(getClass().getResource("icons8_Play_48.png"));
                    rPlay.setIcon(new ImageIcon(img));
                    rPlay.setText("Play");
                } catch (IOException ex) {
                    Logger.getLogger(Home_User.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        if (rhyClicked == 2) {

            if (rPlayClicked % 2 == 0) {
                rPlayClicked++;
                Image img;
                try {
                    img = ImageIO.read(getClass().getResource("icons8_Stop_48.png"));
                    rPlay.setIcon(new ImageIcon(img));
                    rPlay.setText("Stop");
                } catch (IOException ex) {
                    Logger.getLogger(Home_User.class.getName()).log(Level.SEVERE, null, ex);
                }
                musicPlayer.startMidi("minuetTP.mid");
                // The rPlay button change to "Play" when the sequencer musicPlayer.midiPlayer has done playing.
                musicPlayer.midiPlayer.addMetaEventListener(new MetaEventListener() {
                    public void meta(MetaMessage m) {
                        // A message of this type is automatically sent
                        if (m.getType() == 47) //47 - reach the end of the sequence
                        {
                            try {
                                Image img = ImageIO.read(getClass().getResource("icons8_Play_48.png"));
                                rPlay.setIcon(new ImageIcon(img));
                                rPlay.setText("Play");
                                rPlayClicked++;
                            } catch (IOException ex) {
                                Logger.getLogger(Home_User.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            musicPlayer.midiPlayer.stop();
                            musicPlayer.midiPlayer.close();
                        }

                    }
                });

            } else {
                musicPlayer.stop();
                rPlayClicked++;
                Image img;
                try {
                    img = ImageIO.read(getClass().getResource("icons8_Play_48.png"));
                    rPlay.setIcon(new ImageIcon(img));
                    rPlay.setText("Play");
                } catch (IOException ex) {
                    Logger.getLogger(Home_User.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (rhyClicked == 3) {

            if (rPlayClicked % 2 == 0) {
                rPlayClicked++;
                Image img;
                try {
                    img = ImageIO.read(getClass().getResource("icons8_Stop_48.png"));
                    rPlay.setIcon(new ImageIcon(img));
                    rPlay.setText("Stop");
                } catch (IOException ex) {
                    Logger.getLogger(Home_User.class.getName()).log(Level.SEVERE, null, ex);
                }
                musicPlayer.startMidi("melodyTP.mid");
                // The rPlay button change to "Play" when the sequencer musicPlayer.midiPlayer has done playing.
                musicPlayer.midiPlayer.addMetaEventListener(new MetaEventListener() {
                    public void meta(MetaMessage m) {
                        // A message of this type is automatically sent
                        if (m.getType() == 47) //47 - reach the end of the sequence
                        {
                            try {
                                Image img = ImageIO.read(getClass().getResource("icons8_Play_48.png"));
                                rPlay.setIcon(new ImageIcon(img));
                                rPlay.setText("Play");
                                rPlayClicked++;
                            } catch (IOException ex) {
                                Logger.getLogger(Home_User.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            musicPlayer.midiPlayer.stop();
                            musicPlayer.midiPlayer.close();
                        }

                    }
                });

            } else {
                musicPlayer.stop();
                rPlayClicked++;
                Image img;
                try {
                    img = ImageIO.read(getClass().getResource("icons8_Play_48.png"));
                    rPlay.setIcon(new ImageIcon(img));
                    rPlay.setText("Play");
                } catch (IOException ex) {
                    Logger.getLogger(Home_User.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }//GEN-LAST:event_rPlayMouseClicked

    /**
     * Apply Time Point Rhythm on Minuet (Bach) on Rhythm Panel.
     *
     * @param evt
     */
    private void minuetTPBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minuetTPBtnMouseClicked
        try {
            int a = Integer.parseInt(dur1.getText());
            int b = Integer.parseInt(dur2.getText());
            int c = Integer.parseInt(dur3.getText());

            if (a >= 1 && a <= 12 && b >= 1 && b <= 12 && c >= 1 && c <= 12) {
                try {
                    matrix.setTrithm(a, b, c);
                    logstr = matrix.timePointMinuet(logstr);
                    musicPlayer.setUpPlayerMinueTimePoint(matrix);
                    logstr += "\nWrting MIDI FIle Success.";
                    //matrix.constructDurations();
                    logArea.append(logstr);
                    logFrame.setSize(965, 880);
                    logFrame.setVisible(true);
                    rhyClicked = 2;
                } catch (Exception e2) {
                    tip = "Please generate the melody first.";
                    tipLabel.setText(tip);
                    tipFrame.setSize(800, 280);
                    tipFrame.setTitle("Tip");
                    tipFrame.setVisible(true);
                }
            } else {
                tip = "The duration is 1 to 12, please re-enter.";
                tipLabel.setText(tip);
                tipFrame.setSize(800, 280);
                tipFrame.setTitle("Tip");
                tipFrame.setVisible(true);
            }
        } catch (NumberFormatException e) {
            tip = "Please, do not leave any box blank.";
            tipLabel.setText(tip);
            tipFrame.setSize(800, 280);
            tipFrame.setTitle("Tip");
            tipFrame.setVisible(true);
        }
    }//GEN-LAST:event_minuetTPBtnMouseClicked

    /**
     * Perform volume serialism on Minuet(Bach) on Volume Panel.
     *
     * @param evt
     */
    private void minuetVolBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minuetVolBtnMouseClicked

        try {
            int a = Integer.parseInt(vol1.getText());
            int b = Integer.parseInt(vol2.getText());
            int c = Integer.parseInt(vol3.getText());
            if (a >= 0 && a <= 11 && b >= 0 && b <= 11 && c >= 0 && c <= 11) {
                try {
                    matrix.setTriVol(a, b, c);
                    logstr = matrix.constructMinuetVolume(logstr);
                    musicPlayer.setUpPlayerMinuetVolume(matrix);
                    logstr += "\nWriting MIDI File Success.";
                    logArea.append(logstr);
                    logFrame.setSize(965, 880);
                    logFrame.setVisible(true);
                    volClicked = 1;
                } catch (Exception e1) {
                    tip = "Please generate the melody first.";
                    tipLabel.setText(tip);
                    tipFrame.setSize(800, 280);
                    tipFrame.setTitle("Tip");
                    tipFrame.setVisible(true);
                }
            } else {
                tip = "The volume is 0 to 11, please re-enter.";
                tipLabel.setText(tip);
                tipFrame.setSize(800, 280);
                tipFrame.setTitle("Tip");
                tipFrame.setVisible(true);
            }
        } catch (NumberFormatException e2) {
            tip = "Please, do not leave any box blank.";
            tipLabel.setText(tip);
            tipFrame.setSize(800, 280);
            tipFrame.setTitle("Tip");
            tipFrame.setVisible(true);
        }


    }//GEN-LAST:event_minuetVolBtnMouseClicked

    /**
     * Perform .mid file playing using the sequencer defined in MusicPlayer.java
     * when clicking on the Play button on Volume Panel.
     *
     * @param evt
     */
    private void vPlayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vPlayMouseClicked
        if (volClicked == 0) {
            tip = "Please generate the volume series first (by clicking on any one of the 2 buttons below)";
            tipLabel.setText(tip);
            tipFrame.setSize(1100, 280);
            tipFrame.setTitle("Tip");
            tipFrame.setVisible(true);
        }
        if (volClicked == 1) {
            if (vPlayClicked % 2 == 0) {
                vPlayClicked++;
                Image img;
                try {
                    img = ImageIO.read(getClass().getResource("icons8_Stop_48.png"));
                    vPlay.setIcon(new ImageIcon(img));
                    vPlay.setText("Stop");
                } catch (IOException ex) {
                    Logger.getLogger(Home_User.class.getName()).log(Level.SEVERE, null, ex);
                }
                musicPlayer.startMidi("minuetVolume.mid");
                // The vPlay button change to "Play" when the sequencer musicPlayer.midiPlayer has done playing.
                musicPlayer.midiPlayer.addMetaEventListener(new MetaEventListener() {
                    public void meta(MetaMessage m) {
                        // A message of this type is automatically sent
                        if (m.getType() == 47) //47 - reach the end of the sequence
                        {
                            try {
                                Image img = ImageIO.read(getClass().getResource("icons8_Play_48.png"));
                                vPlay.setIcon(new ImageIcon(img));
                                vPlay.setText("Play");
                                vPlayClicked++;
                            } catch (IOException ex) {
                                Logger.getLogger(Home_User.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            musicPlayer.midiPlayer.stop();
                            musicPlayer.midiPlayer.close();
                        }

                    }
                });

            } else {
                musicPlayer.stop();
                vPlayClicked++;
                Image img;
                try {
                    img = ImageIO.read(getClass().getResource("icons8_Play_48.png"));
                    vPlay.setIcon(new ImageIcon(img));
                } catch (IOException ex) {
                    Logger.getLogger(Home_User.class.getName()).log(Level.SEVERE, null, ex);
                }
                vPlay.setText("Play");
            }

        }
        if (volClicked == 2) {
            if (vPlayClicked % 2 == 0) {
                vPlayClicked++;
                Image img;
                try {
                    img = ImageIO.read(getClass().getResource("icons8_Stop_48.png"));
                    vPlay.setIcon(new ImageIcon(img));
                    vPlay.setText("Stop");
                } catch (IOException ex) {
                    Logger.getLogger(Home_User.class.getName()).log(Level.SEVERE, null, ex);
                }
                musicPlayer.startMidi("melodyTPVolume.mid");

                // The vPlay button change to "Play" when the sequencer musicPlayer.midiPlayer has done playing.
                musicPlayer.midiPlayer.addMetaEventListener(new MetaEventListener() {
                    public void meta(MetaMessage m) {
                        // A message of this type is automatically sent
                        if (m.getType() == 47) //47 - reach the end of the sequence
                        {
                            try {
                                Image img = ImageIO.read(getClass().getResource("icons8_Play_48.png"));
                                vPlay.setIcon(new ImageIcon(img));
                                vPlay.setText("Play");
                                vPlayClicked++;
                            } catch (IOException ex) {
                                Logger.getLogger(Home_User.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            musicPlayer.midiPlayer.stop();
                            musicPlayer.midiPlayer.close();
                        }

                    }
                });

            } else {
                musicPlayer.stop();
                vPlayClicked++;
                Image img;
                try {
                    img = ImageIO.read(getClass().getResource("icons8_Play_48.png"));
                    vPlay.setIcon(new ImageIcon(img));
                } catch (IOException ex) {
                    Logger.getLogger(Home_User.class.getName()).log(Level.SEVERE, null, ex);
                }
                vPlay.setText("Play");
            }
        }
    }//GEN-LAST:event_vPlayMouseClicked

    /**
     * Apply Time Point Rhythm on the generated melody on Rhythm Panel
     *
     * @param evt
     */
    private void melodyTPBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_melodyTPBtnMouseClicked
        if (mClicked == 0) {
            tip = "Please generate the matrix and melody first";
            tipLabel.setText(tip);
            tipFrame.setSize(800, 280);
            tipFrame.setTitle("Tip");
            tipFrame.setVisible(true);
        } else {
            try {
                int a = Integer.parseInt(dur1.getText());
                int b = Integer.parseInt(dur2.getText());
                int c = Integer.parseInt(dur3.getText());

                if (a >= 1 && a <= 12 && b >= 1 && b <= 12 && c >= 1 && c <= 12) {
                    try {
                        matrix.setTrithm(a, b, c);
                        logstr = matrix.timePointTwelveTone(logstr);
                        musicPlayer.setUpPlayerTimePoint(matrix);
                        logstr += "\nWriting MIDI File Success.";
                        //matrix.constructDurations();
                        logArea.append(logstr);
                        logFrame.setSize(965, 880);
                        logFrame.setVisible(true);
                        rhyClicked = 3;
                    } catch (Exception e2) {
                        tip = "Please generate the melody first.";
                        tipLabel.setText(tip);
                        tipFrame.setSize(800, 280);
                        tipFrame.setTitle("Tip");
                        tipFrame.setVisible(true);
                    }
                } else {
                    tip = "The duration is 1 to 12, please re-enter.";
                    tipLabel.setText(tip);
                    tipFrame.setSize(800, 280);
                    tipFrame.setTitle("Tip");
                    tipFrame.setVisible(true);
                }
            } catch (NumberFormatException e) {
                tip = "Please, do not leave any box blank.";
                tipLabel.setText(tip);
                tipFrame.setSize(800, 280);
                tipFrame.setTitle("Tip");
                tipFrame.setVisible(true);
            }
        }
    }//GEN-LAST:event_melodyTPBtnMouseClicked

    /**
     * Perform volume serialism to play the melody with Time Point Rhythm on
     * Volume Panel.
     *
     * @param evt
     */
    private void VAndRAndMBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VAndRAndMBtnMouseClicked
        if ((mClicked == 0) || (rhyClicked == 0)) {
            tip = "Please generate the matrix and melody first";
            tipLabel.setText(tip);
            tipFrame.setSize(800, 280);
            tipFrame.setTitle("Tip");
            tipFrame.setVisible(true);
        } else {
            try {
                int a = Integer.parseInt(vol1.getText());
                int b = Integer.parseInt(vol2.getText());
                int c = Integer.parseInt(vol3.getText());
                if (a >= 0 && a <= 11 && b >= 0 && b <= 11 && c >= 0 && c <= 11) {
                    try {
                        matrix.setTriVol(a, b, c);
                        logstr = matrix.constructMelodyTimePointVolume(logstr);
                        musicPlayer.setUpPlayerVolume(matrix);
                        logstr += "\nWriting MIDI File Success.";
                        logArea.append(logstr);
                        logFrame.setSize(965, 880);
                        logFrame.setVisible(true);
                        volClicked = 2;
                    } catch (Exception e2) {
                        tip = "Please generate the melody first.";
                        tipLabel.setText(tip);
                        tipFrame.setSize(800, 280);
                        tipFrame.setTitle("Tip");
                        tipFrame.setVisible(true);
                    }
                } else {
                    tip = "The duration is 0 to 11, please re-enter.";
                    tipLabel.setText(tip);
                    tipFrame.setSize(800, 280);
                    tipFrame.setTitle("Tip");
                    tipFrame.setVisible(true);
                }
            } catch (NumberFormatException e) {
                tip = "Please, do not leave any box blank.";
                tipLabel.setText(tip);
                tipFrame.setSize(800, 280);
                tipFrame.setTitle("Tip");
                tipFrame.setVisible(true);
            }
        }
    }//GEN-LAST:event_VAndRAndMBtnMouseClicked

    /**
     * Perform convert .mid to Sheet Music by calling cmd.exe and perform
     * lilypond function midi2ly on Melody Panel
     *
     * @param evt
     */
    private void mStaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mStaveMouseClicked
        //Generate melody.ly
        //Generate melody.pdf Music sheet
        if (mClicked == 0) {
            tip = "Please click the \"Play\" button first to generate the MIDI file.";
            tipLabel.setText(tip);
            tipFrame.setSize(1080, 280);
            tipFrame.setTitle("Tip");
            tipFrame.setVisible(true);
        } else {
            ProcessBuilder pb1 = new ProcessBuilder();
            pb1.command("cmd.exe", "/c", "midi2ly melody.mid --output melody.ly");
            try {
                pb1.start();
                //Runtime.getRuntime().exec("cmd /c start cmd.exe /K \" midi2ly melody.mid --output melody.ly");
            } catch (Exception e1) {
                tip = "Convert MIDI to Lilypond file goes wrong.";
            }
            try {
                tip = lyFileChange("melody.ly", "melody_sheet.ly", "Serial Melody");
            } catch (IOException ex) {
                tip = "Writing from melody.ly to melody_sheet.ly goes wrong.";
            }
            ProcessBuilder pb2 = new ProcessBuilder();
            pb2.command("cmd.exe", "/c", "lilypond melody_sheet.ly && melody_sheet.pdf");
            try {
                //Runtime.getRuntime().exec("cmd /c start cmd.exe /K \" lilypond melody_sheet.ly && melody_sheet.pdf");
                pb2.start();
                tip = "Stave successfully generated!";
            } catch (Exception e2) {
                tip = "Cannot open the PDF stave file.";
            }

        }
        // tip = "<html>Now you need to delete \"\\midi{}\" at the end of the file. <br/> After saving the file, type $ lilypond melody.ly on the shell to generate your sheet.<br/> You can then open the full PDF file by typing $ melody.pdf on the shell.</html>";
        tipLabel.setText(tip);
        tipFrame.setSize(800, 280);
        tipFrame.setTitle("Tip");
        tipFrame.setVisible(true);
    }//GEN-LAST:event_mStaveMouseClicked

    /**
     * Perform convert .mid to Sheet Music by calling cmd.exe and perform
     * lilypond function midi2ly on Rhythm Panel
     *
     * @param evt
     */
    private void rStaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rStaveMouseClicked
        if (rhyClicked == 0) {
            tip = "Please click the \"Play\" button first to generate the MIDI file.";
            tipLabel.setText(tip);
            tipFrame.setSize(1080, 280);
            tipFrame.setTitle("Tip");
            tipFrame.setVisible(true);
        } else if (rhyClicked == 1) {
            //Generate minuetDR.ly
            //Generate mimuetDR.pdf Music sheet
            ProcessBuilder pb1 = new ProcessBuilder();
            pb1.command("cmd.exe", "/c", "midi2ly minuetDR.mid --output minuetDR.ly");
            try {
                pb1.start();
            } catch (Exception e1) {
                tip = "Convert MIDI to Lilypond file goes wrong.";
            }
            try {
                tip = lyFileChange("minuetDR.ly", "minuetDR_sheet.ly", "Duration Row Minuet");
            } catch (IOException ex) {
                tip = "Writing from minuetDR.ly to minuetDR_sheet.ly goes wrong.";
            }
            ProcessBuilder pb2 = new ProcessBuilder();
            pb2.command("cmd.exe", "/c", "lilypond minuetDR_sheet.ly && minuetDR_sheet.pdf");
            try {
                pb2.start();
                tip = "Stave successfully generated!";
            } catch (Exception e2) {
                tip = "Cannot open the PDF stave file.";
            }
            tipLabel.setText(tip);
            tipFrame.setSize(1080, 280);
            tipFrame.setTitle("Tip");
            tipFrame.setVisible(true);
        } else if (rhyClicked == 2) {
            //Generate minuetTP.ly
            //Generate minuetTP.pdf Music sheet
            ProcessBuilder pb1 = new ProcessBuilder();
            pb1.command("cmd.exe", "/c", "midi2ly minuetTP.mid --output minuetTP.ly");
            try {
                pb1.start();
            } catch (Exception e1) {
                tip = "Convert MIDI to Lilypond file goes wrong.";
            }
            try {
                tip = lyFileChange("minuetTP.ly", "minuetTP_sheet.ly", "Time Point Minuet");
            } catch (IOException ex) {
                tip = "Writing from minuetTP.ly to minuetTP.ly goes wrong.";
            }
            ProcessBuilder pb2 = new ProcessBuilder();
            pb2.command("cmd.exe", "/c", "lilypond minuetTP_sheet.ly && minuetTP_sheet.pdf");
            try {
                pb2.start();
                tip = "Stave successfully generated!";
            } catch (Exception e2) {
                tip = "Cannot open the PDF stave file.";
            }
            tipLabel.setText(tip);
            tipFrame.setSize(1080, 280);
            tipFrame.setTitle("Tip");
            tipFrame.setVisible(true);
        } else if (rhyClicked == 3) {
            //Generate melodyTP.ly
            //Generate melodyTP.pdf Music sheet
            ProcessBuilder pb1 = new ProcessBuilder();
            pb1.command("cmd.exe", "/c", "midi2ly melodyTP.mid --output melodyTP.ly");
            try {
                pb1.start();
            } catch (Exception e1) {
                tip = "Convert MIDI to Lilypond file goes wrong.";
            }
            try {
                tip = lyFileChange("melodyTP.ly", "melodyTP_sheet.ly", "Time Point Serial Melody");
            } catch (IOException ex) {
                tip = "Writing from melodyTP.ly to melodyTP_sheet.ly goes wrong.";
            }
            ProcessBuilder pb2 = new ProcessBuilder();
            pb2.command("cmd.exe", "/c", "lilypond melodyTP_sheet.ly && melodyTP_sheet.pdf");
            try {
                pb2.start();
                tip = "Stave successfully generated!";
            } catch (Exception e2) {
                tip = "Cannot open the PDF stave file.";
            }
            tipLabel.setText(tip);
            tipFrame.setSize(1080, 280);
            tipFrame.setTitle("Tip");
            tipFrame.setVisible(true);
        }
    }//GEN-LAST:event_rStaveMouseClicked

    /**
     * Perform convert .mid to Sheet Music by calling cmd.exe and perform
     * lilypond function midi2ly on Volume Panel
     *
     * @param evt
     */
    private void vStaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vStaveMouseClicked
        if (volClicked == 0) {
            tip = "Please click the \"Play\" button first to generate the MIDI file.";
            tipLabel.setText(tip);
            tipFrame.setSize(1080, 280);
            tipFrame.setTitle("Tip");
            tipFrame.setVisible(true);
        } else if (volClicked == 1) {
            //Generate minuetVolume.ly
            //Generate minuetVolume.pdf Music sheet
            ProcessBuilder pb1 = new ProcessBuilder();
            pb1.command("cmd.exe", "/c", "midi2ly minuetVolume.mid --output minuetVolume.ly");
            try {
                pb1.start();
            } catch (Exception e1) {
                tip = "Convert MIDI to Lilypond file goes wrong.";
            }
            try {
                tip = lyFileChange("minuetVolume.ly", "minuetVolume_sheet.ly", "Serial Volume Minuet");
            } catch (IOException ex) {
                tip = "Writing from minuetVolume.ly to minuetVolume_sheet.ly goes wrong.";
            }
            ProcessBuilder pb2 = new ProcessBuilder();
            pb2.command("cmd.exe", "/c", "lilypond minuetVolume_sheet.ly && minuetVolume_sheet.pdf");
            try {
                pb2.start();
                tip = "Stave successfully generated!";
            } catch (Exception e2) {
                tip = "Cannot open the PDF stave file.";
            }
            tipLabel.setText(tip);
            tipFrame.setSize(1080, 280);
            tipFrame.setTitle("Tip");
            tipFrame.setVisible(true);
        } else if (volClicked == 2) {
            //Generate melodyTPVolume.ly
            //Generate melody.pdf Music sheet
            ProcessBuilder pb1 = new ProcessBuilder();
            pb1.command("cmd.exe", "/c", "midi2ly melodyTPVolume.mid --output melodyTPVolume.ly");
            try {
                pb1.start();
            } catch (Exception e1) {
                tip = "Convert MIDI to Lilypond file goes wrong.";
            }
            try {
                tip = lyFileChange("melodyTPVolume.ly", "melodyTPVolume_sheet.ly", "Serial Volume Time Point Melody");
            } catch (IOException ex) {
                tip = "Writing from melodyTPVolume.ly to melodyTPVolume_sheet.ly goes wrong.";
            }
            ProcessBuilder pb2 = new ProcessBuilder();
            pb2.command("cmd.exe", "/c", "lilypond melodyTPVolume_sheet.ly && melodyTPVolume_sheet.pdf");
            try {
                pb2.start();
                tip = "Stave successfully generated!";
            } catch (Exception e2) {
                tip = "Cannot open the PDF stave file.";
            }
            tipLabel.setText(tip);
            tipFrame.setSize(1080, 280);
            tipFrame.setTitle("Tip");
            tipFrame.setVisible(true);
        }
    }//GEN-LAST:event_vStaveMouseClicked

    /**
     * Perform save to MP3 on Melody Panel
     *
     * @param evt
     */
    private void mSaveMP3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mSaveMP3MouseClicked
        try {
            URI uri = new URI("https://www.zamzar.com/convert/midi-to-mp3/");
            java.awt.Desktop.getDesktop().browse(uri);
            tip = "<html>Now, please open your directory, convert the .mid to MP3.</html>";
        } catch (Exception e) {
            tip = "Oh no! Something is going Wrong ";
            e.printStackTrace();
        }
        tipLabel.setText(tip);
        tipFrame.setSize(1080, 280);
        tipFrame.setTitle("Tip");
        tipFrame.setVisible(true);
    }//GEN-LAST:event_mSaveMP3MouseClicked

    /**
     * Perform save to MP3 on rhythm panel
     *
     * @param evt
     */
    private void rSaveMP3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSaveMP3MouseClicked
        try {
            URI uri = new URI("https://www.zamzar.com/convert/midi-to-mp3/");
            java.awt.Desktop.getDesktop().browse(uri);
            tip = "<html>Now, please open your directory, convert the .mid to MP3.</html>";
        } catch (Exception e) {
            tip = "Oh no! Something is going Wrong ";
            e.printStackTrace();
        }
        tipLabel.setText(tip);
        tipFrame.setSize(1080, 280);
        tipFrame.setTitle("Tip");
        tipFrame.setVisible(true);
    }//GEN-LAST:event_rSaveMP3MouseClicked

    /**
     * Perform save to MP3 on Volume Panel
     *
     * @param evt
     */
    private void vSaveMP3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vSaveMP3MouseClicked
        try {
            URI uri = new URI("https://www.zamzar.com/convert/midi-to-mp3/");
            java.awt.Desktop.getDesktop().browse(uri);
            tip = "<html>Now, please open your directory, convert the .mid to MP3.</html>";
        } catch (Exception e) {
            tip = "Oh no! Something is going Wrong ";
            e.printStackTrace();
        }
        tipLabel.setText(tip);
        tipFrame.setSize(1080, 280);
        tipFrame.setTitle("Tip");
        tipFrame.setVisible(true);
    }//GEN-LAST:event_vSaveMP3MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home_User().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton VAndRAndMBtn;
    private javax.swing.JLabel aBtn;
    private javax.swing.JLabel a_Btn;
    private javax.swing.JLabel bBtn;
    private javax.swing.JLabel cBtn;
    private javax.swing.JLabel c_Btn;
    private javax.swing.JLabel dBtn;
    private javax.swing.JLabel d_Btn;
    private javax.swing.JTextField dur1;
    private javax.swing.JTextField dur2;
    private javax.swing.JTextField dur3;
    private javax.swing.JLabel eBtn;
    private javax.swing.JLabel exitBtn;
    private javax.swing.JLabel fBtn;
    private javax.swing.JLabel f_Btn;
    private javax.swing.JLabel gBtn;
    private javax.swing.JLabel g_Btn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel log;
    private javax.swing.JTextArea logArea;
    private javax.swing.JFrame logFrame;
    private javax.swing.JPanel logPanel;
    private javax.swing.JButton mBtn;
    private javax.swing.JButton mClear;
    private javax.swing.JPanel mPanel;
    private javax.swing.JLabel mPlay;
    private javax.swing.JLabel mSaveMP3;
    private javax.swing.JLabel mStave;
    private javax.swing.JLabel matx;
    private javax.swing.JButton melodyBtn;
    private javax.swing.JButton melodyTPBtn;
    private javax.swing.JButton minuetDRBtn;
    private javax.swing.JButton minuetTPBtn;
    private javax.swing.JButton minuetVolBtn;
    private javax.swing.JLabel orgTune;
    private javax.swing.JLabel output;
    private javax.swing.JButton rBtn;
    private javax.swing.JPanel rPanel;
    private javax.swing.JLabel rPlay;
    private javax.swing.JLabel rSaveMP3;
    private javax.swing.JLabel rStave;
    private javax.swing.JFrame tipFrame;
    private javax.swing.JLabel tipLabel;
    private javax.swing.JPanel tipPanel;
    private javax.swing.JButton vBtn;
    private javax.swing.JPanel vPanel;
    private javax.swing.JLabel vPlay;
    private javax.swing.JLabel vSaveMP3;
    private javax.swing.JLabel vStave;
    private javax.swing.JTextField vol1;
    private javax.swing.JTextField vol2;
    private javax.swing.JTextField vol3;
    private javax.swing.JButton wBtn;
    private javax.swing.JPanel welcome;
    // End of variables declaration//GEN-END:variables
}
