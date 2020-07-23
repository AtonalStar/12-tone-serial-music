\version "2.14.0"

\header {
title = \markup { "Time Point Minuet" }
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
  r8. g'''16 c, d e f r8*5 g4. c, c16 a' 
  | % 3
  r16*13 f4. r16*13 
  | % 5
  g16 a b16*7 r16*9 c16 c, c f g f r8*5 e4. d e16 f 
  | % 8
  r16*13 e4. r16*13 
  | % 10
  d16 c b16*7 r16*9 c16 d e c d g r8*5 c,4. d e16 f 
  | % 13
  r16*13 g4. r16*13 
  | % 15
  c,16 c a'16*7 r16*9 f16 g a b c c, r8*5 c4. f g16 f 
  | % 18
  r16*13 e4. r16*13 
  | % 20
  d16 e f16*7 r16*9 e16 d c d e d r8*5 c4. b c16 
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
