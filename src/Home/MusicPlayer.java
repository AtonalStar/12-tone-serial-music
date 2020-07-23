/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author pearl
 */
public class MusicPlayer {    
  //  Sequencer sequencer;
    /**
     * Sequencer to  play .mid File from existing file.
     */
    Sequencer midiPlayer;
    
    //Prepare to use MidiSystem.write(Sequence, int, File) to write MIDI File for each track.
    /**
     * Sequence generated from "melodySoundList" - melody series using Son Clave Rhythm.
     */
    Sequence melodySequence;
    /**
     * The MIDI file converted from melodySequence track, [use MidiSystem.write(Sequence, int, File) to write].
     */
    File melodyMidi;
    /**
     * Sequence generated from "minuetDurationRow" - Minuet melody using Duration Row Rhythm.
     */
    Sequence minuetDRSequence;
    /**
     * The MIDI file converted from minuetDRSequence track, [use MidiSystem.write(Sequence, int, File) to write].
     */
    File minuetDRMidi;
    /**
     * Sequence generated from "minuetTimePoint" - Minuet melody using Time Point Rhythm.
     */
    Sequence minuetTPSequence;
    /**
     * The MIDI file converted from minuetTPSequence track, [use MidiSystem.write(Sequence, int, File) to write].
     */
    File minuetTPMidi;
    /**
     * Sequence generated from "timePointList" - the 12-tone melody using Time Point Rhythm.
     */
    Sequence melodyTPSequence;
    /**
     * The MIDI file converted from melodyTPSequence track, [use MidiSystem.write(Sequence, int, File) to write].
     */
    File melodyTPMidi;
    /**
     * Sequence generated from Minuet with volume serialism.
     */
    Sequence minuetVSequence;
    /**
     * The MIDI file converted from minuetVSequence track, [use MidiSystem.write(Sequence, int, File) to write].
     */
    File minuetVMidi;
    /**
     * Sequence generated from 12-tone melody using Time Point Rhythm with volume serialism.
     */
    Sequence melodyVSequence;
    /**
     * The MIDI file converted from melodyVSequence track, [use MidiSystem.write(Sequence, int, File) to write].
     */
    File melodyVMidi;

    /**
     * Constructor for MusicPlayer
     */
    public MusicPlayer() {

    }
    
    /**
     * Stop the running sequencer midiPlayer.
     */
    public void stop(){
        this.midiPlayer.stop();
    }
    
    /**
     * Set up sequencer midiPlayer to start playing backgrand music.
     * @param panelIndex the index passed from Home_User class, to indicate when to play and stop the background music.
     */
    public void setUpBGM(int panelIndex){
       if(panelIndex==0){
        try {
	         File midiFile = new File("minuet.mid");
	         Sequence song = MidiSystem.getSequence(midiFile);
	         midiPlayer = MidiSystem.getSequencer();
	         midiPlayer.open();
	         midiPlayer.setSequence(song);
	         midiPlayer.setLoopCount(0); // repeat 0 times (play once)
	         midiPlayer.start();
	      } catch (MidiUnavailableException e) {
	         e.printStackTrace();
	      } catch (InvalidMidiDataException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
        }else if(midiPlayer.isOpen()){
            midiPlayer.stop();
            midiPlayer.close();
        }else{}
    }
 /**
  * Set up the sequence for the 12-tone melody with Son Clave Rhythm, as well as write Midi file to MidiFile folder, and use midiPlayer Sequencer to play it.
  * @param m The Matrix to pass message like "melodySoundList".
  */
    public void setUpPlayerMelody(Matrix m) {
        try {
            // Creating a sequence. 
            melodySequence = new Sequence(Sequence.PPQ, 2);

            // PPQ(Pulse per ticks) is used to specify timing 
            // type and 4 is the timing resolution. 
            // Creating a track on our sequence upon which 
            // MIDI events would be placed 
            Track track = melodySequence.createTrack();
            int time = 0;
            int i;
            // Adding some events to the track 
            // Adding some events to the track 
            for (i = 0; i < m.melodySoundList.size(); i++) {

                if (i % 7 == 0) {
                    int count = 1;
                    if (m.melodySoundList.get(i).size() >= 1) {

                        while (count <= m.melodySoundList.get(i).size()) {
                           if(count==1){
                            // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.melodySoundList.get(i).get(count - 1) , 100, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.melodySoundList.get(i).get(count - 1) , 100, time + 3));
                           }else{
                               // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.melodySoundList.get(i).get(count - 1) , 60, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.melodySoundList.get(i).get(count - 1) , 60, time + 3));
                           }
                            count++;
                        }

                        time += 3;
                    } else {
                        // Add Note On event 
                        track.add(makeEvent(144, count, 0 , 0, time));

                        // Add Note Off event 
                        track.add(makeEvent(128, count, 0 , 0, time + 3));
                        time += 3;
                    }
                } else if (i % 7 == 1) {
                    int count = 1;
                    if (m.melodySoundList.get(i).size() >= 1) {

                        while (count <= m.melodySoundList.get(i).size()) {
                            if(count==1){
                            // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.melodySoundList.get(i).get(count - 1) , 100, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.melodySoundList.get(i).get(count - 1) , 100, time + 1));
                           }else{
                               // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.melodySoundList.get(i).get(count - 1) , 60, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.melodySoundList.get(i).get(count - 1) , 60, time + 1));
                           }
                            count++;
                        }

                        time++;
                    } else {
                        // Add Note On event 
                        track.add(makeEvent(144, count, 0 , 0, time));

                        // Add Note Off event 
                        track.add(makeEvent(128, count, 0 , 0, time + 1));
                        time++;
                    }
                } else if (i % 7 == 2 || i % 7 == 3 || i % 7 == 4 || i % 7 == 5) {
                    int count = 1;
                    if (m.melodySoundList.get(i).size() >= 1) {

                        while (count <= m.melodySoundList.get(i).size()) {
                           if(count==1){
                            // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.melodySoundList.get(i).get(count - 1) , 100, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.melodySoundList.get(i).get(count - 1) , 100, time + 2));
                           }else{
                               // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.melodySoundList.get(i).get(count - 1) , 60, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.melodySoundList.get(i).get(count - 1) , 60, time + 2));
                           }
                            count++;
                        }

                        time += 2;
                    } else {
                        // Add Note On event 
                        track.add(makeEvent(144, count, 0 , 0, time));

                        // Add Note Off event 
                        track.add(makeEvent(128, count, 0 , 0, time + 2));
                        time += 2;
                    }
                } else {
                    int count = 1;
                    if (m.melodySoundList.get(i).size() >= 1) {

                        while (count <= m.melodySoundList.get(i).size()) {
                           if(count==1){
                            // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.melodySoundList.get(i).get(count - 1) , 100, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.melodySoundList.get(i).get(count - 1) , 100, time + 4));
                           }else{
                               // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.melodySoundList.get(i).get(count - 1) , 60, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.melodySoundList.get(i).get(count - 1) , 60, time + 4));
                           }
                            count++;
                        }

                        time += 4;
                    } else {
                        // Add Note On event 
                        track.add(makeEvent(144, count, 0 , 0, time));

                        // Add Note Off event 
                        track.add(makeEvent(128, count, 0 , 0, time + 4));
                        time += 4;
                    }
                }
            }

            //Write to File
            melodyMidi = new File("melody.mid");
            try {
                MidiSystem.write(melodySequence, 1, melodyMidi);
            } catch (Exception e) {
                System.out.println("Exception caught " + e.toString());
            }
            System.out.println("Writing file end ");
            //Play midi File
            ////startMidi("melody.mid");     // start the midi player
        } catch (Exception ex) {
            ex.printStackTrace();
        }     
    }

    /**
     * Set up the sequence for Minuet with Duration Row Rhythm, as well as write Midi file to MidiFile folder, and use midiPlayer Sequencer to play it.
     * @param m The Matrix to pass message like "minuateDurationRow". 
     */
    public void setUpPlayerMinuetDurationRow(Matrix m) {
        try {
//
//            // A static method of MidiSystem that returns 
//            // a sequencer instance. 
//            sequencer = MidiSystem.getSequencer();
//            sequencer.open();

            // Creating a sequence. 
            minuetDRSequence = new Sequence(Sequence.PPQ, 4);

            // PPQ(Pulse per ticks) is used to specify timing 
            // type and 4 is the timing resolution. 
            // Creating a track on our sequence upon which 
            // MIDI events would be placed 
            Track track = minuetDRSequence.createTrack();
            int time = 0;
            //int durIndex = 0;
            // Adding some events to the track 
            for (int i = 0; i < m.minuetDurationRow.size(); i++) {
                
                if( m.minuetDurationRow.get(i).size()>0){   
                // Add Note On event 
                track.add(makeEvent(144, 1, (int) m.minuetDurationRow.get(i).get(0) , 100, time));

                // Add Note Off event 
                track.add(makeEvent(128, 1, (int) m.minuetDurationRow.get(i).get(0) , 100, time + m.minuetDurationArray[i]));
                }else{
                     // Add Note On event 
                track.add(makeEvent(144, 1, 0, 0, time));

                    // Add Note Off event 
                    track.add(makeEvent(128, 1, 0, 0, time + m.minuetDurationArray[i]));
                }
                time += m.minuetDurationArray[i];

            }
            //Write to File
            minuetDRMidi = new File("minuetDR.mid");
            try {
                MidiSystem.write(minuetDRSequence, 1, minuetDRMidi);
            } catch (Exception e) {
                System.out.println("Exception caught " + e.toString());
            }
            System.out.println("Writing file end ");
            //Play midi File
            //startMidi("minuetDR.mid");     // start the midi player
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Set up the sequence for Minuet with Time Point Rhythm, as well as write Midi file to MidiFile folder, and use midiPlayer Sequencer to play it.
    * @param m The Matrix to pass message like "minuetTimePoint".
     */
    public void setUpPlayerMinueTimePoint(Matrix m) {
        try {

//            // A static method of MidiSystem that returns 
//            // a sequencer instance. 
//            sequencer = MidiSystem.getSequencer();
//            sequencer.open();

            // Creating a sequence. 
            minuetTPSequence = new Sequence(Sequence.PPQ, 4);

            // PPQ(Pulse per ticks) is used to specify timing 
            // type and 4 is the timing resolution. 
            // Creating a track on our sequence upon which 
            // MIDI events would be placed 
            Track track = minuetTPSequence.createTrack();
            int time = 0;

            // Adding some events to the track 
            for (int i = 0; i < m.minuetTimePoint.size(); i++) {
                if (m.minuetTimePoint.get(i).size() > 0) {
                    // Add Note On event 
                    track.add(makeEvent(144, 1, (int) m.minuetTimePoint.get(i).get(0) , 100, time));

                    // Add Note Off event 
                    track.add(makeEvent(128, 1, (int) m.minuetTimePoint.get(i).get(0) , 100, time + m.minuetTPDurations.get(i)));
                } else {
                    // Add Note On event 
                    track.add(makeEvent(144, 1, 0, 0, time));

                    // Add Note Off event 
                    track.add(makeEvent(128, 1, 0, 0, time + m.minuetTPDurations.get(i)));
                }
                time += m.minuetTPDurations.get(i);
            }

            //Write to File
            minuetTPMidi = new File("minuetTP.mid");
            try {
                MidiSystem.write(minuetTPSequence, 1, minuetTPMidi);
            } catch (Exception e) {
                System.out.println("Exception caught " + e.toString());
            }
            System.out.println("Writing file end ");
            //Play midi File
            //startMidi("minuetTP.mid");     // start the midi player

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        
    }

    /**
     * Set up the sequence for the 12-tone melody with Time Point Rhythm, as well as write Midi file to MidiFile folder, and use midiPlayer Sequencer to play it.
     * @param m The Matrix to pass message like "timePointList".
     */
    //Player for time point rhythm change
    public void setUpPlayerTimePoint(Matrix m) {
        try {
            // Creating a sequence. 
            melodyTPSequence = new Sequence(Sequence.PPQ, 4);

            // PPQ(Pulse per ticks) is used to specify timing 
            // type and 4 is the timing resolution. 
            // Creating a track on our sequence upon which 
            // MIDI events would be placed 
            Track track = melodyTPSequence.createTrack();
            int time = 0;

            // Adding some events to the track 
            int index = 0; //index of durationRowList
            for (int i = 0; i < m.tpDurations.size(); i++) {
                if (m.timePointList.get(index).size() != 0) {
                    int count = 1; //channel number
                    while (count <= m.timePointList.get(index).size()) {
                        if (count == 1) {
                            // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.timePointList.get(index).get(count - 1) , 100, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.timePointList.get(index).get(count - 1) , 100, time + m.tpDurations.get(i)));
                        } else {
                            // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.timePointList.get(index).get(count - 1) , 60, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.timePointList.get(index).get(count - 1) , 60, time + m.tpDurations.get(i)));
                        }
                        count++;

                    }
                } else {
                    track.add(makeEvent(144, 1, 0 , 0, time));

                    // Add Note Off event 
                    track.add(makeEvent(128, 1, 0 , 0, time + m.tpDurations.get(i)));
                }
                time += m.tpDurations.get(i);
                index++;
            }

            //Write to File
            melodyTPMidi = new File("melodyTP.mid");
            try {
                MidiSystem.write(melodyTPSequence, 1, melodyTPMidi);
            } catch (Exception e) {
                System.out.println("Exception caught " + e.toString());
            }
            System.out.println("Writing file end ");
            //Play midi File
            //startMidi("melodyTP.mid");     // start the midi player

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Set up the sequence for Minuet with serial volume, as well as write Midi file to MidiFile folder, and use midiPlayer Sequencer to play it.
     * @param m The Matrix to pass message like "minuetAllVolume".
     */
    public void setUpPlayerMinuetVolume(Matrix m) {
        try {
            // Creating a sequence. 
            minuetVSequence = new Sequence(Sequence.PPQ, 2);

            // PPQ(Pulse per ticks) is used to specify timing 
            // type and 4 is the timing resolution. 
            // Creating a track on our sequence upon which 
            // MIDI events would be placed 
            Track track = minuetVSequence.createTrack();
            int time = 0;
            int[] dur = {4,2,2,2,2,4,4,4,4,2,2,2,2,4,4,4,4,2,2,2,2,4,2,2,2,2,4,2,2,2,2,12,4,2,2,2,2,4,4,4,4,2,2,2,2,4,4,4,4,2,2,2,2,4,2,2,2,2,4,2,2,2,2,12};
            //For standard MIDI volume setting (1 - 127), 1 - ppppp, 8 - pppp, 20 - ppp, 31 - pp, 42 - p, 53 - mp, 64 - mf, 80 - f, 96 - ff, 112 - fff, 120 - ffff, 127 - fffff.
            int count = m.minuetAllVolume.length;
            int[] minuetV = new int[count];
            for(int i=0; i<count; i++){
                switch(m.minuetAllVolume[i]){
                    case 0:
                           minuetV[i] = 1;
                           break;
                    case 1:
                           minuetV[i] = 8;
                           break;
                    case 2:
                           minuetV[i] = 20;
                           break;
                    case 3:
                           minuetV[i] = 31;
                           break;
                    case 4:
                           minuetV[i] = 42;
                           break;
                    case 5:
                           minuetV[i] = 53;
                           break;
                    case 6:
                           minuetV[i] = 64;
                           break;
                    case 7:
                           minuetV[i] = 80;
                           break;
                    case 8:
                           minuetV[i] = 96;
                           break;
                    case 9:
                           minuetV[i] = 112;
                           break;
                    case 10:
                           minuetV[i] = 120;
                           break;
                    case 11:
                           minuetV[i] = 127;
                           break;
                }
            }
            // Adding some events to the track 
            for (int i = 0; i < 64; i++) {

                // Add Note On event 
                track.add(makeEvent(144, 1, (int) m.minuet.get(i).get(0) , minuetV[i], time));

                // Add Note Off event 
                track.add(makeEvent(128, 1, (int) m.minuet.get(i).get(0) , minuetV[i], time + dur[i]));

                time += dur[i];
            }

            //Write to File
            minuetVMidi = new File("minuetVolume.mid");
            try {
                MidiSystem.write(minuetVSequence, 1, minuetVMidi);
            } catch (Exception e) {
                System.out.println("Exception caught " + e.toString());
            }
            System.out.println("Writing file end ");

            //Play midi File
            //startMidi("minuetVolume.mid");     // start the midi player

//
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Set up the sequence for the 12-tone melody with Time Point Rhythm and serial volume, as well as write Midi file to MidiFile folder, and use midiPlayer Sequencer to play it.
    * @param m The Matrix to pass message like "allVolume".
     */
    //Player for volume alternation
    public void setUpPlayerVolume(Matrix m) {
        try {
            // Creating a sequence. 
            melodyVSequence = new Sequence(Sequence.PPQ, 4);

            // PPQ(Pulse per ticks) is used to specify timing 
            // type and 4 is the timing resolution. 
            // Creating a track on our sequence upon which 
            // MIDI events would be placed 
            Track track = melodyVSequence.createTrack();
            int time = 0;
            
            int songlength = 0;
            if (m.tpDurations.size() > m.allVolume.length) {
                songlength = m.allVolume.length;
            } else {
                songlength = m.tpDurations.size();
            }

            int[] melodyV = new int[songlength];
            for(int i=0; i<songlength; i++){
                switch(m.allVolume[i]){
                    case 0:
                           melodyV[i] = 1;
                           break;
                    case 1:
                            melodyV[i] = 8;
                           break;
                    case 2:
                            melodyV[i] = 20;
                           break;
                    case 3:
                            melodyV[i] = 31;
                           break;
                    case 4:
                            melodyV[i] = 42;
                           break;
                    case 5:
                            melodyV[i] = 53;
                           break;
                    case 6:
                            melodyV[i] = 64;
                           break;
                    case 7:
                            melodyV[i] = 80;
                           break;
                    case 8:
                           melodyV[i] = 96;
                           break;
                    case 9:
                            melodyV[i] = 112;
                           break;
                    case 10:
                            melodyV[i] = 120;
                           break;
                    case 11:
                            melodyV[i] = 127;
                           break;
                }
            }
 
             // Adding some events to the track 
            int index = 0; //index of durationRowList
            for (int i = 0; i < songlength; i++) {
                if (m.timePointList.get(index).size() != 0) {
                    int count = 1; //channel number
                    while (count <= m.timePointList.get(index).size()) {
                        if (count == 1) {
                            // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.timePointList.get(index).get(count - 1) ,  melodyV[i], time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.timePointList.get(index).get(count - 1) , melodyV[i], time + m.tpDurations.get(i)));
                        } else {
                            // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.timePointList.get(index).get(count - 1) , melodyV[i]/2, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.timePointList.get(index).get(count - 1) , melodyV[i]/2, time + m.tpDurations.get(i)));
                        }
                        count++;

                    }
                } else {
                    track.add(makeEvent(144, 1, 0 , 0, time));

                    // Add Note Off event 
                    track.add(makeEvent(128, 1, 0 , 0, time + m.tpDurations.get(i)));
                }
                time += m.tpDurations.get(i);
                    index++;
            }

        //Write to File
        melodyVMidi = new File("melodyTPVolume.mid");
	try {
		MidiSystem.write(melodyVSequence,1,melodyVMidi);
	}catch(Exception e){
		System.out.println("Exception caught " + e.toString());
	} 
	System.out.println("Writing file end "); 
        
        //Play midi File
            //startMidi("melodyTPVolume.mid");     // start the midi player

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        
    }

    /**
     * Method to set the playing of each pitches
     * @param command  Midi command: 144 - Note On; 128 - Note Off; 192 - Change default instrument...
     * @param channel  Midi supports upto 16 different channels. We can send off a midi event to any of those channels which are later synchronized by the sequencer.
     * @param note  The note to be played. From 1 to 127, Middle C (C4) is 60.
     * @param velocity  The volume of the note. From 1 to 127.
     * @param tick  The tick (time) when the command is executed.
     * @return MidiEvent  to be added into the track.
     */
    public MidiEvent makeEvent(int command, int channel, int note, int velocity, int tick) {
        MidiEvent event = null;

        try {
            // ShortMessage stores a note as command type, channel, 
            // instrument it has to be played on and its speed. 
            ShortMessage a = new ShortMessage();
            a.setMessage(command, channel, note, velocity);

            // A midi event is comprised of a short message(representing 
            // a note) and the tick at which that note has to be played 
            event = new MidiEvent(a, tick);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return event;
    }
    
    /**
     * Set up the sequencer midiPlayer to play a midi file once.
     * @param midFilename  the midi file to be played.
     */
    public void startMidi(String midFilename) {
        try {
            File midiFile = new File(midFilename);
            Sequence song = MidiSystem.getSequence(midiFile);
            midiPlayer = MidiSystem.getSequencer();
            midiPlayer.open();
            midiPlayer.setSequence(song);
            midiPlayer.setTempoInBPM(200);
            midiPlayer.setLoopCount(0);
            midiPlayer.start();
            } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
