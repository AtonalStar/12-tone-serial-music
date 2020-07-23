\version "2.14.0"

\header {
title = \markup { "Duration Row Minuet" }
}
\layout {
  \context {
    \Voice
    \remove "Note_heads_engraver"
    \consists "Completion_heads_engraver"
    \remove "Rest_engraver"
    \consists "Completion_rest_engraver"
  }
}

trackAchannelB = \relative c {
  g'''8. c,4 d16*5 e4. f16*7 g2 r8. c,16*9 c8*5 a'16*11 r4. f2. 
  g16 a8 r16*9 
  | % 7
  b8. c4 c,16*5 c4. f16*7 g2 r8. f16*9 e8*5 d16*11 r4. e2. f16 
  e8 r16*9 
  | % 13
  d8. c4 b16*5 c4. d16*7 e2 r8. c16*9 d8*5 g16*11 r4. c,2. d16 
  e8 r16*9 
  | % 19
  f8. g4 c,16*5 c4. a'16*7 f2 r8. g16*9 a8*5 b16*11 r4. c2. c,16 
  c8 r16*9 
  | % 25
  f8. g4 f16*5 e4. d16*7 e2 r8. f16*9 e8*5 d16*11 r4. c2. d16 
  e8 r16*9 
  | % 31
  d8. c4 b16*5 c4. 
}

trackA = <<
  \context Voice = voiceA \trackAchannelB
>>


\score {
  <<
    \context Staff=trackA \trackA
  >>
  \layout {}
}
