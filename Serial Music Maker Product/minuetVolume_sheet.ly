\version "2.14.0"

\header {
title = \markup { "Serial Volume Minuet" }
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
  g'''2 c,4 d 
  | % 2
  e f g2 
  | % 3
  c, c 
  | % 4
  a' f4 g 
  | % 5
  a b c2 
  | % 6
  c, c 
  | % 7
  f g4 f 
  | % 8
  e d e2 
  | % 9
  f4 e d c 
  | % 10
  b2 c4 d 
  | % 11
  e c d1. g2 c,4 d 
  | % 14
  e f g2 
  | % 15
  c, c 
  | % 16
  a' f4 g 
  | % 17
  a b c2 
  | % 18
  c, c 
  | % 19
  f g4 f 
  | % 20
  e d e2 
  | % 21
  f4 e d c 
  | % 22
  d2 e4 d 
  | % 23
  c b c1. 
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
