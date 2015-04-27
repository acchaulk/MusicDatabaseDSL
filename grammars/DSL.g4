/*
 * Adam Chaulk
 * 
 * This is the grammar for my DSL.
 * supports queries like:
 * 
 * artist.similarTo("Nirvana")
 * artist.sameDecade("1990") & artist.sameGenre("Rock") & sfadsf | ....
 * song.similarTo("Happy")
 * 
 * Optional? 
 * artist.similarTo("Nirvana" & "Foo Fighters")
 * Parens ((this & that) | something)
 * 
 * Typechecking...
 * (IN GRAMMAR) sameDecade is an Integer of length 4 & <= 2015 or 2 Ints + s ie 50s or 80s
 * sameGenre is a String, ie Rock or Funk 
 * artist.SimilarTo(Nirvana) & artist.sameGenre(Rock)  --> no
 * 
 */

grammar DSL;

dslText: createPlaylist EOF; // for future work in order to create a playlist (spotify) from the query provided
createPlaylist: CREATE ID queryList;

queryList:	queryList AND queryList
			| queryList OR queryList
			| query;

query: type DOT function LPAR queryString RPAR;

queryString: YEAR_STRING | STRING_NONUM | STRING;
function: SAMEDECADE | SIMILARTO | SAMEGENRE;
type: ARTIST | ALBUM | SONG;

// Separators and operators
AND: '&';
OR: '|';
LPAR: '(';
RPAR: ')';
DOT: '.';

// Reserved words
ARTIST: 'artist';
SONG: 'song';
ALBUM: 'album';
SIMILARTO: 'similarTo';
SAMEDECADE: 'sameDecade';
SAMEGENRE: 'sameGenre';
CREATE: 'create';


YEAR : (DIGIT DIGIT DIGIT DIGIT ) | (DIGIT DIGIT 's');
YEAR_STRING : '"' YEAR '"';
STRING_NONUM :   '"' ( ESCAPED_QUOTE | ~('\n'|'\r' | [0-9]))*? '"';
STRING :   '"' ( ESCAPED_QUOTE | ~('\n'|'\r') )*? '"';

INTEGER : DIGIT+ ;
WS :[ \t\r\n]+ -> skip ;

fragment ESCAPED_QUOTE : '\\"';
fragment LETTER :[A-Za-z] ;
fragment DIGIT :[0-9] ;

//COMMENT :'#' .*? ('\n'|EOF) -> skip;
//IDSTRING : '"' ID '"';
ID : LETTER (LETTER|DIGIT|'_'|'?')* ;
 

