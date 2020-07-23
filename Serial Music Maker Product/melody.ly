% Lily was here -- automatically converted by D:\Music\LilyPond\usr\bin\midi2ly.py from melody.mid
\version "2.14.0"

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
  ais'4. b8 r4 dis' 
  | % 2
  r4 d c2 
  | % 3
  g,4. fis'8 r4 d' 
  | % 4
  r4 ais, c,2 
  | % 5
  f''4. g,,8 r4 dis'' 
  | % 6
  r4 fis b,2 
  | % 7
  c4. ais'8 r4 a 
  | % 8
  r4 b,, g2 
  | % 9
  ais4. b8 r4 fis 
  | % 10
  r4 cis gis'''2 
  | % 11
  a4. c,,,8 r4 g' 
  | % 12
  r4 cis a'2 
  | % 13
  ais4. c8 r4 f 
  | % 14
  r4 a gis2 
  | % 15
  b4. c,8 r4 ais 
  | % 16
  r4 d b'2 
  | % 17
  cis,,4. g8 r4 ais' 
  | % 18
  r4 e gis2 
  | % 19
  d,4. f''8 r4 g 
  | % 20
  r4 b gis,,2 
  | % 21
  dis4. f''8 r4 c 
  | % 22
  r4 dis,, b'2 
  | % 23
  ais4. c'8 r4 dis, 
  | % 24
  r4 d fis2 
  | % 25
  ais4. b8 r4 fis, 
  | % 26
  r4 a d,2 
  | % 27
  c4. f''8 r4 g 
  | % 28
  r4 e, gis2 
  | % 29
  c,4. f'8 r4 a,, 
  | % 30
  r4 gis cis,2 
  | % 31
  e4. ais''8 r4 c,,, 
  | % 32
  r4 ais'' d,,2 
  | % 33
  fis4. 
}

trackAchannelC = \relative c {
  \voiceFour
  e fis'8 r4 a 
  | % 2
  r4 g' f,,2 
  | % 3
  cis''4. b8 r4 gis' 
  | % 4
  r4 f,, r2 
  | % 5
  c4. d'8 r4 gis, 
  | % 6
  r2 e'' 
  | % 7
  f,4. dis8 r4 e' 
  | % 8
  r4 fis r2 
  | % 9
  e,,4. r8*9 dis'2 
  | % 11
  d,4. f''8 r2. fis4 d,2 
  | % 13
  f'4. r4. c,,4 
  | % 14
  r4 dis cis2 
  | % 15
  e''4. f,,8 r4 dis' 
  | % 16
  r4 a' fis'2 
  | % 17
  r4*5 b4 dis,2 
  | % 19
  g,,4. r4. cis4 
  | % 20
  r4 e' r2 
  | % 21
  ais,4. r4. g,4 
  | % 22
  r4 gis e2 
  | % 23
  r4. f8 r4 gis' 
  | % 24
  r4 a' cis,2 
  | % 25
  e4. r4. cis,,4 
  | % 26
  r2 g' 
  | % 27
  r2. cis'4 
  | % 28
  r4 a' dis,,2 
  | % 29
  r4. c'8 r4 dis 
  | % 30
  r2 fis, 
  | % 31
  r2. f4 
  | % 32
  r4 dis, a'2 
  | % 33
  cis'4. 
}

trackAchannelD = \relative c {
  \voiceOne
  r4. cis'8 r8*15 e'8 r4 dis, 
  | % 4
  r8*11 a'8 r4 cis 
  | % 6
  r2 ais' 
  | % 7
  r4. gis8 r2. cis,,,4 r2*5 g'4. r8*7 b4 gis''2 
  | % 13
  r2. g,,4 
  | % 14
  r2 fis' 
  | % 15
  ais'4. r4. gis,4 
  | % 16
  r4 e, r4*7 fis'4 a2 
  | % 19
  c4. r4. fis,4 
  | % 20
  r4 a, r2 
  | % 21
  f'4. r4. d'4 
  | % 22
  r4 cis r8*7 ais'8 r2. e,,4 g'2 
  | % 25
  r2. gis,4 
  | % 26
  r4*7 fis4 
  | % 28
  r4 d ais''2 
  | % 29
  r4. g8 r1 b'2 
  | % 31
  r4*5 gis,,4 e''2 
  | % 33
  g,,4. 
}

trackAchannelE = \relative c {
  \voiceTwo
  r4. gis'8 r8*15 a'8 r8*31 d,,8 r4*19 e'4 dis'2 
  | % 13
  r2. d,4 
  | % 14
  r4*17 cis,4 r4*7 d''4 r2 
  | % 21
  c,,4. r4. a'''4 
  | % 22
  r4 fis r4*7 b,,4 r4*5 dis'4 
  | % 26
  r4*7 b4 
  | % 28
  r2 f 
  | % 29
  r4. d'8 r1*3 b2 
  | % 33
  
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
  \midi {}
}
