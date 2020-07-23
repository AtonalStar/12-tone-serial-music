% Lily was here -- automatically converted by D:\Music\LilyPond\usr\bin\midi2ly.py from melodyTPVolume.mid
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
  r8. ais'16 b dis' d c g, fis' d' ais, c,4 
  | % 2
  r8. f''4. g,, dis''16 
  | % 3
  fis b, c ais' r4. a 
  | % 4
  r16*13 b,,16 g ais16*7 b fis8. 
  | % 6
  r8. cis16 gis''' a c,,, g' cis a' ais c f a4 r8. gis4. b 
  | % 8
  c,16 ais d b' cis,, r4. g r16*13 ais'16 e 
  | % 10
  gis16*7 d, f''8. r8. g16 b gis,, dis f'' c dis,, b' ais c' 
  dis,4 r8. d4. fis ais16 b fis, a d, r4. c r16*13 f''16 
  | % 15
  g e,16*7 gis c,8. r8. f'16 a,, gis cis, e ais'' c,,, ais'' 
  d,, 
}

trackAchannelC = \relative c {
  \voiceFour
  r8. e16 fis' a g' f,, cis'' b gis' r2 c,,,4. d' gis,16 
  | % 3
  r16 e'' f, dis r4. e' 
  | % 4
  r16*13 fis16 r16 e,,16*7 r8*7 dis'16 d, f'' r16 fis d, f' r16 c,, 
  dis4 r8. cis4. e'' 
  | % 8
  f,,16 dis' r16 fis' r16*27 b16 
  | % 10
  dis,16*7 g,, r4. cis16 e' r16 ais, r16 g, gis e r16 f gis'4 
  r8. a'4. cis, e16 r16 cis,, r16 g' r8*13 cis'16 a'16*7 dis,, 
  r4. c'16 dis r16 fis, r8. dis,16 a' 
}

trackAchannelD = \relative c {
  \voiceOne
  r4 cis'16 r4 e'16 dis, r8*7 a'4. cis16 
  | % 3
  r16 ais' r16 gis r16*25 cis,,,16 r16*23 g'16 r8 b16 gis'' r8 g,,16 
  r16*7 fis'4. ais' 
  | % 8
  r16 gis, r16*29 fis16 
  | % 10
  a16*7 c r4. fis,16 a, r16 f' r16 d' cis r8 ais'16 r16*7 e,,4. 
  g' r8 gis,16 r4*7 fis16 d16*7 ais'' r4. g16 r8 b'16 r8. gis,,16 
  e'' 
}

trackAchannelE = \relative c {
  \voiceTwo
  r4 gis'16 r4 a'16 r16*25 d,,16 r4*13 e'16 dis' r8 d,16 r8*25 cis,16 
  | % 10
  r16*21 d''16 r16 c,, r16 a''' fis r8*5 b,,4. r2 dis'16 r4*7 b16 
  r16*7 f r4. d'16 r16*7 b16 
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
