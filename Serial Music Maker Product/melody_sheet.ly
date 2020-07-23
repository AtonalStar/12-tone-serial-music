\version "2.14.0"

\header {
title = \markup { "Serial Melody" }
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
  \voiceThree
  gis'4. f8 r4 cis' 
  | % 2
  r4 fis' b,,2 
  | % 3
  a'4. c,,8 r4 f' 
  | % 4
  r4 ais b'2 
  | % 5
  cis,,4. e,8 r4 d 
  | % 6
  r4 a' fis'2 
  | % 7
  f,4. dis'8 r4 gis, 
  | % 8
  r4 cis, fis''2 
  | % 9
  ais,,4. f''8 r4 c,, 
  | % 10
  r4 d' a2 
  | % 11
  gis4. dis''8 r4 c 
  | % 12
  r4 fis, a'2 
  | % 13
  a,,4. c'8 r4 e,, 
  | % 14
  r4 fis e''2 
  | % 15
  b,4. fis8 r4 g' 
  | % 16
  r4 c, gis2 
  | % 17
  cis,4. fis8 r4 f 
  | % 18
  r4 c a''2 
  | % 19
  gis'4. dis8 r4 c,, 
  | % 20
  r4 g' fis''2 
  | % 21
  b,4. a'8 r4 a, 
  | % 22
  r4 g f,2 
  | % 23
  b''4. fis,,8 r4 e' 
  | % 24
  r4 b' g,2 
  | % 25
  dis'4. cis'8 r4 b 
  | % 26
  r4 e, g,2 
  | % 27
  a''4. gis8 r4 dis, 
  | % 28
  r4 ais' cis,,2 
  | % 29
  b'''4. e,8 r4 a 
  | % 30
  r4 d,,, c'2 
  | % 31
  b''4. dis,,8 r4 e' 
  | % 32
  r4 a b2 
  | % 33
  g,,4. ais8 r4 dis 
  | % 34
  r4 gis, cis2 
  | % 35
  fis4. b'8 r4 c,, 
  | % 36
  r4 d, a'''2 
  | % 37
  
}

trackAchannelC = \relative c {
  \voiceTwo
  dis''4. c,8 r1 a'2 
  | % 3
  d,,4. r8*7 e4 fis'2 
  | % 5
  r4*5 b,4 cis2 
  | % 7
  ais'4. r8*7 dis,4 b''2 
  | % 9
  r2. g4 
  | % 10
  r8*11 ais8 r4 g,, 
  | % 12
  r4 b d'2 
  | % 13
  d,,4. f8 r4 b' 
  | % 14
  r4 gis' d,2 
  | % 15
  r4. cis8 r2. f4 r2 
  | % 17
  dis,4. b'8 r2. g''4 r8*7 ais,,8 r2. cis,4 r8*7 d'8 r4 d, 
  | % 22
  r4 c ais'2 
  | % 23
  r4. gis8 r4 d' 
  | % 24
  r4 fis' c2 
  | % 25
  gis4. dis'8 r2. ais,4 d,2 
  | % 27
  r4*5 f4 fis'2 
  | % 29
  a4. r8*7 g,4 f2 
  | % 31
  fis4. cis''8 r4 d, 
  | % 32
  r2 fis 
  | % 33
  c4. r8*9 dis2 
  | % 35
  r4. e,8 r4 g' 
  | % 36
  
}

trackAchannelD = \relative c {
  \voiceFour
  ais''4. g8 r1 d,2 
  | % 3
  g''4. r8*9 gis,2 
  | % 5
  r1. g2 
  | % 7
  r4*5 gis4 e,2 
  | % 9
  r8*19 f8 r4 cis 
  | % 12
  r2 e 
  | % 13
  g4. ais'8 r2. dis,4 a'2 
  | % 15
  r4*5 ais'4 r2 
  | % 17
  gis,,4. e''8 r2. d,4 r8*7 f'8 r8*15 e8 r1 e,,2 
  | % 23
  r4. dis8 r4 a' 
  | % 24
  r4 cis f2 
  | % 25
  r4. gis,8 r2. f''4 r4*7 c,,4 r2 
  | % 29
  d4. r8*9 ais''2 
  | % 31
  gis4. r8*9 cis,2 
  | % 33
  f,4. r8*9 gis2 
  | % 35
  r4. ais''8 
}

trackAchannelE = \relative c {
  \voiceOne
  r1. e''2 
  | % 3
  r1. dis2 
  | % 5
  r1. c,,2 
  | % 7
  r4*29 cis'4 r4*7 dis'4 r8*7 ais8 r8*47 cis,8 r1 ais''2 
  | % 25
  r4. fis,8 r2. c'4 r4*7 g4 r1*2 e,2 
  | % 31
  r8*35 f'8 
}

trackA = <<
  \context Voice = voiceA \trackAchannelB
  \context Voice = voiceB \trackAchannelC
  \context Voice = voiceC \trackAchannelD
  \context Voice = voiceD \trackAchannelE
>>


\score {
  <<
    \context Staff=trackA \trackA
  >>
  \layout {}
}
