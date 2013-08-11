*-----------------------------------------------------------
* Title      :
* Written by :
* Date       :
* Description:
*-----------------------------------------------------------
    ORG    $1000
START:                  ; first instruction of program

* Put program code here
    move.b  #200,d0
    move.w  #500,d1
    move.l  #999999,d2
    
    move.w #60,a0
    move.l #466,a2
    
    move.b d2,d0
    move.w d2,d1
    move.l d2,d3
    SIMHALT             ; halt simulator

* Put variables and constants here
    END    START        ; last line of source

*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
