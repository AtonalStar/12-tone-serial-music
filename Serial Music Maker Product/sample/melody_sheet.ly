% Lily was here -- automatically converted by D:\Music\LilyPond\usr\bin\midi2ly.py from melody.mid
\version "2.14.0"

\header{
title = \markup{ "Serial Melody"}
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
  gis''4. dis,8 r4 g' 
  | % 2
  r4 fis e,2 
  | % 3
  d''4. f8 r4 cis 
  | % 4
  r4 d, e,2 
  | % 5
  d'4. b'8 r4 cis, 
  | % 6
  r4 c, f''2 
  | % 7
  gis4. b8 r4 d,,, 
  | % 8
  r4 cis ais''2 
  | % 9
  gis,4. f''8 r4 c 
  | % 10
  r4 cis, a'''2 
  | % 11
  f,,4. ais8 r4 fis'' 
  | % 12
  r4 c e2 
  | % 13
  b'4. d,,,8 r4 a' 
  | % 14
  r4 e' b'2 
  | % 15
  fis,4. g8 r4 f' 
  | % 16
  r4 b' a,2 
  | % 17
  g4. c8 r4 fis 
  | % 18
  r4 gis, ais2 
  | % 19
  f,4. gis''8 r4 c, 
  | % 20
  r4 g' b,2 
  | % 21
  d,4. f,8 r4 gis 
  | % 22
  r4 fis' a'2 
  | % 23
  d,4. e,,8 r4 b' 
  | % 24
  r4 c gis''2 
  | % 25
  b,,4. g''8 r4 gis, 
  | % 26
  r4 dis' f2 
  | % 27
  gis,4. c,8 r4 g'' 
  | % 28
  r4 e, f2 
  | % 29
  cis'4. fis'8 r4 d 
  | % 30
  r4 e d,2 
  | % 31
  a'4. cis,8 r4 g' 
  | % 32
  r4 dis, gis''2 
  | % 33
  b,,4. e'8 r4 c, 
  | % 34
  r4 dis' 
}

trackAchannelC = \relative c {
  \voiceOne
  r4. ais'''8 r4 cis,, 
  | % 2
  r4 b a'2 
  | % 3
  r4. ais,8 r4 fis' 
  | % 4
  r4 a, b2 
  | % 5
  a''4. fis,8 r4 g 
  | % 6
  r2 ais, 
  | % 7
  r4. e''8 r4 g 
  | % 8
  r4 gis, f''2 
  | % 9
  dis,,4. r4. g4 
  | % 10
  r4 fis'' d2 
  | % 11
  r4. dis,8 r2. g''4 r2*5 cis,4. c,8 r4 ais' 
  | % 16
  r4 e' d,,2 
  | % 17
  r2. cis''4 
  | % 18
  r4 dis r8*7 dis8 r2. cis,,4 e''2 
  | % 21
  r4. ais,8 r4 cis 
  | % 22
  r4 c,, e''2 
  | % 23
  a,,4. r4. fis'4 
  | % 24
  r4 f, r2 
  | % 25
  e'4. c,8 r2. ais'''4 r2 
  | % 27
  dis,,4. r4. cis4 
  | % 28
  r4 a'' ais,2 
  | % 29
  r4. c,8 r4 a'' 
  | % 30
  r4 b,, r2 
  | % 31
  e,4. r4. c4 
  | % 32
  r8*11 a''8 r4 fis' 
  | % 34
  r4 ais, 
}

trackAchannelD = \relative c {
  \voiceTwo
  r4. f''8 r8*15 dis,,8 r4 c' 
  | % 4
  r1 
  | % 5
  e,4. r8*9 dis2 
  | % 7
  r4. a'8 r4 c 
  | % 8
  r4 dis r2 
  | % 9
  ais4. r8*7 b''4 r8*7 gis,,8 r2. d''4 r4*13 dis,4 
  | % 16
  r8*27 ais'8 r2. fis'4 a2 
  | % 21
  r4. dis8 r2. g,4 b,2 
  | % 23
  r2. cis4 
  | % 24
  r4 ais' r2 
  | % 25
  a4. fis8 r1. ais'4. r4. fis4 
  | % 28
  r4 d,, dis''2 
  | % 29
  r4. g,,8 r1. b''4. r4. f4 
  | % 32
  r8*11 d,8 r4 cis 
  | % 34
  r4 f 
}

trackAchannelE = \relative c {
  \voiceFour
  r4. c'8 r8*15 gis'8 r4 g, 
  | % 4
  r4*15 fis'4 
  | % 8
  r4*9 e,4 r8*7 cis'8 r2. a''4 r4*13 gis4 
  | % 16
  r8*27 f8 r4*11 d,,4 r4*5 g'4 
  | % 24
  r4 dis, r2 
  | % 25
  d'4. cis,8 r1. f'4. r4. b,,4 
  | % 28
  r2 gis 
  | % 29
  r1*2 fis''4. r4. ais4 
  | % 32
  r8*11 g,8 r4 gis, 
  | % 34
  
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
