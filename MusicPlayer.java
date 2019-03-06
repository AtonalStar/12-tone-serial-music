package Home;

import java.util.*;
import java.math.*;
import javax.sound.midi.*;

public class MusicPlayer {

    int firstIndex;
    Sequencer sequencer;

    public MusicPlayer() {

    }
    
    public void stop(){
       this.sequencer.stop();            
    }

    //Player for simple melody change
    public void setUpPlayerMelody(Matrix m) {
        try {

            // A static method of MidiSystem that returns 
            // a sequencer instance. 
            sequencer = MidiSystem.getSequencer();
            sequencer.open();

            // Creating a sequence. 
            Sequence sequence = new Sequence(Sequence.PPQ, 2);

            // PPQ(Pulse per ticks) is used to specify timing 
            // type and 4 is the timing resolution. 
            // Creating a track on our sequence upon which 
            // MIDI events would be placed 
            Track track = sequence.createTrack();
            int time = 0;
            // Adding some events to the track 
            // Adding some events to the track 
            for (int i = 0; i < m.melodySoundList.size(); i++) {

                if (i % 7 == 0) {
                    int count = 1;
                    if (m.melodySoundList.get(i).size() >= 1) {

                        while (count <= m.melodySoundList.get(i).size()) {
                           if(count==1){
                            // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.melodySoundList.get(i).get(count - 1) + firstIndex, 100, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.melodySoundList.get(i).get(count - 1) + firstIndex, 100, time + 3));
                           }else{
                               // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.melodySoundList.get(i).get(count - 1) + firstIndex, 60, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.melodySoundList.get(i).get(count - 1) + firstIndex, 60, time + 3));
                           }
                            count++;
                        }

                        time += 3;
                    } else {
                        // Add Note On event 
                        track.add(makeEvent(144, count, 0 + firstIndex, 0, time));

                        // Add Note Off event 
                        track.add(makeEvent(128, count, 0 + firstIndex, 0, time + 3));
                        time += 3;
                    }
                } else if (i % 7 == 1) {
                    int count = 1;
                    if (m.melodySoundList.get(i).size() >= 1) {

                        while (count <= m.melodySoundList.get(i).size()) {
                            if(count==1){
                            // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.melodySoundList.get(i).get(count - 1) + firstIndex, 100, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.melodySoundList.get(i).get(count - 1) + firstIndex, 100, time + 1));
                           }else{
                               // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.melodySoundList.get(i).get(count - 1) + firstIndex, 60, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.melodySoundList.get(i).get(count - 1) + firstIndex, 60, time + 1));
                           }
                            count++;
                        }

                        time++;
                    } else {
                        // Add Note On event 
                        track.add(makeEvent(144, count, 0 + firstIndex, 0, time));

                        // Add Note Off event 
                        track.add(makeEvent(128, count, 0 + firstIndex, 0, time + 1));
                        time++;
                    }
                } else if (i % 7 == 2 || i % 7 == 3 || i % 7 == 4 || i % 7 == 5) {
                    int count = 1;
                    if (m.melodySoundList.get(i).size() >= 1) {

                        while (count <= m.melodySoundList.get(i).size()) {
                           if(count==1){
                            // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.melodySoundList.get(i).get(count - 1) + firstIndex, 100, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.melodySoundList.get(i).get(count - 1) + firstIndex, 100, time + 2));
                           }else{
                               // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.melodySoundList.get(i).get(count - 1) + firstIndex, 60, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.melodySoundList.get(i).get(count - 1) + firstIndex, 60, time + 2));
                           }
                            count++;
                        }

                        time += 2;
                    } else {
                        // Add Note On event 
                        track.add(makeEvent(144, count, 0 + firstIndex, 0, time));

                        // Add Note Off event 
                        track.add(makeEvent(128, count, 0 + firstIndex, 0, time + 2));
                        time += 2;
                    }
                } else {
                    int count = 1;
                    if (m.melodySoundList.get(i).size() >= 1) {

                        while (count <= m.melodySoundList.get(i).size()) {
                           if(count==1){
                            // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.melodySoundList.get(i).get(count - 1) + firstIndex, 100, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.melodySoundList.get(i).get(count - 1) + firstIndex, 100, time + 4));
                           }else{
                               // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.melodySoundList.get(i).get(count - 1) + firstIndex, 60, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.melodySoundList.get(i).get(count - 1) + firstIndex, 60, time + 4));
                           }
                            count++;
                        }

                        time += 4;
                    } else {
                        // Add Note On event 
                        track.add(makeEvent(144, count, 0 + firstIndex, 0, time));

                        // Add Note Off event 
                        track.add(makeEvent(128, count, 0 + firstIndex, 0, time + 4));
                        time += 4;
                    }
                }

            // Setting our sequence so that the sequencer can 
            // run it on synthesizer 
            sequencer.setSequence(sequence);

            // Specifies the beat rate in beats per minute. 
            sequencer.setTempoInBPM(200);

            // Sequencer starts to play notes 
            sequencer.start();

            // Exit the program when sequencer has stopped playing. 
            if (!sequencer.isRunning()) {
                sequencer.close();
                System.exit(0);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //Player for Minuet duration row rhythm change
    public void setUpPlayerMinuetDurationRow(Matrix m) {
        try {

            // A static method of MidiSystem that returns 
            // a sequencer instance. 
            sequencer = MidiSystem.getSequencer();
            sequencer.open();

            // Creating a sequence. 
            Sequence sequence = new Sequence(Sequence.PPQ, 2);

            // PPQ(Pulse per ticks) is used to specify timing 
            // type and 4 is the timing resolution. 
            // Creating a track on our sequence upon which 
            // MIDI events would be placed 
            Track track = sequence.createTrack();
            int time = 0;
            //int durIndex = 0;
            // Adding some events to the track 
            for (int i = 0; i < m.minuetDurationRow.size(); i++) {
                
                if( m.minuetDurationRow.get(i).size()>0){   
                // Add Note On event 
                track.add(makeEvent(144, 1, (int) m.minuetDurationRow.get(i).get(0) + firstIndex, 100, time));

                // Add Note Off event 
                track.add(makeEvent(128, 1, (int) m.minuetDurationRow.get(i).get(0) + firstIndex, 100, time + m.minuetDurationArray[i]));
                }else{
                     // Add Note On event 
                track.add(makeEvent(144, 1, 0, 0, time));

                // Add Note Off event 
                track.add(makeEvent(128, 1, 0, 0, time + m.minuetDurationArray[i]));
                }
                time += m.minuetDurationArray[i];
                
            }

            // Setting our sequence so that the sequencer can 
            // run it on synthesizer 
            sequencer.setSequence(sequence);

            // Specifies the beat rate in beats per minute. 
            sequencer.setTempoInBPM(200);

            // Sequencer starts to play notes 
            sequencer.start();

            // Exit the program when sequencer has stopped playing. 
            if (!sequencer.isRunning()) {
                sequencer.close();
                System.exit(0);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
     //Player for Minuet time point rhythm change
    public void setUpPlayerMinueTimePoint(Matrix m) {
        try {

            // A static method of MidiSystem that returns 
            // a sequencer instance. 
            sequencer = MidiSystem.getSequencer();
            sequencer.open();

            // Creating a sequence. 
            Sequence sequence = new Sequence(Sequence.PPQ, 2);

            // PPQ(Pulse per ticks) is used to specify timing 
            // type and 4 is the timing resolution. 
            // Creating a track on our sequence upon which 
            // MIDI events would be placed 
            Track track = sequence.createTrack();
            int time = 0;

            // Adding some events to the track 
            for (int i = 0; i < m.minuetTimePoint.size(); i++) {
                if( m.minuetTimePoint.get(i).size()>0){   
                // Add Note On event 
                track.add(makeEvent(144, 1, (int) m.minuetTimePoint.get(i).get(0) + firstIndex, 100, time));

                // Add Note Off event 
                track.add(makeEvent(128, 1, (int) m.minuetTimePoint.get(i).get(0) + firstIndex, 100, time + m.minuetTPDurations.get(i)));
                }else{
                     // Add Note On event 
                track.add(makeEvent(144, 1, 0, 0, time));

                // Add Note Off event 
                track.add(makeEvent(128, 1, 0, 0, time + m.minuetTPDurations.get(i)));
                }
                time += m.minuetTPDurations.get(i);
            }

            // Setting our sequence so that the sequencer can 
            // run it on synthesizer 
            sequencer.setSequence(sequence);

            // Specifies the beat rate in beats per minute. 
            sequencer.setTempoInBPM(200);

            // Sequencer starts to play notes 
            sequencer.start();

            // Exit the program when sequencer has stopped playing. 
            if (!sequencer.isRunning()) {
                sequencer.close();
                System.exit(0);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //Player for time point rhythm change
    public void setUpPlayerTimePoint(Matrix m) {
        try {

            // A static method of MidiSystem that returns 
            // a sequencer instance. 
            sequencer = MidiSystem.getSequencer();
            sequencer.open();

            // Creating a sequence. 
            Sequence sequence = new Sequence(Sequence.PPQ, 2);

            // PPQ(Pulse per ticks) is used to specify timing 
            // type and 4 is the timing resolution. 
            // Creating a track on our sequence upon which 
            // MIDI events would be placed 
            Track track = sequence.createTrack();
            int time = 0;

            // Adding some events to the track 
            int index = 0; //index of durationRowList
            for (int i = 0; i < m.tpDurations.size(); i++) {
                if (m.timePointList.get(index).size() != 0) {
                    int count = 1; //channel number
                    while (count <= m.timePointList.get(index).size()) {
                        if (count == 1) {
                            // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.timePointList.get(index).get(count - 1) + firstIndex, 100, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.timePointList.get(index).get(count - 1) + firstIndex, 100, time + m.tpDurations.get(i)));
                        } else {
                            // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.timePointList.get(index).get(count - 1) + firstIndex, 60, time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.timePointList.get(index).get(count - 1) + firstIndex, 60, time + m.tpDurations.get(i)));
                        }
                        count++;

                    }
                } else {
                    track.add(makeEvent(144, 1, 0 + firstIndex, 0, time));

                    // Add Note Off event 
                    track.add(makeEvent(128, 1, 0 + firstIndex, 0, time + m.tpDurations.get(i)));
                }
                time += m.tpDurations.get(i);
                index++;
            }


            // Setting our sequence so that the sequencer can 
            // run it on synthesizer 
            sequencer.setSequence(sequence);

            // Specifies the beat rate in beats per minute. 
            sequencer.setTempoInBPM(200);

            // Sequencer starts to play notes 
            sequencer.start();

            // Exit the program when sequencer has stopped playing. 
            if (!sequencer.isRunning()) {
                sequencer.close();
                System.exit(0);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
     //Player for Minuet volume change
    public void setUpPlayerMinuetVolume(Matrix m) {
        try {

            // A static method of MidiSystem that returns 
            // a sequencer instance. 
            sequencer = MidiSystem.getSequencer();
            sequencer.open();

            // Creating a sequence. 
            Sequence sequence = new Sequence(Sequence.PPQ, 2);

            // PPQ(Pulse per ticks) is used to specify timing 
            // type and 4 is the timing resolution. 
            // Creating a track on our sequence upon which 
            // MIDI events would be placed 
            Track track = sequence.createTrack();
            int time = 0;
            int[] dur = {2,1,1,1,1,2,2,2,2,1,1,1,1,2,2,2,2,1,1,1,1,2,1,1,1,1,2,1,1,1,1,6,2,1,1,1,1,2,2,2,2,1,1,1,1,2,2,2,2,1,1,1,1,2,1,1,1,1,2,1,1,1,1,6};
            
            // Adding some events to the track 
            for (int i = 0; i < 64; i++) {

                // Add Note On event 
                track.add(makeEvent(144, 1, (int) m.minuet.get(i).get(0) + firstIndex, m.minuetAllVolume[i], time));

                // Add Note Off event 
                track.add(makeEvent(128, 1, (int) m.minuet.get(i).get(0) + firstIndex, m.minuetAllVolume[i], time + dur[i]));

                time += dur[i];
            }

            // Setting our sequence so that the sequencer can 
            // run it on synthesizer 
            sequencer.setSequence(sequence);

            // Specifies the beat rate in beats per minute. 
            sequencer.setTempoInBPM(200);

            // Sequencer starts to play notes 
            sequencer.start();

            // Exit the program when sequencer has stopped playing. 
            if (!sequencer.isRunning()) {
                sequencer.close();
                System.exit(0);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //Player for volume alternation
    public void setUpPlayerVolume(Matrix m) {
        try {

            // A static method of MidiSystem that returns 
            // a sequencer instance. 
            sequencer = MidiSystem.getSequencer();
            sequencer.open();

            // Creating a sequence. 
            Sequence sequence = new Sequence(Sequence.PPQ, 2);

            // PPQ(Pulse per ticks) is used to specify timing 
            // type and 4 is the timing resolution. 
            // Creating a track on our sequence upon which 
            // MIDI events would be placed 
            Track track = sequence.createTrack();
            int time = 0;
            
            int songlength = 0;
            if (m.tpDurations.size() > m.allVolume.length) {
                songlength = m.allVolume.length;
            } else {
                songlength = m.tpDurations.size();
            }
 
 
             // Adding some events to the track 
            int index = 0; //index of durationRowList
            for (int i = 0; i < songlength; i++) {
                if (m.timePointList.get(index).size() != 0) {
                    int count = 1; //channel number
                    while (count <= m.timePointList.get(index).size()) {
                        if (count == 1) {
                            // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.timePointList.get(index).get(count - 1) + firstIndex, 5 * (m.allVolume[i] + 1), time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.timePointList.get(index).get(count - 1) + firstIndex, 5 * (m.allVolume[i] + 1), time + m.tpDurations.get(i)));
                        } else {
                            // Add Note On event 
                            track.add(makeEvent(144, count, (int) m.timePointList.get(index).get(count - 1) + firstIndex, 2 * (m.allVolume[i] + 1), time));

                            // Add Note Off event 
                            track.add(makeEvent(128, count, (int) m.timePointList.get(index).get(count - 1) + firstIndex, 2 * (m.allVolume[i] + 1), time + m.tpDurations.get(i)));
                        }
                        count++;

                    }
                } else {
                    track.add(makeEvent(144, 1, 0 + firstIndex, 0, time));

                    // Add Note Off event 
                    track.add(makeEvent(128, 1, 0 + firstIndex, 0, time + m.tpDurations.get(i)));
                }
                time += m.tpDurations.get(i);
                    index++;
            }

            // Setting our sequence so that the sequencer can 
            // run it on synthesizer 
            sequencer.setSequence(sequence);

            // Specifies the beat rate in beats per minute. 
            sequencer.setTempoInBPM(200);

            // Sequencer starts to play notes 
            sequencer.start();

            while (true) {

                // Exit the program when sequencer has stopped playing. 
                if (!sequencer.isRunning()) {
                    sequencer.close();
                    System.exit(1);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //method to set the playing of each pitches
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

}
